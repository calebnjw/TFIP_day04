package sg.edu.nus.iss.app.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

// import static sg.edu.nus.iss.app.server.Cookie;

public class ServerApp {
  private static final String ARG_MESSAGE = "sg.edu.nus.iss.app.server.ServerApp [Port No.] [Cookie File]";

  public static void main(String[] args) {

    try {
      // get server port from java cli first arg
      String serverPort = args[0];
      System.out.println(serverPort);

      // get cookie file from second arg
      String cookieFile = args[1];
      System.out.println(cookieFile);

      // instantiate socket server with port number
      ServerSocket server = new ServerSocket(Integer.parseInt(serverPort));
      System.out.printf("Cookie server started on Port %s \n", serverPort);

      // client connect to the server
      // server accepts connection from client
      Socket sock = server.accept();

      // this is for input data from client program (in bytes) to come in
      InputStream is = sock.getInputStream();
      // convert from bytes into something readable
      DataInputStream dis = new DataInputStream(is);

      // this is for sending data to the client
      OutputStream os = sock.getOutputStream();
      DataOutputStream dos = new DataOutputStream(os);

      String dataFromClient = dis.readUTF(); // this is the command that the user passes into the terminal
      if (dataFromClient.equals("get-cookie")) {
        // get random cookie from method we defined in cookie class
        String randomCookie = Cookie.getRandomCookie(cookieFile);
        dos.writeUTF("cookie-name:" + randomCookie); // this is where the server sends data to the client
      } else {
        dos.writeUTF("Invalid Command."); // this is where the server tells the client the command is invalid
      }

      // release resources
      is.close();
      os.close();
      sock.close();

    } catch (ArrayIndexOutOfBoundsException e) {
      // tell the user what the command format is
      System.out.println(ARG_MESSAGE);
      System.exit(1);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
