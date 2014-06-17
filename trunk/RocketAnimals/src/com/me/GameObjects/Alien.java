package com.me.GameObjects;

import com.me.helpers.Constants;

public class Alien extends AbstractObstacle{

	public Alien(float x, float y, int width, int height, float ySpeed) {
		super(x, y, width, height, ySpeed);
		
		
	}
	
	public Alien(float x, float y, int width, int height, float xSpeed, float ySpeed ) {
		super(x, y, width, height, xSpeed, ySpeed);
		
		
	}
	
	@Override
	public void update(float delta){
		super.update(delta);
		prepareEvent();
		
	}
	
	public void prepareEvent(){
		if(this.position.x >= Constants.TRUE_WIDTH/2 - 57){       //changed this value to fit UFO Sprite  25 > 57
			velocity.x = 0;
		}
	}
	
	public boolean inPosition(){
		return (this.position.x >= Constants.TRUE_WIDTH/2 - 57); //changed this value to fit UFO sprite. 25 > 57
	}
	
	
	

	

}
