
public class AllTreasures {
     public int instant_x;
     public int instant_y;
     public int score_for_human;
     public int score_for_computer;
     public char mode; 
     
      AllTreasures(int x,int y,char mode) {
    	 this.instant_x=x;
    	 this.instant_y=y;
    	 this.mode=mode;
    	 if (mode=='4') {
    		 score_for_computer=100;
        	 score_for_human=50;
		}
    	 else if(mode=='3') {
    		 score_for_computer=30;
        	 score_for_human=15;
    	 }
    	 else if(mode=='2') {
    		 score_for_computer=10;
        	 score_for_human=5;
    	 }
    	 else if(mode=='1') {
    		 score_for_computer=2;
        	 score_for_human=1;
    	 }
    	 else if(mode=='5') {
    		 score_for_computer=300;
        	 score_for_human=150;
    	 }    	
     }

	public int getInstant_x() {
		return instant_x;
	}

	public void setInstant_x(int instant_x) {
		this.instant_x = instant_x;
	}

	public int getInstant_y() {
		return instant_y;
	}

	public void setInstant_y(int instant_y) {
		this.instant_y = instant_y;
	}

	public int getScore_for_human() {
		return score_for_human;
	}

	public void setScore_for_human(int score_for_human) {
		this.score_for_human = score_for_human;
	}

	public int getScore_for_computer() {
		return score_for_computer;
	}

	public void setScore_for_computer(int score_for_computer) {
		this.score_for_computer = score_for_computer;
	}

	public char getMode() {
		return mode;
	}

	public void setMode(char mode) {
		this.mode = mode;
	}

}
