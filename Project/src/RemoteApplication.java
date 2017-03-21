import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class RemoteApplication implements RemoteInterface{
	protected String [] SenderIds;
	public RemoteApplication(){

	}
	@Override
	public String BackupProtocol(String filename,String Chunkname)  {
		File inputFile = new File(filename);

		FileInputStream inputStream;

		String newFileName;

		FileOutputStream filePart;

		int fileSize = (int) inputFile.length();

		 int nChunks = 0, read = 0, readLength = 64;

		byte[] byteChunkPart;

		try {

			inputStream = new FileInputStream(inputFile);

			while (fileSize > 0) {

				if (fileSize <=64 ) {

					readLength = fileSize;

				}

				byteChunkPart = new byte[readLength];

				read = inputStream.read(byteChunkPart, 0, readLength);

				fileSize -= read;

				assert (read == byteChunkPart.length);

				nChunks++;

				newFileName =Chunkname+"@"+nChunks;

				                       

				filePart = new FileOutputStream(new File(newFileName));

				filePart.write(byteChunkPart);

				filePart.flush();

				filePart.close();

				byteChunkPart = null;

				filePart = null;

			}
				inputStream.close();

			} catch (IOException exception) {

				exception.printStackTrace();

			}

			return null;
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
