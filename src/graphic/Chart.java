package graphic;

import java.awt.Component;
import java.awt.Container;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import model.Sensor;
import model.SensorType;
import model.Data;

public class Chart {
	
	private LocalDateTime start;
	private LocalDateTime stop;
	private List<Sensor> sensors;
	
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
	
	public Chart(List<Sensor> sensors, LocalDateTime start, LocalDateTime stop) {
		this.sensors=sensors;
		this.start=start;
		this.stop=stop;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getStop() {
		return stop;
	}

	public void setStop(LocalDateTime stop) {
		this.stop = stop;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	public List<SensorType> getSensorType() {
		List<SensorType> types = new ArrayList<>();
		for(Sensor s : sensors) {
			types.add(s.getSensorType());
		}
		return types;
	}
	
	public ChartPanel show(List <Sensor> sensors, LocalDateTime start, LocalDateTime stop,Container frame) {
		JFreeChart chart = ChartFactory.createLineChart("Donnees en fonction du temps","Temps", "Donnees",createCategoryDataset(sensors,start,stop));
		ChartPanel cp = new ChartPanel(chart,true);
		return cp;
	}
	
	private static CategoryDataset createCategoryDataset(List<Sensor> sensors, LocalDateTime start, LocalDateTime stop) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Sensor sensor : sensors ) {
			for (Data data : sensor.getDatas())
			{
				// Si valeur dans l'intervalle de temps choisi par l'utilisateur
				if(data.getMoment().compareTo(start) >= 0 && data.getMoment().compareTo(stop) <= 0) {
				dataset.setValue(data.getValue(), sensor.getName() , String.valueOf(data.getMoment().format(formatter)));
				}
			}
		}
		return dataset;
	}
	
}
