import java.util.Hashtable;


public class RemoteApplication implements RemoteInterface {
	protected Hashtable<String, String> database = new Hashtable<String,String>();
	public  RemoteApplication() {
	database.put( "AA|44|BB","Fulano");
	database.put("AB|33|CC","Tipo2");}
	@Override
	public String register(String plate,String owner)  {
		String answer;
		
		if(database.contains(plate))
			answer="That Plate has been already registered";
		else{
			database.put(plate,owner);
			answer="Plate registered \n"+owner+":"+plate;
		}
		return answer;
		// TODO Auto-generated method stub
		
	}

	@Override
	public String lookup(String plate) {
		String answer;
		if(database.contains(plate))
			answer=plate+":"+database.get(plate)+" Found";
		else{
			answer="Plate not found";
		}
		return answer;
		// TODO Auto-generated method stub
		
	}

}
