import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

public class AddCategoriesWindow extends JDialog {
	private DefaultTableModel tableModel;
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField textField;
	
	public AddCategoriesWindow(JComboBox<String> comboBox) {
		setResizable(false);
		setBounds(500,200,570,400);
		setTitle("Add more categories");
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblCurrentDefaultItems = new JLabel("Current categories");
		lblCurrentDefaultItems.setVerticalAlignment(SwingConstants.BOTTOM);
		lblCurrentDefaultItems.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblNewLabel = new JLabel("Add a new category:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblCategory = new JLabel("Category");
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!textField.getText().trim().equals("")) {
					
					boolean containsInvalidChars = Main.containsInvalidChars(textField.getText());
					
					if(!containsInvalidChars) {
						Main.comboBox.addItem(textField.getText());
						Vector<Object> data = new Vector<Object>();
						data.add(textField.getText());
						data.add("Delete");
						tableModel.addRow(data);
						
						textField.setText("");
						
						writeComboBoxToFile();
					}

					else {
						JOptionPane.showMessageDialog(null, "Please don't use these symbols: '#' '|'");
					}
					
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 514, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(67)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
							.addGap(109)
							.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(24, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(201, Short.MAX_VALUE)
					.addComponent(lblCurrentDefaultItems, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
					.addGap(141))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(212, Short.MAX_VALUE)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
					.addGap(168))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(132)
					.addComponent(lblCategory, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(381, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addComponent(lblCurrentDefaultItems)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNewLabel)
					.addGap(18)
					.addComponent(lblCategory)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAdd)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(45, Short.MAX_VALUE))
		);
		
		table = new JTable() {
			private static final long serialVersionUID = 1L;
			
			@Override
		    public boolean isCellEditable(int rowIndex, int columnIndex) {
				if(columnIndex == 1) {
					return true;
				}
		            return false;   
		    }
			
		};
		scrollPane.setViewportView(table);
		getContentPane().setLayout(groupLayout);
		
		
		//table + header
		String header[] = new String[] {"Category", "Delete row"};
		tableModel = new DefaultTableModel(null,header);
		
		//end of cell renderer
		table.setModel(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		
		AbstractAction btnNewButton1 = new AbstractAction() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				
				for(int i = 0; i < tableModel.getRowCount(); i++) {
					
					if(table.isCellSelected(i, 1)) {

						tableModel.removeRow(i);
						Main.comboBox.removeItemAt(i);
						
						writeComboBoxToFile();
					}
				}
		    }
		};
		
		ButtonColumn buttonColumn1 = new ButtonColumn(table, btnNewButton1, 1);
		buttonColumn1.setMnemonic(KeyEvent.VK_ENTER);
		
		//populate the table from the comboBox
		for(int i = 0; i < comboBox.getItemCount(); i++) {
			Vector<Object> data = new Vector<Object>();
			comboBox.setSelectedIndex(i);
			data.add(comboBox.getSelectedItem());
			data.add("Delete");
			
			tableModel.addRow(data);
		}
		
	}

	private void writeComboBoxToFile() {
		String filename = Main.defaultCategories.getAbsolutePath();
		
		//first have to read the file 
		ArrayList<String> myList = new ArrayList<String>();
		
		try {
			FileReader fin = new FileReader(filename);
			BufferedReader br = new BufferedReader(fin);
			String line;
			
			
			
			while ((line = br.readLine()) != null) {				
				line = line.trim();
				myList.add(line);
				

			}

			br.close();
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, "File not found");
		}
		
		
		try {

			PrintWriter out = new PrintWriter(filename);
			int x = 0;
			
			for(String item : myList) {
				if(x < 7) {
					out.println(item);
				}
				
				else {
					break;
				}
				
				x++;
			}
			
			for(int i = 0; i < tableModel.getRowCount(); i++) {
				out.println(tableModel.getValueAt(i, 0));
			}
				
			out.close();	
					
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File not found");
		}	
	}

}
