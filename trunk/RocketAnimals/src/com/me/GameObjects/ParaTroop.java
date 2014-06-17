package com.me.GameObjects;

import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.helpers.Constants;

public class ParaTroop extends AbstractObstacle
{
	private static final int maxXSpeed = 200 , minXSpeed = 0, maxYSpeed = 250, minYSpeed = 150;
	private float xMax, xMin;
	
	public ParaTroop()
	{
		// Using width and height defined below and random number inbetween the speed boundaries
		super(Constants.TRUE_WIDTH / 2, -30, 30, 30, randInt(minXSpeed, maxXSpeed), randInt(minYSpeed, maxYSpeed));
	
		isScrolledDown = false;
	
		collisionRect = new Rectangle(position.x,position.y,width,height);
	
		rotation = 0;
		
		if(position.x > (Constants.TRUE_WIDTH - position.x))
		{
			xMax = Constants.TRUE_WIDTH - position.x; 
		}
		else
		{
			xMax = position.x;
		}
		xMin = position.x - xMax;
		xMax = position.x + xMax;
	}
	
	public ParaTroop(float x, float y, int width, int height, float scrollSpeed)
	{
		super(x, y, width, height, scrollSpeed);
		
		collisionRect = new Rectangle(position.x,position.y,width,height);
		
		if(x > (Constants.TRUE_WIDTH - x))
		{
			xMax = Constants.TRUE_WIDTH - x; 
		}
		else
		{
			xMax = x;
		}
		xMin = x - xMax;
		xMax = x + xMax;
	}
	
	public ParaTroop(float x, float y, int width, int height, float scrollSpeed, float glideSpeed, Constants.DIRECTION direction) 
	{
		super(x, y, width, height, scrollSpeed, glideSpeed, direction);
		
		collisionRect = new Rectangle(position.x,position.y,width,height);
		
		if(x > (Constants.TRUE_WIDTH - x))
		{
			xMax = Constants.TRUE_WIDTH - x; 
		}
		else
		{
			xMax = x;
		}
		xMin = x - xMax;
		xMax = x +xMax;
	}
	
	public void update(float delta)
	{
		// Move the obstacle using velocity
		position.add(velocity.cpy().scl(delta));
				
		// Move the rectangle to the new position
		collisionRect.setPosition(position);
		
		if (position.x + width >= xMax)
		{
			setDirection(Constants.DIRECTION.DOWN_LEFT);
			System.out.println("im moving left!");
		}
		else if(position.x <= xMin)
		{
			setDirection(Constants.DIRECTION.DOWN_RIGHT);
			System.out.println("im movin right!");
		}
		
		// If the Scrollable object is no longer visible:
		if (((position.y + height) > 480 + height) || (((position.x + width < 0) || position.x - width > Constants.TRUE_WIDTH))) 
		{
			isScrolledDown = true;
		}
	}
	
}
