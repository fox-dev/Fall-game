package objects;

import helpers.B2DVars;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class B2DSprite {
	

	protected Body body;
	protected Animation animation;
	protected float width, height, runtime;
	protected TextureRegion[] frames;
	protected TextureRegion currentFrame;
	
	public B2DSprite(Body body){
		this.body = body;
		
	}
	
	public void setAnimation(TextureRegion[] reg, float delay)
	{
		animation = new Animation(delay, reg);
	}
	
	public void update (float dt)
	{
		runtime += dt;
		currentFrame = animation.getKeyFrame(runtime);
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(currentFrame, 
				body.getPosition().x * B2DVars.PPM - width / 2,
				body.getPosition().y * B2DVars.PPM - height / 2
				);
		sb.end();
	}
	
	public void load(){
		
	}
	
	public Body getBody(){return body;}
	public Vector2 getPosition(){return body.getPosition();}

	public float getWidth(){return width;}
	public float getHeight(){return height;}

}	
