package network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.util.Date;

public class ClientProcessor implements Runnable{

   private Socket sock;
   private BufferedReader reader = null;
   
   public ClientProcessor(Socket pSock){
      sock = pSock;
   }
   
   public void run(){

      boolean closeConnexion = false;

      while(!sock.isClosed()){
         try {
        	InetSocketAddress remote = (InetSocketAddress)sock.getRemoteSocketAddress();
            
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));            
            String response = reader.readLine();
            
            if (response != null) {
            	String debug = "";
                String[] resParts = response.split(" ");
            	debug += "\nTYPE: " + resParts[0];
                debug += "\nCAPTEUR: " + resParts[1];
                if (resParts.length >= 3) {
                	debug += "\nDATA: " + resParts[2];
                }
                System.out.println(debug);
                if (resParts[0].equals("Deconnexion")) {
                	closeConnexion = true;
                }
            }
                        
            if(closeConnexion){
               System.out.println("Deconnexion du capteur");
               reader = null;
               sock.close();
               break;
            }
         } catch(SocketException e){
            System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
            break;
         } catch (IOException e) {
            e.printStackTrace();
         }         
      }
   }
      
}