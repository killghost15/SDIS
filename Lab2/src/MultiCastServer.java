

import java.io.IOException;

public class MultiCastServer {

	public static void main(String[] args) throws IOException, InterruptedException {
		if(args.length<1)
			return ;
		else
		new MultiCastServerThread(args[0],args[1],args[2],args[3]).start();
		
	}

}
