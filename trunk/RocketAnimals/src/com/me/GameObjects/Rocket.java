package com.me.GameObjects;


import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.helpers.Constants;


public class Rocket {

	
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;
	
	// Collision detection for circle
	private Vector2 circleVector;
	private Vector2 polygonVertexVector;
	private Vector2 circleCenter;
	private float squareRadius;
	private float[] polygonVertices;
	
	private float rotation; // Used to rotate this bird
	private int width;
	private int height;
	
	protected Polygon hitBox; 
	
	private boolean moveRight;
	private boolean moveLeft;
	
	
	//Used to stop the rocket when it reaches the finger/mouse
	private int stopper;
	
	
	public Rocket(float x, float y, int width, int height) {
		this.width = width;
		this.height = height;
		
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 460);

		circleCenter = new Vector2();
		circleVector = new Vector2();
		polygonVertexVector = new Vector2();
		
		hitBox = new Polygon(new float[] {
				0,height,
				width/2,0,
				width,height
		});
		hitBox.setPosition(-42, -42); // Move off screen
		moveRight = false;
		moveLeft = false;
	
	}
	

	
	// Update method
	public void update(float delta) {
		
		// Move the rocket using velocity
		position.add(velocity.cpy().scl(delta));
		
		// Move hitbox to the player's position
		hitBox.setPosition(position.x, position.y);
		
		// Movement boundaries
		if (position.x <= 0) {
			velocity.x = 0; // Stop moving
		}
		
		if (position.x >= Constants.TRUE_WIDTH - width) {
			velocity.x = 0;
		}
		
		
		//check if rocket has reached the finger/mouse's location.
		if( (int) getMiddleX() >= stopper && (moveRight == true)){
	
			velocity.x = 0;
		}
		else if( (int) getMiddleX() <= stopper && (moveRight == false)){
			
			velocity.x = 0;
		}
		
	}
	
	public void moveBird(){
		if(position.y >= Constants.ROCKET_LIFTOFF_STOP_AT_Y){
			position.y = (float) (position.y - 0.50);
		}
	}
	
	public void resetRocket(){
		position.x = Constants.ROCKET_STARTING_X;
		position.y = Constants.ROCKET_STARTING_Y;
	}
	
	//Input Control Methods to track user's current X location
	public void userAtX(int userLocationX){
		this.stopper = userLocationX;
	}
	
	// Control methods
	public void onLeft() {
		if (position.x > 0) {
		moveLeft = true;
		moveRight = false;
		velocity.x -= Constants.ROCKET_VELOCITY;
			if(velocity.x < -Constants.ROCKET_VELOCITY){
				velocity.x = -Constants.ROCKET_VELOCITY ;
			}
		}	
	}
	public void onRight() {
		if (position.x < Constants.TRUE_WIDTH - width) {
			moveLeft = false;
			moveRight = true;
			velocity.x += Constants.ROCKET_VELOCITY;
			if(velocity.x > Constants.ROCKET_VELOCITY){
				velocity.x = Constants.ROCKET_VELOCITY;
			}
		}
	}
	public void onNoClick() {
		moveLeft = false;
		moveRight = false;
		velocity.x = 0; 
		}
	
	// Getter methods
	public float getX() { return position.x; }
	public float getY() { return position.y; }
	public float getWidth() { return width; }
	public float getHeight() { return height; }
	public float getMiddleX() { return (position.x + (width / 2)); }
	public float getMiddleY() { return (position.y + (height / 2)); }
	public float bottom() { return (position.y + height); }
	public boolean isMovingLeft() { return moveLeft; }
	public boolean isMovingRight() { return moveRight; }
	public boolean isMoving() { return (velocity.x != 0); }
	public Polygon getPolygon() { return hitBox; }
	
	
	// Collision Testing methods
	public boolean overlapsWith(AbstractObstacle obstacle) {
		if (obstacle.collisionRect != null)
			return collidesWith(obstacle.collisionRect);
		if (obstacle.collisionCirc != null)
			return collidesWith(obstacle);
		if (obstacle.collisionPoly != null)
			return collidesWith(obstacle.collisionPoly);
		return false;
	}

	
	// Circle test
	private boolean collidesWith(AbstractObstacle obstacle) {
		if (hitBox.contains(obstacle.getCollisionCirc().x, obstacle.getCollisionCirc().y)) {
			return true;
		}
		
		polygonVertices = hitBox.getTransformedVertices();
		squareRadius = obstacle.getCollisionCirc().radius;

		for (int i = 0; i < polygonVertices.length; i += 2) {
			circleVector.set(obstacle.getCollisionCirc().x, obstacle.getCollisionCirc().y);
			polygonVertexVector.set(polygonVertices[i], polygonVertices[i+1]);
			
			if (polygonVertexVector.dst(circleVector) < squareRadius) {
				System.out.println("SHIT");
				return true;
			}
		}
		
		return false;
	}
	
	private boolean collidesWith(Polygon poly) {
		return Intersector.overlapConvexPolygons(hitBox, poly);
	}
	
	
	private boolean collidesWith(Rectangle rect) {
		return Intersector.overlapConvexPolygons(hitBox.getTransformedVertices(), new float[]{
			rect.x, rect.y,
			rect.x + rect.width, rect.y,
			rect.x + rect.width, rect.y + rect.height,
			rect.x, rect.y + rect.height
		}, null);
	}
	
	public Vector2 getposition() { return position; }
}
