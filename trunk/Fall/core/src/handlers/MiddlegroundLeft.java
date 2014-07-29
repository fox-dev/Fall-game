package handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MiddlegroundLeft extends Background{

	public MiddlegroundLeft(TextureRegion image, OrthographicCamera cam) {
		super(image, cam);
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(bg, 
				0 - bg.getRegionWidth() / 2,
				myCam.position.y - bg.getRegionHeight() / 2
				);
		sb.end();
	}

}
