import java.awt.Color;
import java.awt.Paint;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChart extends JFrame {

	private static final long serialVersionUID = 1L;
	private DefaultCategoryDataset dataset;
	private ChartPanel chartPanel;
	private JFreeChart chart;
	private CategoryPlot plot;
	private CategoryItemRenderer renderer;
	//constructor with filename as the dataset parameter
	
	// constructor with tableModel as the dataset parameter
	public BarChart(String title, DefaultTableModel tableModel) {
		CategoryDataset dataset = createDataset(tableModel);		//creates dataset
		
		//creates chart
		chart = ChartFactory.createBarChart("", "", "Completion %", dataset, PlotOrientation.HORIZONTAL, false, true, false);
		
		//create the panel
		chartPanel = new ChartPanel(chart);
		setContentPane(chartPanel);

		//formats the panel
		plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setOutlinePaint(Color.white);
	
		
		renderer = new CustomRenderer();
		plot.setRenderer(renderer);
		
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setRange(0, 100);
	}


	public ChartPanel getChartPanel() {
		return chartPanel;
	}

	
	private CategoryDataset createDataset(DefaultTableModel tableModel) {
		dataset = new DefaultCategoryDataset();
		
		for(int i = 0; i < tableModel.getRowCount(); i++) {
			double percentage = ((double)tableModel.getValueAt(i, 1) / (double)tableModel.getValueAt(i, 2)) * 100;
			percentage = Main.round(percentage, 2);
			
			if(percentage > 100) {
				percentage = 100;
			}
			
			dataset.setValue(percentage, "Goals", (String)tableModel.getValueAt(i, 0));	
		}
		
		return dataset;
				
	}

	public void updateDataset(String name, double value) {
		
		if(value > 100) {
			value = 100;
		}
		
		dataset.setValue(value ,"Goals", name);		
		
	}

	public void updateExisting(String name, double value) {
		// we recieve the item name and time we have to add to the existing one
		double currentGoal = 0.0;
		
		for(int i = 0; i < Main.goalTableModel.getRowCount(); i++) {
			
			if(Main.goalTableModel.getValueAt(i, 0).equals(name)) {
				currentGoal = (double)Main.goalTableModel.getValueAt(i, 2);
				break;
			}
		}
		
		if(currentGoal != 0) {
			double newValue = (value/currentGoal) * 100 ;
			Main.round(newValue, 2);
			//System.out.println(newValue);
			
			double oldValue = (double)dataset.getValue("Goals", name);
			//System.out.println(oldValue);
			
			dataset.addValue(oldValue + newValue, "Goals", name);
		}

	}

	public void remove(String name) {
		dataset.removeColumn(name);
	}
	
	
	//class for changing colors for each individual bar in the bar chart
	class CustomRenderer extends BarRenderer
	{

		private static final long serialVersionUID = 1L;

	public CustomRenderer() {
	   //empty constructor
	}

	   public Paint getItemPaint(final int row, final int column) {
	   
	      // returns color depending on column count
		   
		   switch(column) {
		   
		   		default:
		   			return Color.decode("#333338");			//gray
		   			
		   		case 0:
		   			return Color.decode("#f24141");			//red
		   			
		   		case 1:
		   			return Color.decode("#6cc63b");			//green
		   		
		   		case 2:
		   			return Color.decode("#7172bc");			//blue
		   			
		   		case 4:
		   			return Color.decode("#dd66bd");			//purple
		   			
		   		case 5:
		   			return Color.decode("#e7ed36");			//yellow
		   			
		   		case 6:
		   			return Color.decode("#79f2e6");			//light blue
		   			
		   		case 7:
		   			return Color.decode("#ffa216");			//orange
		   			
		   		case 8:
		   			return Color.decode("#ffb7f0");			//pink
		   			
		   		case 9:
		   			return Color.decode("#003300");			//dark green
		   			
		   		case 10:
		   			return Color.decode("#663300");			//brown
		   			
		   		case 11:
		   			return Color.decode("#ffffcc");			//light yellow
		   			
		   		case 12:
		   			return Color.decode("#003366");			//dark blue
		   			
		   		case 13:
		   			return Color.decode("#cc99ff");			//magenta
		   			
		   		case 14:
		   			return Color.decode("#800000");			//maroon
		   			
		   		case 15:
		   			return Color.decode("#cccccc");			//gray
		   			
		   		case 16:
		   			return Color.decode("#6cc63b");			//green
		   			
		   		case 17:
		   			return Color.decode("#00cc99");			//weird green
		   			
		   		case 18:
		   			return Color.decode("#660033");			//pink red
		   			
		   		case 19:
		   			return Color.decode("#b3b300");			//yellowish
		   }
   
	   }
	}
	
}
