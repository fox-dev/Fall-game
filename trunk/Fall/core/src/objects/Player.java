package objects;

import objects.B2DSprite;
import helpers.AssetLoader;
import helpers.B2DVars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class Player extends B2DSprite
{
	
	private TextureRegion cliffJumper1, cliffJumper2, cliffJumper3, cliffJumper4, currentFrame;
	
	private float runtime;
	
	private boolean glide;
	private boolean falling;
	
	public Player(Body body)
	{
		super(body);
	
		load();
		
		TextureRegion[] temp = {cliffJumper1, cliffJumper2, cliffJumper3, cliffJumper4};
		frames = temp;
		animation = new Animation(.1f, frames);
		animation.setPlayMode(Animation.PlayMode.REVERSED);
		
		currentFrame = frames[0];
		
		runtime = 0;
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(currentFrame, 
				body.getPosition().x * B2DVars.PPM - 12,
				body.getPosition().y * B2DVars.PPM - 12, 24, 24
	
				);
		sb.end();
	}
	
	public void renderSample(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(currentFrame, 
				body.getPosition().x * B2DVars.PPM - 24,
				body.getPosition().y * B2DVars.PPM - 24, 48, 48
	
				);
		sb.end();
	}
	
	public void update(float dt)
	{
	
		runtime += dt;
		
		currentFrame = animation.getKeyFrame(runtime);
	}
	
	public void updateA(float dt){
		System.out.println("RT : " + runtime);
		if(glide){
			runtime += dt;
			System.out.println("FFFFFFFFFFFFFF");
			
		}
		
		if(currentFrame == frames[3] && !glide){
			runtime = 0;
			
		}
		
		if(falling){
			runtime += dt;
			System.out.println("TTTTTTTTTTTTT");
		}
		
		if(currentFrame == frames[0] && !falling){
			runtime = 0;
			
			
			
		}
		
		currentFrame = animation.getKeyFrame(runtime);
	}
	
	public void falling()
	{
		
		runtime = 0;
		animation.setFrameDuration(.1f);
		animation.setPlayMode(Animation.PlayMode.REVERSED);
	}
	
	public void stopping()
	{
		
		runtime = 0;
		animation.setFrameDuration(.05f);
		animation.setPlayMode(Animation.PlayMode.NORMAL);
	}
	
	public void load()
	{
		cliffJumper1 = AssetLoader.cliffJumper1;
		cliffJumper2 = AssetLoader.cliffJumper2;
		cliffJumper3 = AssetLoader.cliffJumper3;
		cliffJumper4 = AssetLoader.cliffJumper4;
	}
	
	public void stoppingA()
	{
		
		
		animation.setFrameDuration(.05f);
		animation.setPlayMode(Animation.PlayMode.NORMAL);
	}
	
	public void fallingA()
	{
		
		
		animation.setFrameDuration(.1f);
		animation.setPlayMode(Animation.PlayMode.REVERSED);
		
	}
	
	public float getRT(){
		return runtime;
	}
	
	public boolean gliding(){
		return glide;
	}
	
	public void setGliding(){
		glide = true;
		falling = false;
	}
	
	public void setFalling(){
		falling = true;
		glide = false;
	}

}
