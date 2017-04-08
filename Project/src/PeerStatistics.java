import java.util.ArrayList;

public class PeerStatistics {
	
	private ArrayList<ChunkInformation> list;
	private int maxCapacity;
	
	public PeerStatistics() {
		this.list = new ArrayList<ChunkInformation >();
		this.maxCapacity = 1000;
	}
	
	public void addChunk(String chunkId, int kilobytes, int repdegree) {
		this.list.add(new ChunkInformation(chunkId,kilobytes,repdegree));
	}
	
	public void getChunkId(int i) {
		this.list.get(i).getId();
	}
	
	public void getKilobytes(int i) {
		this.list.get(i).getKilobytes();
	}
	
	public void getRepDegree(int i) {
		this.list.get(i).getRepDegree();
	}
	
	
	//information for each chunk
	public class ChunkInformation {
		private String chunkId;
		private int kilobytes;
		private int repDegree;
		
		public ChunkInformation () {
			this.chunkId = new String();
			this.kilobytes = 0;
			this.repDegree = 0;
		}
		
		public ChunkInformation(String chunkId,int kilobytes,int repDegree) {
			this.chunkId = chunkId;
			this.kilobytes = kilobytes;
			this.repDegree = repDegree;
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
	}

}
