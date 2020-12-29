import java.util.Date;
import java.util.List;

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
	
	public void show(Sensor sensor, Date start, Date stop, List<Data> data) {
		
	}
	
	public void clear() {
		
	}
}
