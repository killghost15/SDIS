import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class Service_App {
	//estou um bocado confuso com as mensagens n percebi se � assim e depois junto numa string ou se pode ser tudo String 
	private static byte [] versionId={'1','.','0'};
	/* #TODO aldrabei para string pk perdi a paciencia
	private static byte[] CR ={'0','x','D'};
	private static byte[] LF={'0','x','A'};
	*/
	private static String CR="0xD";
	private static String LF="0xA";
	private static String mac="224.0.0.3";
	//will need to change senderId to be generated by SHA
	private static String senderId="Eu";
	public static void main(String[] args) throws AlreadyBoundException, IOException, NoSuchAlgorithmException {
		if(args.length==0){
			System.out.println("usage like:");
			System.out.println("java Service_App <port><BACKUP><filename><replicationDeg>");
			System.out.println("java Service_App <port><RESTORE><filename>");
			return;
		}
		//inicializa��o das variaveis 
		
		RemoteInterface stub=null;
		Registry registry=null;
		DatagramSocket socket = new DatagramSocket();


		byte[] buf;

		DatagramPacket packetsend;
		DatagramPacket packetreceive;

		String msgHeader = null;
		String msgBody=null;
		String answer=null;
		InetAddress address=InetAddress.getByName(mac);
		String msg=null;

		
		
		if(args[1].equals("BACKUP") /*&& args.length==4*/ ){
			int repDegree=Integer.parseInt(args[3]);
			MessageDigest md =MessageDigest.getInstance("SHA-256");
			md.update(args[2].getBytes());
			byte byteData[]=md.digest();
			int answerCount=0;
			//convert to hex
			StringBuffer filename = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				filename.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			//envio da mensagem para o grupo multicast
			//filename=args[2]


			File inputFile = new File(args[2]);

			FileInputStream inputStream;

			int fileSize = (int) inputFile.length();

			int nChunks = 0, read = 0, readLength=64*1000;

			byte[] byteChunkPart;

			try {

				inputStream = new FileInputStream(inputFile);

				while (fileSize > 0) {

					if (fileSize <=readLength ) {

						readLength = fileSize;

					}


					buf = new byte[readLength];

					byteChunkPart = new byte[readLength];

					read = inputStream.read(byteChunkPart, 0, readLength);

					fileSize -= read;

					assert (read == byteChunkPart.length);

					nChunks++;
					
					msgHeader="PUTCHUNK"+" "+versionId+" "+senderId+" "+ filename.toString()+" "+nChunks +" "+repDegree +" "+CR+LF+" ";
					
					msgBody=new String(byteChunkPart);
					msg=msgHeader+msgBody;
					//System.out.println(msg);
					//System.out.println("Buffer");
					
					buf=msg.getBytes();
					System.out.println(buf);
					//first message gets sent to multicastgroup
					packetsend = new DatagramPacket(buf, buf.length, address, Integer.parseInt(args[0]));
					socket.send(packetsend);
					
					//waits for response of peers and the number of responses must be equal to repdegree or gets re-sent
					//#TODO test with more peers and bigger repdegree than 1 so see if there is an issue around all peers answering at same time :o
					while(answerCount<repDegree){
						//Timeout so it re-sends 
						socket.setSoTimeout(5000);
						try{
						//cleans up buffer
						buf=new byte[256];
						packetreceive = new DatagramPacket(buf, buf.length);
						//waits for reception or launches exception
						socket.receive(packetreceive);
						//when received shows the answer
						answer = new String(packetreceive.getData(), 0, packetreceive.getLength());
						System.out.println(answer);
						answerCount++;
						try {
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//on reception uses that packet's sender address to make the remote method of savin the chunk
						registry = LocateRegistry.getRegistry(packetreceive.getAddress().getHostName());
				        stub=(RemoteInterface)registry.lookup("Teste2");
				        //
				        stub.StoreBackupProtocol(filename.toString(), nChunks, byteChunkPart);
						
						}
						catch (SocketTimeoutException e) {
							System.out.println("still missing "+(repDegree-answerCount)+" answers");
							System.out.println("re-sending");
							socket.send(packetsend);
							
						} catch (NotBoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					byteChunkPart = null;
					answerCount=0;
					
				}
				inputStream.close();
				
			} catch (IOException exception) {

				exception.printStackTrace();

			}

			
			//tratar a recepc��o da resposta, o backup por exemplo tem que receber o numero de respostas iguais ao replication degree
			//Cria o objecto RMI para executar os subprotocols 
			//fazer igual para os ifs todos 

			
			
			socket.close();
			
		}	

		//BACKUP
		/*if(args[1].equals("RESTORE") && args.length==4){
			//envio da mensagem para o grupo multicast
			//tratar a recepc��o da resposta, o backup por exemplo tem que receber o numero de respostas iguais ao replication degree
			//Cria o objecto RMI para executar os subprotocols 
			//fazer igual para os ifs todos 

			stub=(RemoteInterface)UnicastRemoteObject.exportObject(app, 0);
			registry= LocateRegistry.getRegistry();
			registry.bind("Service", stub);
		}
		//RESTORE 
		if(args[1].equals("DELETE")){
			//envio da mensagem para o grupo multicast
			//tratar a recepc��o da resposta, o backup por exemplo tem que receber o numero de respostas iguais ao replication degree
			//Cria o objecto RMI para executar os subprotocols 
			//fazer igual para os ifs todos 

			stub=(RemoteInterface)UnicastRemoteObject.exportObject(app, 0);
			registry= LocateRegistry.getRegistry();
			registry.bind("Service", stub);
		}
		//DELETE
		if(args[1].equals("STATE")){
			//envio da mensagem para o grupo multicast
			//tratar a recepc��o da resposta, o backup por exemplo tem que receber o numero de respostas iguais ao replication degree
			//Cria o objecto RMI para executar os subprotocols 
			//fazer igual para os ifs todos 

			stub=(RemoteInterface)UnicastRemoteObject.exportObject(app, 0);
			registry= LocateRegistry.getRegistry();
			registry.bind("Service", stub);
		}
		//Space allocation management
*/

	}


}
