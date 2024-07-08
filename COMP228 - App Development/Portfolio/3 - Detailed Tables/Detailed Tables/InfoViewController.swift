//
//  InfoViewController.swift
//  Detailed Tables
//
//  Created by Adam Cain on 13/12/2022.
//

import UIKit

class InfoViewController: UIViewController {

    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var roomLabel: UILabel!
    @IBOutlet weak var emailLabel: UILabel!
    
    var person = ("","","")
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        nameLabel.text = person.0
        roomLabel.text = person.1
        emailLabel.text = person.2
    }
    

}
