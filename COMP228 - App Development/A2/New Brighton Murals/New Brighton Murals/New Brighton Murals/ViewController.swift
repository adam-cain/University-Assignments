//
//  ViewController.swift
//  New Brighton Murals
//
//  Created by Adam Cain on 05/12/2022.
//

import UIKit
import MapKit
import CoreLocation
import CoreData

class ViewController: UIViewController, UITableViewDataSource, UITableViewDelegate,
MKMapViewDelegate, CLLocationManagerDelegate {
     
    // MARK: Map & Location related stuff
    
    @IBOutlet weak var map: MKMapView!
    @IBOutlet weak var table: UITableView!
    
    
    var locationManager = CLLocationManager()
    var firstRun = true
    var startTrackingTheUser = false
    var muralList: [MuralCD] = []
    var imageData: [Data] = []
    var idIndex: [Int16] = []
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
     
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        let locationOfUser = locations[0] //this method returns an array of locations
        //generally we always want the first one (usually there's only 1 anyway)
        let latitude = locationOfUser.coordinate.latitude
        let longitude = locationOfUser.coordinate.longitude
        //get the users location (latitude & longitude)
        let location = CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
         
        if firstRun {
            firstRun = false
            let latDelta: CLLocationDegrees = 0.0025
            let lonDelta: CLLocationDegrees = 0.0025
            //a span defines how large an area is depicted on the map.
            let span = MKCoordinateSpan(latitudeDelta: latDelta, longitudeDelta: lonDelta)
             
            //a region defines a centre and a size of area covered.
            let region = MKCoordinateRegion(center: location, span: span)
             
            //make the map show that region we just defined.
            self.map.setRegion(region, animated: true)
             
            //the following code is to prevent a bug which affects the zooming of the map to the user's location.
            //We have to leave a little time after our initial setting of the map's location and span,
            //before we can start centering on the user's location, otherwise the map never zooms in because the
            //intial zoom level and span are applied to the setCenter( ) method call, rather than our "requested" ones,
            //once they have taken effect on the map.
             
            //we setup a timer to set our boolean to true in 5 seconds.
            _ = Timer.scheduledTimer(timeInterval: 5.0, target: self, selector: #selector(startUserTracking), userInfo: nil, repeats: false)
        }
         
        if startTrackingTheUser == true {
            map.setCenter(location, animated: true)
        }
    }
     
    //this method sets the startTrackingTheUser boolean class property to true. Once it's true,
   //subsequent calls to didUpdateLocations will cause the map to centre on the user's location.
    @objc func startUserTracking() {
        startTrackingTheUser = true
    }
     
    //MARK: Table related stuff
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return muralList.count
    }
    
    let favFilled = UIImage(systemName: "star.fill")
    let favEmpty = UIImage(systemName: "star")
     
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! MuralCell
        var content = UIListContentConfiguration.subtitleCell()
        
        let currMural = muralList[indexPath.row]
        content.text = currMural.title!
        if(currMural.favourited){
            content.text! += "   ⭐"
        }
        if !((currMural.artist ?? "").isEmpty){
            content.secondaryText = currMural.artist!
        }
        if(muralList.count == imageData.count){
            content.image = UIImage(data: imageData[indexPath.row])
        }
        else{
            content.image = UIImage(named: "Placeholder")
        }
        
        cell.contentConfiguration = content
        return cell
    }

    // MARK: Retrieving and storing mural data from api
    
    func getAPICall(){
        let lastmodified = getLastModified()
        if let url = URL(string: "https://cgi.csc.liv.ac.uk/~phil/Teaching/COMP228/nbm/data2.php?class=newbrighton_murals&lastModified=\(lastmodified.description.prefix(10))") {
            let session = URLSession.shared
            session.dataTask(with: url) { (data, response, err) in
                guard let jsonData = data else {
                    return
                }
                do {
                    let decoder = JSONDecoder()
                    let murals = try decoder.decode(NewBrightonMurals.self, from: jsonData)
                    self.updateCoreData(murals)
                    self.setLastModified()
                } catch let jsonErr {
                    print("Error decoding JSON", jsonErr)
                }
                self.getCoreData()
                DispatchQueue.main.async { [self] in
                    table.reloadData()
                }
            }.resume()
        }
        table.reloadData()
    }
        
    func updateCoreData(_ murals: NewBrightonMurals){
        let context = appDelegate.persistentContainer.viewContext
        murals.newbrighton_murals.forEach{ mural in
            let newMuralID = Int16(mural.id)!

            let request = NSFetchRequest<NSFetchRequestResult>(entityName: "MuralCD")
            request.returnsObjectsAsFaults = false
            request.predicate = NSPredicate(format: "id == \(newMuralID)")
            do{
                let results = try context.fetch(request)
                results.forEach{Entity in
                    context.delete(Entity as! NSManagedObject)
                }
            } catch {
                print("Error deleting entity of id \(newMuralID)")
            }
            
            let newMural = NSEntityDescription.insertNewObject(forEntityName: "MuralCD", into: context) as! MuralCD
           
            newMural.id = newMuralID
            newMural.title = mural.title
            newMural.artist = mural.artist
            newMural.info = mural.info
            newMural.thumbnail = mural.thumbnail
            newMural.lastModified = Date()
            newMural.lat = Double(mural.lat)!
            newMural.lon = Double(mural.lon)!
            if mural.enabled == "1"{
                newMural.enabled = true
            }else{
                newMural.enabled = false
            }
            mural.images.forEach{image in
                let newImage = NSEntityDescription.insertNewObject(forEntityName: "ImageCD", into: context) as! ImageCD
                newImage.id = image.id
                newImage.fileName = image.filename
                newMural.addToImages(newImage)
            }
            do {
                try context.save()
            } catch{
                print("Error saving new entity")
            }
        }
        print("Finished Updating Core Data")
    }
    
    
    func getCoreData(){
        let context = appDelegate.persistentContainer.viewContext
        let request = NSFetchRequest<NSFetchRequestResult>(entityName: "MuralCD")
        request.returnsObjectsAsFaults = false
        do{
            let results = try context.fetch(request)
            results.forEach{ result in
                
                let convResult = result as! MuralCD
                self.muralList.append(convResult)
                
                let coordinate = CLLocationCoordinate2D(latitude: convResult.lat, longitude: convResult.lon)
                let annotation = MKPointAnnotation()
                annotation.coordinate = coordinate
                annotation.title = convResult.title
                map.addAnnotation(annotation)
                
                if let url = URL(string: convResult.thumbnail!){
                    let session = URLSession.shared
                    session.dataTask(with: url) { (data, response, err) in
                        guard let imgData = data else {
                            return
                        }
                        self.imageData.append(imgData)
                        if(self.muralList.count == self.imageData.count){
                            DispatchQueue.main.async { [self] in
                                table.reloadData()
                            }
                        }
                    }.resume()
                }
            }
            idIndex = muralList.map{$0.id}
        } catch {
            print("Couldnt fetch results")
        }
    }
    
// MARK: Last modified user defaults setters and getters
    
    func getLastModified() -> Date{
        let defaults = UserDefaults.standard
        let unixEpoch = Date(timeIntervalSince1970: 0).description
        let lastModified = defaults.string(forKey: "lastModifiedMuralData") ?? unixEpoch
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-M-dd HH:mm:ss Z"
        return dateFormatter.date(from: lastModified)!
    }
    
    func setLastModified(){
        let defaults = UserDefaults.standard
        defaults.set(Date().description, forKey: "lastModifiedMuralData")
    }
    
    //MARK: Segue
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        //let cell = tableView.cellForRow(at: indexPath)
        tableView.deselectRow(at: indexPath, animated: true)
        performSegue(withIdentifier: "infoBoard", sender: indexPath.row)
    }
    

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let nextViewController = segue.destination as? InfoBoardViewController
        {
            let index = sender as! Int
            nextViewController.id = idIndex[index]
        }
    }
    
     
    // MARK: View related Stuff
     
    override func viewDidLoad() {
        getAPICall()
        super.viewDidLoad()
        table.reloadData()
        // Make this view controller a delegate of the Location Manager, so that it
        //is able to call functions provided in this view controller.
        locationManager.delegate = self as CLLocationManagerDelegate
        
        //set the level of accuracy for the user's location.
        locationManager.desiredAccuracy = kCLLocationAccuracyBestForNavigation
         
        //Ask the location manager to request authorisation from the user. Note that this
        //only happens once if the user selects the "when in use" option. If the user
        //denies access, then your app will not be provided with details of the user's
        //location.
        locationManager.requestWhenInUseAuthorization()
         
        //Once the user's location is being provided then ask for updates when the user
        //moves around.
        locationManager.startUpdatingLocation()
         
        //configure the map to show the user's location (with a blue dot).
        map.showsUserLocation = true
    }
    
    override func viewDidAppear(_ animated: Bool) {
        table.reloadData()
    }
}

