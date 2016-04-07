import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketsClient {
    public static void main(String[] args) {
        //validate number of parameters
        if (args.length != 4) {
            System.err.println(
                    "Usage: java SocketsClient  <host name> <port number> <country or state> <city>");
            System.exit(1);
        }

        //parse parameters
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String country = args[2];
        String city = args[3];

        try (
                Socket wSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(wSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(wSocket.getInputStream()));
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser = country + "%" + city;

            fromServer = in.readLine();
            System.out.println("Server: " + fromServer);

            if (fromServer.equals("Closed")) {
                try {
                    wSocket.close();
                } catch (IOException ioe) {
                    System.err.println("The client was unable to close the connection to the server.");
                }
            } else if (fromUser != null) {
                System.out.println("Client: " + fromUser);
                out.println(fromUser);

                //get response from server
                while ((fromServer = in.readLine()) != null) {
                    System.out.println("Server: " + fromServer);
                }
            } else {
                fromUser = "Closed";
                out.println(fromUser);
            }

        } catch (UnknownHostException e) {
            System.err.println("Could not connect to " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName + ". It seems the server is not running on port " + portNumber + ".");
            System.exit(1);
        }
    }
}

