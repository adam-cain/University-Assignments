//
//  Place+CoreDataProperties.swift
//  My Favourite Places
//
//  Created by Adam Cain on 14/12/2022.
//
//

import Foundation
import CoreData


extension Place {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<Place> {
        return NSFetchRequest<Place>(entityName: "Place")
    }

    @NSManaged public var lat: Double
    @NSManaged public var lon: Double
    @NSManaged public var name: String?

}

extension Place : Identifiable {

}
