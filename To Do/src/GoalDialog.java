
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class GoalDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField textField;

	public GoalDialog(String dialogName) {
		setTitle(dialogName);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(500, 200, 412, 302);
		setResizable(false);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		
		JLabel lblNewLabel = new JLabel("Select item:");	
		JLabel lblNewLabel_1 = new JLabel("Set goal(h):");
		JLabel lblNewLabel_2 = new JLabel("Set current time:");
		
		
		//start of actions with components
		// 1) comboBox
		ArrayList<ListItem> myList = Main.readFile(Main.itemData.getAbsolutePath());
		for(ListItem item : myList) {
			comboBox.addItem(item.getName());
		}
		
		//comboBox filled	
		
		ButtonGroup bg = new ButtonGroup();
		JRadioButton rdbtnNewRadioButton = new JRadioButton("0");
		JRadioButton rdbtnExistingTime = new JRadioButton("Existing time");
		rdbtnExistingTime.setSelected(true);
		bg.add(rdbtnExistingTime);
		bg.add(rdbtnNewRadioButton);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Done");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// now getting input from textField
				double goalTime = 0;
				boolean exceptionFound = false;
				
				try {
					
					try {
						
						if(!textField.getText().isEmpty()) {
							goalTime = Double.parseDouble(textField.getText());
							goalTime = Main.round(goalTime, 2);
						}
						
						else {
							JOptionPane.showMessageDialog(null, "Don't leave the the text field empty");
							exceptionFound = true;
						}
						
					}
					
					catch(NullPointerException ex) {
						exceptionFound = true;
					}

				}
				
				catch(NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Please enter numbers and use '.' instead of ',' for decimals ");
					exceptionFound = true;
				}
				
				//goal time received unless there was an exception
				
				//send everything to the table
				if(!exceptionFound) {
					Vector<Object> data = new Vector<Object>();
					
					String goalName = (String) comboBox.getSelectedItem();
					data.add(goalName);
					
					double currentTime = 0;
					
					if(rdbtnExistingTime.isSelected()) {
						
						for(ListItem item : myList) {
							
							if(item.getName().equals(goalName)) {
								currentTime = item.getTimeSpent();
								break;
							}
						}
					}
					
					data.add(currentTime);
					data.add(goalTime);
					data.add("Delete");				//meant for deleting row
					
					boolean itemExists = false;
					
					for(int k = 0; k < Main.goalTableModel.getRowCount(); k++) {
						if(goalName.equals(Main.goalTableModel.getValueAt(k, 0))) {
							itemExists = true;
							JOptionPane.showMessageDialog(null, "Goal for this item already exists");
							break;
						}
					}
					
					if(!itemExists) {
						Main.goalTableModel.addRow(data);
						
						double percentage = (currentTime / goalTime) * 100;
						percentage = Main.round(percentage, 2);
						Main.barChart.updateDataset(goalName, percentage);
						
					}
					

	
				}
				
				//write goalTableModel to file
				Main.writeGoals();
				
				
			}
		});
		
		JButton btnNewButton_1 = new JButton("Close");
		btnNewButton_1.addActionListener(event -> dispose()); 

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(36)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
								.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
								.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnNewButton)
							.addGap(28)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
									.addComponent(comboBox, 0, 182, Short.MAX_VALUE)
									.addComponent(textField, GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
									.addGroup(groupLayout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(rdbtnExistingTime)))
								.addGap(36))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(59)
								.addComponent(btnNewButton_1)
								.addGap(94)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(rdbtnNewRadioButton, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(31)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(rdbtnNewRadioButton)
						.addComponent(rdbtnExistingTime))
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_1)
						.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
		//end of set group layout
		
	}
}
