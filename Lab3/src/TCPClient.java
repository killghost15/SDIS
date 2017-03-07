

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class TCPClient {
	public static void main(String[] args) throws IOException {

        if (args.length != 4 && args.length !=5) {
             System.out.println("Usage: java Client <host_name> <port_number> <oper> <opnd>*");
             return;
        }

        DatagramSocket socket = new DatagramSocket();

        byte[] buf = new byte[256];
        InetAddress address = InetAddress.getByName(args[0]);
        DatagramPacket packet;
        if("lookup".equals(args[2]))
        packet = lookUp(socket,args[3],buf,address,Integer.parseInt(args[1]));
        if("register".equals(args[2]))
        packet=register(socket,args[3],args[4],buf,address,Integer.parseInt(args[1]));
        
        
        //set timeout on socket love this API 10 seconds
        socket.setSoTimeout(10000);
        
        while(true){        // recieve data until timeout
            try {
            	 packet = new DatagramPacket(buf, buf.length);
                 socket.receive(packet);
                 break;
            }
            catch (SocketTimeoutException e) {
            	for(int j=0;j<3;j++){
                // timeout exception.
                System.out.println("Timeout reached!!! " + "could not reach server");
                
                if("lookup".equals(args[2]))
                packet = lookUp(socket,args[3],buf,address,Integer.parseInt(args[1]));
                if("register".equals(args[2]))
                packet=register(socket,args[3],args[4],buf,address,Integer.parseInt(args[1]));
                
                socket.setSoTimeout(10000);
                while(true){        // recieve data until timeout
                    try {
                    	 packet = new DatagramPacket(buf, buf.length);
                         socket.receive(packet);
                         break;
                    }
                    catch (SocketTimeoutException f) {
                    	break;
                    	
                    }
                    }
                
            	}
                socket.close();
                return;
            }
        }
        
       

        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println(received);
    
        socket.close();
    }
    
    public static DatagramPacket lookUp(DatagramSocket socket, String plate, byte[] buf, InetAddress address,int port) throws IOException {
    	String msg = "lookup#"+plate;
    	buf = msg.getBytes();
    	
    	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
		return packet;
    }
    public static DatagramPacket register(DatagramSocket socket, String plate,String owner, byte[] buf, InetAddress address,int port) throws IOException {
    	String msg = "register#"+plate+"#"+owner;
    	buf = msg.getBytes();
    	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
		return packet;
    }  
}
