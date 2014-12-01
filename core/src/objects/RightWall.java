package objects;

import helpers.AssetLoader;
import helpers.B2DVars;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class RightWall extends Wall{
	
	public RightWall(Body body)
	{
		super(body);
		
		wallTexture = AssetLoader.wallRight;
		width = wallTexture.getRegionWidth();
		height = wallTexture.getRegionHeight();
		
		yPos = 0;
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(wallTexture, 
				body.getPosition().x * B2DVars.PPM - 16,
				yPos * B2DVars.PPM
				);
		sb.end();
		
		//System.out.println(width + " " + height);
	}
	
}
