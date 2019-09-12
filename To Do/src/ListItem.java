
public class ListItem {

	private String name;
	private double timeSpent;
	private boolean isCompleted;
	private String category;
	
	public ListItem(String name, String category ,double timeSpent, boolean isCompleted) {
		this.name = name;
		this.category = category;
		this.timeSpent = timeSpent;
		this.isCompleted = isCompleted;
		
	}

	public String getName() {
		return name;
	}

	public double getTimeSpent() {
		return timeSpent;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setTimeSpent(double timeSpent) {
		this.timeSpent = timeSpent;
	}

}
