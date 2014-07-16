package objects;

import static helpers.B2DVars.PPM;
import helpers.AssetLoader;
import helpers.B2DVars;
import Lights.PointLight;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class LedgeMiddle extends StaticSprite{
	private float drawWidth;
	PointLight l;
	int grow = 150;
	boolean down = false;
	
	public LedgeMiddle(Body body)
	{
		super(body);
		load();
		
		width = texture.getRegionWidth();
		height = texture.getRegionHeight();
	}
	
	public LedgeMiddle(Body body, float width, PointLight l)
	{
		this(body);
		drawWidth = width * B2DVars.PPM;
		this.l = l;
		
		
		
	}
	
	public void update(){
		l.setPosition(body.getPosition().x, body.getPosition().y + 12/B2DVars.PPM);
		
		l.setDistance(grow/PPM);
		
		if(grow >= 200){
			down = true;
		}
		if(grow <= 150){
			down = false;
		}
		if(grow >= 150 && !down){
			grow += 3;
		}
		else{
			grow -= 3;
		}
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(texture, 
				body.getPosition().x * B2DVars.PPM - drawWidth,
				body.getPosition().y * B2DVars.PPM - 33, drawWidth * 2, 60
				
				);
		sb.end();
	}
	
	public void load()
	{
		texture = AssetLoader.ledgeMiddle;
	}

}
