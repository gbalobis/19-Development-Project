package model;

public class GameSimulation extends Game {

	private char chosenDir;
	private int numOfRuns;
	private int [][] savedBoard = new int [4][4];
	private int savedCurrentScore;

	public GameSimulation(int num) {
		super();
		numOfRuns = num;
	}

	public void startSim(int[][] board, int currentScore) {
		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				savedBoard[i][j]=board[i][j];
			}
		}
		savedCurrentScore = currentScore;
		
		double highest;
		double avgScoreUp = sim('w');
		double avgScoreDown = sim('s');
		double avgScoreLeft = sim('a');
		double avgScoreRight = sim('d');
		
		highest = Math.max(avgScoreUp, avgScoreDown);
		highest = Math.max(avgScoreLeft, highest);
		highest = Math.max(avgScoreRight, highest);
		
//		lowest = Math.min(avgScoreUp, avgScoreDown);
//		lowest = Math.min(avgScoreLeft, highest);
//		lowest = Math.min(avgScoreRight, highest);
//		System.out.println(avgScoreUp);
//		System.out.println(avgScoreDown);
//		System.out.println(avgScoreLeft);
//		System.out.println(avgScoreRight);
//		System.out.println(highest);
		
		if (highest == avgScoreUp)
			setChosenDir('w');
		else if (highest == avgScoreDown)
			setChosenDir('s');
		else if (highest == avgScoreLeft)
			setChosenDir('a');
		else if (highest == avgScoreRight)
			setChosenDir('d');

	}

	public double sim(char dir) {
		
		double avgScore = 0;
		for (int i = 0; i < numOfRuns; i++) {
			
			setBoard(savedBoard);
			setCurrentScore(savedCurrentScore);

			individualSim(dir);

			avgScore += currentScore;

		}
		avgScore /= numOfRuns;
		return avgScore;

	}

	public void individualSim(char dir) {
		move (dir);
//		int i = 0;
		while (!defeatCheck()) {
//			System.out.println(dir);
			randomMove();
        	generateNewTile();
//			i++;
		}

	}

	public char getChosenDir() {
		return chosenDir;
	}

	public void setChosenDir(char chosenDir) {
		this.chosenDir = chosenDir;
	}
//	
//	public int getDifficultyModifier() {
//		return difficultyModifier;
//	}
//
//	public void setDifficultyModifier(int difficultyModifier) {
//		this.difficultyModifier = difficultyModifier;
//	}

//	public Game getMainGame() {
//		return mainGame;
//	}
//
//	public void setMainGame(Game mainGame) {
//		this.mainGame = mainGame;
//	}

}
