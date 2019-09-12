import javax.swing.JDialog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SetCategoriesWindow extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private ArrayList<String> oldCategories;
	
	public SetCategoriesWindow() {
		
		ArrayList<String> myList = readDefaultCategoryFile(Main.defaultCategories.getAbsolutePath());

		
		if(myList.size() < 7) {
			for(int i = myList.size(); i < 7; i++) {
				myList.add("Empty category");
			}
		}
		

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(500, 200, 519, 659);
		setTitle("Set default categories");
		setResizable(false);
		
		
		JLabel lblNewLabel = new JLabel("Current default categories");
		
		JLabel lblNewLabel_0 = new JLabel(myList.get(0));
		
		JLabel lblNewLabel_1 = new JLabel(myList.get(1));
		
		JLabel lblNewLabel_2 = new JLabel(myList.get(2));
		
		JLabel lblNewLabel_3 = new JLabel(myList.get(3));
		
		JLabel lblNewLabel_4 = new JLabel(myList.get(4));
		
		JLabel lblNewLabel_5 = new JLabel(myList.get(5));
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		
		JLabel lblNewDefaultCategories = new JLabel("New default categories");
		
		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ArrayList<String> textList = new ArrayList<String>();
				textList.add(textField.getText());
				textList.add(textField_1.getText());
				textList.add(textField_2.getText());
				textList.add(textField_3.getText());
				textList.add(textField_4.getText());
				textList.add(textField_5.getText());
				
				boolean isInvalid = false; 
				for(String name : textList) {
					isInvalid = Main.containsInvalidChars(name);
					if(isInvalid) {
						break;
					}
				}
				
				
				
				//get textField text
				//write it to file, empty ones become " "
				
				if(!isInvalid) {
					ArrayList<String> myList = new ArrayList<String>();
					
					String category  = textField.getText();
					category = category.trim();
					if(category.equals("")) {
						category = lblNewLabel_0.getText();
					}
					myList.add(category);
					
					String category_1  = textField_1.getText();
					category_1 = category_1.trim();
					if(category_1.equals("")) {
						category_1 = lblNewLabel_1.getText();
					}
					myList.add(category_1);
					
					String category_2  = textField_2.getText();
					category_2 = category_2.trim();
					if(category_2.equals("")) {
						category_2 = lblNewLabel_2.getText();
					}
					myList.add(category_2);
					
					String category_3  = textField_3.getText();
					category_3 = category_3.trim();
					if(category_3.equals("")) {
						category_3 = lblNewLabel_3.getText();
					}
					myList.add(category_3);
					
					String category_4  = textField_4.getText();
					category_4 = category_4.trim();
					if(category_4.equals("")) {
						category_4 = lblNewLabel_4.getText();
					}
					myList.add(category_4);
					
					String category_5  = textField_5.getText();
					category_5 = category_5.trim();
					if(category_5.equals("")) {
						category_5 = lblNewLabel_5.getText();
					}
					myList.add(category_5);
					
					
					writeToDefaultCategories(myList);	
					
					System.exit(0);
				}
				
				else {
					JOptionPane.showMessageDialog(null, "Please don't use these symbols: '#' '|'");
				}
				
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(event -> dispose()); 
		
		JLabel lblNoteThisProgram = new JLabel("Note: this program will shut down after the 'done' button is clicked");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnDone, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(60)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_0)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel_2)
								.addComponent(lblNewLabel)
								.addComponent(lblNewLabel_3)
								.addComponent(lblNewLabel_5)
								.addComponent(lblNewLabel_4))))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(129)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(91)
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(108)
							.addComponent(lblNewDefaultCategories, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(63, Short.MAX_VALUE)
					.addComponent(lblNoteThisProgram, GroupLayout.PREFERRED_SIZE, 383, GroupLayout.PREFERRED_SIZE)
					.addGap(57))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(34)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(lblNewDefaultCategories))
					.addGap(53)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_0)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(55)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(56)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(56)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_3)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(54)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_4))
					.addGap(50)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_5)
						.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(42)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDone)
						.addComponent(btnCancel))
					.addGap(28)
					.addComponent(lblNoteThisProgram)
					.addGap(21))
		);
		getContentPane().setLayout(groupLayout);
	}


	
	
	
	
	private ArrayList<String> readDefaultCategoryFile(String filename) {
		// first read file to know where to put new data
		int i = 0;
		ArrayList<String> myList = new ArrayList<>();
		
		try {
			FileReader fin = new FileReader(filename);
			BufferedReader br = new BufferedReader(fin);
			String line;
						
			while ((line = br.readLine()) != null) {				
				
				if(i > 5) {
					break;
				}
				
				else {
					line = line.trim();
					myList.add(line);
					i++;
				}

			}
			
			br.close();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File not found");
		}		
				
			return myList;
	}
	
	private void writeToDefaultCategories(ArrayList<String> items) {
		String filename = Main.defaultCategories.getAbsolutePath();
		
		//need to read the file first in order to save the rest of the old categories
		
		try {
			FileReader fin = new FileReader(filename);
			BufferedReader br = new BufferedReader(fin);
			oldCategories = new ArrayList<String>();
			String line;
			int i = 0;
			
			while ((line = br.readLine()) != null) {				
				
				if(i > 5) {
					line = line.trim();
					oldCategories.add(line);
				}
				
				i++;
			}
		
			br.close();
		
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "File not found");
		}		
				
		
		try {
			FileWriter fout = new FileWriter(filename);
			PrintWriter out = new PrintWriter(fout);
				
			for(String line : items) {
				out.println(line);		
			}
			
			for(String oldCateg : oldCategories) {
				out.println(oldCateg);
			}
				
			out.close();	
					
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File not found");
		}	
			
	}
}
