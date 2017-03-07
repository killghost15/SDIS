

import java.io.IOException;

public class TCPServer {

	public static void main(String[] args) throws IOException, InterruptedException {
		if(args.length<1)
			return ;
		else {
			System.out.println("Initialiazing server...");
			new TCPServerThread(args[0]).start();
		}


	}

}
