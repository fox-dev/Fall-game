package handlers;

import helpers.B2DVars;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class AnimatedBackground extends Background
{
	protected Animation animation;
	protected float width, height;
	protected TextureRegion[] frames;
	
	public AnimatedBackground(TextureRegion[] images, OrthographicCamera cam)
	{
		super(images[0], cam);
		frames = images;
		animation = new Animation(.1f, frames);
		animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
	}
	
	public void setAnimation(TextureRegion[] reg, float delay)
	{
		animation = new Animation(delay, reg);
	}
	
	public void update (float dt)
	{
		bg = animation.getKeyFrame(dt);
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(bg, 
				x + 80, //- bg.getRegionWidth() / 2,
				myCam.position.y - bg.getRegionHeight() / 4,
				160, 240
				);
		sb.end();
	}
	
	public void load(){
		
	}

	public float getWidth(){return width;}
	public float getHeight(){return height;}
}
