
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import enigma.core.Enigma;

public class Player {
	public static enigma.console.Console cn = Enigma.getConsole("StarTrek",100,25,35,4);
	public static KeyListener klis; 
	public static int keypr;   // key pressed?
	public static int rkey;    // key   (for press/release)
	private int instant_x;
	private int instant_y;	
	private int score;
    private int health;
    private int remaining_energy_time;
    Stack Backpack=new Stack(8);
	
    public Player(int x,int y,int s,int h,int time) {
  	  this.score=s;
  	  this.health=h;
  	  this.remaining_energy_time=time;
  	  this.instant_x=x;
  	  this.instant_y=y;
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

	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getRemaining_energy_time() {
		return remaining_energy_time;
	}
	public void setRemaining_energy_time(int remaining_energy_time) {
		this.remaining_energy_time = remaining_energy_time;
	}
	
	
   
	public void playerActions(Object[][] board) {

	      cn.getTextWindow().addKeyListener(klis);
	      klis=new KeyListener() {
	          public void keyTyped(KeyEvent e) {}
	          public void keyPressed(KeyEvent e) {
	             if(keypr==0) {
	                keypr=1;
	                rkey=e.getKeyCode();
	             }
	          }
	          public void keyReleased(KeyEvent e) {}
	       };

	       if(keypr == 1) { // if a key is pressed
	    	   Object nextPiece = null;
	    	   switch(rkey){
	    	   case KeyEvent.VK_RIGHT:
	    		   playerMove(1,nextPiece,board);break;
	    	   case KeyEvent.VK_LEFT:
	    		   	playerMove(-1,nextPiece,board);break;
	    		   	    		  
	    	   case KeyEvent.VK_UP:
	    		   playerMove(-2,nextPiece,board);break;
	    	   case KeyEvent.VK_DOWN:
	    		   playerMove(2,nextPiece,board);break;
	    	   case KeyEvent.VK_S:
	    		   numberRelease(2, nextPiece, board);break;
	    	   case KeyEvent.VK_W:
	    		   numberRelease(-2, nextPiece, board);break;	    		   
	    	   case KeyEvent.VK_D:
	    		   numberRelease(1, nextPiece, board);break;
	    	   case KeyEvent.VK_A:
	    		   numberRelease(-1, nextPiece, board);break;
	    	   }   	   
	       }
	       keypr =0;
	}
	
	public void scoreCalculator(Object nextPiece) {
		if(nextPiece.getClass().equals(AllTreasures.class)) {//If player eats treasure
			setScore(getScore()+ ((AllTreasures)nextPiece).getScore_for_human()); // updating player score
			if((!Backpack.isEmpty()) && Backpack.peek().getClass().equals(AllTreasures.class)){// if backpack is not empty and there is treasure
				
				if(((AllTreasures)Backpack.peek()).getMode()==((AllTreasures)nextPiece).getMode()) {//if Treasure and eaten treasure's modes are same
					switch(((AllTreasures)nextPiece).getMode()) {
					case '2':Backpack.pop();setRemaining_energy_time(getRemaining_energy_time()+30);break; // if two 2, energy increased and backpack 2 is popped
					case '3':Backpack.pop();Backpack.push(new Trap(0,0,25,'='));break;// if two 3, trap device created and backpack 3 is popped
					case '4':Backpack.pop();setRemaining_energy_time(getRemaining_energy_time()+240);break; // if two 4, energy increased and backpack 4 is popped
					case '5':Backpack.pop();Backpack.push(new Trap(0,0,25,'*'));break; // if two 5, warp device created and backpack 5 is popped
					   
					}				
				}
				else if(!Backpack.isEmpty()&&((AllTreasures)nextPiece).getMode() != '1')//if Treasure and eaten treasure's modes are not the same
					Backpack.pop();	  
			}
			
			else if(!Backpack.isFull()&&((AllTreasures)nextPiece).getMode() != '1')//If the backpack is empty
				Backpack.push(nextPiece); // eaten treasure added to the backpack
		}
		else if(nextPiece.getClass().equals(TrapArea.class)) {//if next piece is trap area type object
			if(((TrapArea)nextPiece).getTrappedObj().getClass().equals(Character.class))//If the eaten object is not a character or a player
				return;
			if(((TrapArea)nextPiece).getTrappedObj().getClass().equals(AllTreasures.class)) 
				setScore(getScore()+((AllTreasures)((TrapArea)nextPiece).getTrappedObj()).score_for_human); // calculating score 
			else if(((TrapArea)nextPiece).getTrappedObj().getClass().equals(Computer.class)) 
				setScore(getScore()+300);
			
			// for trapped objects
			if((!Backpack.isEmpty()) && Backpack.peek().getClass().equals(AllTreasures.class)){// if backpack is not empty and there is treasure
				if(((AllTreasures)Backpack.peek()).getMode()==((AllTreasures)((TrapArea)nextPiece).getTrappedObj()).getMode()) {//if Treasure and eaten treasure's modes are same
					switch(((AllTreasures)Backpack.peek()).getMode()) {
					case '2':Backpack.pop();setRemaining_energy_time(getRemaining_energy_time()+30);break;// if two 2, energy increased and backpack 2 is popped
					case '3':Backpack.pop();Backpack.push(new Trap(0,0,3,'='));break;// if two 3, trap device created and backpack 3 is popped
					case '4':Backpack.pop();setRemaining_energy_time(getRemaining_energy_time()+240);break;// if two 4, energy increased and backpack 4 is popped
					case '5':Backpack.pop();Backpack.push(new Trap(0,0,3,'*'));break; // if two 5, warp device created and backpack 5 is popped
					   
					}				
				}
				else if(!Backpack.isEmpty()&&((AllTreasures)((TrapArea)nextPiece).getTrappedObj()).getMode() != '1')//if Treasure and eaten treasure's modes are not the same
					Backpack.pop();	  
			}
			else if(!Backpack.isFull()&&((AllTreasures)((TrapArea)nextPiece).getTrappedObj()).getMode() != '1')//If the backpack is empty
				Backpack.push(((AllTreasures)((TrapArea)nextPiece).getTrappedObj()));
			else if(((TrapArea)nextPiece).getTrappedObj().getClass().equals(Trap.class) && !Backpack.isFull()){// adds items from trap area to backpack
				
				((Trap)((TrapArea)nextPiece).getTrappedObj()).setRemaining_time(25); // time setted
				Backpack.push(((Trap)((TrapArea)nextPiece).getTrappedObj())); // added to backpack
			}
				
		}
		else if(nextPiece.getClass().equals(Trap.class) && !Backpack.isFull()){//If player eats trap
			((Trap)nextPiece).setRemaining_time(25); // time setted
			 Backpack.push(nextPiece); // added to backpack
		}
		}
	
	
	public void playerMove(int direction, Object nextPiece,Object[][] boardChar) {//1 right, -1 left, -2 up, 2 down
			if(direction== -1 || direction == 1 ) {
				nextPiece = boardChar [getInstant_y()][getInstant_x()+direction]; // updating x whether it will move right or left
			
			}
			else if(direction == -2 ||  direction ==2) {
				nextPiece= boardChar [getInstant_y()+(direction/2)][getInstant_x()]; // updating y whether it will move up or down
			}
				
			if((nextPiece.getClass().equals(Character.class) && (char) nextPiece=='#')
					||(nextPiece.getClass().equals(Trap.class) && ((Trap) nextPiece).getRemaining_time()>0)
					||(nextPiece.getClass().equals(Computer.class))) {//If there is a wall, active trap or computer
				return;
			}
			else {	//if player can move 
				scoreCalculator(nextPiece);
				if(boardChar [getInstant_y()][getInstant_x()].getClass().equals(TrapArea.class)) // if player is in trapped area
					((TrapArea)boardChar[getInstant_y()][getInstant_x()]).setTrappedObj(' ');
				else // if player is not in trapped area
					boardChar [getInstant_y()][getInstant_x()] = ' ';
				
	 		   	cn.getTextWindow().setCursorPosition(getInstant_x(),getInstant_y() );
	 		   	System.out.println(' '); // game area display
	 		   	if(direction==-1 || direction == 1 )
	 		   		setInstant_x(instant_x+direction);
	 		   	else if(direction == -2 ||  direction ==2)
	 		   		setInstant_y(instant_y+(direction/2));
	 		   	
	 		   	cn.getTextWindow().setCursorPosition(getInstant_x(),getInstant_y());
	 		   	if(nextPiece.getClass().equals(TrapArea.class))  // if player moved to trapped area
	 		   		((TrapArea)boardChar[getInstant_y()][getInstant_x()]).setTrappedObj(Main.pl);
	 		   	else  
	 		   		boardChar [getInstant_y()][getInstant_x()] = Main.pl;
	 		   	System.out.println('P');
	 		   	
			}
		}
	public void numberRelease(int direction, Object nextPiece, Object[][]boardChar) {//1 right, -1 left, -2 up, 2 down
		if(direction==-1 || direction == 1 )
			nextPiece= boardChar [getInstant_y()][getInstant_x()+direction];
		else if(direction == -2 ||  direction ==2)
			nextPiece= boardChar [getInstant_y()+(direction/2)][getInstant_x()];
		if((nextPiece.getClass().equals(Character.class) && (char) nextPiece==' ') 
				/*|| (nextPiece.getClass().equals(TrapArea.class) && ((TrapArea)nextPiece).getClass().equals(Character.class))*/) {
			if(!Backpack.isEmpty() && Backpack.peek().getClass().equals(Trap.class)) {//Bos deðilse ve trap atýlýyorsa sayý atýlamýyor varsayarsak
			   if(direction==-1 || direction == 1 ) {
				   cn.getTextWindow().setCursorPosition(getInstant_x()+direction,getInstant_y());
				   System.out.println(((Trap)Backpack.peek()).getMode());				   
				   boardChar[getInstant_y()][getInstant_x()+direction] = Backpack.pop();
				   ((Trap) boardChar[getInstant_y()][getInstant_x()+direction]).setCoordinate_x(instant_x+direction);//Trap location update
				   ((Trap) boardChar[getInstant_y()][getInstant_x()+direction]).setCoordinate_y(instant_y);
				   Main.traps[Main.trap_index]=((Trap)boardChar[getInstant_y()][getInstant_x()+direction]) ;
				   trapchecker(Main.traps[Main.trap_index]);
				   Main.trap_index++;

			   }
		 	   else if(direction == -2 ||  direction ==2) {
		 		  cn.getTextWindow().setCursorPosition(getInstant_x(),getInstant_y()+(direction/2));
		 		  System.out.println(((Trap)Backpack.peek()).getMode());   			 		 
				   boardChar[getInstant_y()+direction/2][getInstant_x()] = Backpack.pop();
				  ((Trap) boardChar[getInstant_y()+(direction/2)][getInstant_x()]).setCoordinate_x(instant_x);
				  ((Trap) boardChar[getInstant_y()+(direction/2)][getInstant_x()]).setCoordinate_y(instant_y+direction/2);
				   Main.traps[Main.trap_index]=((Trap)boardChar[getInstant_y()+(direction/2)][getInstant_x()]) ;
				   trapchecker(Main.traps[Main.trap_index]);
		 		  Main.trap_index++;
		 	   }		   
			}
			else if(!Backpack.isEmpty() && Backpack.peek().getClass().equals(AllTreasures.class))
				Backpack.pop();
		}
	}
	
	public void trapchecker(Trap trap){
		if ( trap.getMode()=='=' ) {
			for (int j = trap.getCoordinate_x()-1; j <= trap.getCoordinate_x()+1; j++) {
				for (int j2 = trap.getCoordinate_y()-1; j2 <= trap.getCoordinate_y()+1; j2++) {
				    if(Main.boardObj[j2][j].getClass().equals(Character.class)&&!(j2==trap.getCoordinate_y()&&j==trap.getCoordinate_x())&&(char)Main.boardObj[j2][j]==' ') {
				    	Main.boardObj[j2][j]=new TrapArea(' ','?');//yenilen obje , mode
				    	//boardcharý dolduruyoruz
				    }
				    else if(!(j2==trap.getCoordinate_y()&&j==trap.getCoordinate_x())&&Main.boardObj[j2][j].getClass().equals(AllTreasures.class)){
				    	Main.boardObj[j2][j]=new TrapArea(Main.boardObj[j2][j],'?');
				    }
				    else if(!(j2==trap.getCoordinate_y()&&j==trap.getCoordinate_x())&&Main.boardObj[j2][j].getClass().equals(Trap.class)){
				    	Main.boardObj[j2][j]=new TrapArea(Main.boardObj[j2][j],'?');	}	
				    else if(!(j2==trap.getCoordinate_y()&&j==trap.getCoordinate_x())&&Main.boardObj[j2][j].getClass().equals(Player.class)){
				    	Main.boardObj[j2][j]=new TrapArea(Main.pl,'?');	}
				    else if(!(j2==trap.getCoordinate_y()&&j==trap.getCoordinate_x())&&Main.boardObj[j2][j].getClass().equals(Computer.class)) {
				    	Main.boardObj[j2][j]=new TrapArea(Main.boardObj[j2][j],'?');	}
				
			}
		}}
		
		else if(trap.getMode()=='*') {
			for (int j = trap.getCoordinate_x()-1; j <= trap.getCoordinate_x()+1; j++) {
				for (int j2 = trap.getCoordinate_y()-1; j2 <= trap.getCoordinate_y()+1; j2++) {
					if(Main.boardObj[j2][j].getClass().equals(Character.class)&&!(j2==trap.getCoordinate_y()&&j==trap.getCoordinate_x())&&(char)Main.boardObj[j2][j]==' ') {
						Main.boardObj[j2][j]=new TrapArea(' ','!');  
					}
					else if(!(j2==trap.getCoordinate_y()&&j==trap.getCoordinate_x())&&Main.boardObj[j2][j].getClass().equals(AllTreasures.class)){
						setScore(getScore()+((AllTreasures)Main.boardObj[j2][j]).score_for_human);
						cn.getTextWindow().setCursorPosition(j,j2);
						System.out.println(' ');
						Main.boardObj[j2][j]=new TrapArea(' ','!');
				    }
					else if(!(j2==trap.getCoordinate_y()&&j==trap.getCoordinate_x())&&Main.boardObj[j2][j].getClass().equals(Trap.class)){
						Main.boardObj[j2][j]=new TrapArea(' ','!');
						cn.getTextWindow().setCursorPosition(j,j2);
						System.out.println(' ');
					}
					else if(!(j2==trap.getCoordinate_y()&&j==trap.getCoordinate_x())&&Main.boardObj[j2][j].getClass().equals(Player.class)){
				    		Main.boardObj[j2][j]=new TrapArea(Main.pl,'!');}
					 else if(!(j2==trap.getCoordinate_y()&&j==trap.getCoordinate_x())&&Main.boardObj[j2][j].getClass().equals(Computer.class)) {
						 setScore(getScore()+ 500/*((Computer)Main.boardChar[j2][j]).score_for_human*/);
						 Main.boardObj[j2][j]=new TrapArea(' ','!');
						 cn.getTextWindow().setCursorPosition(j,j2);
						 System.out.println(' ');	}
			}
		}
 	}
}
	}
