package handlers;

import objects.Player;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Middleground extends Background
{
	protected Player player;
	
	public Middleground(TextureRegion image, OrthographicCamera cam) {
		super(image, cam);
	}
	
	public Middleground(TextureRegion image, OrthographicCamera cam, Player player)
	{
		this(image, cam);
		this.player = player;
	}
	
	public Middleground(TextureRegion image, OrthographicCamera cam, Player player, float s)
	{
		this(image, cam, player);
		speed = s;
	}
	
	public Middleground(TextureRegion image, OrthographicCamera cam, Player player, float x, float y, float s)
	{
		this(image, cam, player, s);
		this.x = x; 
		this.y = y;
	}
	
	public void update (float dt)
	{
		y += speed * dt * Math.abs(player.getBody().getLinearVelocity().y);
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

}
