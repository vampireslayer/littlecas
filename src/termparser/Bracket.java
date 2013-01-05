package termparser;

public class Bracket extends Node{
	private char bracket;
	public Bracket(char bracket){
		this.bracket = bracket;
	}
	public boolean isClose(){
		int test = ")]}".indexOf(bracket);
		if(test != -1){
			return true;
		}else{
			return false;
		}
	}
	public boolean isOpen(){
		int test = "([{".indexOf(bracket);
		if(test != -1){
			return true;
		}else{
			return false;
		}
	}
}
