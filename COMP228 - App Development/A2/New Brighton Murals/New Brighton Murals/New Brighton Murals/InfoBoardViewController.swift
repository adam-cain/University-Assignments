//
//  InfoBoardViewController.swift
//  New Brighton Murals
//
//  Created by Adam Cain on 11/12/2022.
//

import UIKit
import CoreData

class InfoBoardViewController: UIViewController {
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    let favFilled = UIImage(systemName: "star.fill")
    let favEmpty = UIImage(systemName: "star")
 
    var id: Int16 = 0
    var muralInfo: MuralCD = MuralCD()
    var imageData: [Data] = []
    
    @IBAction func favButtonClicked(_ sender: Any) {
        if muralInfo.favourited {
            muralInfo.favourited = false
        } else{
            muralInfo.favourited = true
        }
        favButtonState(muralInfo.favourited)
        let context = appDelegate.persistentContainer.viewContext
        do{
            try context.save()
        }catch{
            print("Unable to save context")
        }
        
    }
    
    func favButtonState(_ favourited: Bool){
        if muralInfo.favourited {
            favButtonIcon.setImage(favFilled, for: UIControl.State.normal)
        } else{
            favButtonIcon.setImage(favEmpty, for: UIControl.State.normal)
        }
    }

    @IBOutlet weak var favButtonIcon: UIButton!
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var artistLabel: UILabel!
    @IBOutlet weak var infoLabel: UILabel!
    @IBOutlet weak var imageBox: UIImageView!
    
    func getCoreDataFromId(_ id: Int16) -> MuralCD {
        let context = appDelegate.persistentContainer.viewContext
        let request = NSFetchRequest<NSFetchRequestResult>(entityName: "MuralCD")
        request.returnsObjectsAsFaults = false
        request.predicate = NSPredicate(format: "id == \(id)")

        do{
            let results = try context.fetch(request)
            return results[0] as! MuralCD
        } catch {
            print("Couldnt fetch results")
            return MuralCD()
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
    }

    func setupView(){
        muralInfo = getCoreDataFromId(id)
        
        titleLabel.text = muralInfo.title
        artistLabel.text = muralInfo.artist ?? ""
        infoLabel.text = muralInfo.info
        favButtonState(muralInfo.favourited)
        
        imageBox.image = UIImage(named: "placeholder")
        
        var imageArr: [UIImage] = []
        muralInfo.images?.forEach{image in
            let image = image as! ImageCD
            if let url = URL(string: "https://cgi.csc.liv.ac.uk/~phil/Teaching/COMP228/nbm_images/\(image.fileName!)"){
                let session = URLSession.shared
                session.dataTask(with: url) { (data, response, err) in
                    guard let imgData = data else {
                        return
                    }
                    imageArr.append(UIImage(data: imgData)!)
                    DispatchQueue.main.async { [self] in
                        self.imageBox.animationImages = imageArr
                        self.imageBox.animationDuration = TimeInterval(imageArr.count)
                        self.imageBox.startAnimating()
                    }
                }.resume()
            }
        }
    }
}
