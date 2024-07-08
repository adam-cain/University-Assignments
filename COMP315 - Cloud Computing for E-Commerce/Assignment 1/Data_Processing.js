const fs = require('fs');

class Data_Processing {
    constructor() {
        this.raw_user_data = [];
        this.formatted_user_data = [];
        this.cleaned_user_data = [];
    }

    load_CSV(filename) {
        const fileContent = fs.readFileSync(`${filename}.csv`, 'utf8');
        this.raw_user_data = fileContent
    }

    format_data() {
        this.formatted_user_data = this.raw_user_data
            .split('\r\n')
            .filter(row => row.trim() !== "") 
            .map(row => {
                let [titleAndName, dob, age, email] = row.split(',');
                age = this.formatAge(age);
                dob = this.formatDOB(dob, age);
                const [title, firstName, middleName, surname] = this.extract_name_components(titleAndName);
                return {
                    title: title,
                    first_name: firstName,
                    middle_name: middleName,
                    surname: surname,
                    date_of_birth: dob,
                    age: age,
                    email: email,
                };
            });
        fs.writeFileSync('formatted_user_data.json', JSON.stringify(this.formatted_user_data, null, 2));
    }

    extract_name_components(fullName) {
        
        const parts = fullName.split(/\s+/);

        
        let title = '';
        let firstName = '';
        let middleName = '';
        let surname = '';

        
        const titles = ["Mr", "Mrs", "Miss", "Ms", "Dr", "Dr."];
        let startIndex = 0;

        if (titles.includes(parts[0])) {
            title = parts[0];
            startIndex = 1;
        }

        
        surname = parts[parts.length - 1];

        
        firstName = parts[startIndex];

        
        if (parts.length - startIndex > 2) {
            
            middleName = parts.slice(startIndex + 1, parts.length - 1).join(' ');
        }

        return [title, firstName, middleName, surname];
    }

    capitalizeName(name) {
        return name.replace(/(^|[-'])(\w)/g, (match, delimiter, char) =>
            delimiter + char.toUpperCase()
        );
    }

    text_to_number(text) {
        if (typeof text !== 'string') {
            return text;
        }
        const ones = {
            zero: 0,
            one: 1,
            two: 2,
            three: 3,
            four: 4,
            five: 5,
            six: 6,
            seven: 7,
            eight: 8,
            nine: 9
        };
        const teens = {
            eleven: 11,
            twelve: 12,
            thirteen: 13,
            fourteen: 14,
            fifteen: 15,
            sixteen: 16,
            seventeen: 17,
            eighteen: 18,
            nineteen: 19
        };
        const tens = {
            ten: 10,
            twenty: 20,
            thirty: 30,
            forty: 40,
            fifty: 50,
            sixty: 60,
            seventy: 70,
            eighty: 80,
            ninety: 90
        };
        let parts = text.toLowerCase().split(/-| /); 
        let number = 0;

        parts.forEach(part => {
            if (teens[part] !== undefined) {
                number += teens[part];
            } else if (tens[part] !== undefined) {
                number += tens[part];
            } else if (ones[part] !== undefined) {
                number += ones[part];
            } else {
                
            }
        });

        return number;
    }

    formatAge(age) {
        const result = parseInt(age);
        if (isNaN(result)) {
            return this.text_to_number(age);
        }
        if (!isNaN(result) && result > 0) {
            return result;
        }
        return age;
    }

    formatDOB(dob, age) {
        let day, month, year;

        const regexDDMMYYYY = /^\d{2}\/\d{2}\/\d{4}$/;
        const regexDDMMYY = /^\d{2}\/\d{2}\/\d{2}$/;
        const regexDD_MMMM_YYYY = /^\d{2} [a-zA-Z]+ \d{4}$/;
        if (regexDDMMYYYY.test(dob)) {
            return dob;
        }
        else if (regexDDMMYY.test(dob)) {
            const parts = dob.split('/');
            day = parts[0];
            month = parts[1];
            const fullDob = this.calculateFullDOB(day, month, age);
            return `${day}/${month}/${fullDob.split('/')[2][0]}${fullDob.split('/')[2][1]}${parts[2]}`
        }
        else if (regexDD_MMMM_YYYY.test(dob)) {
            const date = new Date(dob);

            
            day = date.getDate().toString().padStart(2, '0');
            month = (date.getMonth() + 1).toString().padStart(2, '0'); 
            year = date.getFullYear().toString();

            return `${day}/${month}/${year}`
        }
    }

    calculateFullDOB(dobDay, dobMonth, age) {
        
        const collectedDate = new Date("2024-02-26");
        const yearCollected = collectedDate.getFullYear();
        const monthCollected = collectedDate.getMonth() + 1; 
        const dayCollected = collectedDate.getDate();

        
        const dobDayNum = parseInt(dobDay, 10);
        const dobMonthNum = parseInt(dobMonth, 10);

        let birthYear;

        
        if (dobMonthNum < monthCollected || (dobMonthNum === monthCollected && dobDayNum <= dayCollected)) {
            
            birthYear = yearCollected - age;
        } else {
            
            birthYear = yearCollected - age - 1;
        }

        
        const dob = dobDay.padStart(2, '0') + '/' + dobMonth.padStart(2, '0') + '/' + birthYear.toString();
        return dob;
    }

    extract_name_from_email(email) {
        const parts = email.split('@')[0].split('.');
        const first_name = parts[0];
        const surname = parts[1];
        return { first_name, surname };
    }

    calculateAge(dob) {
        
        const [day, month, year] = dob.split('/').map(part => parseInt(part, 10));
        const birthDate = new Date(year, month - 1, day);
        const collectedDate = new Date("2024-02-26");

        
        let age = collectedDate.getFullYear() - birthDate.getFullYear();

        
        const monthDiff = collectedDate.getMonth() - birthDate.getMonth();
        const dayDiff = collectedDate.getDate() - birthDate.getDate();

        if (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)) {
            age--;
        }

        return age;
    }

    getDistinctUsers(users) {
        const seen = new Set();
        return users.filter(user => {
            const signature = `${user.title}|${user.first_name}|${user.middle_name}|${user.surname}|${user.age}|${user.date_of_birth}`;
            if (seen.has(signature)) {
                return false;
            } else {
                seen.add(signature);
                return true;
            }
        });
    }

    generateEmail(users) {
        let emailCount = {};
        for (let i = 0; i < users.length; i++) {
            const user = users[i];
            const userString = `${user.first_name}.${user.surname}`;
            if (emailCount[userString]) {
                emailCount[userString]++;
            } else {
                emailCount[userString] = 1;
            }
        }
        let emailCounter = {}
        for (let i = 0; i < users.length; i++) {
            const user = users[i];
            const userString = `${user.first_name}.${user.surname}`;
            if (emailCount[userString] > 1) {
                if (emailCounter[userString]) {
                    emailCounter[userString]++;
                } else {
                    emailCounter[userString] = 1;
                }
                users[i].email = `${user.first_name}.${user.surname}${emailCounter[userString]}@example.com`;
            } else {
                users[i].email = `${user.first_name}.${user.surname}@example.com`;
            }
        }
        return users;
    }

    clean_data() {
        this.cleaned_user_data = this.formatted_user_data.map(user => {
            if (user.title === "Dr.") user.title = "Dr";
            if (user.first_name === "" || user.surname === "") {
                const { first_name, surname } = this.extract_name_from_email(user.email)
                if (first_name !== "") user.first_name = first_name;
                if (surname !== "") user.surname = surname;
            }
            user.first_name = this.capitalizeName(user.first_name);
            user.middle_name = user.middle_name ? this.capitalizeName(user.middle_name) : '';
            user.surname = this.capitalizeName(user.surname);

            user.age = this.calculateAge(user.date_of_birth);
            return user;
        });
        this.cleaned_user_data = this.getDistinctUsers(this.cleaned_user_data);
        this.cleaned_user_data = this.generateEmail(this.cleaned_user_data);
        fs.writeFileSync('cleaned_user_data.json', JSON.stringify(this.cleaned_user_data, null, 2));
    }

    most_common_surname() {
        
        const surnameCounts = this.cleaned_user_data.reduce((acc, user) => {
            const surname = user.surname;
            acc[surname] = (acc[surname] || 0) + 1;
            return acc;
        }, {});
    
        
        const maxCount = Math.max(...Object.values(surnameCounts));
    
        
        const mostCommonSurnames = Object.keys(surnameCounts).filter(surname => surnameCounts[surname] === maxCount);
    
        return mostCommonSurnames;
    }

    average_age() {
        const totalAge = this.cleaned_user_data.reduce((sum, user) => sum + user.age, 0);
        return parseFloat((totalAge / this.cleaned_user_data.length).toFixed(1));
    }

    youngest_dr() {
        const doctors = this.cleaned_user_data.filter(user => user.title === 'Dr');
        return doctors.reduce((youngest, current) => youngest.age < current.age ? youngest : current, doctors[0]);
    }

    most_common_month() {
        const monthCounts = this.cleaned_user_data.reduce((acc, user) => {
            const month = user.date_of_birth.split('/')[1]; 
            acc[month] = (acc[month] || 0) + 1;
            return acc;
        }, {});

        return Object.keys(monthCounts).reduce((a, b) => monthCounts[a] > monthCounts[b] ? a : b);
    }

    percentage_titles() {
        const titles = ['Mr', 'Mrs', 'Miss', 'Ms', 'Dr', ''];
        const titleCounts = titles.map(title => this.cleaned_user_data.filter(user => user.title === title).length);
        const total = this.cleaned_user_data.length;

        const percentages = titleCounts.map(count => {
            const percentage = (count / total) * 100;
            return Math.round(percentage % 1 === 0.5 ? (percentage + 1) % 2 === 0 ? percentage + 1 : percentage - 1 : percentage);
        });

        return percentages;
    }

    percentage_altered() {
        let totalValuesChecked = this.formatted_user_data.length;
        let valuesAltered = 0;
        const cleaned_user_data_copy = this.cleaned_user_data.slice();
        
        for (let i = 0; i < this.formatted_user_data.length; i++) {
            const formattedUser = this.formatted_user_data[i];
            const formattedUserSig = JSON.stringify(formattedUser);
            let dataChanged = true;

            for (let j = 0; j < cleaned_user_data_copy.length; j++) {
                const cleanedUser = cleaned_user_data_copy[j];
                const cleanedUserSig = JSON.stringify(cleanedUser);

                if (formattedUserSig === cleanedUserSig) {
                    dataChanged = false;
                    cleaned_user_data_copy.splice(j, 1);
                    break;
                }
            }
            if (dataChanged) {
                valuesAltered++;
            }
        }
        let percentage = (valuesAltered / totalValuesChecked) * 100;
        return parseFloat(percentage).toPrecision(3);
    }
}

// module.exports = Data_Processing;