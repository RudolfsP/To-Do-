
public class GoalItem {

	private String name;
	private double currentTime;
	private double goal;

	public GoalItem(String name, double currentTime, double goal) {
		this.name = name;
		this.currentTime = currentTime;
		this.goal = goal;
	}
	public String getName() {
		return name;
	}
	public double getCurrentTime() {
		return currentTime;
	}
	public double getGoal() {
		return goal;
	}
	
}
