import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Connection extends Thread {

	// Network connection
	Socket socket;
	
	private static Map<String, Page> addressToPage = new HashMap<>();
	
	// Get socket for connection
	Connection(Socket socket) {
		this.socket = socket;
	}
	
	// Handles connection
	public void run() {
		try {
			// Creating IO variables
			BufferedInputStream input = new BufferedInputStream(socket.getInputStream());
			OutputStream output = socket.getOutputStream();
			// Reading Request
			StringBuilder request = new StringBuilder();
			long start = System.nanoTime();
			NewLineType os = null;
			while(true) {
				int in = input.read();
				if(in > -1) {
					request.append((char)in);
				}
				int length = request.length();
				try{
					if(request.charAt(length-1) == 10 && request.charAt(length-2) == 13 && request.charAt(length-3) == 10 && request.charAt(length-4) == 13) {
						os = NewLineType.CRLF;
						break;
					} else if(request.charAt(length-1) == 10 && request.charAt(length-2) == 10) {
						os = NewLineType.LF;
						break;
					} else if(request.charAt(length-1) == 13 && request.charAt(length-2) == 13) {
						os = NewLineType.CR;
						break;
					}
				} catch(StringIndexOutOfBoundsException e) {
					
				}
				if(System.nanoTime() - start > 3000000000L) {
					input.close();
					output.close();
					throw new Exception("Timed out");
				}
			}
			// Parsing Request
			String[] lines = null;
			switch(os) {
			case CRLF: 
				lines = request.toString().split("\r\n");
				break;
			case CR: 
				lines = request.toString().split("\r");
				break;
			case LF: 
				lines = request.toString().split("\n");
				break;
			}
			String[] firstLine = lines[0].split(" ");
			// Operations
			switch(firstLine[0]) {
			case "GET": 
				output.write(getPage(firstLine[1]).get());
				break;
			case "HEAD": 
				output.write(getPage(firstLine[1]).getHead());
				break;
			case "POST": 
				Default.getRecords().addRecord(Double.parseDouble(firstLine[1].substring(1)), new Date());
				output.write("HTTP/1.1 200 OK\n\nsuccess".getBytes());
				break;
			default: 
				new Exception(request.toString());
			}
			input.close();
			output.close();
		} catch(Exception e) {
			System.err.println("Connection Failure: " + e);
			//e.printStackTrace();
		}
	}
	
	// Returns Page from String
	private Page getPage(String address) {
		if(addressToPage.containsKey(address)) {
			return addressToPage.get(address);
		} else {
			return new Page();
		}
	}
	
	public static void addPageMapping(String address, Page page) {
		addressToPage.put(address, page);
	}
	
}

enum NewLineType {
	LF, CR, CRLF
}