const Data_Processing = require("./Data_Processing");
const data = new Data_Processing();
data.load_CSV("Raw_User_Data");
data.format_data();
data.clean_data();

console.log("Most common surname: ", data.most_common_surname());
console.log("Average age: ", data.average_age());
console.log("Youngest Dr: ", data.youngest_dr());
console.log("Most common month: ", data.most_common_month());
console.log("Percentage titles: ", data.percentage_titles());
console.log("Percentage altered: ", data.percentage_altered());

// console.log("Percentage altered: ", data.percentage_altered());
