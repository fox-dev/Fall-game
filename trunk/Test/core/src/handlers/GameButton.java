package handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class GameButton 
{
	private float x;
	private float y;
	private float width;
	private float height;
	
	private TextureRegion reg;
	
	Vector3 vec;
	private OrthographicCamera cam;
	private boolean clicked;

	private String text; 
	private TextureRegion[] font;
	
	public GameButton(TextureRegion reg, float x, float y, OrthographicCamera cam)
	{
		this.reg = reg;
		this.x = x;
		this.y = y;
		this.cam = cam;
		
		width = reg.getRegionWidth();
		height = reg.getRegionHeight();
		vec = new Vector3();
		
		Texture tex = AssetLoader.button;
		font = new TextureRegion[11];
		
	}
	
	public boolean isClicked(){return clicked;}
	public void setText(String s){text = s;}
	
	public void update(float dt)
	{
		vec.set(MyInput.x, MyInput.y, 0);
		cam.unproject(vec);
		
		if(MyInput.isPressed() &&
				vec.x > x - width / 2 && vec.x < x + width / 2 &&
				vec.y > y - height /2 && vec.y < y + height /2)
		{
			clicked = true;
		}
		else{
			clicked = false;
		}
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		
		sb.draw(reg, x - width / 2, y - height / 2);
		if(text != null)
		{
			drawString(sb, text, x, y);
		}
		
		sb.end();
	}
}
