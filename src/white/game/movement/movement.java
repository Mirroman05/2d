package white.game.movement;

public class movement {

	public int move;
	
	public int[] square ;
	
	public void createSquare(){
		square = new int[40];
	for (int i =0; i <2;i++){
		square[i] = 3;
		}
	for (int i =2; i <4;i++){
		square[i] = 1;
		}
	for (int i =4; i <6;i++){
		square[i] = 2;
		}
	for (int i =6; i <8;i++){
		square[i] = 0;
		}
	
	}
	
	public int Move(){
		int r=0;
		if(move!=7){
			r = square[move];
			move++;
			}
			else{
			move =0;
			}
		return r;
	}
	
	
}
