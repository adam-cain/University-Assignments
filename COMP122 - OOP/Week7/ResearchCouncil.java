public class ResearchCouncil implements Billable, Emailable {
    private String name;
    private String email;

    public void sendEmail() {
        System.out.println("sendto: " + email + "Hi " + name + ",\n");
    }

    public void payBill(int n) {
        System.out.println(n);
    }
    
}
