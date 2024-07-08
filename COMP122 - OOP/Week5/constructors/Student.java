public class Student {
    private String name;
    private String email;
    private int yearOfBirth;
    private int enrolmentYear;
    private int studentId;

    private int grade = 0;
    private boolean hasSubmitted = false;

    public Student(){

    }

    public String getName(){return name;}
    public String getEmail(){return email;}
    public int getYearOfBirth(){return yearOfBirth;}
    public int getEnrolmentYear(){return enrolmentYear;}
    public int getStudentId(){return studentId;}
    public int getGrade(){return grade;}
    public boolean getHasSubmitted(){return hasSubmitted;}

    public Student(String studentName) {
        name = studentName;
    }

    public Student(String studentName, String studentEmail, int studentYearOfBirth, int studentEnrolmentYear, int newStudentId ){
        name = studentName;
        email = studentEmail;
        yearOfBirth = studentYearOfBirth;
        enrolmentYear = studentEnrolmentYear;
        studentId = newStudentId;
    }

    public Student(String studentName, String studentEmail, int studentYearOfBirth){
        name = studentName;
        email = studentEmail;
        yearOfBirth = studentYearOfBirth;
    }

    public Student(String studentName, String studentEmail, String studentYearOfBirth){
        name = studentName;
        email = studentEmail;
        yearOfBirth = Integer.parseInt(studentYearOfBirth.split("/")[2]);
    }

    public void submitCoursework() {
        hasSubmitted = true;
    }
    public void updateGrade(int mark){
        if(mark>100 || mark <0){
            System.out.println("Enter a grade from 0-100.");
            return;
        }
        else{
            grade += mark;
            hasSubmitted = true;
        }
    }
}
