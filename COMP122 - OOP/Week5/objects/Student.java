public class Student {
    public String name;
    public String email;
    public int yearOfBirth;
    public int enrolmentYear;
    public int studentID;
    public int grade = 0;
    public boolean hasSubmitted = false;


    public void submitCoursework() {
        hasSubmitted = true;
    }
    public void updateGrade(int mark){
        grade += mark;
    }
}
