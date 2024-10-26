
import java.io.*;
import java.net.*;

class TCPClient2 {
	public static void main(String args[]) throws Exception {
		String modifiedSentence;
        String file = "questions.txt";

		Socket clientSocket = new Socket(args[0], 6789);
		DataOutputStream outToServer = new DataOutputStream(
				clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));

         // Initialize BufferedReader inside try-with-resources
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            // Read each line until end of file is reached
            System.out.println("Reading from file:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                outToServer.writeBytes(line + '\n');
		        modifiedSentence = inFromServer.readLine();
		        System.out.println("FROM SERVER: " + modifiedSentence);
            }
            outToServer.writeBytes("DONE\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

		clientSocket.close();
	}
}