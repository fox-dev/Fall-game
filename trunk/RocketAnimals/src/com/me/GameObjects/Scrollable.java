package com.me.GameObjects;

import com.badlogic.gdx.math.Vector2;
import com.me.helpers.Constants;

public class Scrollable {
	
	// Protected is similar to private, but allows inheritance by subclasses.
    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;
    protected int height;
    protected boolean isScrolledDown;
    
    protected float rotation; // Can be used to rotate object
    
    private float deltaScaler = 0;
    
    ////////////////////////////////////////////////////
    
    public Scrollable(float x, float y, int width, int height, float ySpeed) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, ySpeed);
        
        this.width = width;
        this.height = height;
        
        isScrolledDown = false;

        rotation = 0;
    }
    
	////////////////////////////////////////////////////
	    
	public Scrollable(float x, float y, int width, int height, float xSpeed, float ySpeed) {
		position = new Vector2(x, y);
		velocity = new Vector2(xSpeed, ySpeed);
	
		this.width = width; 
		this.height = height;
	
		isScrolledDown = false;

		rotation = 0;
	}
    
    ////////////////////////////////////////////////////
    
    public void update(float delta){
    	//Get delta scaler
    	deltaScaler = delta;
    
		// Move the obstacle using velocity
		position.add(velocity.cpy().scl(delta));

        if (((position.y + height) > 480 + height) || (position.y + height <= 0) || (((position.x + width < 0) || position.x - width > Constants.TRUE_WIDTH))) {
            isScrolledDown = true;
            
        }
	}
    
    /////////////////////////////////////////////////////
    
    public void resetPosition(float newX, float newY){
		position.x = newX;
		position.y = newY;
		isScrolledDown = false;
	}
    
    public void reset(float newY){
    	position.y = newY;
    	isScrolledDown = false;
    }
    
    public void setRandomX(float x){
    	position.x = x;
    }
    
 // Getter methods
 	public float getX() { return position.x; }
 	public float getY() { return position.y; }
 	public float getWidth() { return width; }
 	public float getHeight() { return height; }
	public float getMiddleX() { return width / 2; }
	public float getMiddleY() { return height / 2; }
	public float getRotation() { return rotation; }
 	public boolean isScrolledDown() { return isScrolledDown; }
 	
 // Set Methods for velocity
 	
 	public void setVelocity(int x, int y){
 		velocity = new Vector2(x, y);
 		//position.add(velocity.cpy().scl(deltaScaler));
 		 
 	}
 	
 	
}
