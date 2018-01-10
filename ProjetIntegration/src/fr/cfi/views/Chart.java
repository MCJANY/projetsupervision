package fr.cfi.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import fr.cfi.model.ChartModel;
import fr.cfi.model.IChartModelListener;

public class Chart extends ChartPanel {
	
	public Chart() {
		super(ChartFactory.createTimeSeriesChart(             
		         "Memory usage", 
		         "Millisecond",              
		         "%",              
		         new TimeSeriesCollection(),             
		         false,              
		         false,              
		         false));
		initUI();
	}

	private void initUI() {
		this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		this.setBackground(Color.white);
		JFreeChart chart = getChart();
		XYPlot plot = chart.getXYPlot();

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));

		//plot.setS
		plot.getRangeAxis().setRange(0, 100);
		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		//chart.getLegend().setFrame(BlockBorder.NONE);

		chart.setTitle(new TextTitle("Utilisation m�moire dans le temps", new Font("Serif", java.awt.Font.BOLD, 18)));
	}
	

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("ChartExemple");
			Chart ex = new Chart();
			ChartModel chartModel = new ChartModel(new IChartModelListener() {
				@Override
				public void dataChanged(XYDataset dataset) {
					JFreeChart chart = ex.getChart();
					XYPlot plot = chart.getXYPlot();
					plot.setDataset(dataset);
				}
			});
			frame.setContentPane(ex);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			
			chartModel.startMonitoring();
		});
	}
}
