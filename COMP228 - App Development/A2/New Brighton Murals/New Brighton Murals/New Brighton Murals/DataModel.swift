//
//  DataModel.swift
//  New Brighton Murals
//
//  Created by Adam Cain on 10/12/2022.
//

import Foundation

struct Image: Decodable{
    let id: String
    let filename: String
}

struct Mural: Decodable {
    let id: String
    let title: String
    let artist: String?
    let info: String?
    let thumbnail: String
    let lat: String
    let lon: String
    let enabled: String
    let lastModified: String
    let images: [Image]
}

struct NewBrightonMurals: Decodable{
    let newbrighton_murals: [Mural]
}
