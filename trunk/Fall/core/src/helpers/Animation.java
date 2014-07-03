package helpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation 
{
	private TextureRegion[] frames; 
	private float time; 
	private float delay;
	private int currentFrame, numAnimations;
	private int timesPlayed;
	private boolean setAnim;
	
	public Animation(){
		
	}
	
	public Animation(TextureRegion[] frames){
		this(frames, 1 / 12f);
	}
	
	public Animation(TextureRegion[] frames, float delay)
	{
		setFrames(frames, delay);
		numAnimations = 0;
		setAnim = false;
	}
	
	public Animation(TextureRegion[] frames, float delay, int numTimes)
	{
		this(frames, delay);
		setAnim = true;
		numAnimations = numTimes;
	}
	
	public void setFrames(TextureRegion[] frames, float delay)
	{
		this.frames = frames;
		this.delay = delay;
		time = 0;
		currentFrame = 0; 
		timesPlayed = 0;
		numAnimations = 0;
		setAnim = false;
	}
	
	public void setFrames(TextureRegion[] frames, float delay, int numTimes)
	{
		this.frames = frames;
		this.delay = delay;
		time = 0;
		currentFrame = 0; 
		timesPlayed = 0;
		numAnimations = numTimes;
		setAnim = true;
		
	}
	
	public void update(float dt)
	{
		if(delay <= 0) return;
		time += dt;
		while( time >= delay)
		{
			step();
		}
	}
	
	public void step()
	{
		time -= delay;
		currentFrame++;
		if(currentFrame == frames.length)
		{
			currentFrame = 0;
			timesPlayed++;
		}
	}
	
	public void runOnce(float dt)
	{
		if(delay <= 0) return;
		time += dt;
		while( time >= delay)
		{
			time -= delay;
			if(currentFrame < frames.length - 1)
			{
				currentFrame++;
			}
		}

	}
	
	public TextureRegion getFrame(){return frames[currentFrame];}
	public int getTimesPlayed(){return timesPlayed;}
	
}
