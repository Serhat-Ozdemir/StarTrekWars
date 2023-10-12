import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import enigma.core.Enigma;

public class Main {
	static Computer[] compArray= new Computer[20];
	static int computer_index=0;
	static Player pl = new Player(1,1,0,5,50);
	static int index=0;//global variable for instant x and instant y coordinates
	static int trap_index=1;
	public static int count=0;//global count variable
	public static enigma.console.Console cn = Enigma.getConsole("StarTrek",100,25,35,4);//part of enigma console
	static AllTreasures[] dynamic_treasures=new AllTreasures[1000];//traser instant x and y coordinates
	static AllTreasures[] all_treasures=new AllTreasures[1000];
	static Object[][]boardObj=boardObjAssigner();	
	static Trap[] traps=new Trap[100];
	static Computer compForScore= new Computer(0,0,0, new Stack(10));
	
	public static void randomCompMove(Computer computer) {
		if(boardObj[computer.getInstant_y()][computer.getInstant_x()+1].getClass().equals(Character.class)&&((char)boardObj[computer.getInstant_y()][computer.getInstant_x()+1]) == ' ') {
			boardObj[computer.getInstant_y()][computer.getInstant_x()] = ' ';
			cn.getTextWindow().setCursorPosition(computer.getInstant_x(), computer.getInstant_y());
			System.out.println(' ');
			computer.setInstant_x(computer.getInstant_x()+1);
			boardObj[computer.getInstant_y()][computer.getInstant_x()] = computer;
			cn.getTextWindow().setCursorPosition(computer.getInstant_x(), computer.getInstant_y());
			System.out.println('C');
		}
		else if(boardObj[computer.getInstant_y()][computer.getInstant_x()-1].getClass().equals(Character.class)&&((char)boardObj[computer.getInstant_y()][computer.getInstant_x()-1]) == ' ') {
			boardObj[computer.getInstant_y()][computer.getInstant_x()] = ' ';
			cn.getTextWindow().setCursorPosition(computer.getInstant_x(), computer.getInstant_y());
			System.out.println(' ');
			computer.setInstant_x(computer.getInstant_x()-1);
			boardObj[computer.getInstant_y()][computer.getInstant_y()] = computer;
			cn.getTextWindow().setCursorPosition(computer.getInstant_x(), computer.getInstant_y());
			System.out.println('C');
		}
		else if(boardObj[computer.getInstant_y()+1][computer.getInstant_x()].getClass().equals(Character.class)&&((char)boardObj[computer.getInstant_y()+1][computer.getInstant_x()]) == ' ') {
			boardObj[computer.getInstant_y()][computer.getInstant_y()] = ' ';
			cn.getTextWindow().setCursorPosition(computer.getInstant_x(), computer.getInstant_y());
			System.out.println(' ');
			computer.setInstant_y(computer.getInstant_y()+1);
			boardObj[computer.getInstant_y()][computer.getInstant_y()] = computer;
			cn.getTextWindow().setCursorPosition(computer.getInstant_x(), computer.getInstant_y());
			System.out.println('C');
		}
		else if(boardObj[computer.getInstant_y()-1][computer.getInstant_x()+1].getClass().equals(Character.class)&&((char)boardObj[computer.getInstant_y()-1][computer.getInstant_x()]) == ' ') {
			boardObj[computer.getInstant_y()][computer.getInstant_y()] = ' ';
			cn.getTextWindow().setCursorPosition(computer.getInstant_x(), computer.getInstant_y());
			System.out.println(' ');
			computer.setInstant_y(computer.getInstant_y()-1);
			boardObj[computer.getInstant_y()][computer.getInstant_y()] =computer;
			cn.getTextWindow().setCursorPosition(computer.getInstant_x(), computer.getInstant_y());
			System.out.println('C');
		}
	}
	public static Object mostProfit(Computer computer) {
		double min=999;
		Object mostProfitTreasure = null;
		int coordinate_diff;
		for(int i=0; i<boardObj.length; i++ ) {
			for(int j =0; j< boardObj[0].length;j++) {
				if(boardObj[i][j].getClass().equals(AllTreasures.class)) {
					coordinate_diff = Math.abs(computer.getInstant_x()-j)+Math.abs(computer.getInstant_y()-i);
					if(min >coordinate_diff ) {
						min = coordinate_diff;
						mostProfitTreasure = boardObj[i][j];
					}
				}
				else if(boardObj[i][j].getClass().equals(Trap.class)) {
					coordinate_diff = Math.abs(computer.getInstant_x()-j)+Math.abs(computer.getInstant_y()-i);
					if(min >coordinate_diff ) {
						min = coordinate_diff;
						mostProfitTreasure = boardObj[i][j];
					}
				}
				else if(boardObj[i][j].getClass().equals(Player.class)) {
					coordinate_diff = Math.abs(computer.getInstant_x()-j)+Math.abs(computer.getInstant_y()-i);
					if(min >coordinate_diff ) {
						min = coordinate_diff;
						mostProfitTreasure = boardObj[i][j];
					}
				}
				else if(boardObj[i][j].getClass().equals(TrapArea.class)) {
					if(((TrapArea)boardObj[i][j]).getTrappedObj().getClass().equals(AllTreasures.class)) {
						coordinate_diff = Math.abs(computer.getInstant_x()-j)+Math.abs(computer.getInstant_y()-i);
						if(min >coordinate_diff ) {
							min = coordinate_diff;
							mostProfitTreasure = boardObj[i][j];
						}
					}
					else if(((TrapArea)boardObj[i][j]).getTrappedObj().getClass().equals(Trap.class)) {
						coordinate_diff = Math.abs(computer.getInstant_x()-j)+Math.abs(computer.getInstant_y()-i);
						if(min >coordinate_diff ) {
							min = coordinate_diff;
							mostProfitTreasure = boardObj[i][j];
						}
					}
					else if(((TrapArea)boardObj[i][j]).getTrappedObj().getClass().equals(Player.class)) {
						coordinate_diff = Math.abs(computer.getInstant_x()-j)+Math.abs(computer.getInstant_y()-i);
						if(min >coordinate_diff ) {
							min = coordinate_diff;
							mostProfitTreasure = boardObj[i][j];
						}
					}
				}
			}
		}
		
        return mostProfitTreasure;
    }
	
	public static void basicComputerMove(Computer computer,Player player, int initialX, int initialY) {
		if(computer.getPath().isEmpty())
			return;
		int pathY = initialY - 5 + (int)computer.getPath().pop();
		int pathX = initialX- 5 + (int)computer.getPath().pop();
		if(pathY<=0 || pathY >=23 || pathX<=0 || pathX >=55 )
			return;
		Object nextPiece= boardObj [pathY][pathX]; // next piece is now the right unit
		if(!(nextPiece.getClass().equals(Character.class) && (char)nextPiece == '#')){
		// if (next piece is space) or (eaten object)
		
			if(!(boardObj[computer.getInstant_y()][computer.getInstant_x()]).getClass().equals(TrapArea.class)) { // if treasure is not in the trap area

				boardObj[computer.getInstant_y()][computer.getInstant_x()]=' '; // treasure's current place is now space in board object as data
				
				cn.getTextWindow().setCursorPosition(computer.getInstant_x(),computer.getInstant_y()); // displayed on console						
				System.out.println(' ');
				
				computer.setInstant_x(pathX);
				computer.setInstant_y(pathY);
				
				if((nextPiece.getClass().equals(TrapArea.class)&&((TrapArea)nextPiece).getMode()=='?') ) // if the moved unit is in trap area and at mode ?(pause)
					((TrapArea)boardObj [computer.getInstant_y()][computer.getInstant_x()]).setTrappedObj(computer);//To keep the trap area stable when there is movement into the trap area
				
				else if((nextPiece.getClass().equals(TrapArea.class)&&((TrapArea)nextPiece).getMode()=='!')) {// if the moved unit is in trap area and at mode !(warp)
					pl.setScore(pl.getScore()+300); // eaten treasure's point added to player's score
					cn.getTextWindow().setCursorPosition(computer.getInstant_x(),computer.getInstant_y());
					System.out.println(' ');
					computer=null; // treasure is no longer exist (data erased)
				}							
				else if (nextPiece.getClass().equals(AllTreasures.class)){
					compForScore.setScore(compForScore.getScore()+((AllTreasures)nextPiece).getScore_for_computer());
					boardObj[computer.getInstant_y()][computer.getInstant_x()]= computer; // treasure's coordinates added to boardObj2 as data
				}
				else if (nextPiece.getClass().equals(Player.class)){
					pl.setHealth(pl.getHealth()-1);; // treasure's coordinates added to boardObj2 as data
				}
				else if (nextPiece.getClass().equals(Trap.class)){
					compForScore.setScore(compForScore.getScore()+ 300);
					boardObj[computer.getInstant_y()][computer.getInstant_x()]= computer; // treasure's coordinates added to boardObj2 as data
				}
				if(computer!=null) {// if treasure exist
					cn.getTextWindow().setCursorPosition(computer.getInstant_x(),computer.getInstant_y());
					System.out.println('C'); // treasure displayed 
				}					
			}
			}
 		
 				
 	}
	public static void mazeCreator(int [][] maze,Computer computer){ // if searched are contains treasure, flag will return as TRUE
		for (int y = -5; y <= 5; y++) {			
			for (int x = -5; x <= 5; x++) {
				if(y==-5 || y == 5 || x ==-5 || x ==5)
					maze[y+5][x+5] = 1;
				else if(computer.getInstant_x()+x <1 || computer.getInstant_x()+x >boardObj[0].length-1) 
					maze[y+5][x+5] = 1;
				else if(computer.getInstant_y()+y <1 || computer.getInstant_y()+y >boardObj.length-1)
					maze[y+5][x+5] = 1;
				else if(boardObj[computer.getInstant_y()+y][computer.getInstant_x()+x].getClass().equals(Character.class) && ((char)boardObj[computer.getInstant_y()+y][computer.getInstant_x()+x]) =='#')
					maze[y+5][x+5] = 1;
				else 
					maze[y+5][x+5] = 0;
					
				}
			}			
		}			

	


	public static Object[][] boardObjAssigner()   { //maze data pulled from the maze.txt file, transferred into object type to a 2-dimensional array.
		int totalRow = 23;
		int totalColumn = 55;
		Object[][] myArray = new Object[totalRow][totalColumn]; 
		File file = new File("maze.txt");//file read operation
		Scanner scanner = null;		
			try {
				scanner = new Scanner(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
          cn.getTextAttributes().getBackground();
          
		for (int row = 0; scanner.hasNextLine() && row < totalRow; row++) {
		    char[] chars = scanner.nextLine().toCharArray();
		    for (int i = 0; i < totalColumn && i < chars.length; i++) {
		        myArray[row][i] = chars[i];
		    }
		}
		scanner.close();
		
		return myArray;
	}
 	public static Object[] addRandomTreasure(AllTreasures[] treasures){//adding random treasure to map 
		int counter=0;
				while(counter<20) {	//while loop will turn till the counter is 20 (first 20 treasure)
					 int x=(int)(Math.random()*23);
					 int y = (int)(Math.random()*55);
					if (boardObj[x][y].getClass().equals(Character.class) && (char)boardObj[x][y]==' ') {//check if the unit is space
						boardObj[x][y]=randomlyTreasure();		// random treasure data putted		
						if(boardObj[x][y].getClass().equals(AllTreasures.class) &&  (char)((AllTreasures) boardObj[x][y]).getMode() == '5' ) {
							treasures[index]=new AllTreasures(y,x,'5');//treasures instant x and y for Treasure5
							boardObj[x][y] = treasures[index];
							index++;		
						}
						 else if(boardObj[x][y].getClass().equals(AllTreasures.class) && (char)((AllTreasures) boardObj[x][y]).getMode() == '4') {
							treasures[index]=new AllTreasures(y,x,'4');//treasures instant x and y for Treasure4
							boardObj[x][y] = treasures[index];
							index++;
						}
						 else if(boardObj[x][y].getClass().equals(AllTreasures.class) &&  (char)((AllTreasures) boardObj[x][y]).getMode() == '3') {
							 boardObj[x][y] = new AllTreasures(y,x,'3');
							
						 }
						 else if(boardObj[x][y].getClass().equals(AllTreasures.class) && (char)((AllTreasures) boardObj[x][y]).getMode() == '2') {
							 boardObj[x][y] = new AllTreasures(y,x,'2');
						 }
						 else if(boardObj[x][y].getClass().equals(AllTreasures.class) && (char)((AllTreasures) boardObj[x][y]).getMode() == '1') {
							 boardObj[x][y] = new AllTreasures(y,x,'1');
						 }
						 else if(boardObj[x][y].getClass().equals(AllTreasures.class) && (char)((AllTreasures) boardObj[x][y]).getMode() == '=') {
							 boardObj[x][y] = new Trap(y,x,-1,'=');
						 }
						 else if(boardObj[x][y].getClass().equals(AllTreasures.class) &&  (char)((AllTreasures) boardObj[x][y]).getMode() == '*') {
							 boardObj[x][y] = new Trap(y,x,-1,'*');
						 }
						 else if(boardObj[x][y].getClass().equals(Computer.class)) {
							 boardObj[x][y] = new Computer(y,x,0,new Stack(500));
							 compArray[computer_index]= (Computer) boardObj[x][y];
							 computer_index++;

						 }
						counter++;
						}						
					}	
				for(int i = 0; i<boardObj.length; i++)//map print operation
                {
                    for(int j = 0; j<boardObj[i].length; j++)
                    {
                    	if(boardObj[i][j].getClass().equals(AllTreasures.class))
        		    		System.out.print(((AllTreasures) boardObj[i][j]).getMode());
        		    	else if(boardObj[i][j].getClass().equals(Trap.class))
        		    		System.out.print(((Trap) boardObj[i][j]).getMode());
        		    	else if(boardObj[i][j].getClass().equals(Computer.class))
        		    		System.out.print('C');
        		    	else 
        		    		System.out.print(boardObj[i][j]);
                    }
                    System.out.println();
                }
				return boardObj;
	}
 	
	public static Queue inputToMap(Queue input,AllTreasures[] treasures){//the  function for adding treasures to inputQ
		boolean flag=true;		
				while(flag) {
					 int x=(int)(Math.random()*23);//random x
					 int y = (int)(Math.random()*55);//random y
					if (boardObj[x][y].getClass().equals(Character.class)&& (char)boardObj[x][y]==' ') {//check if the unit is space
						boardObj[x][y]= input.dequeue(); 
						cn.getTextWindow().setCursorPosition(y, x); 
						
						if(boardObj[x][y].getClass().equals(AllTreasures.class)) { // if the checked data unit is a treasure
							System.out.print(((AllTreasures) boardObj[x][y]).getMode()); // data printed
							
							((AllTreasures) boardObj[x][y]).setInstant_x(y); // The data of the treasure 
							((AllTreasures) boardObj[x][y]).setInstant_y(x); // thrown to boardObj is kept.
							
							if(((AllTreasures) boardObj[x][y]).getMode() == '4'||((AllTreasures) boardObj[x][y]).getMode() == '5') {
								// if treasures are dynamic numbers,								
								treasures[index] = (AllTreasures) boardObj[x][y]; // their data hold
								index++;
							}							
					
						}
							
						else if(boardObj[x][y].getClass().equals(Trap.class)) { // if treasures are trap
							System.out.print(((Trap) boardObj[x][y]).getMode()); // trap printed
							
							((Trap) boardObj[x][y]).setCoordinate_x(y);// The data of the trap 
							((Trap) boardObj[x][y]).setCoordinate_y(x);// thrown to boardObj is kept.
						}
						else if(boardObj[x][y].getClass().equals(Computer.class)) { // if treasure is Computer
							 compArray[computer_index]= (Computer) boardObj[x][y];
							 computer_index++;
							System.out.print('C');
							((Computer) boardObj[x][y]).setInstant_x(y);// The data of the trap 
							((Computer) boardObj[x][y]).setInstant_y(x);// thrown to boardObj is kept.
							((Computer) boardObj[x][y]).setPath(new Stack(500));
						}				
						flag = false;							
						}								
				}
				return input;
	}

	public static void inputQDisplayer() {// InputQ display method
		int coordinatx=77;
		int coordinatx2=77;
		for (int i = 0; i < 20; i++) {
			cn.getTextWindow().setCursorPosition(coordinatx, 2);
			System.out.print("<");
			coordinatx--;
		}
		for (int i = 0; i < 20; i++) {
			cn.getTextWindow().setCursorPosition(coordinatx2, 4);
			System.out.print("<");
			coordinatx2--;
		}
	}
	public static void inputQWriter(Queue q) {// inputQ writer 
		int x=58;
		while(x<78) {
			cn.getTextWindow().setCursorPosition(x, 3);
			if(q.peek().getClass().equals(AllTreasures.class))
				System.out.print(((AllTreasures)q.peek()).getMode());
			else if(q.peek().getClass().equals(Trap.class))
				System.out.print(((Trap)q.peek()).getMode());
			else if (q.peek().getClass().equals(Character.class))
				System.out.print(((char)q.peek()));
			else if (q.peek().getClass().equals(Computer.class))
				System.out.print('C');
			
			q.enqueue(q.dequeue());
			x++;
		}
	}
	public static Object randomlyTreasure() {//randomly treasure creater
		Object retObject=null;
		int x=(int)((Math.random()*40)+1);
		if (x<=12) {
			retObject= new AllTreasures(0,0,'1');			
		}
		else if ( x<=20&&x>12) {
			retObject= new AllTreasures(0,0,'2');					
		}
		else if (x<=26 && x>20) {
			retObject= new AllTreasures(0,0,'3');					
		}
		else if (x<=31 && x>26) {
			retObject= new AllTreasures(0,0,'4');				
		}
		else if (x<=35 && x>31) {
			retObject= new AllTreasures(0,0,'5');					
		}
		else if (x<=37 && x>35) {
			retObject= new Trap(0, 0, 0, '=');		
		}
		else if (x<=38 && x>37) {
			retObject= new Trap(0, 0, 0, '*');						
		}
		else if (x<=40 && x>38) {
			retObject= new Computer(0, 0, 0, new Stack(500));		
		}		
		return retObject;
	}
	public static void backPackItemDisplayer(Stack s) {// backpack writer method
		Stack emptyStack=new Stack(s.size());
		int y=13;
		while(!s.isEmpty()) {
			emptyStack.push(s.pop());
			
		}
		while(!emptyStack.isEmpty()) {
			cn.getTextWindow().setCursorPosition(62, y);
			s.push(emptyStack.pop());
			if(s.peek().getClass().equals(AllTreasures.class))
				System.out.println(((AllTreasures)s.peek()).getMode());
			else if(s.peek().getClass().equals(Trap.class))
				System.out.println(((Trap)s.peek()).getMode());
			y--;
		}
		
		
	
		
	}
	public static void backPackDisplayer( Player p) {// backpack displayer 
		int a=6;
		for (int i = 0; i <8; i++) {
			cn.getTextWindow().setCursorPosition(60, a);
			System.out.println("|   |");
			a++;
		}
		
		cn.getTextWindow().setCursorPosition(60, 14);
		System.out.println("+---+");
		cn.getTextWindow().setCursorPosition(60, 15);
		System.out.println("P.Backpack" );
		cn.getTextWindow().setCursorPosition(60, 17);
		System.out.println("P.Energy: "+ p.getRemaining_energy_time());
		cn.getTextWindow().setCursorPosition(60, 18);
		System.out.println("P.Score: " + p.getScore());
		cn.getTextWindow().setCursorPosition(60, 19);
		System.out.println("P.Life: " + p.getHealth());
		cn.getTextWindow().setCursorPosition(60, 21);
		System.out.println("C.Score: "+compForScore.getScore());
		cn.getTextWindow().setCursorPosition(62, 22);
		System.out.println("Time: ");
		
	}
	public static void numberMovements(AllTreasures treasure,Object[][] boardObj2) {//Number movements method
		Object nextPiece; 
		boolean flag=true;
		int randomCount =0;
		while(flag) {
			if(randomCount == 10) // if dynamic numbers tried ways 10 times and stuck, while loop broke
				break;
			int direction=(int)((Math.random()*4)+1); // random direction 
			switch(direction) {
			case 1: //moving to the Right
				nextPiece= boardObj2 [treasure.getInstant_y()][treasure.getInstant_x()+1]; // next piece is now the right unit
				if((nextPiece.getClass().equals(Character.class) && (char)nextPiece == ' ')||(nextPiece.getClass().equals(TrapArea.class) &&((TrapArea)nextPiece).getTrappedObj().getClass().equals(Character.class))){
				// if (next piece is space) or (eaten object)
				
					if(!(boardObj2[treasure.getInstant_y()][treasure.getInstant_x()]).getClass().equals(TrapArea.class)) { // if treasure is not in the trap area
	
						boardObj2[treasure.getInstant_y()][treasure.getInstant_x()]=' '; // treasure's current place is now space in board object as data
						
						cn.getTextWindow().setCursorPosition(treasure.getInstant_x(),treasure.getInstant_y()); // displayed on console						
						System.out.println(' ');
						
						treasure.setInstant_x(treasure.getInstant_x()+1);// treasure moved 1 unit to the right
						
						if((nextPiece.getClass().equals(TrapArea.class)&&((TrapArea)nextPiece).getMode()=='?') ) // if the moved unit is in trap area and at mode ?(pause)
							((TrapArea)boardObj2 [treasure.getInstant_y()][treasure.getInstant_x()]).setTrappedObj(treasure);//To keep the trap area stable when there is movement into the trap area
						
						else if((nextPiece.getClass().equals(TrapArea.class)&&((TrapArea)nextPiece).getMode()=='!')) {// if the moved unit is in trap area and at mode !(warp)
							pl.setScore(pl.getScore()+treasure.getScore_for_human()); // eaten treasure's point added to player's score
							treasure=null; // treasure is no longer exist (data erased)
						}							
						else
							boardObj2[treasure.getInstant_y()][treasure.getInstant_x()]= treasure; // treasure's coordinates added to boardObj2 as data
						
						if(treasure!=null) {// if treasure exist
							cn.getTextWindow().setCursorPosition(treasure.getInstant_x(),treasure.getInstant_y());
							System.out.println(treasure.getMode()); // treasure displayed 
						}					
					}
					flag=false;
					}
					break;
			case 2://moving to the left
			    nextPiece= boardObj2 [treasure.getInstant_y()][treasure.getInstant_x()-1];
			    if((nextPiece.getClass().equals(Character.class) && (char)nextPiece == ' ')||(nextPiece.getClass().equals(TrapArea.class) && (((TrapArea)nextPiece).getMode() =='?'||
						((TrapArea)nextPiece).getMode()=='!'))){//check next place 
			    	if(!(boardObj2[treasure.getInstant_y()][treasure.getInstant_x()]).getClass().equals(TrapArea.class)) {
						boardObj2[treasure.getInstant_y()][treasure.getInstant_x()]=' ';
						cn.getTextWindow().setCursorPosition(treasure.getInstant_x(),treasure.getInstant_y());
						System.out.println(' ');
						treasure.setInstant_x(treasure.getInstant_x()-1);// checking  (instan_x+1) place (right)
						if((nextPiece.getClass().equals(TrapArea.class)&&((TrapArea)nextPiece).getMode()=='?') )
							((TrapArea)boardObj2 [treasure.getInstant_y()][treasure.getInstant_x()]).setTrappedObj(treasure);
						else if((nextPiece.getClass().equals(TrapArea.class)&&((TrapArea)nextPiece).getMode()=='!')) {
							pl.setScore(pl.getScore()+treasure.getScore_for_human());
							treasure=null;
						}							
						else
							boardObj2[treasure.getInstant_y()][treasure.getInstant_x()]= treasure;
						if(treasure!=null) {
							cn.getTextWindow().setCursorPosition(treasure.getInstant_x(),treasure.getInstant_y());
							System.out.println(treasure.getMode());
						}					
					}
					flag=false;
					}
					break;
			case 3://moving above
				nextPiece=boardObj2 [treasure.getInstant_y()-1][treasure.getInstant_x()];
				if((nextPiece.getClass().equals(Character.class) && (char)nextPiece == ' ')||(nextPiece.getClass().equals(TrapArea.class) && (((TrapArea)nextPiece).getMode() =='?'||
						((TrapArea)nextPiece).getMode()=='!'))){//check next place 
					if(!(boardObj2[treasure.getInstant_y()][treasure.getInstant_x()]).getClass().equals(TrapArea.class)) {
						boardObj2[treasure.getInstant_y()][treasure.getInstant_x()]=' ';
						cn.getTextWindow().setCursorPosition(treasure.getInstant_x(),treasure.getInstant_y());
						System.out.println(' ');
						treasure.setInstant_y(treasure.getInstant_y()-1);// checking  (instan_x+1) place (right)
						if((nextPiece.getClass().equals(TrapArea.class)&&((TrapArea)nextPiece).getMode()=='?') )
							((TrapArea)boardObj2 [treasure.getInstant_y()][treasure.getInstant_x()]).setTrappedObj(treasure);
						else if((nextPiece.getClass().equals(TrapArea.class)&&((TrapArea)nextPiece).getMode()=='!')) {
							pl.setScore(pl.getScore()+treasure.getScore_for_human());
							treasure=null;
						}							
						else
							boardObj2[treasure.getInstant_y()][treasure.getInstant_x()]= treasure;
						if(treasure!=null) {
							cn.getTextWindow().setCursorPosition(treasure.getInstant_x(),treasure.getInstant_y());
							System.out.println(treasure.getMode());
						}					
					}
					flag=false;
					}
					break;
			case 4: //moving below
				
				nextPiece=boardObj2 [treasure.getInstant_y()+1][treasure.getInstant_x()];
				if((nextPiece.getClass().equals(Character.class) && (char)nextPiece == ' ')||(nextPiece.getClass().equals(TrapArea.class) && (((TrapArea)nextPiece).getMode() =='?'||
						((TrapArea)nextPiece).getMode()=='!'))){//check next place 
					if(!(boardObj2[treasure.getInstant_y()][treasure.getInstant_x()]).getClass().equals(TrapArea.class)) {
						boardObj2[treasure.getInstant_y()][treasure.getInstant_x()]=' ';
						cn.getTextWindow().setCursorPosition(treasure.getInstant_x(),treasure.getInstant_y());
						System.out.println(' ');
						treasure.setInstant_y(treasure.getInstant_y()+1);// checking  (instan_x+1) place (right)
						if((nextPiece.getClass().equals(TrapArea.class)&&((TrapArea)nextPiece).getMode()=='?') )
							((TrapArea)boardObj2 [treasure.getInstant_y()][treasure.getInstant_x()]).setTrappedObj(treasure);
						else if((nextPiece.getClass().equals(TrapArea.class)&&((TrapArea)nextPiece).getMode()=='!')) {
							pl.setScore(pl.getScore()+treasure.getScore_for_human());
							treasure=null;
						}							
						else
							boardObj2[treasure.getInstant_y()][treasure.getInstant_x()]= treasure;
						if(treasure!=null) {
							cn.getTextWindow().setCursorPosition(treasure.getInstant_x(),treasure.getInstant_y());
							System.out.println(treasure.getMode());
						}					
					}
					flag=false;
					}
					break;
					
				}	
			randomCount++;
		}			
		}
	public static void gameBoardWriter() {
	    cn.getTextWindow().setCursorPosition(0,0);
	    for(int i = 0; i<boardObj.length; i++) // game board writer
		{
		    for(int j = 0; j<boardObj[i].length; j++)
		    {
		    	if(boardObj[i][j].getClass().equals(AllTreasures.class))
		    		System.out.print(((AllTreasures) boardObj[i][j]).getMode());
		    	else if(boardObj[i][j].getClass().equals(Trap.class))
		    		System.out.print(((Trap) boardObj[i][j]).getMode());
		    	else if(boardObj[i][j].getClass().equals(Computer.class))
		    		System.out.print('C');
		    	else 
		    		System.out.print(boardObj[i][j]);
		    }
		    System.out.println();
		}
	}
	public static void main(String[] args) throws Exception {
	int[][] maze = new int[11][11];
	Stack path = new Stack(500);
	
		
	    Queue q=new Queue(21); // to transfer 20 treasures to the map at the start of the game
	    
	    for (int i = 0; i < 20; i++) {
			q.enqueue(randomlyTreasure()); // random treasures added to the queue 'q'
		}
	    
		int menu = GameArea.menuPrinter();	
		boardObj=(Object[][]) addRandomTreasure(dynamic_treasures);// adding 20 treasures to game area

		while(true) {
			if(menu == 1) {									
			    cn.getTextWindow().setCursorPosition(0,0);
			    
				inputQDisplayer();	// inputQ displayed		
			    long a= System.currentTimeMillis(); // for inputQ timing
			    long c=System.currentTimeMillis();// for game time
			    long playermove=System.currentTimeMillis();
			    long numbermove=System.currentTimeMillis();
			    long energytime=System.currentTimeMillis();
			    Long traptimer=System.currentTimeMillis();			    
			    gameBoardWriter();
			    long  comp=System.currentTimeMillis();

				while(true) {
					if(pl.getHealth() ==0)
						System.exit(0);
				    cn.getTextWindow().setCursorPosition(0,0);
					boolean flag=true;
					long b= System.currentTimeMillis(); // for inputQ timing
					long d=System.currentTimeMillis(); // for game time
					long playermove2=System.currentTimeMillis();
					long numbermove2=System.currentTimeMillis();
					long energytime2=System.currentTimeMillis();
					long traptimer2=System.currentTimeMillis();
					long comp2=System.currentTimeMillis();
					cn.getTextWindow().setCursorPosition(0, 0);
					
					
					if(comp2- comp >=500) {
					for(int i = 0; i<= computer_index; i++) {
					if(compArray[i]!= null) {
					Object temp = mostProfit(compArray[i]);
					if(temp.getClass().equals(AllTreasures.class)) {
						if(!compArray[i].getPath().isEmpty()) {
							basicComputerMove(compArray[i],pl, compArray[i].getinitialX() , compArray[i].getinitialY());
							break;
						}
						
						if((((AllTreasures)temp).getInstant_x()-compArray[i].getInstant_x()+5>0 &&((AllTreasures)temp).getInstant_x()-compArray[i].getInstant_x()+5<10)
								&& (((AllTreasures)temp).getInstant_y()-compArray[i].getInstant_y()+5>=0&&((AllTreasures)temp).getInstant_y()-compArray[i].getInstant_y()+5<=10)) {
							
							mazeCreator(maze,compArray[i]);
							maze[((AllTreasures)temp).getInstant_y()-compArray[i].getInstant_y()+5][((AllTreasures)temp).getInstant_x()-compArray[i].getInstant_x()+5] = 9;
							Computer.pathFind(maze, 5, 5, compArray[i].getPath());
							compArray[i].getPath().pop();
							compArray[i].getPath().pop();
							 compArray[i].setinitialX( compArray[i].getInstant_x());
							 compArray[i].setinitialY( compArray[i].getInstant_y());
							basicComputerMove(compArray[i],pl, compArray[i].getinitialX() ,compArray[i].getinitialY());
							
								
						}
						else {
							randomCompMove(compArray[i]);
						}
							
					}
					else if(temp.getClass().equals(Trap.class)) {
						if(!compArray[i].getPath().isEmpty()) {
							basicComputerMove(compArray[i],pl, compArray[i].getinitialX() , compArray[i].getinitialY());
							break;
						}
						
						if((((Trap)temp).getCoordinate_x()-compArray[i].getInstant_x()+5>0 &&((Trap)temp).getCoordinate_x()-compArray[i].getInstant_x()+5<10)
								&& (((Trap)temp).getCoordinate_y()-compArray[i].getInstant_y()+5>=0&&((Trap)temp).getCoordinate_y()-compArray[i].getInstant_y()+5<=10)) {
							mazeCreator(maze,compArray[i]);
							maze[((Trap)temp).getCoordinate_y()-compArray[i].getInstant_y()+5][((Trap)temp).getCoordinate_x()-compArray[i].getInstant_x()+5] = 9;
							Computer.pathFind(maze, 5, 5, compArray[i].getPath());
							compArray[i].getPath().pop();
							compArray[i].getPath().pop();
							 compArray[i].setinitialX( compArray[i].getInstant_x());
							 compArray[i].setinitialY( compArray[i].getInstant_y());
							basicComputerMove(compArray[i],pl, compArray[i].getinitialX() ,compArray[i].getinitialY());
						
								
						}
						else {
							randomCompMove(compArray[i]);
						}
							
					}
					else if(temp.getClass().equals(Player.class)) {
						if(!compArray[i].getPath().isEmpty()) {
							basicComputerMove(compArray[i],pl, compArray[i].getinitialX() , compArray[i].getinitialY());
							break;
						}
						
						if((((Player)temp).getInstant_x()-compArray[i].getInstant_x()+5>0 &&((Player)temp).getInstant_x()-compArray[i].getInstant_x()+5<10)
								&& (((Player)temp).getInstant_y()-compArray[i].getInstant_y()+5>=0&&((Player)temp).getInstant_y()-compArray[i].getInstant_y()+5<=10)) {
							mazeCreator(maze,compArray[i]);
							maze[((Player)temp).getInstant_y()-compArray[i].getInstant_y()+5][((Player)temp).getInstant_x()-compArray[i].getInstant_x()+5] = 9;
							Computer.pathFind(maze, 5, 5, compArray[i].getPath());
							compArray[i].getPath().pop();
							compArray[i].getPath().pop();
							 compArray[i].setinitialX( compArray[i].getInstant_x());
							 compArray[i].setinitialY( compArray[i].getInstant_y());
							basicComputerMove(compArray[i],pl, compArray[i].getinitialX() ,compArray[i].getinitialY());
						
								
						}
						else {
							randomCompMove(compArray[i]);
						}
							
					}

					
					
					
					}
					}
					comp = System.currentTimeMillis();
					}
					
						
						
					backPackDisplayer(pl);					
					backPackItemDisplayer(pl.Backpack);
					cn.getTextWindow().setCursorPosition(67, 22);
					System.out.print((d-c)/1000); // game time data printed
					if(energytime2-energytime>=1000&&pl.getRemaining_energy_time()!=0) { // to decrease player's energy
						pl.setRemaining_energy_time(pl.getRemaining_energy_time()-1); 
						energytime=System.currentTimeMillis();
					}
					
					if(playermove2-playermove>=250&&pl.getRemaining_energy_time()==0) { // player speed without energy
						pl.playerActions((boardObj));
						playermove= System.currentTimeMillis();
					}
					else if(playermove2-playermove>=125&&pl.getRemaining_energy_time()!=0) {// player speed with energy
						pl.playerActions((boardObj));
						playermove= System.currentTimeMillis();
					}
					
					

					for(int i=0;i< index+1;i++) {						
						if( dynamic_treasures[i]!=null&& dynamic_treasures[i].getInstant_x()==pl.getInstant_x()&& dynamic_treasures[i].getInstant_y()==pl.getInstant_y()) {
							// if player and dynamic treasure coordinates are equal, dynamic treasure is eaten
							
							dynamic_treasures[i]=null; // dynamic treasure data erased
							
						}
						if( dynamic_treasures[i]!=null&&(numbermove2-numbermove)>=500) { // if treasure is not eaten
							numberMovements( dynamic_treasures[i],boardObj); // dynamic treasure movement
							flag = false; 
						}
					}
					if(!flag)
						numbermove= System.currentTimeMillis(); // to reset the timer for dynamic treasure
					
					if (traptimer2-traptimer>=1000) { // to decrease the trap timer after trap device is activated
						for(int i = 0;i<trap_index;i++) {
							if(traps[i]!=null) { // if trap time isn't done
							traps[i].setRemaining_time(traps[i].getRemaining_time()-1); // decrease time
							if (traps[i].getRemaining_time()==0) { 
								
								for (int j = traps[i].getCoordinate_x()-1; j <= traps[i].getCoordinate_x()+1; j++) {  // Deleting elements in the searched trap area
									for (int j2 = traps[i].getCoordinate_y()-1; j2 <= traps[i].getCoordinate_y()+1; j2++) {
									 if((boardObj[j2][j].getClass().equals(TrapArea.class))) { //Deactivates the area affected by the trap
										 boardObj[j2][j] = ((TrapArea)boardObj[j2][j]).getTrappedObj(); 
									 }
									 else if(boardObj[j2][j].getClass().equals(Trap.class)) { // to delete trap in the middle
										 	cn.getTextWindow().setCursorPosition(j, j2);
											System.out.print(' ');
											boardObj[j2][j] = ' '; // trap data doesn't exist in board object
									 } 
							}								
						}								
								traps[i] = null; //trap data doesn't exist in traps array
					}					
			}
	}
						traptimer=System.currentTimeMillis(); // reset
						}
					
					
					if((b-a)>=3000) {
						inputToMap(q, dynamic_treasures); // in every 3 seconds, 1 item added to the map from inputQ
						a = System.currentTimeMillis();
						q.enqueue(randomlyTreasure()); // 1 item added to inputQ
					}
					inputQWriter(q);
					
					cn.getTextWindow().setCursorPosition(0, 0);
					Thread.sleep(20);	
				    cn.getTextWindow().setCursorPosition(0,0);
			}			
			}
			else if(menu == 2) {
				System.out.println("Bye");
				System.exit(0);
			}
			
		
	
	}
	}
}
