
public class Page {

	// Returns response header
	public byte[] getHead() {
		return "HTTP/1.1 404 NoPage\n\n".getBytes();
	}
	
	// Returns response
	public byte[] get() {
		return "HTTP/1.1 404 NoPage\n\n<html><head><title>Not a valid page</title></head><body>The page you tried to access does not exist. <br><a href=\"/\">&#8592;Home</a></body></html>".getBytes();
	}
	
}
