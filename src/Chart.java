import java.awt.FlowLayout;
import java.util.Date;
import java.util.List;

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
	
	public void show(Sensor sensor, Date start, Date stop, List<Data> data,JFrame frame) {
		JFreeChart chart = ChartFactory.createLineChart("Donnees en fonction temps","Temps", "Donnees",createCategoryDataset());
		ChartPanel cp = new ChartPanel(chart,true);
		frame.add(cp);
	}
	
	private static CategoryDataset createCategoryDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(10, "SELLER 1" , "Jan-Mar");
		dataset.setValue(8, "SELLER 1" , "Avr-Jui");
		dataset.setValue(12, "SELLER 1" , "Jui-Sep");
		dataset.setValue(20, "SELLER 1" , "Oct-Dec");
		dataset.setValue(4, "SELLER 2" , "Jan-Mar");
		dataset.setValue(8, "SELLER 2" , "Avr-Jui");
		dataset.setValue(12, "SELLER 2" , "Jui-Sep");
		dataset.setValue(24, "SELLER 2" , "Oct-Dec");
		dataset.setValue(30, "SELLER 3" , "Jan-Mar");
		dataset.setValue(4, "SELLER 3" , "Avr-Jui");
		dataset.setValue(12, "SELLER 3" , "Jui-Sep");
		dataset.setValue(1, "SELLER 3" , "Oct-Dec");
		return dataset;

	}
	
	public void clear() {
		
	}
}
