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
	public String RestoreProtocol()  {
		// TODO Auto-generated method stub
		return null;
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
