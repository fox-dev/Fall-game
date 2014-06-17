package com.me.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AssetLoader {
	
	public static Texture texture, texture2, logoTexture; //temporary
	
	//UFO Boss Texture
	public static TextureRegion uFOTop, uFOSide;
	
	// Bullet Textures
	public static TextureRegion bulletRed, bulletBlue, bulletGreen, bulletYellow, bulletOrange, bulletPurple;
	
	//Boss Plane Texture
	public static TextureRegion bossPlane;
	
	//LogoTexture
	public static TextureRegion logo;
	
	public static TextureRegion bg; //temp
	
	
	//Button Textures
	public static TextureRegion playButtonUp, playButtonDown;
	
	
	public static TextureRegion rocketLeft, rocket, rocketRight, meteor, hotAirBalloon, hotAirBalloon_flipped, 
	 							jetPlane, jetPlane_flipped, rocketFire1, rocketFire2, rocketFire3, gameOver,
	 							skyDiver, helicopter, helicopterL, chopperBlade1, chopperBlade2, chopperBlade3;
	
	public static Animation rocketAnimation, rocketFireAnimation, chopperAnimation;
	
	public static Sound hit1, hit2;
	public static Array<Sound> hitSounds;
	public static Music bgm; // change names later!
	public static BitmapFont font, shadow;
	public static Preferences prefs;
	
	public static void load() {
		
		hitSounds = new Array<Sound>();
		
		
		// Load the texture
		texture = new Texture(Gdx.files.internal("data/SpriteSheetObjects.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		texture2 = new Texture(Gdx.files.internal("data/texture.png")); //temporary
		texture2.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		//Logo Texture
		logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
		logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		logo = new TextureRegion(logoTexture, 0, 0, 512, 114);
		
		//Button Textures
		playButtonUp = new TextureRegion(texture2, 0, 83, 29, 16);
		playButtonDown = new TextureRegion(texture2, 29, 83, 29, 16);
		playButtonUp.flip(false, true);
		playButtonDown.flip(false, true);
		
		//UFO Boss
		uFOTop = new TextureRegion(texture, 303, 132, 105, 87);
		uFOSide = new TextureRegion(texture, 298, 225, 114, 62);
		
		uFOTop.flip(false, true);
		uFOSide.flip(false, true);
		//Cargo Plane Boss
		bossPlane = new TextureRegion(texture, 329, 7, 174, 112);
		bossPlane.flip(false, true);
		
		//Bullets
		bulletRed = new TextureRegion(texture, 449, 168, 12, 12);
		bulletOrange = new TextureRegion(texture, 449, 183, 12, 12);
		bulletYellow = new TextureRegion(texture, 449, 198, 12, 12);
		bulletGreen = new TextureRegion(texture, 464, 168, 12, 12);
		bulletBlue = new TextureRegion(texture, 464, 183, 12, 12);
		bulletPurple = new TextureRegion(texture, 464, 198, 12, 12);
		
		//Bg temp
		bg = new TextureRegion(texture2, 0, 0, 136, 43);
		bg.flip(false, true);
		
		
		// Rocket
		rocketLeft = new TextureRegion(texture, 0, 0, 45, 48);
		rocket = new TextureRegion(texture, 58, 0, 45, 48); // Spritesheet may need fixing
		rocketRight = new TextureRegion(texture, 115, 0, 45, 48	);
		
		rocketLeft.flip(false, true);
		rocket.flip(false, true);
		rocketRight.flip(false, true);
		
		// Rocket Animation
		TextureRegion[] rockets = {rocketLeft, rocket, rocketRight};
		rocketAnimation = new Animation(0.01f, rockets);
		rocketAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		
		// Rocket fire
		rocketFire1 = new TextureRegion(texture, 3, 48, 39, 30);
		rocketFire2 = new TextureRegion(texture, 61, 48, 39, 30);
		rocketFire3 = new TextureRegion(texture, 118, 48, 39, 30);
		
		rocketFire1.flip(false, true);
		rocketFire2.flip(false, true);
		rocketFire3.flip(false, true);
		
		// Rocket fire Animation
		TextureRegion[] fire = {rocketFire1, rocketFire2, rocketFire3};
		rocketFireAnimation = new Animation(0.05f, fire);
		rocketFireAnimation.setPlayMode(Animation.PlayMode.LOOP);
		
		// Meteor
		meteor = new TextureRegion(texture, 192, 6, 49, 48); //change sprite later
		
		//HotAirBalloon
		hotAirBalloon = new TextureRegion(texture, 7, 90, 83, 139);
		hotAirBalloon.flip(false, true);
		hotAirBalloon_flipped = new TextureRegion(texture, 7, 90, 83, 139);
		hotAirBalloon_flipped.flip(true, true);
		
		// JetPlane
		jetPlane = new TextureRegion(texture, 97, 90, 158, 58);
		jetPlane.flip(false, true);
		jetPlane_flipped = new TextureRegion(texture, 97, 90, 158, 58);
		jetPlane_flipped.flip(true, true);
		
		// Sky diver
		skyDiver = new TextureRegion(texture, 104, 168, 35, 62);
		skyDiver.flip(false, true);
		
		//Helicopter
		helicopter = new TextureRegion(texture, 148, 200, 101, 52);
		helicopterL = new TextureRegion(texture, 148,200, 101, 52);
		
		helicopter.flip(false, true);
		helicopterL.flip(true, true);
		
		//Helicopter Blades
		chopperBlade1 = new TextureRegion(texture, 146, 152, 86, 15);
		chopperBlade2 = new TextureRegion(texture, 146, 168, 86, 15);
		chopperBlade3 = new TextureRegion(texture, 146, 184, 86, 15);
		
		chopperBlade1.flip(false, true);
		chopperBlade2.flip(false, true);
		chopperBlade3.flip(false, true);
		
		//Helicopter Blade Animation
		TextureRegion[] copterSpin = {chopperBlade1, chopperBlade2, chopperBlade3};
		chopperAnimation = new Animation(0.05f, copterSpin);
		chopperAnimation.setPlayMode(Animation.PlayMode.LOOP);
		
		// Load audio
		hit1 = Gdx.audio.newSound(Gdx.files.internal("data/hit1.wav"));
		hit2 = Gdx.audio.newSound(Gdx.files.internal("data/hit2.wav"));
		hitSounds.add(hit1);
		hitSounds.add(hit2);
		
		// Load music
		bgm = Gdx.audio.newMusic(Gdx.files.internal("data/themoon.mp3"));
		
		//Font
		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.setScale(.25f, -.25f);
        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.setScale(.25f, -.25f);
        
        //Gameover
        gameOver = new TextureRegion(texture2, 59, 92, 46, 7);
		gameOver.flip(false, true);

	}
	
	public static void dispose() {
		// Dipose of the texture when we're done
		texture.dispose();
		
		// Also dispose of audio
		hit1.dispose();
		bgm.dispose();
		
		//Fonts
		font.dispose();
		shadow.dispose();
	}
}
