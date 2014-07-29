package handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import helpers.B2DVars;

public class MiddlegroundRight extends Background{

	public MiddlegroundRight(TextureRegion image, OrthographicCamera cam) {
		super(image, cam);
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(bg, 
				B2DVars.TRUE_WIDTH - bg.getRegionWidth() / 2,
				myCam.position.y - bg.getRegionHeight() / 2
				);
		sb.end();
	}
}
