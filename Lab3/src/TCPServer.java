

import java.io.IOException;

public class TCPServer {

	public static void main(String[] args) throws IOException, InterruptedException {
		if(args.length<1)
			return ;
		else
		new TCPServerThread(args[0],args[1],args[2]).start();
		
	}

}
