import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Peer {

	
	/* #TODO aldrabei para string pk perdi a paciencia
	private static byte[] CR ={'0','x','D'};
	private static byte[] LF={'0','x','A'};
	 */
	private static String CR="\r";
	private static String LF="\n";
	public Peer(){


	}
	public static void main(String[] args) throws NumberFormatException, IOException, NotBoundException{
		if(args.length<6){
			System.out.println("usage like:");
			System.out.println("java Peer <portmc><mcaddress><portmdb><mdbaddress><portmdr><mdraddress>");
			
			return;
		}
		//Estabilish connection with Mac group
		MulticastSocket mc = new MulticastSocket(Integer.parseInt(args[0]));
		InetAddress mcaddress = InetAddress.getByName(args[1]);
		
		MulticastSocket mdb = new MulticastSocket(Integer.parseInt(args[2]));
		InetAddress mdbaddress = InetAddress.getByName(args[3]);
		MulticastSocket mdr = new MulticastSocket(Integer.parseInt(args[4]));
		InetAddress mdraddress = InetAddress.getByName(args[5]);
		
		//host_name
		String []splittedHead;

		byte[] buf = new byte[64000];
		

		RemoteApplication app=new RemoteApplication();
		RemoteInterface stub=null;
		Registry registry=null;

		stub=(RemoteInterface)UnicastRemoteObject.exportObject(app, 0);
		registry= LocateRegistry.getRegistry();

		DatagramPacket packetreceive = null;
		DatagramPacket packetsend = null;

		//junta-se ao grupo com o mac
		mc.joinGroup(mcaddress);
		mdb.joinGroup(mdbaddress);
		mdr.joinGroup(mdraddress);
		String msghead=null;
		String msgbody=null;
		String answer=null;
		byte[] doublebuf;
		try {
			registry.bind("Teste2", stub);
		} catch (AlreadyBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Treat the message received loop infinito
		//need a break condition that is usefull

		while(true){
			buf = new byte[stub.getreadLength()];
			packetreceive = new DatagramPacket(buf, buf.length);
			
			//State loop to check messages in each multicast chanel
			while(true){
				mc.setSoTimeout(1000);
				mdb.setSoTimeout(1000);
				mdr.setSoTimeout(1000);
				try{
					mc.receive(packetreceive);
					break;
					}catch(SocketTimeoutException e){
						try{
							
							mdb.receive(packetreceive);
							break;
							
							}catch(SocketTimeoutException e1){
								
								try{
									
									mdr.receive(packetreceive);
									break;
									}catch(SocketTimeoutException e2){
										
										
									}
							}
					}
				
				
			}
				
			String request = new String(packetreceive.getData(), 0, packetreceive.getLength());

			/*System.out.println(CR+LF);
		byte b[]={0xD,0xA};
		System.out.println(new String(b.toString()));
		System.out.println(new String(b).getBytes());
			 */
			//Regex this way you can put as much spaces as you want it will still divide it the same
			msghead=request.split(" +"+CR+LF+" +")[0];

			System.out.println(msghead);


			splittedHead=msghead.split(" +");
			//need to convert the "PUTCHUNK" Bytes to String or find away to split the bytes data
			if(splittedHead[0].equals("PUTCHUNK")){
				msgbody=request.split(" +"+CR+LF+" +")[1];
				buf=new byte[256];

				System.out.println("Saved file");
				answer="STORED "+" "+splittedHead[1]+" "+splittedHead[2]+" " +splittedHead[3]+" " + splittedHead[4]+" " +CR+LF+" ";
				buf=answer.getBytes();

				packetsend=new DatagramPacket(buf, buf.length,packetreceive.getAddress(),packetreceive.getPort());
				mc.send(packetsend);

				registry.rebind("Teste2", stub);


			}
			if(splittedHead[0].equals("GETCHUNK")){
				
				File folder = new File(System.getProperty("user.dir"));
				for(int i=0;i<folder.listFiles().length;i++){

					if(folder.listFiles()[i].getName().split(".part")[0].equals(splittedHead[3])){
						System.out.println(splittedHead[3]);
						
						doublebuf=ReadFile(folder.listFiles()[i].getName());
						msghead="CHUNK "+ splittedHead[1]+" "+splittedHead[2]+" "+splittedHead[3]+" "+folder.listFiles()[i].getName().split(".part")[1]+" "+CR+LF+" ";
						buf=new byte[msghead.getBytes().length+doublebuf.length];
						System.arraycopy(msghead.getBytes(), 0, buf, 0, msghead.getBytes().length);
						System.arraycopy(doublebuf, 0, buf, msghead.getBytes().length, doublebuf.length);
						packetsend=new DatagramPacket(buf, buf.length,packetreceive.getAddress(),packetreceive.getPort());
						mdr.send(packetsend);
					}
				}



			}



		}
		//socket.close();
	}

	public static byte[] ReadFile(String filename){

		byte[] byteChunkPart;
		File inputFile = new File(filename);

		FileInputStream inputStream;
		
		int fileSize = (int) inputFile.length();
		byteChunkPart = new byte[fileSize];
		int read = 0;


		try {
			inputStream = new FileInputStream(inputFile);


			

			read = inputStream.read(byteChunkPart, 0, fileSize);

			fileSize -= read;

			assert (read == byteChunkPart.length);
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}


return byteChunkPart;
	}

}