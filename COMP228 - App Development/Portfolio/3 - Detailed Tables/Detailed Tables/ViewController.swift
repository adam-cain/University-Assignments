//
//  ViewController.swift
//  Detailed Tables
//
//  Created by Adam Cain on 13/12/2022.
//

import UIKit

class ViewController: UIViewController, UITableViewDataSource, UITableViewDelegate{
    
    var staff =
    [("Phil","A1.20","phil@liverpool.ac.uk"),("Terry","A2.18","trp@liverpool.ac.uk"),("Valli","A2.12","V.Tamma@liverpool.ac.uk"),("Boris","A1.15","Konev@liverpool.ac.uk")]
    
    
    @IBOutlet weak var table: UITableView!
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        staff.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        var content = UIListContentConfiguration.cell()
        content.text = staff[indexPath.row].0
        cell.contentConfiguration = content
        cell.accessoryType = UITableViewCell.AccessoryType.disclosureIndicator
        return cell
    }
    
    
    
    func tableView(_ tableView: UITableView, editingStyleForRowAt indexPath: IndexPath) -> UITableViewCell.EditingStyle {
        return UITableViewCell.EditingStyle.delete
    }

    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if (editingStyle == .delete) {
            staff.remove(at: indexPath.row)
            table.reloadData()
        }
    }
    
    

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        performSegue(withIdentifier: "toDetail", sender: indexPath.row)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let detailView = segue.destination as? InfoViewController
        {
            detailView.person = staff[sender as! Int]
        }
    }
    
    @IBAction func unwind(_ unwindSegue: UIStoryboardSegue) {
    }
    
    

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
}

