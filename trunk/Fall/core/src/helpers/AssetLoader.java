package helpers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	private HashMap<String, Texture> textures;
	
	public static Texture player, cliffWalls, ledgeA, ledgeB, ledge, logoTexture, bg, bg2, mbg, lampTexture;
	public static Texture middlePurple;
	
	//Background textures
	public static TextureRegion waterFallBG, waterFallBG2, middleBGLeft, middleBGRight;
	
	//public static TextureRegion playerLeft, playerMid, playerRight;
	public static TextureRegion logo, cliffJumper1, cliffJumper2, cliffJumper3, cliffJumper4, 
								wallLeft, wallRight, ledgeLeft, ledgeRight, ledgeMiddle, lamp;
	
	public static TextureRegion midPurp;
	
	public static Music bgm;
	
	public static Music caveIn;
	
	public static Sound hit;
	
	public AssetLoader(){
		textures = new HashMap <String, Texture>();
	}
	
	public static void load()
	{
		logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
		player = new Texture(Gdx.files.internal("data/basejumperanimatewhite.png"));
		cliffWalls = new Texture(Gdx.files.internal("data/cliff-wallsblue.png"));
		
		
		ledge = new Texture(Gdx.files.internal("data/platformB5.png"));
		//ledge = new Texture(Gdx.files.internal("data/platform.png"));
		ledgeA = new Texture(Gdx.files.internal("data/ledgeA.png"));
		ledgeB = new Texture(Gdx.files.internal("data/ledgeB.png"));
		
		middlePurple = new Texture(Gdx.files.internal("data/platformB8.png"));
		
		lampTexture = new Texture(Gdx.files.internal("data/lamp2.png"));
		
		bg = new Texture(Gdx.files.internal("data/waterfallbackdrop1.png"));
		bg2 = new Texture(Gdx.files.internal("data/waterfallbackdrop2.png"));
		mbg = new Texture(Gdx.files.internal("data/cliff-walls-midgroundNEW.png"));
		
		logo = new TextureRegion(logoTexture);
		
		cliffJumper1 = new TextureRegion(player, 0, 0, 30 ,30);
		
		//Background textures
		waterFallBG = new TextureRegion(bg);
		waterFallBG2 = new TextureRegion(bg2);
		middleBGLeft = new TextureRegion(mbg, 0, 0, 155, 480);
		middleBGRight = new TextureRegion(mbg, 160, 0, 165, 480);
		
		
		cliffJumper2 = new TextureRegion(player, 31, 0, 30, 30);
		cliffJumper3 = new TextureRegion(player, 68, 0, 30, 30);
		cliffJumper4 = new TextureRegion(player, 103, 0, 30, 30);
		
		wallLeft = new TextureRegion(cliffWalls, 0, 0,  120, 480);
		wallRight = new TextureRegion(cliffWalls, 200, 0, 120, 480);
		
		ledgeLeft = new TextureRegion(ledgeA);
		ledgeRight = new TextureRegion(ledgeB);
		ledgeMiddle = new TextureRegion(ledge);
		
		midPurp = new TextureRegion(middlePurple);
		
		lamp = new TextureRegion(lampTexture);
		
		bgm = Gdx.audio.newMusic(Gdx.files.internal("data/Greg_Davis_-_01_-_slow_motion.mp3"));
		caveIn = Gdx.audio.newMusic(Gdx.files.internal("data/caveIn.mp3"));
		hit = Gdx.audio.newSound(Gdx.files.internal("data/hit.mp3"));
		
		/*
		playerLeft = new TextureRegion(texture, 4, 20, 43, 47);
		playerMid = new TextureRegion(texture, 62, 20, 44, 47);
		playerRight = new TextureRegion(texture, 119, 20, 44, 47);
		
		playerLeft.flip(false, true);
		playerMid.flip(false, true);
		playerRight.flip(false, true);*/
		
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
		player.dispose();
		cliffWalls.dispose();
		caveIn.dispose();
		hit.dispose();
		ledge.dispose();
		ledgeA.dispose();
		ledgeB.dispose();
		lampTexture.dispose();
		logoTexture.dispose();
		bg.dispose();
		bg2.dispose();
		mbg.dispose();
		
		
	}

}
