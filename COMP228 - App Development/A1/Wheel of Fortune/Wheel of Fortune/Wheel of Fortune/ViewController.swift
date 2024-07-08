//
//  ViewController.swift
//  Wheel of Fortune
//
//  Created by Adam Cain on 12/11/2022.
//

import UIKit

class ViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout, UITableViewDelegate, UITableViewDataSource{
    
    // variables used for persitance
    let userDefaults = UserDefaults.standard
    var scoreboard: [Any] = []
    
    // Game values
    var noMatchCounter: Int = 0
    var score: Int = 0
    var rewardValue: Int = 1
    
    // Array holding characters of answer string split into section
    var answerArr: [[Character]] = [[Character]]()
    // Array to hold logical visual representation of correct inputs
    // by user
    var visualArr: [[Character]] = [[Character]]()

    // Refrences to elements in storyboard
    @IBOutlet weak var noMatchOutput: UILabel!
    @IBOutlet weak var scoreOutput: UILabel!
    @IBOutlet weak var rewardValOutput: UILabel!
    @IBOutlet weak var genreOutput: UILabel!
    @IBOutlet weak var table: UICollectionView!
    @IBOutlet weak var scoreboardView: UITableView!
    @IBOutlet weak var guessInput: UITextField!
    @IBOutlet weak var guessButton: UIButton!
    
    // Action connected to guess button
    @IBAction func guess(_ sender: Any) {
        guessInput.resignFirstResponder()
        let guess = (guessInput.text ?? "").uppercased() as String
        if guess.count == 1{
            var count = 0
            let guess = Character(guess)
            // Find if and how many times the guessed character appears
            // in the hidden phrase.
            for arr in 0..<visualArr.count{
                for char in 0..<visualArr[arr].count{
                    let curr = Character(answerArr[arr][char].uppercased())
                    // Only finds the character if it has not already been
                    // found done through checking array contains "_"
                    if(visualArr[arr][char] == "_" && curr == guess){
                        count += 1
                        // Adds the guesssed chracters to visual representation
                        visualArr[arr][char] = guess
                    }
                }
            }
            // If count is greater than one increase the score
            if(count > 0){
                incrementScore(count)
            // If count is 0 increase no match counter
            }else{
                incrementNoMatch()
            }
        }
        // If no match counter >10 round is over. Appends score to scoreboard
        // and then sorts and adds new score to user defaults data persitance
        if(noMatchCounter >= 10){
            scoreboard.append(score)
            scoreboard.sort{ ($0 as! Int) > ($1 as! Int) }
            userDefaults.set(scoreboard, forKey: "scoreboard")
            scoreboardView.reloadData()
            // Initilises new game after loss
            initGame()
        }
        // Checks if the round has been won by comparing cisual and answer arr
        if(answerArr == visualArr){
            newRound()
        }
        guessInput.text = ""
        table.reloadData()
    }
    
    // Initilises game at start up of app and after every new game
    func initGame(){
        noMatchCounter = -1
        score = 0
        incrementNoMatch()
        incrementScore(0)
        newRound()
    }
    
    // Set ups app for new round
    func newRound(){
        answerArr = [[Character]]()
        visualArr = [[Character]]()
        
        // Gets json data for genre and answer
        let jsonData = getJSONDataIntoArray()
        let answer = jsonData.0[Int.random(in: 0..<(jsonData.0.count))]
        let genre = jsonData.1
        genreOutput.text = genre
        print("Genre: "+genre)
        print("Answer: "+answer)
        
        var wordCount = 0
        
        
        var line: [String] = [String]()
        var layoutArr: [[String]] = [[String]]()
        // Seperates words into their own string from answer string. This is
        // done to determine how many words will fit on each line of cell
        // container. And will place them into 2d array where first dimension
        // signifies new line and second dimension are the chracters
        for word in answer.split(whereSeparator: {$0 == " "}).map(String.init) {
            if(wordCount + word.count > 13){
                layoutArr.append(line)
                wordCount = word.count
                line = [String]()
                line.append(word)
            }
            else{
                wordCount += word.count + 1
                line.append(word)
            }
        }
        if(line.count != 0){
            layoutArr.append(line)
        }
        
        // Adds in blank spaces between words for each line in array
        for arr in layoutArr{
            answerArr.append(Array(arr.joined(separator: " ").uppercased()))
        }
        
        // Sets all characters of array to an "_" unless its a blank space so
        // so that visual representation is correct format for string but empty
        visualArr = answerArr
        for arr in 0..<visualArr.count{
            for char in 0..<visualArr[arr].count{
                if(visualArr[arr][char] != " "){
                    visualArr[arr][char] = "_"
                }
            }
        }
        
        // Sets height of cell container to fit mutiple lines of words
        table.frame.size.height = CGFloat(42 * answerArr.count)
        table.reloadData()
        // Sets new reward value after each guess
        setRewardValue()
    }
    
    // Increases no match counter and updates label
    func incrementNoMatch(){
        noMatchCounter+=1
        noMatchOutput.text = "No Match Counter: \(noMatchCounter)"
        setRewardValue()
    }
    
    // Sets reward value to random value of set and updates label
    func setRewardValue(){
        let posValues = [1,2,5,10,20]
        rewardValue = posValues[Int.random(in: 0..<5)]
        rewardValOutput.text = "Reward Value: \(rewardValue)"
    }
    
    // Increments score by current reward value by count of times guess
    // appears answer string
    func incrementScore(_ count: Int){
        score += count * rewardValue
        scoreOutput.text = "Score: \(score)"
        setRewardValue()
    }
    
    // Sets number of sections of cell container to length(number of lines)
    // of answer array
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return answerArr.count
    }
    
    // Sets number of chracters that appear in section
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return answerArr[section].count
    }
    
    // Sets dynamically calulated margins for cells in cell container
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        let sectionLength = answerArr[section].count
        let totalCellWidth = 24 * sectionLength
        let totalSpacingWidth = 0.5 * (Double(sectionLength) - 1)
        let leftInset = (table.frame.size.width - CGFloat(Double(totalCellWidth) + totalSpacingWidth)) / 2
        let rightInset = leftInset
        return UIEdgeInsets(top: 0, left: leftInset, bottom: 0, right: rightInset)
    }
    
    // Sets image view of each cell to corresponding letter at index in visual arr
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell (withReuseIdentifier: "cell", for: indexPath) as! CollectionViewCell;
        cell.theImage.image = UIImage(named: String(visualArr[indexPath.section][indexPath.row]).capitalized)
        return cell
    }
    
    // Sets section spacing
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout:
                        UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        return 0.5
    }
    
    // Sets line spacing
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout:
                        UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 0.5
    }
    
    // Sets cell size
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout:
                        UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let theSize = CGSize(width: 24.0, height: 42.0)
        return theSize
    }
    
    // Sets how many cells are needed to dispally scoreboard
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return scoreboard.count
    }
    
    // Sets each cells text contents to corresponding index of
    // scoreboard
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let newCell = tableView.dequeueReusableCell(withIdentifier: "scoreboardCell", for: indexPath)
        var content = UIListContentConfiguration.cell()
        content.text = "\(indexPath.row + 1) - \(scoreboard[indexPath.row])"
        newCell.contentConfiguration = content
        return newCell
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // initialize game on startup
        initGame()
        // Gets scoreboard from memory of user defualts
        scoreboard = userDefaults.array(forKey: "scoreboard") ?? []
    }
    
    func getFilesInBundleFolder(named fileOrFolderName:String, withExt: String) -> [URL] {
        var fileURLs = [URL]() //the retrieved file-based URLs will be placed here
        let path = Bundle.main.url(forResource: fileOrFolderName,withExtension: withExt)
        //get the URL of the item from the Bundle (in this case a folder
        //whose name was passed as an argument to this function)
        do {// Get the directory contents urls (including subfolders urls)
            fileURLs = try
            FileManager.default.contentsOfDirectory(at: path!,
                                                    includingPropertiesForKeys: nil, options: [])
        } catch {
            print(error.localizedDescription)
        }
        return fileURLs
    }
    
    func getJSONDataIntoArray() -> ([String],String) {
        var theGamePhrases = [String]() //empty array which will evenutally hold our phrases
        //and which we will use to return as part of the result of this function.
        var theGameGenre = ""
        //get the URL of one of the JSON files from the JSONdatafiles folder, at random
        let aDataFile = getFilesInBundleFolder(named:"JSONdatafiles",withExt: "").randomElement()
        do {
            let theData = try Data(contentsOf: aDataFile!) //get the contents of that file as data
            do {
                let jsonResult = try
                JSONSerialization.jsonObject(with: theData,options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                let theTopicData = (jsonResult as? NSDictionary)
                let gameGenre = theTopicData!["genre"] as! String
                theGameGenre = gameGenre //copied so we can see the var outside of this block
                let tempArray = theTopicData!["list"]
                let gamePhrases = tempArray as! [String]
                //compiler complains if we just try to assign this String rray to a standard Swift one
                //so instead, we extract individual strings and add them to our larger scope var
                for aPhrase in gamePhrases { //done so we can see the var outside of this block
                    theGamePhrases.append(aPhrase)
                }
            } catch {
                print("couldn't decode JSON data")
            }
        } catch {
            print("couldn't retrieve data from JSON file")
        }
        return (theGamePhrases,theGameGenre) //tuple composed of Array of phrase Strings and genre
    }
}

