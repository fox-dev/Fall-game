package com.me.GameObjects;

import com.badlogic.gdx.math.Circle;

public class Projectile extends AbstractObstacle{
	
	public Projectile(float x, float y, int width, int height, float ySpeed) {
		super(x, y, width, height, ySpeed);
		
		collisionCirc = new Circle(position, (width + height) / 4);
		
	}
	
	public Projectile(float x, float y, int width, int height, float xSpeed, float ySpeed) {
		super(x, y, width, height, ySpeed);
		
		this.velocity.x = xSpeed;
		
		collisionCirc = new Circle(position, (width + height) / 4);
		
	}
	
	@Override
	public void update(float delta){
		super.update(delta);
	}
}
