package sg.edu.nus.iss.app.client;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientApp {
  public static void main(String[] args) {
    String hostname = args[0].split(":")[0];
    String port = args[0].split(":")[1];

    System.out.println(hostname);
    System.out.println(port);

    try {
      Socket sock = new Socket(hostname, Integer.parseInt(port));

      // this is for results from server to come in
      InputStream is = sock.getInputStream();
      DataInputStream dis = new DataInputStream(is);

      // this is for sending commands to the server
      OutputStream os = sock.getOutputStream();
      DataOutputStream dos = new DataOutputStream(os);

      Console console = System.console();
      String input = console.readLine("Command to send to the cookie server: ");

      dos.writeUTF(input); // this is where the command is sent to the server
      dos.flush(); // clear the output stream so it will not repeat anything

      String response = dis.readUTF();
      if (response.contains("cookie-name")) {
        String cookie = response.split(":")[1];
        System.out.println("Cookie you got: " + cookie);
      } else {
        System.out.println(response);
      }

      // this is to clear resources and close connections
      // not sure how to keep application running to receive more commands
      is.close();
      os.close();
      sock.close();

    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
