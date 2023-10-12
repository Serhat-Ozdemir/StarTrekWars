
public class Trap {
   private  char mode;
   private  int remaining_time;
   private int coordinate_x;
   private int coordinate_y;
   public Trap(int x,int y,int time,char mode) {
	   this.coordinate_x=x;
	   this.coordinate_y=y;
	   this.mode=mode;
	   this.remaining_time=time;
   }
public char getMode() {
	return mode;
}
public void setMode(char mode) {
	this.mode = mode;
}
public int getRemaining_time() {
	return remaining_time;
}
public void setRemaining_time(int remaining_time) {
	this.remaining_time = remaining_time;
}
public int getCoordinate_x() {
	return coordinate_x;
}
public void setCoordinate_x(int coordinate_x) {
	this.coordinate_x = coordinate_x;
}
public int getCoordinate_y() {
	return coordinate_y;
}
public void setCoordinate_y(int coordinate_y) {
	this.coordinate_y = coordinate_y;
}       
}
