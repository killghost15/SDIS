

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLClient {
	public static void main(String[] args) throws IOException {

        if (args.length != 4 && args.length !=5) {
             System.out.println("Usage: java SSLClient <host_name> <port_number> <oper> <opnd><cypher crypt>*");
             return;
        }
        
        SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault(); 
        SSLSocket s = (SSLSocket) ssf.createSocket(args[0], Integer.parseInt(args[1]));
        s.startHandshake();
        
       Socket socket = new Socket(args[0],Integer.parseInt(args[1]));
String msg="";
String answer="not received";
       PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	    BufferedReader in = new BufferedReader(
	        new InputStreamReader(socket.getInputStream()));
	    
        if("lookup".equals(args[2]))
        msg="lookup#"+args[3];
        if("register".equals(args[2]))
        msg="register#"+args[3]+"#"+args[4];
        out.println(msg);
        
        //set timeout on socket love this API 10 seconds
        socket.setSoTimeout(10000);
        
        while(true){        // recieve data until timeout
            try {
            	 answer=in.readLine();
                 break;
            }
            catch (SocketTimeoutException e) {
            	out.close();
            	in.close();
                socket.close();
                return;
            }
        }
        
       

        
        System.out.println(answer);
        out.close();
    	in.close();
        socket.close();
    }
}