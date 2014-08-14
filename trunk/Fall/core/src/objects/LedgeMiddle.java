package objects;

import static helpers.B2DVars.PPM;

import java.util.Random;

import helpers.AssetLoader;
import helpers.B2DVars;
import Lights.PointLight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class LedgeMiddle extends StaticSprite{
	private float drawWidth;
	PointLight l;
	float grow = 90;
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
		
		if(grow >= 120){
			down = true;
		}
		if(grow <= 90){
			down = false;
		}
		if(grow >= 90 && !down){
			grow += 0.7;
		}
		else{
			grow -= 0.7;
		}
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		
		if(texture == AssetLoader.ledgePurple){
			l.setColor(Color.valueOf("FCD7FB"));
		}
		sb.draw(texture, 
				body.getPosition().x * B2DVars.PPM - drawWidth - drawWidth/10,
				body.getPosition().y * B2DVars.PPM - 25 - drawWidth / 20, drawWidth * 2 + drawWidth / 5, 60 + drawWidth / 10
				
				);
		
		sb.end();
	}
	
	public void load()
	{
		TextureRegion[] textures = {AssetLoader.ledgeBlue, AssetLoader.ledgeGreen, AssetLoader.ledgePurple, AssetLoader.ledgeOrange,
									AssetLoader.ledgePink, AssetLoader.ledgeLightBlue, AssetLoader.ledgeOrangeRed, AssetLoader.ledgeRed};
		
		int r = randInt(0,7);
		
		texture = textures[r];
			
		
	}
	
	public static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    return rand.nextInt((max - min) + 1) + min;
	}

}
