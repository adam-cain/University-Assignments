import java.io.IOException;
import java.sql.SQLException;

public class ExceptronPlatform {


	
	public String runExceptron(Exceptron exceptron) throws IOException {
		String messages = "";
		try{
			for (int i = 0; i < exceptron.getScenarioLength(); i++) {
				try {
					exceptron.doSomeStuff();
				}
				catch (IOException ioe) {
					if(ioe.getMessage() == "FATAL ERROR"){
						throw ioe;
					}
					else{
						messages += ioe.toString();
					}

				} 
				catch (RuntimeException e) {
					messages += e.toString();
				} 
				catch (SQLException e) {
					messages += e.toString();
				}
				catch (SuperCoolException e) {
					messages += e.toString();
				}
				catch (Exception e) {
					messages += e.toString();
				}
			}
		}
		finally{
			exceptron.goodBye();
		}
		if(messages.equals("")){
			return "Everything's Fine\n";
		}
		return messages;
	}
	
    // Main method, for your convenience.
    // You can run the scenarios directly and debug
	public static void main(String[] args) throws IOException {
		int[] scenarios = {0,1,2,3,4,5,6,7,8,9};
		/*
		 * Scenarios by index, Exceptron throws exceptions in order given 
		 * 0 - Exception
		 * 1 - NumberFormatException
		 * 2 - SQLException 
		 * 3 - ArrayIndexOutOfBoundsException
		 * 4 - SuperCoolException
		 * 5 - Exception,IOException,SQLException, IOException,Exception, IOException
		 * 6 - Exception,IOException,SQLException, IOException,Exception, IOException, ArrayIndexOutOfBoundsException, IOException
		 * 7 - Exception,IOException,SQLException, IOException,Exception, IOException, SQLException, IOException, ArrayIndexOutOfBoundsException
		 * 8 - Should return Everything's Fine\n
		 */
		ExceptronPlatform ex = new ExceptronPlatform();
		for (int i = 0; i < scenarios.length; i++) {
			System.out.println(ex.runExceptron(new Exceptron(0)));
		}
	}

}
