package com.me.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.me.helpers.Constants;

public class Helicopter extends AbstractObstacle
{
	
	public Helicopter(float x, float y, int width, int height, float scrollSpeed)
	{
		super(x, y, width, height, scrollSpeed);
		
		collisionRect = new Rectangle(position.x,position.y,width,height);
	}
	
	public Helicopter (float x, float y, int width, int height, float scrollSpeed, float glideSpeed, Constants.DIRECTION direction) 
	{
		super(x, y, width, height, scrollSpeed, glideSpeed, direction);
	
		collisionRect = new Rectangle(position.x,position.y,width,height);
	}
	
	
}
