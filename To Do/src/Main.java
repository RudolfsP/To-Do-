
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.jfree.chart.ChartPanel;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;


public class Main extends JFrame {
	
	//global variable declaration
	private static JProgressBar progressBar;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private static DefaultListModel<ListItem> listModelLeft = new DefaultListModel<>();
	protected static DefaultListModel<ListItem> listModelRight = new DefaultListModel<>();
	private JTable table;
	protected static DefaultTableModel tableModel;
	protected static JLabel label_1;
	private static int leftCount = 0;
	protected static int rightCount = 0;
	protected static double timeSpentToday = 0;
	private static ListItemCellRenderer renderer; 
	private static JPanel panel_1;
	protected static PieChart piechart;
	private static ChartPanel chartPanel;
	private static ChartPanel acp;
	private static Color grey = Color.decode("#F5F5F5");
	private static JTable allTimeTable;
	protected static DefaultTableModel allTimeTableModel;
	protected static PieChart allTimePieChart;
	protected static JComboBox<String> comboBox;
	protected static JTable goalsTable;
	protected static DefaultTableModel goalTableModel;
	protected static BarChart barChart; 
	protected static String myLocation = System.getProperty("user.dir");
	
	
	protected static File defaultCategories = new File(myLocation + "\\DefaultCategories.txt");
	protected static File defaults = new File(myLocation + "\\Defaults.txt");
	protected static File goalData = new File(myLocation + "\\GoalData.txt");
	protected static File itemData = new File(myLocation + "\\ItemData.txt");
	protected static File leftListFile = new File(myLocation + "\\LeftList.txt");
	protected static File rightListFile = new File(myLocation + "\\RightList.txt");
	protected static File todaysTable = new File(myLocation + "\\Todays table.txt");
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		
		
		//create the save files if they dont exist already
		//JOptionPane.showMessageDialog(null, myLocation);
		if(!defaultCategories.exists()) {
			//create the file
			List<String> lines = Arrays.asList("Categ1", "Categ2", "Categ3", "Categ4", "Categ5", "Categ6", "Other");
			Path file = Paths.get(defaultCategories.getAbsolutePath());
			try {
				Files.write(file, lines, Charset.forName("UTF-8"));
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Error creating a file");
			}
		}
		
		if(!defaults.exists()) {
			//create the file
			List<String> lines = Arrays.asList("Example item|Categ1");
			Path file = Paths.get(defaults.getAbsolutePath());
			try {
				Files.write(file, lines, Charset.forName("UTF-8"));
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Error creating a file");
			}

		}
		
		if(!goalData.exists()) {
			//create the file
			List<String> lines = Arrays.asList("Example goal|8.3#100");
			Path file = Paths.get(goalData.getAbsolutePath());
			try {
				Files.write(file, lines, Charset.forName("UTF-8"));
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Error creating a file");
			}

		}
		
		
		if(!itemData.exists()) {
			//create the file
			List<String> lines = Arrays.asList("");
			Path file = Paths.get(itemData.getAbsolutePath());
			try {
				Files.write(file, lines, Charset.forName("UTF-8"));
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Error creating a file");
			}

		}
		
		if(!leftListFile.exists()) {
			//create the file
			List<String> lines = Arrays.asList("");
			Path file = Paths.get(leftListFile.getAbsolutePath());
			try {
				Files.write(file, lines, Charset.forName("UTF-8"));
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Error creating a file");
			}

		}
		
		if(!rightListFile.exists()) {
			//create the file
			List<String> lines = Arrays.asList("");
			Path file = Paths.get(rightListFile.getAbsolutePath());
			try {
				Files.write(file, lines, Charset.forName("UTF-8"));
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Error creating a file");
			}

		}
		
		if(!todaysTable.exists()) {
			//create the file
			List<String> lines = Arrays.asList("");
			Path file = Paths.get(todaysTable.getAbsolutePath());
			try {
				Files.write(file, lines, Charset.forName("UTF-8"));
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Error creating a file");
			}

		}
		
		//adding items to frame
		setResizable(false);
		setTitle("To do list");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 665, 715);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
			
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 38, 628, 627);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(220, 220, 220));
		tabbedPane.addTab("Today's list", null, panel, null);
		panel.setLayout(null);
		
		JList<ListItem> leftList = new JList<ListItem>(listModelLeft);
		leftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		leftList.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		leftList.setBounds(35, 192, 218, 290);
		//panel.add(leftList);
		
		JList<ListItem> rightList = new JList<ListItem>(listModelRight);
		rightList.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		rightList.setVisibleRowCount(15);
		rightList.setBounds(376, 192, 218, 290);
		//panel.add(rightList);
		
		textField = new JTextField();
		textField.setBounds(20, 32, 576, 20);
		panel.add(textField);
		textField.setColumns(10);

		renderer = new ListItemCellRenderer();
		renderer.setPreferredSize(new Dimension(200, 30));
		leftList.setCellRenderer(renderer);
		rightList.setCellRenderer(renderer);

		JLabel lblAddCustomItem = new JLabel("Add custom item to list");
		lblAddCustomItem.setFont(new Font("Century", Font.PLAIN, 18));
		lblAddCustomItem.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddCustomItem.setBounds(35, 11, 549, 14);
		panel.add(lblAddCustomItem);
		
		JLabel lblToDo = new JLabel("To do");
		lblToDo.setFont(new Font("Century", Font.PLAIN, 15));
		lblToDo.setHorizontalAlignment(SwingConstants.CENTER);
		lblToDo.setBounds(35, 167, 218, 25);
		panel.add(lblToDo);
		
		JLabel lblCompleted = new JLabel("Completed");
		lblCompleted.setHorizontalAlignment(SwingConstants.CENTER);
		lblCompleted.setFont(new Font("Century", Font.PLAIN, 15));
		lblCompleted.setBounds(376, 167, 218, 25);
		panel.add(lblCompleted);
		
		progressBar = new JProgressBar();
		progressBar.setForeground(new Color(0, 204, 51));
		progressBar.setBackground(new Color(255, 255, 204));
		progressBar.setBounds(196, 519, 236, 14);
		panel.add(progressBar);
		progressBar.setMinimum(0);
		progressBar.setMaximum(leftCount);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBackground(new Color(220, 220, 220));
		toolBar.setBounds(20, 88, 576, 25);
		panel.add(toolBar);
		
		label_1 = new JLabel("" + timeSpentToday);
		label_1.setBounds(572, 11, 41, 14);
		//label_1.set
		contentPane.add(label_1);
		
		JLabel lblNewLabel = new JLabel("Total time spent today (h):");
		lblNewLabel.setBounds(421, 11, 157, 14);
		contentPane.add(lblNewLabel);
		// items added
		
		//radioButtonStart
		ButtonGroup bg = new ButtonGroup();
		
		
		
		//get radioButton names
		String filename = defaultCategories.getAbsolutePath();			// filename will need to be dynamic
		ArrayList<String> myList = new ArrayList<String>();
		int categoryCount = 0;
		try {
			FileReader fin = new FileReader(filename);
			BufferedReader br = new BufferedReader(fin);
			String line;
			
			
			while ((line = br.readLine()) != null) {				
					
				
				line.trim();
				myList.add(line);
				categoryCount++;
			}

			br.close();
			
		}
		catch(IOException e){
			return;
		}
		
	
		if(categoryCount < 7) {
			
			for(;categoryCount < 7; categoryCount++) {
				
				if(categoryCount == 6) {
					myList.add("Other");
				}
				
				else {
					myList.add("Empty");
				}
				
				
			}
		}
		
		//add categories to combobox
		comboBox = new JComboBox<String>();
		comboBox.setBounds(465, 120, 131, 20);
		for(int i = 7; i < myList.size(); i++) {
			comboBox.addItem(myList.get(i));
		}
		//categories added to comboBox
				
		//end of getRadioButton names
		JRadioButton rdbtnFirst = new JRadioButton(myList.get(0));
		toolBar.add(rdbtnFirst);
		bg.add(rdbtnFirst);
		
		
		JRadioButton rdbtnSecond = new JRadioButton(myList.get(1));
		toolBar.add(rdbtnSecond);
		bg.add(rdbtnSecond);
		
		JRadioButton rdbtnThird = new JRadioButton(myList.get(2));
		toolBar.add(rdbtnThird);
		bg.add(rdbtnThird);
		
		JRadioButton rdbtnFourth = new JRadioButton(myList.get(3));
		toolBar.add(rdbtnFourth);
		bg.add(rdbtnFourth);
		
		JRadioButton rdbtnFifth = new JRadioButton(myList.get(4));
		toolBar.add(rdbtnFifth);
		bg.add(rdbtnFifth);
		
		JRadioButton rdbtnSixth = new JRadioButton(myList.get(5));
		toolBar.add(rdbtnSixth);
		bg.add(rdbtnSixth);
		
		JRadioButton rdbtnSeventh = new JRadioButton(myList.get(6));
		//rdbtnSeventh.setSelected(true);
		toolBar.add(rdbtnSeventh);
		bg.add(rdbtnSeventh);
		rdbtnSeventh.setSelected(true);
		//radioButtonEnd		
		
		//load both lists
		ArrayList<ListItem> loadedLeftList = readFile(leftListFile.getAbsolutePath());
		for(ListItem item : loadedLeftList) {
			listModelLeft.addElement(item);
			leftCount++;		//because this is 0 it needs to be == listModelLeft.getSize()
		}
		
		ArrayList<ListItem> loadedRightList = readFile(rightListFile.getAbsolutePath());
		for(ListItem item : loadedRightList) {
			listModelRight.addElement(item);
			rightCount++;		//because this is 0 it needs to be == listModelLeft.getSize()
		}
		//lists loaded
		
		//todays table gets loaded on line 600
		
		
		updateProgressBar();
		
		//pie chart in todays table line 583
	
		
		//text field action when enter is pressed
		textField.addKeyListener(new KeyListener() {				// edit functionality to match add Button
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == 10) {
					if(!textField.getText().equals("")) {
		
						AbstractButton button = getSelectedButton(bg);
						
						try {
							
							
							String textFieldText = textField.getText();
							boolean containsInvalidChar = containsInvalidChars(textFieldText);
							
							if(!containsInvalidChar) {
								ListItem item = new ListItem(textField.getText().trim(), button.getText(),0, false);
								listModelLeft.addElement(item);
								
								//this updates piecharts dataset with the new item category
								piechart.getDataset().setValue(item.getCategory(), item.getTimeSpent());
							}
							
							else {
								JOptionPane.showMessageDialog(null, "Please don't use these symbols: '#' '|'");
							}

							
						}
						
						catch(NullPointerException exc) {
							
							String textFieldText = textField.getText();
							boolean containsInvalidChar = containsInvalidChars(textFieldText);
							
							if(!containsInvalidChar) {
								if(comboBox.getItemCount() != 0) {
									ListItem item = new ListItem(textField.getText().trim(), (String)comboBox.getSelectedItem(),0, false);
									listModelLeft.addElement(item);
								}

								else {
									ListItem item = new ListItem(textField.getText().trim(), "Other",0, false);
									listModelLeft.addElement(item);
								}
								
								//piechart.getDataset().setValue(item.getCategory(), item.getTimeSpent());
							}
							
							
							else {
								JOptionPane.showMessageDialog(null, "Please don't use these symbols: '#' '|'");
							}
							

						}
						
						textField.setText("");
						updateProgressBar();
						updateTodayTable();		//automatically updates leftCount	
						
						
						writeLeftListToFile();
						
						
					}
				}	
				
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		//enter click end in text field
		
		JLabel lblSelectCategory = new JLabel("Select category:");
		lblSelectCategory.setFont(new Font("Century", Font.PLAIN, 14));
		lblSelectCategory.setBounds(20, 63, 603, 20);
		panel.add(lblSelectCategory);
		
		
		// defaults button start
				JButton buttonAddDefaults = new JButton("Defaults");
				buttonAddDefaults.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						String filename = defaults.getAbsolutePath();			// filename will need to be dynamic
						
						try {
							FileReader fin = new FileReader(filename);
							BufferedReader br = new BufferedReader(fin);
							String line;
							
							int marker = 0;
							
							while ((line = br.readLine()) != null) {				
								marker = line.indexOf('|');	
								String name = line.substring(0, marker);
								String category = line.substring(marker + 1);
									
								ListItem item = new ListItem(name, category, 0, false);
								listModelLeft.addElement(item);
				 				
														
								
			
							}

							br.close();
							buttonAddDefaults.setEnabled(false);
						}
						catch(IOException e){
							return;
						}
						
						updateTodayTable();	
						//write leftList to file
						writeLeftListToFile();
						
						
						
					}
				});
				buttonAddDefaults.setBounds(263, 266, 103, 23);
				panel.add(buttonAddDefaults);
				//defaults button end
				if(!listModelLeft.isEmpty()) {
					buttonAddDefaults.setEnabled(false);
				}
				
		
		//complete button start
		JButton btnComplete = new JButton("Completed");
		btnComplete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				String filename = itemData.getAbsolutePath();			// filename will need to be dynamic
				
				for(int i = 0; i < listModelLeft.getSize(); i++) {
					if(leftList.isSelectedIndex(i)) {
						// do complete button action
						String itemName = listModelLeft.get(i).getName();
						String timeSpent = JOptionPane.showInputDialog("Enter time spent in minutes for " + itemName.toLowerCase());
						try {
							try {
								
								double time = Double.parseDouble(timeSpent);
								if(time <= 0 || time > 1440) {
									JOptionPane.showMessageDialog(null, "Enter positive numbers smaller than 1440");
								}
								
								else {
									double timeSpentHours = time / 60;
									timeSpentHours = round(timeSpentHours, 2);
									listModelLeft.get(i).setTimeSpent(timeSpentHours);
									listModelLeft.get(i).setCompleted(true);
									
									listModelRight.addElement(listModelLeft.get(i));
									updateElement(listModelLeft.get(i).getName());		//sets time and is completed in the stat table
									rightCount++;
									
									String removedItemCategory = listModelLeft.get(i).getCategory();
									
									//if the category used is new, we have to setValue in dataset to avoid errors
									boolean categoryFound = false;
									for(int x = 0; x < allTimeTableModel.getRowCount(); x++) {
										if(allTimeTableModel.getValueAt(x, 0).equals(removedItemCategory)) {
											categoryFound = true;
										}
									}
									
									if(!categoryFound) {
										allTimePieChart.getDataset().setValue(removedItemCategory, 0);
									}
									

									//end of new category check
									
									
									//do actions for all time table		
									updateAllTimeTable(listModelLeft.get(i));
									writeItemToFile(filename, listModelLeft.get(i));
									//end actions for all time table
									listModelLeft.remove(i);
									leftCount--;
									
									timeSpentToday += timeSpentHours;
									timeSpentToday = round(timeSpentToday, 2);
									if(timeSpentToday > 24) {
										timeSpentToday -= 24;
										timeSpentToday = round(timeSpentToday, 2);
									}
									
									updateProgressBar();

									//we check if the completed items category is in the piechart dataset, if it isn't we add it the dataset 
									boolean itemExists = false;
									for(int y = 0; y < piechart.getDataset().getItemCount(); y++) {
										if(removedItemCategory.equals(piechart.getDataset().getKey(y))) {
											itemExists = true;
											piechart.updateDataset(removedItemCategory, timeSpentHours);
											break;
										}
										
									}

									if(!itemExists) {
										piechart.getDataset().setValue(removedItemCategory, timeSpentHours);
									}
									//end of check
	
									allTimePieChart.updateDataset(removedItemCategory, timeSpentHours);
									barChart.updateExisting(itemName, timeSpentHours);
									
									label_1.setText("" + timeSpentToday);
									
									for(int j = 0; j < goalTableModel.getRowCount(); j++) {
										
										if(goalTableModel.getValueAt(j, 0).equals(itemName)) {
											goalTableModel.setValueAt((double)goalTableModel.getValueAt(j, 1) + timeSpentHours, j, 1);
										}
										
									}
									
									
									writeRightListToFile();
									writeLeftListToFile();
									writeTodaysTable();
									writeGoals();		
									
								}
		
							}
							
							catch(NumberFormatException e) {
								JOptionPane.showMessageDialog(null, "Please enter numbers");
							}
						}
						
						catch(NullPointerException e) {
							
						}
					
					}	
				}
				
				if(listModelLeft.isEmpty()) {
					buttonAddDefaults.setEnabled(true);
				}
	
			}
		});
		btnComplete.setBounds(263, 215, 103, 23);
		panel.add(btnComplete);
		//complete button end
		
		//remove button start
		JButton buttonRemove = new JButton("Remove");
		buttonRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					//step 1) remove item from leftList
					int selected = leftList.getSelectedIndex();
					String itemName = listModelLeft.get(selected).getName();
					listModelLeft.remove(selected);
					leftCount--;
					
					//step 2) remove item from table and shift rows up
					// note. timeSpentToday and the rest of table contents must remain the same
					removeItemFromTable(itemName, tableModel);
					updateProgressBar();
					writeLeftListToFile();
					writeTodaysTable();
					
					if(listModelLeft.isEmpty()) {
						buttonAddDefaults.setEnabled(true);
					}
					
				}
				
				catch(ArrayIndexOutOfBoundsException e) {
					return;
				}
				
			}
		});
		buttonRemove.setBounds(263, 319, 103, 23);
		panel.add(buttonRemove);
		//remove button end
		
		
		
		//clear all button start
		JButton btnClearAll = new JButton("Clear all");
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonAddDefaults.setEnabled(true);
				
				listModelLeft.clear();
				listModelRight.clear();
				progressBar.repaint();
				
				leftCount = 0;
				rightCount = 0;
				
				for(int i = 0; i < tableModel.getRowCount(); i++) {
					// we remove all items from piechart 
					piechart.updateDataset((String)tableModel.getValueAt(i, 1), ((double)tableModel.getValueAt(i, 2) * -1));
				}
				
				clearTable(tableModel);
				timeSpentToday = 0;
				label_1.setText("" + timeSpentToday);
				updateProgressBar();
				//panel_1.remove(chartPanel);
				
				writeRightListToFile();
				writeLeftListToFile();	
				writeTodaysTable();
				
			}		
		});
		btnClearAll.setBounds(263, 373, 103, 23);
		panel.add(btnClearAll);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 192, 218, 290);
		panel.add(scrollPane);
		scrollPane.setViewportView(leftList);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(376, 192, 218, 290);
		panel.add(scrollPane_1);
		scrollPane_1.setViewportView(rightList);
		

		panel.add(comboBox);
		
		JLabel lblOr = new JLabel("Other :");
		lblOr.setBounds(419, 124, 46, 14);
		panel.add(lblOr);
		//add button end
		
		
		//beginning of panel2 start 
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(240, 240, 240));
		tabbedPane.addTab("Today's stats", null, panel_1, null);
		panel_1.setLayout(null);
		


		
		
		String header[] = new String[] { "Item", "Category", "Time spent (h)",
        "Completed"};
		tableModel = new DefaultTableModel(null,header);
		

		
		table = new JTable() {
			//cell renderer for table, allows for cell coloring and different types of customization
			private static final long serialVersionUID = 1L;

			@Override
		    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		        Component comp = super.prepareRenderer(renderer, row, col);	
		       
		        
		        if(table.getValueAt(row, 3).equals(true)) {
		        	Color green = Color.decode("#99FF99");
		        	comp.setBackground(green);
		        }
		        
		        else {
		        	Color red = Color.decode("#FF6666");
		        	comp.setBackground(red);
		        }
       			       
		        return comp;
		    }
			
			@Override
		    public boolean isCellEditable(int rowIndex, int columnIndex) {
		            return false;   
		    }
			
		};
		//end of cell renderer
		
		table.setModel(tableModel);
		table.setBackground(new Color(245, 245, 245));
		table.setBounds(37, 11, 554, 197);
		table.getTableHeader().setReorderingAllowed(false);
		
		tableModel.setColumnCount(4);
		tableModel.setRowCount(0);
		
		
		

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 11, 603, 147);
		panel_1.add(scrollPane_2);
		scrollPane_2.setViewportView(table);
		table.setBackground(grey);
				
		//load today's table
		ArrayList<ListItem> loadedTable = readFile(todaysTable.getAbsolutePath());
		if(!loadedTable.isEmpty()) {
			for(ListItem item : loadedTable) {
				Vector<Object> data = new Vector<Object>();
				data.add(item.getName());
				data.add(item.getCategory());
				data.add(item.getTimeSpent());
				if(item.getTimeSpent() == 0) {
					item.setCompleted(false);
				}
			
				data.add(item.isCompleted());
			
				tableModel.addRow(data);
			}
		//table loaded
		}
		//loading the piechart upon opening the program
		byte redundant = 0;
		if(redundant == 0) {
			piechart = new PieChart("Today's stats", defaultCategories.getAbsolutePath());		
			
			
			for(int i = 0; i < tableModel.getRowCount(); i++) {
				boolean matchExists = false;
				
				for(int j = 0; j < piechart.getDataset().getItemCount(); j++) {
					//we take each key and check it against the values in the table
					if(piechart.getDataset().getKey(j).equals(tableModel.getValueAt(i, 1))) {
						//all good
						//System.out.println("I got here on attempt " + i);
						piechart.updateDataset((String)tableModel.getValueAt(i, 1), (double) tableModel.getValueAt(i, 2));
						matchExists = true;
						break;
					}
				}
				
				if(!matchExists) {
					piechart.getDataset().setValue((String)tableModel.getValueAt(i, 1), (double) tableModel.getValueAt(i, 2));
				}
				
			}
	
			chartPanel = piechart.getChartPanel();
			chartPanel.setVisible(true);
			panel_1.add(chartPanel);
			chartPanel.setBounds(65, 250, 500, 350);
		}
		//piechart loaded
		
		
		// load label for time spent today
		for(int x = 0; x < tableModel.getRowCount(); x++) {
			timeSpentToday += (double)tableModel.getValueAt(x, 2);
			timeSpentToday = round(timeSpentToday, 2);
		}
		
		if(timeSpentToday > 24) {
			timeSpentToday -= 24;
			timeSpentToday = round(timeSpentToday, 2);
		}
		
		label_1.setText("" + timeSpentToday);
		//label loaded
		
		//end of tab 2
	
		
		
		//beginning of tab 3 
		allTimeTable = new JTable() {
			private static final long serialVersionUID = 1L;
			
		    @Override
		    public boolean isCellEditable(int rowIndex, int columnIndex) {
		    	if(columnIndex == 2) {
		    		return true;
		    	}
		            return false;   
		    }
		
		};
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("All time stats", null, panel_3, null);
		panel_3.setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 22, 603, 179);
		panel_3.add(scrollPane_3);

		String allTimeHeader[] = new String[] { "Category", "Time Spent (h) ", "Click for details"};
		
		allTimeTableModel = new DefaultTableModel(null,allTimeHeader);
		allTimeTable.setModel(allTimeTableModel);
		allTimeTable.setBounds(10, 22, 603, 179);
		allTimeTableModel.setColumnCount(3);
		allTimeTableModel.setRowCount(0);
		scrollPane_3.setViewportView(allTimeTable);
		allTimeTable.getTableHeader().setReorderingAllowed(false);
		//creates the click for more details button and specifies its action
		AbstractAction btnNewButton = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
		    {
				boolean foundSelected = false;
				int i = 0;
				
				for(i = 0; i < allTimeTableModel.getRowCount(); i++) {
					if(allTimeTable.isCellSelected(i, 2)) {
						foundSelected = true;
						break;
					}
					
				}

				if(foundSelected) {
					String category = (String) allTimeTableModel.getValueAt(i, 0);
					CustomDialog dialog = new CustomDialog(category + " stats", category);
					dialog.setBounds(300, 80, 650, 750);
					dialog.setVisible(true);
				}

		    }
		};

		
		ButtonColumn buttonColumn = new ButtonColumn(allTimeTable, btnNewButton, 2);
		buttonColumn.setMnemonic(KeyEvent.VK_ENTER);
		//action specified	
		//bar chart or pie chart to alltime stats tab
		
		allTimePieChart = new PieChart("All time stats", defaultCategories.getAbsolutePath());

		
		acp = allTimePieChart.getChartPanel();
		byte redundant1 = 0;
		if(redundant1 == 0) {		// this is used because otherwise we get multiple parent error in design page
			panel_3.add(acp);
			acp.setVisible(true);
			acp.setBounds(65, 250, 500, 350);
			
		}

		//populate allTimeTable and piechart, upon opening the program
		ArrayList<ListItem> allTimeItems = readFile(itemData.getAbsolutePath());
		//populate table with items
		for(ListItem item : allTimeItems) {
			boolean categoryFound = false;
			for(int x = 0; x < allTimeTableModel.getRowCount(); x++) {
				if(allTimeTableModel.getValueAt(x, 0).equals(item.getCategory())) {
					categoryFound = true;
				}
			}
			
			if(!categoryFound) {
				allTimePieChart.getDataset().setValue(item.getCategory(), 0);
			}
			
			updateAllTimeTable(item);
			allTimePieChart.updateDataset(item.getCategory(), item.getTimeSpent());
		}
		// end of third tab
		
		//beggining of tab 4, goals tab
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Goals", null, panel_2, null);
		panel_2.setLayout(null);
		
		JScrollPane scrollPaneGoals = new JScrollPane();
		scrollPaneGoals.setBounds(10, 35, 603, 147);
		panel_2.add(scrollPaneGoals);
		goalsTable = new JTable() {
		//cell renderer for table, colors cells depending on completion percentage
		private static final long serialVersionUID = 1L;
			@Override
	    	public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
	        	Component comp = super.prepareRenderer(renderer, row, col);	
	        	
	        	double percentage =((double)goalsTable.getValueAt(row, 1)/ (double)goalsTable.getValueAt(row, 2)) * 100;
	        	percentage = round(percentage, 2);
	        	
	        	//first
	        	if(percentage >= 0 && percentage < 5) {			
	        		Color color = Color.decode("#fe0a00");
	        		comp.setBackground(color);
	        	}
	        	
	        	//second
	        	else if(percentage > 5 && percentage < 10) {
	        		Color color = Color.decode("#ff1c01");
	        		comp.setBackground(color);
	        	}
	        	
	        	//third
	        	else if(percentage > 10 && percentage < 15) {
	        		Color color = Color.decode("#ff3100");
	        		comp.setBackground(color);
	        	}
	        	
	        	//fourth
	        	else if(percentage > 15 && percentage < 20) {
	        		Color color = Color.decode("#fe4600");
	        		comp.setBackground(color);
	        	}
	        	
	        	//fifth
	        	else if(percentage > 20 && percentage < 25) {
	        		Color color = Color.decode("#ff6300");
	        		comp.setBackground(color);
	        	}
	        	
	        	//sixth
	        	else if(percentage > 25 && percentage < 30) {
	        		Color color = Color.decode("#ff7f00");
	        		comp.setBackground(color);
	        	}
	        	
	        	//seventh
	        	else if(percentage > 30 && percentage < 35) {
	        		Color color = Color.decode("#ff9f00");
	        		comp.setBackground(color);
	        	}
	        	
	        	//eight
	        	else if(percentage > 35 && percentage < 40) {
	        		Color color = Color.decode("#fec200");
	        		comp.setBackground(color);
	        	}
	        	
	        	//ninth
	        	else if(percentage > 40 && percentage < 45) {
	        		Color color = Color.decode("#ffde00");
	        		comp.setBackground(color);
	        	}
	        	
	        	//tenth
	        	else if(percentage > 45 && percentage < 50) {
	        		Color color = Color.decode("#feed00");
	        		comp.setBackground(color);
	        	}
	        	
	        	//eleventh
	        	else if(percentage > 50 && percentage < 55) {
	        		Color color = Color.decode("#fffa00");
	        		comp.setBackground(color);
	        	}
	        	
	        	//twelfth
	        	else if(percentage > 55 && percentage < 60) {
	        		Color color = Color.decode("#dffe00");
	        		comp.setBackground(color);
	        	}
	        	
	        	//thirteenth
	        	else if(percentage > 60 && percentage < 65) {
	        		Color color = Color.decode("#cffe00");
	        		comp.setBackground(color);
	        	}
	        	
	        	//fourteenth
	        	else if(percentage > 65 && percentage < 70) {
	        		Color color = Color.decode("#c0ff00");
	        		comp.setBackground(color);
	        	}
	        	
	        	//fifteenth
	        	else if(percentage > 70 && percentage < 75) {
	        		Color color = Color.decode("#acff01");
	        		comp.setBackground(color);
	        	}
	        	
	        	//sixteenth
	        	else if(percentage > 75 && percentage < 80) {
	        		Color color = Color.decode("#9ffe00");
	        		comp.setBackground(color);
	        	}
	        	
	        	//seventeenth
	        	else if(percentage > 80 && percentage < 85) {
	        		Color color = Color.decode("#76ff01");
	        		comp.setBackground(color);
	        	}
	        	
	        	//eighteenth
	        	else if(percentage > 85 && percentage < 90) {
	        		Color color = Color.decode("#4aff00");
	        		comp.setBackground(color);
	        	}
	        	
	        	//nineteenth
	        	else if(percentage > 90 && percentage < 95) {
	        		Color color = Color.decode("#51ff00");
	        		comp.setBackground(color);
	        	}
	        	
	        	//twentieth
	        	else if(percentage > 95) {
	        		Color color = Color.decode("#0fff00");
	        		comp.setBackground(color);
	        	}
   			       
	        	return comp;
			}
			
			@Override
		    public boolean isCellEditable(int rowIndex, int columnIndex) {
		    	if(columnIndex == 3) {
		    		return true;
		    	}
		            return false;   
		    }
		};
		//end of cell renderer
		String goalHeader[] = new String[] { "Item name", "Current time spent (h) ", "Goal (h)", "Delete row"};
		
		goalTableModel = new DefaultTableModel(null,goalHeader);
		goalsTable.setModel(goalTableModel);
		goalTableModel.setColumnCount(4);
		goalTableModel.setRowCount(0);
		goalsTable.getTableHeader().setReorderingAllowed(false);
		scrollPaneGoals.setViewportView(goalsTable);
		
		JButton btnAddGoal = new JButton("Add Goal");
		btnAddGoal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GoalDialog goalDialog = new GoalDialog("Set goals");
				goalDialog.setVisible(true);
			}
		});
		btnAddGoal.setBounds(193, 193, 89, 23);
		panel_2.add(btnAddGoal);
		
		//begin of clear all button
		JButton btnClearAllGoals = new JButton("Clear all");
		btnClearAllGoals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int userChoice= JOptionPane.showConfirmDialog(null, "This will clear the entire table, are you sure you want to continue?");
				
				//user picked 'Yes', so delete all table contents and the chart
				if(userChoice == 0) {
				
					//Main.piechart.updateDataset(category, -timeSpent);
					for(int i = 0; i < goalTableModel.getRowCount(); i++) {
						barChart.remove((String)goalTableModel.getValueAt(i, 0));
						
					}
					
					clearTable(goalTableModel);	//table cleared
					writeGoals();
				}
				
			}
		});
		btnClearAllGoals.setBounds(342, 193, 89, 23);
		panel_2.add(btnClearAllGoals);
		//end of clear all button
		
		// start of menu items
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(20, 0, 146, 21);
		contentPane.add(menuBar);
		
		JMenu mnDefaults = new JMenu("Defaults");
		menuBar.add(mnDefaults);
		
		JMenuItem mntmSetDefaultCategories = new JMenuItem("Set Default Categories");
		mnDefaults.add(mntmSetDefaultCategories);
		mntmSetDefaultCategories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SetCategoriesWindow window = new SetCategoriesWindow();
				window.setVisible(true);
				
			}
		});
		
		JMenuItem mntmSetDefaultItems = new JMenuItem("Set Default Items");
		mnDefaults.add(mntmSetDefaultItems);
		mntmSetDefaultItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SetDefaultItemWindow window = new SetDefaultItemWindow();
				window.setVisible(true);
			}
			
		});
		
		JMenu mnAdd = new JMenu("Add");
		menuBar.add(mnAdd);
		
		JMenuItem mntmAddMoreCategories = new JMenuItem("Add More Categories");
		mnAdd.add(mntmAddMoreCategories);
		mntmAddMoreCategories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddCategoriesWindow window = new AddCategoriesWindow(comboBox);
				window.setVisible(true);
				
				
			}
		});
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		JMenuItem mntmAboutAuthor = new JMenuItem("About author");
		mnAbout.add(mntmAboutAuthor);
		mntmAboutAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AboutWindow window = new AboutWindow();
				window.setVisible(true);
				
			}
			
		});
		//end of menu items
		
		//goalTableRemove row button
		AbstractAction btnNewButton1 = new AbstractAction() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				
				for(int i = 0; i < goalTableModel.getRowCount(); i++) {
					
					if(goalsTable.isCellSelected(i, 3)) {
						
						//update graph once its added to the window								
						barChart.remove((String) goalTableModel.getValueAt(i, 0));
						//Main.barChart
						
						
						//remove row
						goalTableModel.removeRow(i);
						writeGoals();
					}
				}
		    }
		};
		
		ButtonColumn buttonColumn1 = new ButtonColumn(Main.goalsTable, btnNewButton1, 3);
		buttonColumn1.setMnemonic(KeyEvent.VK_ENTER);
		//end of goalTableRemove row button
		
		//update the bar chart with the new item
		
		ChartPanel barChartPanel;
		barChart = new BarChart("Goals", goalTableModel);
		barChartPanel = barChart.getChartPanel();
		
		
		if(redundant1 == 0) {		// this is used because otherwise we get multiple parent error in design page
			panel_2.add(barChartPanel);
			barChartPanel.setBounds(40, 250, 500, 350);	
			
		}
		
		//read goal table from file and draw the bar chart
		ArrayList<GoalItem> goalItemList = readGoalDataFile(goalData.getAbsolutePath());
		//list populated, now add it to goalTable
		
		for(GoalItem item : goalItemList) {
			Vector<Object> data = new Vector<Object>();
			
			data.add(item.getName());
			data.add(item.getCurrentTime());
			data.add(item.getGoal());
			data.add("Delete");
			
			goalTableModel.addRow(data);
		}
		
		for(int i = 0; i < goalTableModel.getRowCount(); i++) {
			String name = (String) goalTableModel.getValueAt(i, 0);
			double value = round((((double) goalTableModel.getValueAt(i, 1)) / (double) goalTableModel.getValueAt(i, 2)) *100 ,2);
			barChart.updateDataset(name, value);
		}
		
		
	}
	
	//gets selected button from button group
	public static AbstractButton getSelectedButton(ButtonGroup bg) {
		Enumeration<AbstractButton> e = bg.getElements();
		AbstractButton button = e.nextElement();
		
		while(e.hasMoreElements()) {
			if(button.isSelected())
				return button;
			
			else {
				button = e.nextElement();
			}
		}

		return null;
	}
	
	//round method... rounds up from 0,5
	public static double round(double value, int places) {			
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public static void updateTodayTable() {			//adds elements to table..........works with Today's stats table only
		 for (;leftCount < listModelLeft.getSize(); leftCount++) {			
		        Vector<Object> data = new Vector<Object>();
		      
		        // only works with left list		        	
		        data.add(listModelLeft.get(leftCount).getName()); // add item name		
		        data.add(listModelLeft.get(leftCount).getCategory());					// add category	
		        data.add(listModelLeft.get(leftCount).getTimeSpent());
		        data.add(listModelLeft.get(leftCount).isCompleted());
		        
		        boolean alreadyExists = false;
		        for(int i = 0; i < tableModel.getRowCount(); i++) {
		        	if(tableModel.getValueAt(i, 0).equals(listModelLeft.get(leftCount).getName())
		        			&& tableModel.getValueAt(i, 1).equals(listModelLeft.get(leftCount).getCategory())) {			// we check if an item with the same name and category is already in the list
		        		
		        		alreadyExists = true;
		        		//System.out.println((double)tableModel.getValueAt(i, 2) + listModelLeft.get(leftCount).getTimeSpent());
		        		tableModel.setValueAt((double)tableModel.getValueAt(i, 2) + listModelLeft.get(leftCount).getTimeSpent(), i, 2);
		        		
		        	}
		        }
		        
		        
		        if(!alreadyExists) {
		        	tableModel.addRow(data);
		        }
		        
		        
		    }
		 
		 writeTodaysTable();
	}
	
	public static void updateAllTimeTable(ListItem item) {
		boolean itemFound = false;
		
		for(int i = 0; i < allTimeTableModel.getRowCount(); i++) {					//for an existing item
			
			if(item.getCategory().equals(allTimeTableModel.getValueAt(i, 0))) {
				double currentValue = (double)allTimeTableModel.getValueAt(i, 1);
				allTimeTableModel.setValueAt(currentValue + item.getTimeSpent(), i, 1);
				itemFound = true;
			}
		}
		
		// in case the item doesnt exist
		if(!itemFound) {
			Vector<Object> data = new Vector<Object>();
			data.add(item.getCategory());
			data.add(item.getTimeSpent());
			data.add("Click me");
			//data.add(button)
			allTimeTableModel.addRow(data);
		}
		
	}
	
	public static void updateElement(String elementName) {			//sets stats for completed element..........works with Today's stats table only
		//using index variable here causes a bug
		int index = 0;
		for(int i = 0; i < tableModel.getRowCount(); i++) {
			if(tableModel.getValueAt(i, 0).equals(elementName)) {
				index = i;
				break;
			}
		}
		//System.out.println(rightCount);
		if((boolean)tableModel.getValueAt(index, 3) == true) {
			tableModel.setValueAt(listModelRight.get(rightCount).getTimeSpent() + (double)tableModel.getValueAt(index, 2), index, 2);
		}
		
		else {
			tableModel.setValueAt(listModelRight.get(rightCount).getTimeSpent(), index, 2);				//index is the row
			tableModel.setValueAt(listModelRight.get(rightCount).isCompleted(), index, 3);
		}
		

	}
	
	public static void clearTable(DefaultTableModel tableModel) {
		int rowCount = tableModel.getRowCount();
		
		for(int i = rowCount - 1; i >= 0; i--)
			tableModel.removeRow(i);
			
	}
	
	public static void removeItemFromTable(String name, DefaultTableModel tableModel) {
		for(int i = 0; i < tableModel.getRowCount(); i++) {
			
			if(name.equals(tableModel.getValueAt(i, 0))) {
				tableModel.removeRow(i);
			}
		}
	}
	
	// method to update the progress bar
	public static void updateProgressBar() {				// method can be made universal
		progressBar.setStringPainted(true);
		progressBar.setMaximum(listModelLeft.getSize() + listModelRight.getSize());
		progressBar.setValue(rightCount);
	}
	
	public static ArrayList<ListItem> readFile(String filename) {
		// first read file to know where to put new data
		String existingCategoryName;
		String existingItemName;
		double existingTimeSpent;
		ArrayList<ListItem> myList = new ArrayList<>();
		
		try {
			FileReader fin = new FileReader(filename);
			BufferedReader br = new BufferedReader(fin);
			String line;
						
			while ((line = br.readLine()) != null) {				
				//Example of line	Music|Guitar#8.4
				if(line.length() != 0) {
					int endOfFirstString = line.indexOf('|');
					existingCategoryName = line.substring(0, endOfFirstString);		//gets category name from file
					
					int endOfSecondString = line.indexOf('#');						//gets file name from file
					existingItemName = line.substring(endOfFirstString + 1, endOfSecondString);
						
					existingTimeSpent = Double.parseDouble(line.substring(endOfSecondString + 1));		//gets time spent name from file
					
					ListItem item = new ListItem(existingItemName, existingCategoryName, existingTimeSpent, true);
					myList.add(item);
				}

				
			}
			
			br.close();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File not found");
		}		
				
			return myList;
	}

	public static ArrayList<GoalItem> readGoalDataFile(String filename) {
		// first read file to know where to put new data
		String itemName;
		double currentTimeSpent;
		double goalTime;
		ArrayList<GoalItem> myList = new ArrayList<>();
		
		try {
			FileReader fin = new FileReader(filename);
			BufferedReader br = new BufferedReader(fin);
			String line;
						
			while ((line = br.readLine()) != null) {				
				//Example of line	Music|Guitar#8.4
				int endOfFirstString = line.indexOf('|');
				itemName = line.substring(0, endOfFirstString);		//gets item name from file
				
				int endOfSecondString = line.indexOf('#');						//gets current time
				currentTimeSpent = Double.parseDouble(line.substring(endOfFirstString + 1, endOfSecondString));
				currentTimeSpent = round(currentTimeSpent,2);	
				
				goalTime = Double.parseDouble(line.substring(endOfSecondString + 1));		//gets goal time from file
				goalTime = round(goalTime,2);
				
				GoalItem item = new GoalItem(itemName, currentTimeSpent, goalTime);
				myList.add(item);
				
			}
			
			br.close();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File not found");
		}		
				
			return myList;
	}
	
	private static void writeItemToFile(String filename, ListItem completedItem){
		// first read file to know where to put new data
		boolean matchFound = false;
		ArrayList<ListItem> myList = readFile(itemData.getAbsolutePath());

			
		for(ListItem item : myList) {
			if(completedItem.getCategory().equals(item.getCategory()) && completedItem.getName().equals(item.getName())) {	//update existing item
				matchFound = true;
				item.setTimeSpent(item.getTimeSpent() + completedItem.getTimeSpent());
			}
		}
			
		if(!matchFound) {
			myList.add(completedItem);
		}

		try {
			FileWriter fout = new FileWriter(filename);
			PrintWriter out = new PrintWriter(fout);
				
			for(ListItem listItem : myList) {
				out.println(listItem.getCategory() + "|" + listItem.getName() + "#" + listItem.getTimeSpent());		
			}
				
			out.close();	
					
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File not found");
		}	
			
			
	}
	
	protected static void writeItemToFile(String filename, ArrayList<ListItem> items){
		// first read file to know where to put new data	
		try {

			PrintWriter out = new PrintWriter(filename);
				
			for(ListItem listItem : items) {
				out.println(listItem.getCategory() + "|" + listItem.getName() + "#" + listItem.getTimeSpent());		
			}
				
			out.close();	
					
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File not found");
		}	
			
			
	}
	
	//method for saving the goalItem table, because it doesn't know the item category
	private static void writeGoalItemToFile(String filename, ArrayList<GoalItem> items){
		// first read file to know where to put new data	
		try {

			PrintWriter out = new PrintWriter(filename);
				
			for(GoalItem goalItem : items) {
				out.println(goalItem.getName() + "|" + goalItem.getCurrentTime() + "#" + goalItem.getGoal());		
			}
				
			out.close();	
					
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File not found");
		}	
	}
	
	public static void writeGoals() {
		ArrayList<GoalItem> myList = new ArrayList<GoalItem>();
		for(int i = 0; i < goalTableModel.getRowCount(); i++) {
			GoalItem item = new GoalItem((String) goalTableModel.getValueAt(i, 0), (double) goalTableModel.getValueAt(i, 1), 
					(double) goalTableModel.getValueAt(i, 2));
			myList.add(item);
		}
		//list populated, now write to file
		writeGoalItemToFile(goalData.getAbsolutePath(), myList);
	}
	
	public static void writeLeftListToFile() {
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		for(int i = 0; i < listModelLeft.getSize(); i++) {
			items.add(listModelLeft.get(i));
		}
		
		writeItemToFile(leftListFile.getAbsolutePath(),items);
	}
	
	public static void writeRightListToFile() {

		ArrayList<ListItem> items = new ArrayList<ListItem>();
		for(int i = 0; i < listModelRight.getSize(); i++) {
			items.add(listModelRight.get(i));
		}
		
		writeItemToFile(rightListFile.getAbsolutePath(),items);
		
	}
	
	public static void writeTodaysTable() {
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		for(int i = 0; i < tableModel.getRowCount(); i++) {
			String name = (String)tableModel.getValueAt(i, 0);
			String category = (String) tableModel.getValueAt(i, 1);
			double timeSpent = (double) tableModel.getValueAt(i, 2);
			boolean isCompleted = (boolean) tableModel.getValueAt(i, 3);
			
			ListItem item = new ListItem(name, category, timeSpent, isCompleted);
			items.add(item);
		}
		
		writeItemToFile(todaysTable.getAbsolutePath(),items);
		
	}
	
	public static boolean containsInvalidChars(String name) {
		boolean containsInvalid = false;
		for(int i = 0; i < name.length(); i++) {
			
			if(name.charAt(i) == '#' || name.charAt(i) == '|') {
				containsInvalid = true;
				break;
			}
		}
		
		return containsInvalid;
	}
}

