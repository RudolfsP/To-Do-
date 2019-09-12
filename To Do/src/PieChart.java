import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class PieChart extends JFrame {

	private static final long serialVersionUID = 1L;

	private DefaultPieDataset dataset;
	private ChartPanel chartPanel;
	//constructor with filename as the dataset parameter
	public PieChart(String title, String filename) {
		PieDataset dataset = createDataset(filename);		//creates dataset
		
		//creates chart
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, false, true, false);
		
		//Format the label
		PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} {1}h");
		((PiePlot)chart.getPlot()).setLabelGenerator(labelGenerator);
		((PiePlot)chart.getPlot()).setBackgroundPaint(Color.white);
		//create the panel
		chartPanel = new ChartPanel(chart);
		setContentPane(chartPanel);			

	}
	
	// constructor with tableModel as the dataset parameter
	public PieChart(String title, DefaultTableModel tableModel) {
		PieDataset dataset = createDataset(tableModel);		//creates dataset
		
		//creates chart
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, false, true, false);
		
		//Format the label
		PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} {1}h");
		((PiePlot)chart.getPlot()).setLabelGenerator(labelGenerator);
		((PiePlot)chart.getPlot()).setBackgroundPaint(Color.white);
		
		//create the panel
		chartPanel = new ChartPanel(chart);
		setContentPane(chartPanel);

	}
	
	
		
	public DefaultPieDataset getDataset() {
		return dataset;
	}

	public ChartPanel getChartPanel() {
		return chartPanel;
	}

	private PieDataset createDataset(String filename) {
		dataset = new DefaultPieDataset();
		
		try {
			FileReader fin = new FileReader(filename);
			BufferedReader br = new BufferedReader(fin);
			String line;

			
			
			while ((line = br.readLine()) != null) {				
				dataset.setValue(line.trim(), 0);

			}

			br.close();
		}
		catch(IOException e){
			return null;
		}
		

		return dataset;
	}
	
	private PieDataset createDataset(DefaultTableModel tableModel) {
		dataset = new DefaultPieDataset();
		
		for(int i = 0; i < tableModel.getRowCount(); i++) {
			dataset.setValue((String) tableModel.getValueAt(i, 0), (double) tableModel.getValueAt(i, 1));
		}

		return dataset;
	}

	public void updateDataset(String name, double value) {
		dataset.setValue(name, (double)dataset.getValue(name) + value);
	}

}
