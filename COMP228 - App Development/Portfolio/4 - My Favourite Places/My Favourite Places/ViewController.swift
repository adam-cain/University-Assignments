//
//  ViewController.swift
//  My Favourite Places
//
//  Created by Adam Cain on 13/12/2022.
//

import UIKit
import MapKit
import CoreData

class ViewController: UIViewController, MKMapViewDelegate, CLLocationManagerDelegate {
    
    var locationManager = CLLocationManager()
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    let span = MKCoordinateSpan(latitudeDelta: 0.008, longitudeDelta: 0.008)
    
    @IBOutlet weak var map: MKMapView!
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        if(centerMapState == true){
            let location = locations[0].coordinate
            let region = MKCoordinateRegion(center: location, span: span)
            self.map.setRegion(region, animated: false)
            centerMapState = false
        }
    }
    
    @IBAction func longPress(_ sender: UILongPressGestureRecognizer) {
        if sender.state == .began {
            print("===\nLong Press began\n===")
            let touchPoint = sender.location(in: self.map)
            let newCoordinate = self.map.convert(touchPoint, toCoordinateFrom: self.map)
            print(newCoordinate)
            let location = CLLocation(latitude: newCoordinate.latitude, longitude: newCoordinate.longitude)
            var title = ""
            CLGeocoder().reverseGeocodeLocation(location, completionHandler: { (placemarks, error) in
                if error != nil {
                    print(error!)
                } else {
                    if let placemark = placemarks?[0] {
                        if placemark.subThoroughfare != nil {
                            title += placemark.subThoroughfare! + " "
                        }
                        if placemark.thoroughfare != nil {
                            title += placemark.thoroughfare!
                        }
                    }
                }
                if title == "" {
                    title = "Added \(NSDate())"
                }
                let annotation = MKPointAnnotation()
                annotation.coordinate = newCoordinate
                annotation.title = title
                self.map.addAnnotation(annotation)
                
                let context = self.appDelegate.persistentContainer.viewContext
                let newPlace = NSEntityDescription.insertNewObject(forEntityName: "Place", into: context) as! Place
                newPlace.name = title
                newPlace.lat = newCoordinate.latitude
                newPlace.lon = newCoordinate.longitude
                do{
                    try context.save()
                }catch{
                    print("Couldnt save new place")
                }
            } )
        }
    }
    
    
    override func viewDidLoad() {
        
        locationManager.delegate = self as CLLocationManagerDelegate
        locationManager.desiredAccuracy = kCLLocationAccuracyBestForNavigation
        locationManager.requestWhenInUseAuthorization()
        locationManager.startUpdatingLocation()
        
        super.viewDidLoad()
    }
    
}

