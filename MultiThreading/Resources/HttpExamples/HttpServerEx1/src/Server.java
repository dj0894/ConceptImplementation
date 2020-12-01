import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class SocketUtils {
	
	public static String getRequest(Socket sock) throws Exception {
		
		StringBuilder requestString = new StringBuilder();
		
		byte [] requestBytes = new byte[40000];
		
		InputStream in = sock.getInputStream();
		int n = in.read(requestBytes);
		
		String requestPart1 = null;
		
		if (n != -1) {
			requestPart1 = new String(requestBytes, 0, n);
			requestString.append(requestPart1);
		}
		
		if (requestPart1 != null && requestPart1.contains("multipart/form-data")) {
			n = in.read(requestBytes);
			if (n != -1) {
				requestString.append( new String(requestBytes, 0, n));
			}
		}
		
		return requestString.toString();
	}
}

public class Server {

	public static void main(String[] args) throws Exception {

		ServerSocket serSock = new ServerSocket(8000);
		
		while (true) {
			
			System.out.println("\n\nWaiting for client...\n\n");
			
			Socket sock = serSock.accept();
			
			String request = SocketUtils.getRequest(sock);
			
			System.out.println("Obtained Request .... \n\n" + request );
			
			OutputStream out = sock.getOutputStream();
			
			PrintWriter pw = new PrintWriter(out);
			
			pw.println("HTTP/1.0 200 OK");
			pw.println("Content-type: text/html");
			
			pw.println();
			
			pw.println("<html><body>Welcome</body></html>");
			
			pw.close();
			
			sock.close();
		}
		
	}

}
