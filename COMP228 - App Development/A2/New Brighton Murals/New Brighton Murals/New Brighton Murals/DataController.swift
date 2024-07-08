//
//  DataController.swift
//  New Brighton Murals
//
//  Created by Adam Cain on 10/12/2022.
//

import Foundation
import CoreData

class DataController: ObservableObject {
    let container = NSPersistentContainer(name: "MuralInfo")
    
    init() {
        container.loadPersistentStores{ desc, error in
            if let error = error {
                print("Failed to load data \(error.localizedDescription)")
            }
        }
    }
    
    func save(context: NSManagedObjectContext){
        do {
            try context.save()
            print("Data saved")
        } catch{
            print("Couldnt save data")
        }
    }
    
    func addMural(mural: Mural, context: NSManagedObjectContext){
        let muralCoreData = self.container.
        
    }
}
