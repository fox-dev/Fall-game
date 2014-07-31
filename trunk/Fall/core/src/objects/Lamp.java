package objects;

import helpers.AssetLoader;
import helpers.B2DVars;
import Lights.ConeLight;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class Lamp extends StaticSprite
{
	protected ConeLight myLight;
	
	public Lamp(Body body)
	{
		super(body);
		texture = AssetLoader.lamp;
		width = texture.getRegionWidth();
		height = texture.getRegionHeight();
	}
	
	public Lamp(ConeLight c)
	{
		this(c.getBody());
		myLight = c;
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(texture, 
				myLight.getPosition().x * B2DVars.PPM - width / 2,
				myLight.getPosition().y * B2DVars.PPM - height / 2 - 15
				);
		sb.end();
	}
	
	
	public ConeLight getLight(){return myLight;}
}
