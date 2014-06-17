package com.me.Sectors;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.utils.Array;
import com.me.GameObjects.AbstractObstacle;
import com.me.GameObjects.Background;
import com.me.GameObjects.Rocket;
import com.me.helpers.Constants;

public class Sector 
{
	private Background bg;
	
	public static final int SCROLL_SPEED = 150;
	
	protected boolean flipObjectX = false;
			
	protected final int LOW_OBSTACLE_NUM = 5;
	protected final int MID_OBSTACLE_NUM = 7;
	protected final int HI_OBSTACLE_NUM = 9;
	
	protected final int FIRST_WAVE_TIME = 15;
	protected final int SECOND_WAVE_TIME = 30;
	protected final int THIRD_WAVE_TIME = 45;
	
	protected int OBSTACLE_LIMIT = LOW_OBSTACLE_NUM;
	protected Array<AbstractObstacle> obstacleList;
	protected Iterator<AbstractObstacle> iterator;
	protected Random r;
	protected Rocket playerRocket;
	
	protected int dodged = 0;
	
	
	public Sector(Array<AbstractObstacle> myList, Rocket p)
	{
		obstacleList = myList;
		playerRocket = p;
		
		bg = new Background(0, Constants.TRUE_HEIGHT - 102, Constants.TRUE_WIDTH, 102, 15);
		
		r = new Random();
	}
	
	
	public void update(float delta){
		
	}
	
	public void addGenerics(float delta)
	{
		
	}
	
	public void initEvent_1(float delta){
		
	}
	
	public void initEvent_2(float delta) {
			
	}
	
	public void addMeteor()
	{
		// Add objects with down direction first
			                      // (x position, y position, width, height, ySpeed, xSpeed, direction)
		
	}
	
	public static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    return rand.nextInt((max - min) + 1) + min;
	}
	
	public void resetObjects(){
		
	}
	
	
	
	public Array<AbstractObstacle> getList(){return obstacleList;}
	
	public int getDodged(){return dodged;}
	
	public void setRocket(Rocket rocket){playerRocket = rocket;}


	
}
