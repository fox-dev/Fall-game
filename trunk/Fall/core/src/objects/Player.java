package objects;

import objects.B2DSprite;
import helpers.AssetLoader;
import helpers.B2DVars;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class Player extends B2DSprite
{
	
	public Player(Body body)
	{
		super(body);
		
		TextureRegion[] player = {AssetLoader.playerLeft, AssetLoader.playerMid, AssetLoader.playerRight};
		setAnimation(player, 1/12f);
		
		
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(animation.getFrame(), 
				body.getPosition().x * B2DVars.PPM - 10,
				body.getPosition().y * B2DVars.PPM - 10, 20, 20
	
				);
		sb.end();
	}


}
