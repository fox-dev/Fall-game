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
	private TextureRegion[] playerAnim;
	private Animation player;
	
	public Player(Body body)
	{
		super(body);
	
		load();
		
		TextureRegion[] temp = {cliffJumper1, cliffJumper2, cliffJumper3, cliffJumper4};
		playerAnim = temp;
		player = new Animation(.1f, playerAnim);
		player.setPlayMode(Animation.PlayMode.REVERSED);
		
		currentFrame = cliffJumper1;
		
		/*setAnimation(openWings, player, 1/60f);
		setAnimation(dive, player2, 1/60f);
		currentAnim = dive;*/
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(currentFrame, 
				body.getPosition().x * B2DVars.PPM - 10,
				body.getPosition().y * B2DVars.PPM - 10, 20, 20
	
				);
		sb.end();
	}
	
	public void update(float dt)
	{
		currentFrame = player.getKeyFrame(dt);
	}
	
	public void falling()
	{
		player.setFrameDuration(.1f);
		player.setPlayMode(Animation.PlayMode.REVERSED);
	}
	
	public void stopping()
	{
		player.setFrameDuration(.05f);
		player.setPlayMode(Animation.PlayMode.NORMAL);
	}
	
	public void load()
	{
		cliffJumper1 = AssetLoader.cliffJumper1;
		cliffJumper2 = AssetLoader.cliffJumper2;
		cliffJumper3 = AssetLoader.cliffJumper3;
		cliffJumper4 = AssetLoader.cliffJumper4;
	}
	
	


}
