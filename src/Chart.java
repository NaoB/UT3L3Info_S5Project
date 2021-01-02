import java.awt.Component;
import java.awt.FlowLayout;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import model.Sensor;
import model.SensorType;
import model.Data;

public class Chart {
	
	private Date start;
	private Date stop;
	private Sensor sensor;
	private List<Data> values;
	
	public Chart(Sensor sensor, Date start, Date stop, List<Data> data ) {
		this.sensor=sensor;
		this.start=start;
		this.stop=stop;
		this.values=data;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getStop() {
		return stop;
	}

	public void setStop(Date stop) {
		this.stop = stop;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public List<Data> getValues() {
		return values;
	}

	public void setValues(List<Data> values) {
		this.values = values;
	}

	public SensorType getSensorType() {
		return sensor.getSensorType();
	}
	
	public ChartPanel show(List <Sensor> sensors, Date start, Date stop, List<Data> data,JFrame frame) {
		JFreeChart chart = ChartFactory.createLineChart("Donnees en fonction temps","Temps", "Donnees",createCategoryDataset(sensors));
		ChartPanel cp = new ChartPanel(chart,true);
		return cp;
	}
	
	private static CategoryDataset createCategoryDataset(List<Sensor> sensors) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Random r = new Random();
		for (Sensor sensor : sensors ) {
			dataset.setValue(r.nextInt(30), sensor.getName() , "Jan-Mar");
			dataset.setValue(r.nextInt(30), sensor.getName() , "Avr-Jui");
			dataset.setValue(r.nextInt(30), sensor.getName() , "Jui-Sep");
			dataset.setValue(r.nextInt(30), sensor.getName() , "Oct-Dec");
			dataset.setValue(r.nextInt(30), sensor.getName() , "Jan-Mar");
			dataset.setValue(8, sensor.getName() , "Avr-Jui");
			dataset.setValue(12, sensor.getName() , "Jui-Sep");
			dataset.setValue(24, sensor.getName() , "Oct-Dec");
			dataset.setValue(r.nextInt(30),sensor.getName() , "Jan-Mar");
			dataset.setValue(4, sensor.getName() , "Avr-Jui");
			dataset.setValue(r.nextInt(30), sensor.getName() , "Jui-Sep");
			dataset.setValue(1, sensor.getName() , "Oct-Dec");
		}
		
		return dataset;

	}
	
	public void clear(JFrame frame, Component comp) {
		frame.remove(comp);
	}
}
