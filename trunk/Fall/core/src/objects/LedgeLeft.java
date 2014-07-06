package objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.MainGame;

import helpers.AssetLoader;
import helpers.B2DVars;

public class LedgeLeft extends StaticSprite
{
	public LedgeLeft(Body body)
	{
		super(body);
		load();
		
		width = texture.getRegionWidth();
		height = texture.getRegionHeight();
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(texture, 
				body.getPosition().x * B2DVars.PPM - 40,
				body.getPosition().y * B2DVars.PPM - 40, 80, 50
	
				);
		sb.end();
	}
	
	public void load()
	{
		texture = AssetLoader.ledgeLeft;
	}
}
