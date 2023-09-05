
public class DefaultPage extends Page {

	// Returns response header
	public byte[] getHead() {
		return "HTTP/1.1 200 OK\n\n".getBytes();
	}
	
	// Returns response
	public byte[] get() {
		return ("HTTP/1.1 200 OK\n\n" + 
				"<html><head><title>Weight Tracker</title></head>" + 
				"<script>\n function home() {location.reload();}"
				+ "function add() {"
				+ "\nxhttp = new XMLHttpRequest();"
				+ "\nxhttp.open(\"POST\", weight, true);"
				+ "\nxhttp.onload = function run() {"
				+ "\nif(xhttp.responseText == \"success\") { document.open(); document.write(\"<b>\" + weight + \"</b>lbs added. \");}"
				+ "\nelse { document.open(); document.write(\"Something may have gone wrong.\")}"
				+ "document.write(\"<br><button onclick='home()'>Continue</button>\")}"
				+ "\nxhttp.send();"
				+ "}"
				+ "\nfunction no() {"
				+ "location.reload();"
				+ "}"
				+ "\nfunction check() {"
				+ "\nweight = document.getElementById(\"weight\").value;"
				+ "\ndocument.clear();"
				+ "\ndocument.write(\"Are you sure you wish to add <b>\" + weight + \"</b>lbs?<br><button onclick='add()'>Yes</button><button onclick='no()'>No</button>\"); }\n </script>" + 
				"<body>Weight: <input id=\"weight\"><br><button onclick=\"check()\">Add Data</button></body></html>").getBytes();
	}
	
}
