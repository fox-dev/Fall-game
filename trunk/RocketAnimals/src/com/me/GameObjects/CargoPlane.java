package com.me.GameObjects;

import com.me.helpers.Constants;

public class CargoPlane extends AbstractObstacle{

	public CargoPlane(float x, float y, int width, int height, float xSpeed,
			float ySpeed) {
		super(x, y, width, height, xSpeed, ySpeed);
		
	}
	
	@Override
	public void update(float delta){
		super.update(delta);
		prepareEvent();
		
	}
	
	public void prepareEvent(){
		if(this.position.y + height >= height/2){
			velocity.y = 0;
		}
	}
	
	
	
	public void stalk(Rocket player){
		
		if((this.position.x + width/2) >= player.getMiddleX()){  //Move left
			velocity.x = -40;
		}
		else if((this.position.x + width/2) <= player.getMiddleX()){ //Move right
			velocity.x = 40;
		}
		else
			velocity.x = 0;
		
	}

}
