import java.io.File;

public class Default {

	// Contains weight records
	private static Records record;
	
	// Creates server and record
	public static void main(String[] args) {
		try {
			record = new Records(new File("weight.tsv"));
			Connection.addPageMapping("/", new DefaultPage());
			new Server();
		} catch (Exception e) {
			System.err.println("Software Failure: " + e);
		}
	}

	// Returns record object
	public static Records getRecords() {
		return record;
	}
	
}
