import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class PeerStatistics implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<ChunkInformation> list;
	private int maxCapacity;
	private String peerId;
	
	public PeerStatistics() {
		this.peerId = this.generateId();
		this.list = new ArrayList<ChunkInformation >();
		this.maxCapacity = 1000;
	}
	
	private String generateId() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz123456789".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		String output = sb.toString();
		return output;
	}
	
	public void addChunk(String chunkId, int kilobytes, int repdegree,int chunknr) {
		this.list.add(new ChunkInformation(chunkId,kilobytes,repdegree,chunknr));
	}
	public void changePeerCapacity(int storage){
		maxCapacity=storage;
	}
	public String getPeerId() {
		return this.peerId;
	}
	public int getNrofChunks(){
		return list.size();
	}
	public int getMaxCapacity() {
		return this.maxCapacity;
	}
	public ChunkInformation getChunkinfo(int i){
		return list.get(i);
	}
	public String getChunkId(int i) {
		return this.list.get(i).getId();
	}
	
	public int getKilobytes(int i) {
		return this.list.get(i).getKilobytes();
	}
	
	public int getRepDegree(int i) {
		return this.list.get(i).getRepDegree();
	}
	
	public void deleteChunks(String id) {
		for(int i=0; i<this.list.size(); i++) {
			if(this.list.get(i).getId().equals(id))
				this.list.remove(i);
		}
	}
	
	
	//information for each chunk
	public class ChunkInformation implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String chunkId;
		private int kilobytes;
		private int repDegree;
		private int chunknr;
		public ChunkInformation () {
			this.chunkId = new String();
			this.kilobytes = 0;
			this.repDegree = 0;
		}
		
		public ChunkInformation(String chunkId,int kilobytes,int repDegree,int chunknr) {
			this.chunkId = chunkId;
			this.kilobytes = kilobytes;
			this.repDegree = repDegree;
			this.chunknr=chunknr;
		}
		
		public String getId() {
			return this.chunkId;
		}
		
		public int getKilobytes() {
			return this.kilobytes;
		}
		
		public int getRepDegree() {
			return this.repDegree;
		}
		public int getChunknr(){
			return this.chunknr;
		}
	}

}
