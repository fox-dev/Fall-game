package objects;

import helpers.AssetLoader;
import helpers.B2DVars;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class LedgeMiddle extends StaticSprite{
	private float drawWidth;
	
	public LedgeMiddle(Body body)
	{
		super(body);
		load();
		
		width = texture.getRegionWidth();
		height = texture.getRegionHeight();
	}
	
	public LedgeMiddle(Body body, float width)
	{
		this(body);
		drawWidth = width * B2DVars.PPM;
		
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(texture, 
				body.getPosition().x * B2DVars.PPM - drawWidth,
				body.getPosition().y * B2DVars.PPM - 33, drawWidth * 2, 40
				
				);
		sb.end();
	}
	
	public void load()
	{
		texture = AssetLoader.ledgeMiddle;
	}

}
