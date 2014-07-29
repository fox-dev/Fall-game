package handlers;

import helpers.B2DVars;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Background 
{
	protected OrthographicCamera myCam;
	protected Vector2 position;
	protected Vector2 velocity;
	protected TextureRegion bg;
	
	public Background(TextureRegion image, OrthographicCamera cam)
	{
		bg = image;
		myCam = cam;
		position = new Vector2(myCam.position.x, myCam.position.y);
		velocity = new Vector2(0, 0);
	}
	
	public void update (float dt)
	{
		
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(bg, 
				myCam.position.x - bg.getRegionWidth() / 2,
				myCam.position.y - bg.getRegionHeight() / 2
				);
		sb.end();
	}
	
	public void setPosition(float x, float y)
	{
		position.set(x, y);
	}
	public Vector2 getPosition(){return position;}
	public Vector2 setPosition(){return velocity;}
}
