

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Hashtable;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class SSLServerThread extends Thread {

	protected Hashtable<String, String> database = new Hashtable<String,String>();
	SSLServerSocket s = null;  
	SSLServerSocketFactory ssf = null;  

	public SSLServerThread(String port) throws IOException, InterruptedException {
		this("Server",port);
	}

	public SSLServerThread(String name,String portentry) throws IOException, InterruptedException {
		super(name);
		database.put( "AA|44|BB","Fulano");
		database.put("AB|33|CC","Tipo2");
		ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault(); 
		
		try {  
		    s = (SSLServerSocket) ssf.createServerSocket(Integer.parseInt(portentry));  
		}  
		catch( IOException e) {  
		    System.out.println("Server - Failed to create SSLServerSocket");  
		    e.getMessage();  
		    return;  
		} 
		
		s.setNeedClientAuth(true); 
		String[] splitted;
		String msg;
		String answer;
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	    BufferedReader in = new BufferedReader(
	        new InputStreamReader(clientSocket.getInputStream()));
		for(int i=0; i<5; i++){        // recieve data until timeout or until more than 5 packages were sent
			try {
				
				msg=in.readLine();
				System.out.println("Client connected");
				System.out.println("Client message: "+msg);

				splitted = msg.split("#");

				answer = "blabla";

				if(splitted[0].equals("lookup")) {
					if(database.containsKey(splitted[1]))
						answer = "Plate "+splitted[1]+" exists!\n"
								+"owner: " + database.get(splitted[1]).toString();
					else
						answer = "Plate "+splitted[1]+"NOT_FOUND";
				}
				if(splitted[0].equals("register")) {
					if(database.containsKey(splitted[1]))
						answer = "-1";

					else{
						database.put(splitted[1], splitted[2]);
						answer=database.size()+"";
					}
				}

				out.println(answer);

				
				
			}
			catch (SocketTimeoutException e) {
				// timeout exception.
				System.out.println("Timeout reached!!! "+ name+"closed");
				out.close();
            	in.close();
				serversocket.close();
				return;
			}
		}



		

	}  
}