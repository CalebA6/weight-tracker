import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Records {
	
	// Holds records
	private List<Record> records;
	
	// Where data is stored
	private File file;
	
	// Handles Date input and output
	DateFormat dateFormat = DateFormat.getDateInstance();

	// Reads in data from file to create object
	Records(File file) throws FileNotFoundException, ParseException {
		this.file = file;
		records = new ArrayList<>();
		if(file.exists()) {
			Scanner input = new Scanner(file);
			while(input.hasNextLine()) {
				String line = input.nextLine();
				String[] data = line.split("\t");
				Record record = new Record();
				record.date = dateFormat.parse(data[0]);
				Scanner weight = new Scanner(data[1]);
				record.weight = weight.nextDouble();
				weight.close();
				records.add(record);
			}
			input.close();
		}
	}
	
	// Adds record to object
	public void addRecord(double weight, Date date) throws IOException {
		Record record = new Record();
		record.weight = weight;
		record.date = date;
		records.add(record);
		// Write file
		if(!file.exists()) {
			file.createNewFile();
		}
		PrintWriter output = new PrintWriter(file);
		for(Record r: records) {
			output.println((dateFormat.format(r.date) + "\t" + r.weight));
		}
		output.close();
	}
	
	// Graphs data  // FIXME: Removed for now.
	/*public byte[] getGraph() {
		
	}*/
	
}

// Combines two pieces of data
class Record {
	public double weight;
	public Date date;
}