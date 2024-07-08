public class Lecturer extends Person implements Payable {
      private String timetable;

      public void setTimeTable(String newTT) {
          timetable = newTT;
      }

      public String getTimeTable() {
          return timetable;
      }
      
      public void payAmount(int amount) {
        System.out.println(amount);
      }
  }
