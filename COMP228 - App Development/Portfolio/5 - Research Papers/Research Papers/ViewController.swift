//
//  ViewController.swift
//  Research Papers
//
//  Created by Adam Cain on 14/12/2022.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var emailLabel: UILabel!
    @IBOutlet weak var authorLabel: UILabel!
    @IBOutlet weak var yearLabel: UILabel!
    @IBOutlet weak var urlLabel: UITextView!
    @IBOutlet weak var abstractLabel: UITextView!
    
    var paper : techReport
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        titleLabel.text = paper.title
        emailLabel.text = paper.email
        authorLabel.text = paper.authors
        yearLabel.text = paper.authors
        urlLabel.text = paper.pdf?.absoluteString ?? ""
        abstractLabel.text = paper.abstract
    }
}

