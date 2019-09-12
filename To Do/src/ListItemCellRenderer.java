import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class ListItemCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;

	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (value instanceof ListItem) {
			ListItem listItem = (ListItem)value;
			setText(listItem.getName());
			//setToolTipText(listItem.getDescription());
			// setIcon(ingredient.getIcon());
		}
		return this;
	}
}
