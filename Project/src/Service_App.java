import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class Service_App {
	//estou um bocado confuso com as mensagens n percebi se � assim e depois junto numa string ou se pode ser tudo String 
	private static char [] versionId={'1','.','0'};
	private static char [] CR={'0','D'};
	private static char[] LF={'0','A'};
	private static char [] PUTCHANK={'P','U','T','C','H','A','N','K'};
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
		RemoteApplication app=new RemoteApplication();
		RemoteInterface stub=null;
		Registry registry=null;
		DatagramSocket socket = new DatagramSocket();


		byte[] buf = new byte[256];

		DatagramPacket packet;

		String msgHeader = null;
		String msgBody=null;
		String answer=null;
		InetAddress address=InetAddress.getByName(mac);
		String msg=null;

		stub=(RemoteInterface)UnicastRemoteObject.exportObject(app, 0);
		registry= LocateRegistry.getRegistry();
		
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

			int nChunks = 0, read = 0, readLength = 64;

			byte[] byteChunkPart;

			try {

				inputStream = new FileInputStream(inputFile);

				while (fileSize > 0) {

					if (fileSize <=64 ) {

						readLength = fileSize;

					}


					buf = new byte[256];

					byteChunkPart = new byte[readLength];

					read = inputStream.read(byteChunkPart, 0, readLength);

					fileSize -= read;

					assert (read == byteChunkPart.length);

					nChunks++;

					msgHeader="PUTCHANK".getBytes()+" "+Arrays.toString(versionId)+" "+senderId+" "+ filename.toString()+" "+nChunks +" "+CR.toString()+LF.toString();

					msgBody=byteChunkPart.toString();
					msg=msgHeader+msgBody;

					buf = msg.getBytes();
					packet = new DatagramPacket(buf, buf.length, address, Integer.parseInt(args[0]));
					socket.send(packet);
				
					//Espera pelas respostas dos peers 
					while(answerCount<repDegree){
						
						socket.setSoTimeout(5000);
						try{
						packet = new DatagramPacket(buf, buf.length);
						socket.receive(packet);
						registry.bind("SDIS", stub);
						answer = new String(packet.getData(), 0, packet.getLength());
						answerCount++;
						}
						catch (SocketTimeoutException e) {
							System.out.println("still missing "+(repDegree-answerCount)+" answers");
							System.out.println("re-sending");
							socket.send(packet);
							
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



		}	

		//BACKUP
		if(args[0].equals("RESTORE") && args.length==4){
			//envio da mensagem para o grupo multicast
			//tratar a recepc��o da resposta, o backup por exemplo tem que receber o numero de respostas iguais ao replication degree
			//Cria o objecto RMI para executar os subprotocols 
			//fazer igual para os ifs todos 

			stub=(RemoteInterface)UnicastRemoteObject.exportObject(app, 0);
			registry= LocateRegistry.getRegistry();
			registry.bind("Service", stub);
		}
		//RESTORE 
		if(args[0].equals("DELETE")){
			//envio da mensagem para o grupo multicast
			//tratar a recepc��o da resposta, o backup por exemplo tem que receber o numero de respostas iguais ao replication degree
			//Cria o objecto RMI para executar os subprotocols 
			//fazer igual para os ifs todos 

			stub=(RemoteInterface)UnicastRemoteObject.exportObject(app, 0);
			registry= LocateRegistry.getRegistry();
			registry.bind("Service", stub);
		}
		//DELETE
		if(args[0].equals("STATE")){
			//envio da mensagem para o grupo multicast
			//tratar a recepc��o da resposta, o backup por exemplo tem que receber o numero de respostas iguais ao replication degree
			//Cria o objecto RMI para executar os subprotocols 
			//fazer igual para os ifs todos 

			stub=(RemoteInterface)UnicastRemoteObject.exportObject(app, 0);
			registry= LocateRegistry.getRegistry();
			registry.bind("Service", stub);
		}
		//Space allocation management


	}


}
