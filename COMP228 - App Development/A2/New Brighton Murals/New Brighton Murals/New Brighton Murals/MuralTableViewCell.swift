//
//  MuralTableViewCell.swift
//  New Brighton Murals
//
//  Created by Adam Cain on 12/12/2022.
//

import UIKit

class MuralTableViewCell: UITableViewCell {

    @IBOutlet weak var favIcon: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
