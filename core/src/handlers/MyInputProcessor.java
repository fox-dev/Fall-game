package handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

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
		MyInput.x = screenX;
		MyInput.y = screenY;
		MyInput.down = true;
		
		OrthographicCamera cam;
		cam = new OrthographicCamera();
		cam.setToOrtho(true, 320, 480);
		Vector3 tempPos = new Vector3(screenX, screenY, 0);
		cam.unproject(tempPos);
	
		System.out.println("TAPPED: " + tempPos + " " + (tempPos.x < 320/2));
		if(tempPos.x < 320/2 - 30){
		MyInput.setKey(MyInput.BUTTON2, true);
		}
		else if(tempPos.x > 320/2 + 30){
			MyInput.setKey(MyInput.BUTTON3, true);
		}
		
		
		if(tempPos.y > 480/2 + 480/4){
			MyInput.setKey(MyInput.BUTTON1, true);
			
		}
		
		

		return true;
	}
	
	public boolean touchUp(int screenX, int screenY, int pointer, int button){
		System.out.println("Up " + screenX);
		MyInput.x = screenX;
		MyInput.y = screenY;
		MyInput.down = false;
		
		MyInput.setKey(MyInput.BUTTON1, false);
		MyInput.setKey(MyInput.BUTTON2, false);
		MyInput.setKey(MyInput.BUTTON3, false);
		/*
		if(screenX < (365)){
			MyInput.setKey(MyInput.BUTTON2, false);
		}
		else{
			MyInput.setKey(MyInput.BUTTON3, false);
		}
		*/
		
		return true;
	}
	
	public boolean mouseMoved(int x, int y)
	{
		MyInput.x = x;
		MyInput.y = y;
		return true;
	}
	
	public boolean touchDragged(int x, int y, int pointer)
	{
		MyInput.x = x;
		MyInput.y = y;
		//MyInput.down = true;
		
		OrthographicCamera cam;
		cam = new OrthographicCamera();
		cam.setToOrtho(true, 320, 480); 
		Vector3 tempPos = new Vector3(x, y, 0);
		cam.unproject(tempPos);
		
		if(tempPos.x < 320/2 - 30){
			//MyInput.setKey(MyInput.BUTTON3, false);
			MyInput.setKey(MyInput.BUTTON2, true);
		}
		else if(tempPos.x > 320/2 + 30){
			//MyInput.setKey(MyInput.BUTTON2, false);
			MyInput.setKey(MyInput.BUTTON3, true);
		}
		else{
			MyInput.setKey(MyInput.BUTTON2, false);
			MyInput.setKey(MyInput.BUTTON3, false);
		}
		
		
		if(tempPos.y > 480/2 + 480/4){
			MyInput.setKey(MyInput.BUTTON1, true);
			
		}
		
		if(tempPos.y < 480/2 + 480/4){
			MyInput.setKey(MyInput.BUTTON1, false);
			
		}
		
		
	
		
		
		/*
		if(x < (365)){
			MyInput.setKey(MyInput.BUTTON2, true);
		}
		else{
			MyInput.setKey(MyInput.BUTTON3, true);
		}
		*/
		return true;
	}
	
	
		
	
	

}

