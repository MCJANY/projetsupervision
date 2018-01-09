package fr.cfi.model;

import java.util.Random;

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class ChartModel {

	private IChartModelListener listener;
	private TimeSeriesCollection dataset ;
	private TimeSeries series;
	private Random random;
	private boolean started = false;

	public ChartModel(final IChartModelListener listener) {
		this.listener = listener;
		dataset = new TimeSeriesCollection();
		series = new TimeSeries("CPU Usage");
		dataset.addSeries(series);
		random = new Random();
	}

	private void createDataset() {
		int nextInt  = new CPUInformation();
		//int nextInt = random.nextInt(100);
		series.add(new Millisecond(),nextInt);
		listener.dataChanged(dataset);
	}
	
	public void startMonitoring() {
		if (!started) {
			started = true;
			Thread thread = new Thread() {
				public void run() {
					while (started) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						createDataset();
					}
				};
			};
			thread.start();
		}
	}

	public void stopMonitoring() {
		if (started) {
			started = false;
		}
	}
}
