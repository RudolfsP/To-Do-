import javax.swing.JDialog;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;

public class SetDefaultItemWindow extends JDialog {
	
	private DefaultTableModel tableModel;
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	
	public SetDefaultItemWindow() {
		setResizable(false);
		setBounds(500,200,570,400);
		setTitle("Set default items");
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblCurrentDefaultItems = new JLabel("Current default items");
		lblCurrentDefaultItems.setVerticalAlignment(SwingConstants.BOTTOM);
		lblCurrentDefaultItems.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblNewLabel = new JLabel("Add a new default item:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblItemName = new JLabel("Item name");
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JLabel lblCategory = new JLabel("Category");
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if((textField.getText().trim().equals("")) || (textField_1.getText().trim().equals(""))) {
					JOptionPane.showMessageDialog(null, "Don't leave the fields empty");
				}
				
				else {
					
					boolean itemNameInvalid = Main.containsInvalidChars(textField.getText());
					boolean categoryNameInvalid = Main.containsInvalidChars(textField_1.getText());
					
					if(!itemNameInvalid && !categoryNameInvalid) {
						Vector<Object> data = new Vector<Object>();
						data.add(textField.getText());
						data.add(textField_1.getText());
						data.add("Delete");
						tableModel.addRow(data);
						
						textField.setText("");
						textField_1.setText("");
					
						
						writeToDefaults(tableModel);
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
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 514, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
									.addGap(90)
									.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(24, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(56)
					.addComponent(lblItemName)
					.addGap(174)
					.addComponent(lblCategory, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(232, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(201, Short.MAX_VALUE)
					.addComponent(lblCurrentDefaultItems, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
					.addGap(141))
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
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCategory)
						.addComponent(lblItemName))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAdd))
					.addContainerGap(45, Short.MAX_VALUE))
		);
		
		table = new JTable() {
			private static final long serialVersionUID = 1L;
			
			@Override
		    public boolean isCellEditable(int rowIndex, int columnIndex) {
				if(columnIndex == 2) {
					return true;
				}
		            return false;   
		    }
			
		};
		scrollPane.setViewportView(table);
		getContentPane().setLayout(groupLayout);
		
		
		//table + header
		String header[] = new String[] { "Item", "Category", "Delete row"};
		tableModel = new DefaultTableModel(null,header);
		
		//end of cell renderer
		table.setModel(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		
		AbstractAction btnNewButton1 = new AbstractAction() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				
				for(int i = 0; i < tableModel.getRowCount(); i++) {
					
					if(table.isCellSelected(i, 2)) {

						tableModel.removeRow(i);
						writeToDefaults(tableModel);
					}
				}
		    }
		};
		
		ButtonColumn buttonColumn1 = new ButtonColumn(table, btnNewButton1, 2);
		buttonColumn1.setMnemonic(KeyEvent.VK_ENTER);
		
		//populate the tableModel
		String filename = Main.defaults.getAbsolutePath();			// filename will need to be dynamic
		
		try {
			FileReader fin = new FileReader(filename);
			BufferedReader br = new BufferedReader(fin);
			String line;
			
			int marker = 0;
			
			while ((line = br.readLine()) != null) {				
				marker = line.indexOf('|');	
				String name = line.substring(0, marker);
				String category = line.substring(marker + 1);
					
				Vector<Object> data = new Vector<Object>();
				data.add(name);
				data.add(category);
				data.add("Delete");
				
				tableModel.addRow(data);
				

			}

			br.close();
		}
		catch(IOException e){
			return;
		}
		

		
	}

	private void writeToDefaults(DefaultTableModel table) {
		
		String filename = Main.defaults.getAbsolutePath();
		try {

			PrintWriter out = new PrintWriter(filename);
				
			for(int i = 0; i < table.getRowCount(); i++) {
				out.println(table.getValueAt(i, 0) + "|" + table.getValueAt(i, 1));
			}
				
			out.close();	
					
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File not found");
		}	
	}
	
	
}
