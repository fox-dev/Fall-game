package com.me.Sectors;

import com.badlogic.gdx.utils.Array;
import com.me.GameObjects.AbstractObstacle;
import com.me.GameObjects.Background;
import com.me.GameObjects.Meteor;
import com.me.GameObjects.Rocket;
import com.me.helpers.AssetLoader;
import com.me.helpers.Constants;

public class Space extends Sector{
	
	int numObstacles = 0;

	
	private float runTime = 0;

	public Space(Array<AbstractObstacle> myList, Rocket p) {
		super(myList, p);
		//bg = new Background(0, Constants.TRUE_HEIGHT - 102, Constants.TRUE_WIDTH, 102, 15);
		bg = new Background(0, 0-525, 99, 500, 15);
	}
	
	public Space(Array<AbstractObstacle> myList, Rocket p, int dodged) {
		super(myList, p, dodged);
		//bg = new Background(0, Constants.TRUE_HEIGHT - 102, Constants.TRUE_WIDTH, 102, 15);
		bg = new Background(0, 0-525, 99, 500, 15);
	}
	
	@Override
	public void update(float delta){
		runTime += delta;
		addGenerics(delta);
		
		iterator = obstacleList.iterator();
		while(iterator.hasNext())
		{	
			AbstractObstacle o = iterator.next();
			o.update(delta);
			
			
		
			if (runTime <= FIRST_WAVE_TIME) {
				OBSTACLE_LIMIT = LOW_OBSTACLE_NUM;
			}
			
			if (runTime > FIRST_WAVE_TIME && runTime <= SECOND_WAVE_TIME) {
				OBSTACLE_LIMIT = MID_OBSTACLE_NUM;
			}
			
			if (runTime > SECOND_WAVE_TIME && runTime <= THIRD_WAVE_TIME) {
				OBSTACLE_LIMIT = HI_OBSTACLE_NUM;
			}
	
			if(o.isScrolledDown())
			{
				
				
				iterator.remove();
				numObstacles--;
				dodged++;
			}
			
		}
	}
	
	@Override
	public void addGenerics(float delta){
		
		double rMeteor = Math.random();
		if(rMeteor < 0.04 && numObstacles < OBSTACLE_LIMIT) 
		{	
			// Add objects with down direction first
			                        // (x position, y position, width, height, ySpeed, xSpeed, direction)
			obstacleList.add(new Meteor(r.nextInt(305), -30, 30, 30, randInt(Constants.METEOR_MIN_SPEED_Y, Constants.METEOR_MAX_SPEED_Y), 0f, Constants.DIRECTION.DOWN));
			numObstacles++;
	
		}
	}
	
	@Override
	public void resetObjects(){
		numObstacles = 0;
		runTime = 0;
		dodged = 0;
	}

}
