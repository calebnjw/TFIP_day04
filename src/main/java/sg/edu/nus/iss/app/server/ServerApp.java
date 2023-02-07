package sg.edu.nus.iss.app.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
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
      String resultFile = "cookie_result.txt";
      System.out.println(cookieFile);
      System.out.println(resultFile);

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

      // TODO:
      // to save results from random cookie to disk
      // for reading read the file

      // first read existing file
      // take each line of the file
      // save to list

      // take cookie name and position from list
      // start loop to iterate through list up to position
      // if position has content, move to next
      // if position is empty, add ""
      // once reach position, exit the loop
      // no need to change any further position

      // take list and iterate through entire list
      // write to file

      FileReader fr = new FileReader(resultFile);
      BufferedReader bfr = new BufferedReader(fr);
      String line;
      String[] existingResult;
      while ((line = bfr.readLine()) != null) {
        existingResult(line);
      }
      // for writing a file
      OutputStream fos = new FileOutputStream("cookie_result.txt");
      byte[] buffer = new byte[1024];
      int size = 0;

      String dataFromClient = dis.readUTF(); // this is the command that the user passes into the terminal
      if (dataFromClient.equals("get-cookie")) {
        // get random cookie from method we defined in cookie class
        String[] result = Cookie.getRandomCookie(cookieFile).split(";");
        String randomCookie = result[0];
        String position = result[1];
        dos.writeUTF("cookie-name:" + randomCookie + ":" + position); // server sends data to the client
      } else {
        System.out.println("Invalid Command."); // server prints command is invalid
        dos.writeUTF("Invalid Command."); // server tells the client the command is invalid
      }

      // release resources
      // TODO: not sure how to keep server running to receive more commands
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
