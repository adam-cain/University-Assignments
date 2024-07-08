//
//  MuralCD+CoreDataProperties.swift
//  New Brighton Murals
//
//  Created by Adam Cain on 12/12/2022.
//
//

import Foundation
import CoreData


extension MuralCD {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<MuralCD> {
        return NSFetchRequest<MuralCD>(entityName: "MuralCD")
    }

    @NSManaged public var artist: String?
    @NSManaged public var enabled: Bool
    @NSManaged public var favourited: Bool
    @NSManaged public var id: Int16
    @NSManaged public var info: String?
    @NSManaged public var lastModified: Date?
    @NSManaged public var lat: Float
    @NSManaged public var lon: Float
    @NSManaged public var thumbnail: String?
    @NSManaged public var title: String?
    @NSManaged public var images: NSSet?

}

// MARK: Generated accessors for images
extension MuralCD {

    @objc(addImagesObject:)
    @NSManaged public func addToImages(_ value: ImageCD)

    @objc(removeImagesObject:)
    @NSManaged public func removeFromImages(_ value: ImageCD)

    @objc(addImages:)
    @NSManaged public func addToImages(_ values: NSSet)

    @objc(removeImages:)
    @NSManaged public func removeFromImages(_ values: NSSet)

}

extension MuralCD : Identifiable {

}
