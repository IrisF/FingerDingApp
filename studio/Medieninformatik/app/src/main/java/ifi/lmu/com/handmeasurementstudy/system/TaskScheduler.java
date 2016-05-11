package ifi.lmu.com.handmeasurementstudy.system;

public class TaskScheduler {

	private int userID;
	private int trialIndex;
	private int sessionIndex;
	
	private int[][] latinSquare;

	private static final int[][] latinSquareFirstSession = { 
			{1, 2, 3, 4},
			{2, 1, 4, 3},
            {3, 4, 1, 2},
            {4, 3, 2, 1}
			};
	
	private static final int[][] latinSquareSecondSession = { 
			{1, 2, 3, 4},
			{4, 3, 2, 1},
            {2, 1, 4, 3},
            {3, 4, 1, 2}
            };

	public TaskScheduler(int userID, int sessionIndex) {

		this.userID = userID;
		this.sessionIndex = sessionIndex;
		this.trialIndex = 0;
		if(this.sessionIndex == 0){
			this.latinSquare = TaskScheduler.latinSquareFirstSession;
		}
		else {
			this.latinSquare = TaskScheduler.latinSquareSecondSession;
		}
	}

	public int getUserID() {
		return userID;
	}

	public int getCurrentTrial() {
		return this.latinSquare[(this.userID-1) % latinSquareFirstSession.length][this.trialIndex];
	}

	public void advance() {
		this.trialIndex++;
		if(this.trialIndex >= latinSquareFirstSession.length-1){
			this.trialIndex = latinSquareFirstSession.length-1;
		}
	}
	
	public boolean isDone(){
		return this.trialIndex == latinSquareFirstSession.length-1;
	}

}
