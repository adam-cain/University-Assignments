//
//  ImageCD+CoreDataProperties.swift
//  New Brighton Murals
//
//  Created by Adam Cain on 12/12/2022.
//
//

import Foundation
import CoreData


extension ImageCD {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<ImageCD> {
        return NSFetchRequest<ImageCD>(entityName: "ImageCD")
    }

    @NSManaged public var fileName: String?
    @NSManaged public var id: String?
    @NSManaged public var mural: MuralCD?

}

extension ImageCD : Identifiable {

}
