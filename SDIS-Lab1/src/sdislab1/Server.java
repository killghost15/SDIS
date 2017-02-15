package sdislab1;

import java.io.IOException;

public class Server {

	public static void main(String[] args) throws IOException {
		if(args.length<1)
			return ;
		else
		new ServerThread(args[0]).start();
		
	}

}
