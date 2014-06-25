package handlers;

import com.badlogic.gdx.Input.Keys;
import handlers.B2DVars;
import com.badlogic.gdx.InputAdapter;
import com.mygdx.main.Game;

public class MyInputProcessor extends InputAdapter{
	
	public boolean keyDown(int k){
		if(k == Keys.Z){
			MyInput.setKey(MyInput.BUTTON1, true);
		}
		if(k == Keys.X){
			MyInput.setKey(MyInput.BUTTON2, true);
		}
		if(k == Keys.C){
			MyInput.setKey(MyInput.BUTTON3, true);
		}
		return true;
	}
	
	public boolean keyUp(int k){
		if(k == Keys.Z){
			MyInput.setKey(MyInput.BUTTON1, false);
		}
		if(k == Keys.X){
			MyInput.setKey(MyInput.BUTTON2, false);
		}
		if(k == Keys.C){
			MyInput.setKey(MyInput.BUTTON3, false);
		}
		return true;
	}
	
	public boolean touchDown(int screenX, int screenY, int pointer, int button){
		System.out.println("Down: " + screenX);
		if(screenX < (365)){
			MyInput.setKey(MyInput.BUTTON2, true);
		}
		else{
			MyInput.setKey(MyInput.BUTTON3, true);
		}
	
		
		return true;
	}
	
	public boolean touchUp(int screenX, int screenY, int pointer, int button){
		System.out.println("Up " + screenX);
		if(screenX < (365)){
			MyInput.setKey(MyInput.BUTTON2, false);
		}
		else{
			MyInput.setKey(MyInput.BUTTON3, false);
		}
		
		return true;
	}
	
		
	
	

}
