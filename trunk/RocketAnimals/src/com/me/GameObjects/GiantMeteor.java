package com.me.GameObjects;

import com.badlogic.gdx.math.Circle;
import com.me.helpers.Constants;

public class GiantMeteor extends AbstractObstacle{

	private int rotateNum;
	
	public GiantMeteor(float x, float y, int width, int height, float scrollSpeed, boolean flag) {
		
		super(x, y, width, height, scrollSpeed);

		rotation = 0;
		rotateNum = randInt(1, 1);
		collisionCirc = new Circle(position, (width + height) / 4);
	}
	
	public GiantMeteor(float x, float y, int width, int height, float scrollSpeed) {
		
		super(x, y, width, height, scrollSpeed);

		rotation = 0;
		rotateNum = randInt(1, 15);
		collisionCirc = new Circle(position, (width + height) / 4);
	}
	
	public GiantMeteor(float x, float y, int width, int height, float scrollSpeed, float xSpeed, Constants.DIRECTION direction) {
		super(x, y, width, height, scrollSpeed, xSpeed, direction);
		
		rotation = 0;
		rotateNum = randInt(1, 15);
		
		collisionCirc = new Circle(position, (width + height) / 4);
	}
	
	@Override
	public void update(float delta){
		super.update(delta);
		
		rotation += rotateNum;
		
	}
}
