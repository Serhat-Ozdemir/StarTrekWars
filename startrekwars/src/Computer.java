
public class Computer {
	private int instant_x;
    private int instant_y;
    private int score;
    private static Stack path;
    private int initialX;
    private int initialY;
    public int getinitialX() {
    	return initialX;
    }
    public int getinitialY() {
    	return initialY;
    }
    public void setinitialX(int initialX) {
    	this.initialX = initialX;
    }
    public void setinitialY(int initialY) {
    	this.initialY = initialY;
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
	public Stack getPath() {
		return path;
	}
	public void setPath(Stack path) {
		this.path = path;
	}
	
	public Computer(int x,int y,int s, Stack path) {
		instant_x=x;
		instant_y=y;
		score=s;
		this.path = path;
	}
	
	
	public static boolean pathFind(int[][] maze, int x, int y, Stack path) {
		int dx =-1;
		int dy = -1;
		
		if(maze[y][x] ==9) {
			path.push(x);
			path.push(y);
			return true;
		}
		
		if(maze[y][x] == 0) {
			maze[y][x] = 2;
			dx = -1;
			dy = 0;
			if(pathFind(maze,x+dx,y+dy,path)) {
				path.push(x);
				path.push(y);
				return true;
			}
			dx = 1;
			dy = 0;
			if(pathFind(maze,x+dx,y+dy,path)) {
				path.push(x);
				path.push(y);
				return true;
			}
			dx = 0;
			dy = -1;
			if(pathFind(maze,x+dx,y+dy,path)) {
				path.push(x);
				path.push(y);
				return true;
			}
			dx = 0;
			dy = 1;
			if(pathFind(maze,x+dx,y+dy,path)) {
				path.push(x);
				path.push(y);
				return true;
			}
		}
		
			
		return false;
	}
}
	

