//
//  ViewController.swift
//  Dice Roll
//
//  Created by Adam Cain on 11/11/2022.
//

import UIKit

class ViewController: UIViewController {
    
    let diceRoll = Int.random(in: 2..<13)
    
    @IBOutlet weak var output: UILabel!
    @IBOutlet weak var input: UITextField!
    
    @IBAction func guessButton(_ sender: Any) {
        input.resignFirstResponder();
        let guess = Int(input.text ?? "0");
        if(guess == diceRoll){
            output.text = "Correct! the number is " + String(diceRoll)
        }else{
            if(guess == 0){
                output.text = "Incorrect! your guess must be between 2 and 12 "
            }
            else{
                output.text = "Incorrect! the number isnt " + String(guess!)
            }
        }
        input.text = ""
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        output.text = ""
    }
}

