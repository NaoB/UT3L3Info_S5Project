package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.List;

import model.Building;
import model.Data;
import model.Sensor;
import model.SensorType;

public class ClientProcessor implements Runnable{

   private Socket sock;
   private BufferedReader reader = null;
   
   public ClientProcessor(Socket pSock) {
      sock = pSock;
   }
   
   public void run() {

      boolean closeConnexion = false;

      while (!sock.isClosed()) {
         try {
//        	InetSocketAddress remote = (InetSocketAddress) sock.getRemoteSocketAddress();
            
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));            
            String message = reader.readLine();
            
            if (message != null) {
//            	String debug = "";
//                String[] resParts = message.split(" ");
//            	debug += "\nTYPE: " + resParts[0];
//                debug += "\nCAPTEUR: " + resParts[1];
//                if (resParts.length >= 3) {
//                	debug += "\nDATA: " + resParts[2];
//                }
//                System.out.println(debug);
//                if (resParts[0].equals("Deconnexion")) {
//                	closeConnexion = true;
//                }
            	System.out.println("[network.ClientProcessor] New message : " + message);
                parseMessage(message);
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
   
   private void parseMessage(String msg) {
	   String[] resParts = msg.split(" ");
	   switch (resParts[0]) {
	   	case "Connexion":
			connexion(resParts);
	   		break;
	
	   	case "Donnee":
	   		donnee(resParts);
	   		break;
	   		
	   	case "Deconnexion":
	   		deconnexion(resParts);
	   		break;
	   	default:
	   		System.out.println("Unknow message type");
			break;
		}
   }
   
   private void connexion(String[] data) {
	   String name = data[1];
	   String[] description = data[2].split(":");
	   SensorType sensorType = SensorType.findOne(description[0]);
	   Building building = Building.findOne(description[1]);
	   if (building == null) {
		   building = new Building(description[1]);
		   building.save();
	   }
	   int floor = Integer.parseInt(description[2]);
	   String location = description[3];
	   // Search if sensor already exists
	   Sensor sensor = Sensor.findOne(name);
	   if (sensor == null) {
		   sensor = new Sensor(name, sensorType, building, floor, location);
	   }
	   sensor.setConnected(true);
	   sensor.save();		   
   }
   
   private void donnee(String[] data) {
	   Sensor sensor = Sensor.findOne(data[1]);
	   // If sensor doesn't exists do nothing
	   if (sensor != null) {
		 float value = Float.parseFloat(data[2]);
		 sensor.setValue(value);
		 sensor.save();
		 Data newData = new Data(LocalDateTime.now(), sensor, value);
		 newData.save();
	   }
   }
   
   private void deconnexion(String[] data) {
	   Sensor sensor = Sensor.findOne(data[1]);
	   // If sensor doesn't exists do nothing
	   if (sensor != null) {
		 sensor.setConnected(false);
		 sensor.save();
	   }
   }
      
}