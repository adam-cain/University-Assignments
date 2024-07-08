//
//  ResearchPapersTVC.swift
//  Research Papers
//
//  Created by Adam Cain on 14/12/2022.
//

import UIKit

class ResearchPapersTVC: UITableViewController {
    
    @IBOutlet var table: UITableView!
    
    var reports:technicalReports? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if let url = URL(string: "https://cgi.csc.liv.ac.uk/~phil/Teaching/COMP228/techreports/data.php?class=techreports2") {
            let session = URLSession.shared
            session.dataTask(with: url) { (data, response, err) in
                guard let jsonData = data else {
                    return
                }
                do {
                    let decoder = JSONDecoder()
                    let reportList = try decoder.decode(technicalReports.self, from: jsonData)
                    self.reports = reportList
                    DispatchQueue.main.async {
                        self.updateTheTable()
                    }
                } catch let jsonErr {
                    print("Error decoding JSON", jsonErr)
                }
            }.resume()
            print("You are here!")
        }
    }
    
    func updateTheTable() {
        table.reloadData()
    }
    
    // MARK: - Table view data source
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return reports?.techreports2.count ?? 0
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) ->
    UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "theCell", for: indexPath)
        var content = UIListContentConfiguration.subtitleCell()
        content.text = reports?.techreports2[indexPath.row].title ?? "no title"
        content.secondaryText = reports?.techreports2[indexPath.row].authors ?? "no authors"
        cell.contentConfiguration = content
        return cell
    }
    
    
    // MARK: - Navigation
    
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        performSegue(withIdentifier: "toDetail", sender: indexPath.row)
    }
    
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let vc = segue.destination as? ViewController
        {
            vc.paper = (reports?.techreports2[sender as! Int])!
        }
    }
    
    
}
