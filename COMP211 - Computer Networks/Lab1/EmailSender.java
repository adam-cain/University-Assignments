import java.io.*;
import java.net.*;

public class EmailSender
{
   public static void main(String[] args) throws Exception
   {
      // Establish a TCP connection with the mail server.
      Socket socket = new Socket("35.246.112.180",1025);

      // Create a BufferedReader to read a line at a time.
      InputStream is = socket.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);

      // Read greeting from the server.
      String response = br.readLine();
      System.out.println(response);
      if (!response.startsWith("220")) {
         throw new Exception("220 reply not received from server.");
      }

      // Get a reference to the socket's output stream.
      DataOutputStream os = new DataOutputStream(socket.getOutputStream());

      // Send HELO command and get server response.
      String command = "HELO Daddy\r\n";
      System.out.print(command);
      os.writeBytes(command);
      response = br.readLine();
      System.out.println(response);
      if (!response.startsWith("250")) {
         throw new Exception("250 reply not received from server.");
      }

      // Send MAIL FROM command.
      command = "MAIL FROM: new@mailfrom.com\r\n";
      os.writeBytes(command);

      response = br.readLine();
      System.out.println("MAIL FROM:"+response);
      // Send RCPT TO command.

      os.writeBytes("RCPT TO: to@me.com\r\n");

      response = br.readLine();
      System.out.println("RCPT TO:"+response);

      // Send DATA command.

      os.writeBytes("DATA\r\n");

      os.writeBytes("Hello Kitten,\r\n");
      os.writeBytes("Nice hearing from you\r\n");
      os.writeBytes(".\r\n");
      

      // Send message data.
      // End with line with a single period.

      response = br.readLine();
      System.out.println("DATA "+response);

      // os.writeBytes(".\r\n");
      // // Send QUIT command.

      // response = br.readLine();
      // System.out.println("fullstop "+response);

      os.writeBytes("QUIT\r\n");

      response = br.readLine();
      System.out.println("QUIT "+response);

   }
}
