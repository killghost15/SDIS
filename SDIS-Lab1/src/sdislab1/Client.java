package sdislab1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) throws IOException {

        if (args.length < 4) {
             System.out.println("Usage: java Client <host_name> <port_number> <oper> <opnd>*");
             return;
        }

        DatagramSocket socket = new DatagramSocket();

        byte[] buf = new byte[256];
        InetAddress address = InetAddress.getByName(args[0]);
        DatagramPacket packet;
        if("lookup".equals(args[2]))
        packet = lookUp(socket,args[3],buf,address);
        if("register".equals(args[2]))
        packet=register(socket,args[3],args[4],buf,address);
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println(received);
    
        socket.close();
    }
    
    public static DatagramPacket lookUp(DatagramSocket socket, String plate, byte[] buf, InetAddress address) throws IOException {
    	String msg = "lookup#"+plate;
    	buf = msg.getBytes();
    	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
		return packet;
    }
    public static DatagramPacket register(DatagramSocket socket, String plate,String owner, byte[] buf, InetAddress address) throws IOException {
    	String msg = "register#"+plate+"#"+owner;
    	buf = msg.getBytes();
    	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
		return packet;
    }  
}
