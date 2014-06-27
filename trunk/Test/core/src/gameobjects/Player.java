package gameobjects;

import handlers.AssetLoader;
import handlers.B2DVars;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.main.Game;

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
				body.getPosition().x * B2DVars.PPM - width / 2,
				body.getPosition().y * B2DVars.PPM - height / 2, 42, 45
	
				);
		sb.end();
	}


}
