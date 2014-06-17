package com.me.GameObjects;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.utils.Array;
import com.me.Sectors.Earth;
import com.me.Sectors.Sector;
import com.me.helpers.Constants;


public class ScrollableHandler 
{
	
	private Sector e;
	
	private Background bg; //temp
	
	private Array<AbstractObstacle> obstacleList = new Array<AbstractObstacle>();
 	private Iterator<AbstractObstacle> iterator;
	private int numObstacles = 0;
	
	
	private int dodged = 0;
	
	private float runTime = 0;
	
	private Random r;
	
	//To keep track of rocket position
	private Rocket playerRocket;
	
	
	
	public ScrollableHandler()
	{	
		
		e = new Earth(obstacleList, playerRocket);
		//iterator = new AbstractObstacle(0, 0, 30, 30, SCROLL_SPEED); //using default bird sprite, change sprite and position later
		r = new Random();
			
		bg = new Background(0, Constants.TRUE_HEIGHT - 102, Constants.TRUE_WIDTH, 102, 15);
	}
	                   
	public void update(float delta){
		System.out.println("Runtime : " + runTime);
		runTime += delta;
		
		e.update(delta);
		e.setRocket(playerRocket);
		dodged = e.getDodged();
		
	}
	

	
	
	public void scrollBackgroundUpdate(float delta){
		bg.update(delta);
		
	}
	
	public Array<AbstractObstacle> getAbstractObstacles(){ return obstacleList;}
	
	
	// Need this to remove objects for collision detection
	public void removeObject(AbstractObstacle obstacle) 
	{
		obstacleList.removeValue(obstacle, true);
		numObstacles--;
	}
	
	
	//Remove all objects when restarting game
		public void resetObjects(){
			obstacleList = new Array<AbstractObstacle>();
			numObstacles = 0;
		
			runTime = 0;
			
			//bg = new Background(0, Constants.TRUE_HEIGHT - 102, Constants.TRUE_WIDTH, 102, 15);
			
			bg.position.x = 0;
			bg.position.y = Constants.TRUE_HEIGHT - 102;
			dodged = 0;
			
			e = new Earth(obstacleList, playerRocket);
			
		}
	

	public int getDodged() { return dodged;};
	public Background getFrontBackground(){return bg;}
	
	//Set rocket
	public void setRocket(Rocket rocket){
		playerRocket = rocket;
	}

}
