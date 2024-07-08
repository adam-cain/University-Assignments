//
//  ViewController.swift
//  Tables
//
//  Created by Adam Cain on 12/11/2022.
//

import UIKit

class ViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    
    @IBOutlet weak var input: UITextField!
    @IBOutlet weak var table: UITableView!
    
    var inputNum: Double = 1
    @IBAction func update(_ sender: Any) {
        input.resignFirstResponder()
        // Validates text input to regex input of one or more digit including decimal places
        if input.text!.contains(Regex(/[0-9]+/)){
            inputNum = Double(input.text!)!
            table.reloadData()
            table.isHidden = false
        }
        input.text = ""
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 30
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 2
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let newCell = tableView.dequeueReusableCell(withIdentifier: "myCell", for: indexPath)
        var content = UIListContentConfiguration.cell()
        let indexAsDouble = Double(indexPath.row + 1)
        if(indexPath.section == 0){
            let result = String(format: "%.4f", inputNum * indexAsDouble)
            content.text = "\(indexPath.row+1) X \(inputNum) = \(result)"
        }else{
            let result = String(format: "%.4f", inputNum / indexAsDouble)
            content.text = "\(inputNum) / \(indexPath.row+1) = \(result)"
        }
        newCell.contentConfiguration = content
        return newCell
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        table.isHidden = true
        // Do any additional setup after loading the view.
    }


}

