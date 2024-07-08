public class VGather {
    public static void main(String[] args){
        int students = Comp122.getInt("How Many Students In Class?\n");
        Student[] studentArray = new Student[students];

        for (int i = 0; i < studentArray.length; i++) {
            studentArray[i] = new Student();
            studentArray[i].updateGrade(Comp122.getInt("Enter a grade:\n"));
        }

        double average = 0;
        for (int i = 0; i < studentArray.length; i++) {
            average += studentArray[i].grade;
        }
        average = average / students;
        average = ((double)Math.round(100*average))/100;
        System.out.println(average);
    }
}
