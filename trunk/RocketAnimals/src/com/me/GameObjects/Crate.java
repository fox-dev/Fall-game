package com.me.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.me.helpers.Constants;

public class Crate extends AbstractObstacle
{
	private int rotateNum;
	
	public Crate(float x, float y, int width, int height, float scrollSpeed) {
		
		super(x, y, width, height, scrollSpeed);

		rotation = 0;
		rotateNum = randInt(1, 10);
		collisionRect = new Rectangle(position.x,position.y,width,height);
	}
	
	public Crate(float x, float y, int width, int height, float scrollSpeed, float xSpeed, Constants.DIRECTION direction) {
		super(x, y, width, height, scrollSpeed, xSpeed, direction);
		
		rotation = 0;
		rotateNum = randInt(1, 10);
		collisionRect = new Rectangle(position.x,position.y,width,height);
	}
	
	@Override
	public void update(float delta){
		super.update(delta);
		
		rotation += rotateNum;
		
	}
	
	public void setRotation(int rNum)
	{
		rotateNum = rNum;
	}
	
}
