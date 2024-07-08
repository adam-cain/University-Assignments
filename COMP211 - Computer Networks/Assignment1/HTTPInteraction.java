/************************************
 * Filename:  HTTPInteraction.java
 ************************************/

import java.net.*;
import java.io.*;

/**
 * Class for downloading one object from http server.
 *
 */
public class HTTPInteraction {
	private String host;
	private String path;
	private String requestMessage;
	

		
	private static final int HTTP_PORT = 80;
	private static final String CRLF = "\r\n";
	private static final int BUF_SIZE = 4096; 
	private static final int MAX_OBJECT_SIZE = 102400;

 	/* Create a HTTPInteraction object. */
	public HTTPInteraction(String url) {
		
		/* Split "URL" into "host name" and "path name", and
		 * set host and path class variables. 
		 * if the URL is only a host name, use "/" as path 
		 */		
		
		/* Fill in */

		

		if(url.contains("/")){
			int index = url.indexOf("/");
			host = url.substring(0, index);
			path = url.substring(index,url.length());
		}else{
			host = url;
			path = "/";
		}
		if(path==""){path="/";}

		/* Construct requestMessage, add a header line so that
		 * server closes connection after one response. */		
	
		/* Fill in */
		requestMessage = "GET "+path+" HTTP/1.1"+CRLF+"Host: "+host+CRLF+"Connection: close"+CRLF+CRLF;
		return;
	}	
	
	
	/* Send Http request, parse response and return requested object 
	 * as a String (if no errors), 
	 * otherwise return meaningful error message. 
	 * Don't catch Exceptions. EmailClient will handle them. */		
	public String send() throws IOException {
		
		/* buffer to read object in 4kB chunks */
		char[] buf = new char[BUF_SIZE];

		/* Maximum size of object is 100kB, which should be enough for most objects. 
		 * Change constant if you need more. */		
		char[] body = new char[MAX_OBJECT_SIZE];
		
		String statusLine="";	// status line
		int status;		// status code
		String headers="";	// headers
		int bodyLength=-1;	// lenghth of body
				
		String[] tmp;
		
		/* The socket to the server */
		Socket connection;
		
		/* Streams for reading from and writing to socket */
		BufferedReader fromServer;
		DataOutputStream toServer;
		
		System.out.println("Connecting server: " + host+CRLF);
		
		/* Connect to http server on port 80.
		 * Assign input and output streams to connection. */		
		connection = new Socket(host,HTTP_PORT);
		fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		toServer = new DataOutputStream(connection.getOutputStream());

		System.out.println("Send request:\n" + requestMessage);

		/* Send requestMessage to http server */
		/* Fill in */
		toServer.writeBytes(requestMessage);
		
		/* Read the status line from response message */
		statusLine = fromServer.readLine();
		System.out.println("Status Line:\n"+statusLine+CRLF);

		/* Extract status code from status line. If status code is not 200,
		 * close connection and return an error message. 
		 * Do NOT throw an exception */		
		/* Fill in */
		if(!statusLine.contains("200")){
			connection.close();
			System.out.println("200 status code expected, recieved: "+statusLine);
		}

		/* Read header lines from response message, convert to a string, 
 		 * and assign to "headers" variable. 
		 * Recall that an empty line indicates end of headers.
		 * Extract length  from "Content-Length:" (or "Content-length:") 
		 * header line, if present, and assign to "bodyLength" variable. 
		*/
		/* Fill in */ 		// requires about 10 lines of code
		bodyLength = MAX_OBJECT_SIZE;
		String line;
		while ((line = fromServer.readLine()) != null){
			if (!line.contains(":")) {
				break;
			}
			if(line.startsWith("Content-Length:")||line.startsWith("Content-length:")){
				bodyLength = Integer.parseInt(line.split(" ")[1]);
			}
			headers += line+"\n";
		}
		System.out.println("Headers:\n"+headers+CRLF);

		// /* If object is larger than MAX_OBJECT_SIZE, close the connection and 
		//  * return meaningful message. */
		if (bodyLength > MAX_OBJECT_SIZE) {
			connection.close();
			return("Body length (" +bodyLength+ ") is greater than Max object size("+MAX_OBJECT_SIZE+")");
		}
					    
		/* Read the body in chunks of BUF_SIZE using buf[] and copy the chunk
		 * into body[]. Stop when either we have
		 * read Content-Length bytes or when the connection is
		 * closed (when there is no Content-Length in the response). 
		 * Use one of the read() methods of BufferedReader here, NOT readLine().
		 * Make sure not to read more than MAX_OBJECT_SIZE characters.
		 */
		int bytesRead = 0;	
		if(!connection.isClosed()){
			for (bytesRead = 0; bytesRead <= MAX_OBJECT_SIZE; bytesRead++) {
				int tp = fromServer.read();
				//System.out.println(tp+": "+ Integer.toString(tp)) ;
				if (tp == -1){
					break;
				}
				body[bytesRead] = (char)tp;
				if(bytesRead >= bodyLength){
					break;
				}
			}
		}

		/* Fill in */   // Requires 10-20 lines of code

		/* At this points body[] should hold to body of the downloaded object and 
		 * bytesRead should hold the number of bytes read from the BufferedReader
		 */
		
		/* Close connection and return object as String. */
		System.out.println("Done reading file. Closing connection.");
		connection.close();
		return(new String(body, 0, bytesRead));
	}
}
