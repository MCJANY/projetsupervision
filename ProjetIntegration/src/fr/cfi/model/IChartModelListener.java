package fr.cfi.model;

import org.jfree.data.xy.XYDataset;

public interface IChartModelListener {

	public void dataChanged(XYDataset dataset);
}
