import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;

public class AboutWindow extends JDialog {
	public AboutWindow() {
		
		setBounds(500,200,530,400);
		setResizable(false);
		setTitle("Author");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(10, 11, 500, 220);
		panel.add(lblNewLabel);
		
		ImageIcon icon = new ImageIcon(Main.myLocation + "\\Author.JPG");
		
		lblNewLabel.setIcon(icon);
		
		JLabel lblMadeByMrmeadows = new JLabel("Made by: Mr_Meadows");
		lblMadeByMrmeadows.setBounds(165, 255, 147, 14);
		panel.add(lblMadeByMrmeadows);
		
		JLabel lblDateCreated = new JLabel("Date created: 01/08/2018");
		lblDateCreated.setBounds(165, 293, 147, 14);
		panel.add(lblDateCreated);
		
		JLabel lblEmailForQuestions = new JLabel("Email for questions: Mr_Meadows@gmail.com");
		lblEmailForQuestions.setBounds(165, 328, 327, 22);
		panel.add(lblEmailForQuestions);
		
		
	}

	private static final long serialVersionUID = 1L;
}
