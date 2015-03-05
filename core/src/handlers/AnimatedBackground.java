package handlers;

import objects.Player;
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
	protected TextureRegion[] frames;
	protected Player player;
	
	public AnimatedBackground(TextureRegion[] images, OrthographicCamera cam, Player player)
	{
		super(images[0], cam);
		frames = images;
		animation = new Animation(.1f, frames);
		animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		this.player = player;
		width = 320;
		height = 480;
	}
	
	public AnimatedBackground(TextureRegion[] images, OrthographicCamera cam, Player player, float speed)
	{
		this(images, cam, player);
		this.speed = speed;
	}
	
	public AnimatedBackground(TextureRegion[] images, OrthographicCamera cam, Player player, float x, float y, float s)
	{
		this(images, cam, player, s);
		this.x = x; 
		this.y = y;
	}
	
	public AnimatedBackground(TextureRegion[] images, OrthographicCamera cam, float width, float height, Player player)
	{
		this(images, cam, player);
		this.width = width;
		this.height = height;
	}
	
	public void setAnimation(TextureRegion[] reg, float delay)
	{
		animation = new Animation(delay, reg);
	}
	
	public void update (float dt)
	{
		bg = animation.getKeyFrame(dt);
		y += speed * dt * Math.abs(player.getBody().getLinearVelocity().y);
		
	}
	
	public void render(SpriteBatch sb)
	{
	

		sb.begin();
		sb.draw(bg, 
				x, //- bg.getRegionWidth() / 2,
				myCam.position.y - height / 2 + y,
				width, height
				);
		sb.end();
		
		
	}
	
	public void load(){
		
	}
	
}
