import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class RemoteApplication implements RemoteInterface{
	protected String [] SenderIds;
	String metadatafile=".metadata";
	public RemoteApplication(){
		try {
			FileOutputStream file=new FileOutputStream(".metadata", true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public String StoreBackupProtocol(String filename,int Chunknr,byte[]content)  {
		String answer;		
		FileOutputStream filePart;
		
				try {
					filePart = new FileOutputStream(new File(filename+".part"+Chunknr));
					filePart.write(content);
					filePart.flush();
					filePart.close();
					FileOutputStream file=new FileOutputStream(metadatafile,true);
					file.write(filename.getBytes());
					file.flush();
					file.close();
					answer="Stored";
				} catch (IOException exception) {

					answer="Error with saving chunk locally";

				}

		return answer;
	}

	@Override
	public String RestoreProtocol(String filename, int Chunknr) throws IOException{
		
		
		int read;
		File file = new File(filename);
		FileOutputStream out = new FileOutputStream(file);
		
		for(int i=1; i<=Chunknr; i++) {
			File chunk = new File(filename+".part"+i);
			FileInputStream in = new FileInputStream(chunk);
			byte[] bytes = new byte[(int) chunk.length()];
			read = in.read(bytes,0,(int) chunk.length());
			
			out.write(bytes);
			out.flush();
			in.close();
		}
		out.close();
		
		
		return "File restored with sucess";
	}

	@Override
	public String DeleteProtocol()  {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ReclaimProtocol()  {
		// TODO Auto-generated method stub
		return null;
	}


}
