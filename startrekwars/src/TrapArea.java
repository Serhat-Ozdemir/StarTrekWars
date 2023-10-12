
public class TrapArea {
private char mode;
private Object trappedObj;

public TrapArea(Object trappedObj,char mode) {
	this.trappedObj=trappedObj;
	this.mode=mode;
}
public Object getTrappedObj() {
	return trappedObj;
}
public void setTrappedObj(Object trappedObj) {
	this.trappedObj=trappedObj;
}
public char getMode() {
	return mode;
}
public void setMode(char mode) {
	this.mode=mode;
}

}


