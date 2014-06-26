package gameobjects;

import handlers.AssetLoader;

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


}
