import java.io.IOException;
import java.net.ServerSocket;

public class Server {

	// Maintains server socket
	Server() throws IOException {
		ServerSocket server = new ServerSocket(80);
		while(true) {
			new Connection(server.accept()).start();
			System.out.println("Page loaded. ");
		}
	}
	
}
