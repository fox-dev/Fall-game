package com.me.GameObjects;

import java.util.Random;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.me.helpers.Constants.DIRECTION;

public class AbstractObstacle extends Scrollable{
	
	protected DIRECTION scrollDirection;
	
	// Obj's Xspeed and Yspeed
	protected float objXSpeed, objYSpeed;
	
	// Obj's left-facing and right-facing vertices (if needed)
	protected float[] polygonRightVertices, polygonLeftVertices;
	
	// Obj's hitboxes
	protected Rectangle collisionRect;
	protected Circle collisionCirc;
	protected Polygon collisionPoly;

	////////////////////////////////////////////
	// When AbstractObstacle's constructor is invoked, invoke the super (Scrollable)
    // constructor
	public AbstractObstacle(float x, float y, int width, int height, float ySpeed){
		super(x, y, width, height, ySpeed);

		objXSpeed = 0;
		objYSpeed = ySpeed;
		
		polygonRightVertices = new float[]{
				0,0,
				0,height,
				width,height,
				width,0
		};
		polygonLeftVertices = polygonRightVertices;
		
		scrollDirection = DIRECTION.DOWN;
		setDirection(scrollDirection);
	}
	
	public AbstractObstacle(float x, float y, int width, int height, float xSpeed, float ySpeed){
		super(x, y, width, height, xSpeed, ySpeed);

		objXSpeed = xSpeed;
		objYSpeed = ySpeed;
		
		polygonRightVertices = new float[]{
				0,0,
				0,height,
				width,height,
				width,0
		};
		polygonLeftVertices = polygonRightVertices;
	}
	
	public AbstractObstacle(float x, float y, int width, int height, float ySpeed, float xSpeed, DIRECTION direction) {
		super(x, y, width, height, xSpeed, ySpeed);
		
		objXSpeed = xSpeed;
		objYSpeed = ySpeed;
		
		polygonRightVertices = new float[]{
				0,0,
				0,height,
				width,height,
				width,0
		};
		polygonLeftVertices = polygonRightVertices;
		
		scrollDirection = direction;
		setDirection(scrollDirection);

	}
	
	@Override
	public void update(float delta) 
	{
		super.update(delta);
		
		if (collisionRect != null) 
		{
			collisionRect.setPosition(position);
		}
		else if (collisionCirc != null) 
		{
			collisionCirc.set(position.x + (width/2), position.y + (height/2), (width + height) / 4);
		}
		else if (collisionPoly != null) 
		{
			collisionPoly.setPosition(position.x, position.y);
			collisionPoly.translate(getMiddleX(), getMiddleY());
			collisionPoly.setRotation(rotation);
			collisionPoly.translate(-getMiddleX(), -getMiddleY());
		}
	}
	
	public void setDirection(DIRECTION newDirection) 
	{ 
		// Direction logic
		switch (newDirection) 
		{
			case DOWN:
				velocity.x = 0;
				velocity.y = objYSpeed;
				break;
			case DOWN_LEFT:
				velocity.x = -objXSpeed;
				velocity.y = objYSpeed;
				if (collisionPoly != null) {
					collisionPoly.setVertices(polygonLeftVertices);
				}
				break;
			case DOWN_RIGHT:
				velocity.x = objXSpeed;
				velocity.y = objYSpeed;
				if (collisionPoly != null) {
					collisionPoly.setVertices(polygonRightVertices);
				}
				break;
			default:
				velocity.x = 0;
				velocity.y = objYSpeed;
				break;
		}
	}
	
	// Getters
	public DIRECTION getDirection() { return scrollDirection; }
	public Rectangle getCollisionRect() { return collisionRect; }
	public Circle getCollisionCirc() { return collisionCirc; }
	public Polygon getCollisionPoly() { return collisionPoly; }
	
	public static int randInt(int min, int max) 
	{
	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    return rand.nextInt((max - min) + 1) + min;
	}
	
}
