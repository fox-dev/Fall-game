package helpers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	private HashMap<String, Texture> textures;
	
	public static Texture texture;
	
	public static TextureRegion playerLeft, playerMid, playerRight;
	
	public AssetLoader(){
		textures = new HashMap <String, Texture>();
	}
	
	public static void load()
	{
		texture = new Texture(Gdx.files.internal("SpriteSheetObjectsNew2.png"));
		
		playerLeft = new TextureRegion(texture, 4, 20, 43, 47);
		playerMid = new TextureRegion(texture, 62, 20, 44, 47);
		playerRight = new TextureRegion(texture, 119, 20, 44, 47);
		
		playerLeft.flip(false, true);
		playerMid.flip(false, true);
		playerRight.flip(false, true);
		
	}
	
	public void loadTexture(String path, String key)
	{
		Texture tex = new Texture(Gdx.files.internal(path));
		textures.put(key,tex);
	}
	
	public Texture getTexture(String key)
	{
		return textures.get(key);
	}
	
	public void disposeTexture(String key)
	{
		Texture tex = textures.get(key);
		if(tex != null) tex.dispose();
	}
	
	public static void dispose()
	{
		texture.dispose();
	}

}
