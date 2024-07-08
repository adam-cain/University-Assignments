//
//  PlacesViewController.swift
//  My Favourite Places
//
//  Created by Adam Cain on 13/12/2022.
//

import UIKit
import CoreData

var currentPlace = -1
var centerMapState = false
var places : [Place] = []

class PlacesViewController: UITableViewController {
    
    @IBOutlet var table: UITableView!
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    func getPlaceData(){
        places = []
        let context = appDelegate.persistentContainer.viewContext
        let request = NSFetchRequest<NSFetchRequestResult>(entityName: "Place")
        request.returnsObjectsAsFaults = false
        do{
            let results = try context.fetch(request)
            results.forEach { result in
                places.append(result as! Place)
            }
        } catch{
            print("Couldnt fetch requests")
        }
    }
    
    override func viewDidAppear(_ animated: Bool) {
        getPlaceData()
        currentPlace = -1
        table.reloadData()
    }
    
    override func viewDidLoad() {
        let context = self.appDelegate.persistentContainer.viewContext
        let ashtonBuilding = NSEntityDescription.insertNewObject(forEntityName: "Place", into: context) as! Place
        ashtonBuilding.name = "Ashton Building"
        ashtonBuilding.lat = 53.406566
        ashtonBuilding.lon = -2.966531
        do{
            try context.save()
        }catch{
            print("Couldnt save new place")
        }
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return places.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        var content = UIListContentConfiguration.cell()
        content.text = places[indexPath.row].name
        cell.contentConfiguration = content
        return cell
    }
    
    override func tableView(_ tableView: UITableView, editingStyleForRowAt indexPath: IndexPath) -> UITableViewCell.EditingStyle {
        return UITableViewCell.EditingStyle.delete
    }

    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        let context = appDelegate.persistentContainer.viewContext
        if (editingStyle == .delete) {
            context.delete(places[indexPath.row])
            do{
                try context.save()
            }catch{
                print("Coudlnt delete a place")
            }
        }
        getPlaceData()
        table.reloadData()
    }
    
    // MARK: - Navigation
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        currentPlace = indexPath.row
        performSegue(withIdentifier: "toMap", sender: nil)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if sender.debugDescription != "nil"{
            centerMapState = true
        }
    }
}
