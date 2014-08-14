package handlers;

import helpers.B2DVars;
import BackgroundHandlers.ParallaxBackground;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Background 
{
	protected OrthographicCamera myCam;
	protected float speed, x, y, width, height;
	protected TextureRegion bg;
	
	public Background(TextureRegion image, OrthographicCamera cam)
	{
		bg = image;
		width = image.getRegionWidth();
		height = image.getRegionHeight();
		myCam = cam;
		speed = 0;
		y = 0;
		x = cam.position.x;
	}
	
	public Background(TextureRegion image, OrthographicCamera cam, float s)
	{
		this(image, cam);
		speed = s;
	}
	
	public Background(TextureRegion image, OrthographicCamera cam, float x, float y, float s)
	{
		this(image, cam, s);
		
	}
	
	
	public void update (float dt)
	{
		y += speed * dt;
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(bg, 
				x - width / 2,
				myCam.position.y - height / 2 + y
				);
		sb.end();
	}
	
	public void setXPosition(float x)
	{
		this.x = x;
	}
	
	public void setYPosition(float y)
	{
		this.y = y;
	}
	
	public void setWidth(float width){this.width = width;}
	public void setHeight(float height){this.height = height;}
	public void setSpeed(float s){speed = s;}
	public float getXPosition(){return x;}
	public float getYPosition(){return y;}
	public float getSpeed(){return speed;}
	public float getWidth(){return width;}
	public float getHeight(){return height;}
}
