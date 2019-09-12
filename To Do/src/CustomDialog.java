import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;

public class CustomDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	public CustomDialog(String dialogName, String category) {
		initUI(dialogName, category);
	}

 	
	private void initUI(String dialogName, String category) {
		//because of createLayout method we need 3 Components				1) a table 2) Pie Chart 3) Close button
		//dialog window after click for more details button is pressed
		JButton button = new JButton("Close");
		
		button.addActionListener(event -> dispose());
		//button created
		
		JScrollPane scrollPane = new JScrollPane();
		String header[] = new String[] { "Item ", "Time Spent (h) ", "Remove row "};
		DefaultTableModel tableModel = new DefaultTableModel(null, header);
		tableModel.setColumnCount(3);
		JTable table = new JTable() {
			private static final long serialVersionUID = 1L;
			
			@Override
		    public boolean isCellEditable(int rowIndex, int columnIndex) {
				if(columnIndex == 2) {
					return true;
				}
		            return false;   
		    }
			
		};
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		table.getTableHeader().setReorderingAllowed(false);
		//table created, pie chart needs to be created after the table is populated
		
		// table population start
		
		//1) reading ItemData file
		String filename = Main.itemData.getAbsolutePath();			// filename will need to be dynamic
		ArrayList<String> myList = new ArrayList<String>();
		//ItemData file read and added in myList
		try {
			FileReader fin = new FileReader(filename);
			BufferedReader br = new BufferedReader(fin);
			String line;
						
			while ((line = br.readLine()) != null) {					
				line.trim();
				myList.add(line);
			}

			br.close();
			
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, "File not found");
		}
		
		//2)adding all category items and their times to tableModel
		for(String name : myList) {
			int endIndex = name.indexOf('|');
			String currentCategory = name.substring(0, endIndex);
			
			if(currentCategory.equals(category)) {
				Vector<Object> data = new Vector<Object>();
				int hashIndex = name.indexOf('#');
				String itemName = name.substring(endIndex + 1, hashIndex);
				double timeSpent = Double.parseDouble(name.substring(hashIndex + 1));
				
				data.add(itemName);
				data.add(timeSpent);
				data.add("Remove");
				
				tableModel.addRow(data);
			}
		}
		
		// items and times added
		
		//3) create a pie chart
		PieChart pieChart = new PieChart("Time spent", tableModel);
		ChartPanel chartPanel = pieChart.getChartPanel();
		//pie chart created
		
		//4)add elements to dialog window
		createLayout(scrollPane, chartPanel, button);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle(dialogName);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getParent());
		
		// 5) add remove button to table
		
		AbstractAction btnNewButton = new AbstractAction() {

			private static final long serialVersionUID = 1L;


			
			public void actionPerformed(ActionEvent e)
		    {
				boolean foundSelected = false;
				int i = 0;
				
				for(i = 0; i < tableModel.getRowCount(); i++) {
					if(table.isCellSelected(i, 2)) {
						foundSelected = true;
						break;
					}
					
				}

				if(foundSelected) {
					
					ArrayList<ListItem> list = Main.readFile(Main.itemData.getAbsolutePath());
					String removedName = (String) tableModel.getValueAt(i, 0);
					double timeSpent = 0;
					for(ListItem item : list) {
						if(item.getName().equals(removedName)) {
							list.remove(item);
							break;
						}
					}
					
					Main.writeItemToFile(Main.itemData.getAbsolutePath(), list);
					
					//remove item from today's stats table and update total time spent today
					for(int x = 0; x < Main.tableModel.getRowCount(); x++) {
						if(Main.tableModel.getValueAt(x, 0).equals(removedName)) {
							
							if((double)Main.tableModel.getValueAt(x, 2) != 0) {
								timeSpent = (double) Main.tableModel.getValueAt(x, 2);
								Main.tableModel.removeRow(x);		// update today's stat table
								
								for(int l = 0; l < Main.listModelRight.getSize(); l++) { 				//update listModelRight
									if(Main.listModelRight.get(l).getName().equals(removedName)) {
										Main.listModelRight.remove(l);
										//update right count
										Main.rightCount--;		// for the progressbar
									}
									
								}
								Main.writeRightListToFile();
								// update totaltimeSpent
								Main.timeSpentToday = Main.timeSpentToday - timeSpent;	// this only updates the variable
								Main.label_1.setText("" + Main.timeSpentToday);		//label updated
								
								//update progressbar
								Main.updateProgressBar();
								
								//update today's pieChart
								Main.piechart.updateDataset(category, -timeSpent);
																	
								break;
							}
						}
					}

					
					// if we removed the only item in the table we have to delete the whole thing in alltime table
					if(tableModel.getRowCount() == 1) {	
						for(int row = 0; row < Main.allTimeTableModel.getRowCount(); row++) {
							
							if(Main.allTimeTableModel.getValueAt(row, 0).equals(category)) {
								Main.allTimePieChart.updateDataset(category, (double) Main.allTimeTableModel.getValueAt(row, 1) * -1);
								if(Main.allTimeTableModel.getRowCount() == 0) {
									Main.allTimePieChart.removeAll();
								}
								
								Main.allTimeTableModel.removeRow(row);
								pieChart.updateDataset((String)tableModel.getValueAt(i, 0), (double)tableModel.getValueAt(i, 1) * -1);
								updateBarChart(removedName);
								tableModel.removeRow(i);
								
							}
						}
						
					}
					
					else {
						for(int row = 0; row < tableModel.getRowCount(); row++) {		
							
							if(tableModel.getValueAt(row, 0).equals(removedName)) {		//category found(it will always be found) so else is not needed
								Main.allTimePieChart.updateDataset(category, (double) tableModel.getValueAt(row, 1) * -1);
								pieChart.updateDataset(removedName, (double)tableModel.getValueAt(row, 1) * -1);
														
								// find the corresponding category in all time table and change the time spent
								for(int y = 0; y < Main.allTimeTableModel.getRowCount(); y++) {
									
									if(category.equals(Main.allTimeTableModel.getValueAt(y, 0))) {			
										double value = (double)Main.allTimeTableModel.getValueAt(y, 1) - (double)tableModel.getValueAt(row, 1);
										Main.allTimeTableModel.setValueAt(Main.round(value, 2), y, 1);
									}
								}
								updateBarChart(removedName);
								tableModel.removeRow(i);
							}
						}

						
					}
		
				}

		    }
		};
		
		ButtonColumn buttonColumn = new ButtonColumn(table, btnNewButton, 2);
		buttonColumn.setMnemonic(KeyEvent.VK_ENTER);
	}
	
	
	private void updateBarChart(String itemName) {
		for(int i = 0; i < Main.goalTableModel.getRowCount(); i++) {
			
			if(Main.goalTableModel.getValueAt(i, 0).equals(itemName)) {
				Main.goalTableModel.removeRow(i);
				Main.barChart.remove(itemName);
				break;
			}
		}
	}
	
	private void createLayout(JComponent... arg) {

	       Container pane = getContentPane();
	       GroupLayout gl = new GroupLayout(pane);
	       pane.setLayout(gl);

	        gl.setAutoCreateContainerGaps(true);
	        gl.setAutoCreateGaps(true);

	        gl.setHorizontalGroup(gl.createParallelGroup()	
	                .addComponent(arg[0])
	                .addComponent(arg[1])
	                .addComponent(arg[2])
	                .addGap(200)
	        );

	        gl.setVerticalGroup(gl.createSequentialGroup()
	        		.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE))
	                .addGap(30)
	                .addComponent(arg[0])
	                .addGap(20)
	                .addComponent(arg[1])
	                .addGap(20)
	                .addComponent(arg[2])
	                .addGap(30)
	        );

	        pack();
	    
	}	
}
