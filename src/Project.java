import network.Server;

public class Project {

	public static void main(String[] args) {
		
		Server server = new Server(8952);
		server.open();
		
//		SensorType EAU = new SensorType("EAU", "m3", 0, 10);
//		SensorType AIR = new SensorType("AIRCOMPRIME", "m3/h", 0, 5);
//		SensorType ELECTRICITE = new SensorType("ELECTRICITE", "kWh", 10, 500);
//		SensorType TEMPERATURE = new SensorType("TEMPERATURE", "°C", 17, 22);
//		EAU.save();
//		AIR.save();
//		ELECTRICITE.save();
//		TEMPERATURE.save();
		
//		Building bat1 = new Building("Batiment 1");
//		bat1.save();
//		
//		Sensor s1 = new Sensor("S1", EAU, bat1, 1, "Salle201");
//		s1.save();
//		Sensor s2 = new Sensor("S2", EAU, bat1, 2, "Salle301");
//		s2.save();
//		
//		Random rdm = new Random();
//		for (int i = 0; i < 10; i++) {
//			Data x = new Data(LocalDateTime.now(), i % 2 == 0 ? s1 : s2, rdm.nextFloat());
//			x.save();
//		}
//		
//		for (Sensor s : EAU.getSensors()) {
//			System.out.println(s.toString());
//		}
	}
	
}
