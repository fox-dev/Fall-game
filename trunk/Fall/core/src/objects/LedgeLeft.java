package objects;

import static helpers.B2DVars.PPM;
import Lights.ConeLight;
import Lights.PointLight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.MainGame;

import helpers.AssetLoader;
import helpers.B2DVars;

public class LedgeLeft extends StaticSprite
{
private Wall myWall;
private Lamp lamp;

boolean down = false;

int grow = 100;
	
	public LedgeLeft(Body body, Wall wall, Lamp l)
	{
		super(body);
		load();
		myWall = wall;
		this.lamp = l;
		
		width = texture.getRegionWidth();
		height = texture.getRegionHeight();
		
	}
	
	public void update()
	{
		float tempY = getPosition().y;
		body.setTransform(myWall.getPosition().x + ((MainGame.V_WIDTH/10)/B2DVars.PPM), tempY, 0);
		lamp.getLight().setPosition(body.getPosition().x, body.getPosition().y + 120/B2DVars.PPM);
		
		/*
		l.setDistance(grow/PPM);
		
		if(grow >= 130){
			down = true;
		}
		if(grow == 100){
			down = false;
		}
		if(grow >= 100 && !down){
			grow++;
		}
		else{
			grow --;
		}
		*/
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(texture, 
				body.getPosition().x * B2DVars.PPM - 40,
				body.getPosition().y * B2DVars.PPM - 40, 80, 50
	
				);
		sb.end();
		lamp.render(sb);
	}
	
	public void load()
	{
		texture = AssetLoader.ledgeLeft;
	}
}
