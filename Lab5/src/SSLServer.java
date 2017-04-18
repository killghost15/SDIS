

import java.io.IOException;

public class SSLServer {

	public static void main(String[] args) throws IOException, InterruptedException {
		if(args.length<1)
			return ;
		else
		new SSLServerThread(args[0]).start();
		
	}

}
