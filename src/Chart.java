import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
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
	private List<Sensor> sensors;
	private List<Data> values;
	
	public Chart(List<Sensor> sensors, Date start, Date stop, List<Data> data ) {
		this.sensors=sensors;
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

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	public List<Data> getValues() {
		return values;
	}

	public void setValues(List<Data> values) {
		this.values = values;
	}

	public List<SensorType> getSensorType() {
		List<SensorType> types = new ArrayList<>();
		for(Sensor s : sensors) {
			types.add(s.getSensorType());
		}
		return types;
	}
	
	public ChartPanel show(List <Sensor> sensors, Date start, Date stop,JFrame frame) {
		JFreeChart chart = ChartFactory.createLineChart("Donnees en fonction du temps","Temps", "Donnees",createCategoryDataset(sensors));
		ChartPanel cp = new ChartPanel(chart,true);
		return cp;
	}
	
	private static CategoryDataset createCategoryDataset(List<Sensor> sensors) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Sensor sensor : sensors ) {
			for (Data data : sensor.getDatas())
			{
				dataset.setValue(data.getValue(), sensor.getName() , data.getMoment().toString());
			}
		}
		return dataset;
	}
	
	public void clear(JFrame frame, Component comp) {
		frame.remove(comp);
	}
}
