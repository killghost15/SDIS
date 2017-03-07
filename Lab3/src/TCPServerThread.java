

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Hashtable;

public class TCPServerThread extends Thread {

	protected Hashtable<String, String> database = new Hashtable<String,String>();
	protected ServerSocket serversocket = null;

	public TCPServerThread(String port) throws IOException, InterruptedException {
		this("Server",port);
		System.out.println("\n---------------\n");
		System.out.println("Server waiting...");
	}

	public TCPServerThread(String name,String portentry) throws IOException, InterruptedException {
		super(name);
		database.put( "AA|44|BB","Fulano");
		database.put("AB|33|CC","Tipo2");

		serversocket = new ServerSocket(Integer.parseInt(portentry));

		serversocket.setSoTimeout(10000);
		Socket clientSocket = serversocket.accept();

		System.out.println("Server initialized!");

		String[] splitted;
		String msg;
		String answer;
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));

		try {

			msg=in.readLine();
			System.out.println("Client connected!");
			System.out.println("Client message: "+msg);

			splitted = msg.split("#");

			answer = "blabla";

			if(splitted[0].equals("lookup")) {
				if(database.containsKey(splitted[1]))
					answer = "Plate "+splitted[1]+" exists!\n"
							+"owner: " + database.get(splitted[1]).toString();
				else
					answer = "Plate "+splitted[1]+" not found!";
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
			out.close();
			System.out.println("Client disconnected!");


		}
		catch (SocketTimeoutException e) {
			// timeout exception.
			System.out.println("Timeout reached!!! "+ name+"closed");
			out.close();
			in.close();
			serversocket.close();
			System.out.println("System shutdown via timeout...");
			return;
		}

	}  
}