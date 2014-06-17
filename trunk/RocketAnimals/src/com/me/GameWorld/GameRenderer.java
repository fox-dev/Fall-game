package com.me.GameWorld;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.GameObjects.*;
import com.me.TweenAccessors.Value;
import com.me.TweenAccessors.ValueAccessor;
import com.me.helpers.AssetLoader;
import com.me.helpers.Constants;
import com.me.helpers.Constants.DIRECTION;
import com.me.ui.SimpleButton;
import com.me.helpers.InputHandler;

public class GameRenderer {
	
	private GameWorld world;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	
	private SpriteBatch spriteBatch;
	private Rectangle viewport;
	
	private int gameHeight;
	private int midPointY;
	
	// Tween stuff
    private TweenManager manager;
    private Value alpha = new Value();

    // Buttons
    //private List<SimpleButton> menuButtons;
	
	String score;
	
	// Game objects
	Rocket rocket;
	Array<AbstractObstacle> objectList;
	
	// Game sprites;
	TextureRegion rocketLeft, rocketMid, rocketRight, sMeteor, hotAirBalloon, hotAirBalloon_flipped, 
	 			jetPlane, jetPlane_flipped, fire1, fire2, fire3, gameOver, skyDiver,helicopter, helicopterL, 
	 			chopperBlade1, chopperBlade2, chopperBlade3;
	
	TextureRegion bullets;
	
	TextureRegion bossPlane, uFO;
	
	Animation rocketAnimation, rocketFireAnimation, chopperAnimation;
	
	//temp
	TextureRegion bg;
	private Background background; 
	
	public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
		this.world = world;
		this.gameHeight = gameHeight;
		this.midPointY = midPointY;
		/*this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor())
                .getMenuButtons();*/
		
		cam = new OrthographicCamera();
		cam.setToOrtho(true, Constants.TRUE_WIDTH, Constants.TRUE_HEIGHT);
		
		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(cam.combined); // Attach spriteBatch to camera
		
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);
		
		// Init the game objects and visuals
		initGameObjects();
		initGameAssets();
		setupTweens();
	}
	
	public void setupTweens()
	{
		Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, .5f).target(0).ease(TweenEquations.easeOutQuad)
                .start(manager);
	}
	
	/*private void drawMenuUI() {
		batcher.draw(AssetLoader.zbLogo, 136 / 2 - 56, midPointY - 50,
	                AssetLoader.zbLogo.getRegionWidth() / 1.2f,
	                AssetLoader.zbLogo.getRegionHeight() / 1.2f);

	    for (SimpleButton button : menuButtons) {
	            button.draw(spriteBatch);
	    }
	}*/
	
	public void render(float delta, float runTime) {
		// Update camera
		cam.update();
		//cam.apply(Gdx.gl20);
		
		// Set viewport
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
                (int) viewport.width, (int) viewport.height);
		
		// Fill screen with black
		Gdx.gl.glClearColor(0.11f, 0.11f, 0.11f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Draw Background color
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
		shapeRenderer.rect(0, 0, Constants.TRUE_WIDTH, Constants.TRUE_HEIGHT);
		shapeRenderer.end();
		
		// Draw sprites
		spriteBatch.begin();
		
		//spriteBatch.draw(bg, 0, Constants.TRUE_HEIGHT - 102, Constants.TRUE_WIDTH, 102);
		spriteBatch.draw(bg, background.getX(), background.getY(), Constants.TRUE_WIDTH, 102);
		drawObjects(runTime);
		
		if(world.isRunning())
		{
			// Convert integer into String
	        String score = world.getScore() + "";
			drawPlayer(runTime);
			AssetLoader.shadow.draw(spriteBatch, "" + world.getScore(), (136 / 2)
	                - (3 * score.length()), 12);
			// Draw text
	        AssetLoader.font.draw(spriteBatch, "" + world.getScore(), (136 / 2)
	                - (3 * score.length() - 1), 11);
		}
		else if(world.isReady())
		{
			// Convert integer into String
	        String score = world.getScore() + "";
			drawPlayer(runTime);
			AssetLoader.shadow.draw(spriteBatch, "" + world.getScore(), (136 / 2)
	                - (3 * score.length()), 12);
			// Draw text
	        AssetLoader.font.draw(spriteBatch, "" + world.getScore(), (136 / 2)
	                - (3 * score.length() - 1), 11);
	        AssetLoader.shadow.draw(spriteBatch, "Click Anywhere To Start!", 48, Constants.TRUE_HEIGHT / 2 - 50);
	        AssetLoader.font.draw(spriteBatch, "Click Anywhere To Start!", 47, Constants.TRUE_HEIGHT / 2 - 51);
		}
		else if(world.isStandby())
		{
			// Convert integer into String
	        String score = world.getScore() + "";
			drawPlayer(runTime);
			AssetLoader.shadow.draw(spriteBatch, "" + world.getScore(), (136 / 2)
	                - (3 * score.length()), 12);
			// Draw text
	        AssetLoader.font.draw(spriteBatch, "" + world.getScore(), (136 / 2)
	                - (3 * score.length() - 1), 11);
		}
		else if(world.isGameOver())
		{
			// Convert integer into String
	        String score = world.getFinalScore() + "";
	        AssetLoader.shadow.draw(spriteBatch, "" + score, (136 / 2)
	                - (3 * score.length()), 12);
			// Draw text
	        AssetLoader.font.draw(spriteBatch, "" + score, (136 / 2)
	                - (3 * score.length() - 1), 11);
        	drawGameOver();
        	//AssetLoader.shadow.draw(spriteBatch, "Game Over", 25, 56);
           // AssetLoader.font.draw(spriteBatch, "Game Over", 24, 55);
        }

        // Draw shadow first
        /*if(!world.isGameOver()){
        	AssetLoader.shadow.draw(spriteBatch, "" + world.getScore(), (136 / 2)
                - (3 * score.length()), 12);
        	// Draw text
        	AssetLoader.font.draw(spriteBatch, "" + world.getScore(), (136 / 2)
                - (3 * score.length() - 1), 11);
        }
        else{
        	AssetLoader.shadow.draw(spriteBatch, "" + world.getFinalScore(), (136 / 2)
                - (3 * score.length()), 12);
            // Draw text
            AssetLoader.font.draw(spriteBatch, "" + world.getFinalScore(), (136 / 2)
                - (3 * score.length() - 1), 11);
        }*/
        
        // Draw Game Over
        
		spriteBatch.end();
		drawTransition(delta);
		
		// If DRAW_BOUNDS is enabled
		if (Constants.DRAW_BOUNDS) {
			shapeRenderer.begin(ShapeType.Line);
			drawObjectsBoundaries();
			shapeRenderer.end();
		}
		
	}
	
	private void drawGameOver() {
		
		spriteBatch.draw(AssetLoader.gameOver, (Constants.TRUE_WIDTH/2) - 92/2, (Constants.TRUE_HEIGHT/2 - 14/2), 92, 14);
	}
	
	private void drawPlayer(float runTime){
		// Draw player first
				/*
				if (rocket.isMoving())
					spriteBatch.draw(rocketAnimation.getKeyFrame(runTime), rocket.getX(), rocket.getY(), rocket.getWidth(), rocket.getHeight());
				else
					spriteBatch.draw(rocket1, rocket.getX(), rocket.getY(), rocket.getWidth(), rocket.getHeight());
				*/
			
				if(!rocket.isMoving()){
					spriteBatch.draw(rocketFireAnimation.getKeyFrame(runTime), rocket.getX(), rocket.getY() + 25, Constants.ROCKET_FIRE_WIDTH, Constants.ROCKET_FIRE_HEIGHT);
					spriteBatch.draw(rocketMid, rocket.getX(), rocket.getY(), rocket.getWidth(), rocket.getHeight());
				}
				else if (rocket.isMovingLeft()) {
					spriteBatch.draw(rocketFireAnimation.getKeyFrame(runTime), rocket.getX(), rocket.getY() + 25, Constants.ROCKET_FIRE_WIDTH, Constants.ROCKET_FIRE_HEIGHT);
					spriteBatch.draw(rocketLeft, rocket.getX(), rocket.getY(), rocket.getWidth(), rocket.getHeight());
				}
				else if (rocket.isMovingRight()) {
					spriteBatch.draw(rocketFireAnimation.getKeyFrame(runTime), rocket.getX(), rocket.getY() + 25, Constants.ROCKET_FIRE_WIDTH, Constants.ROCKET_FIRE_HEIGHT);
					spriteBatch.draw(rocketRight, rocket.getX(), rocket.getY(), rocket.getWidth(), rocket.getHeight());
				}
				
		
	}
	private void drawObjects(float runTime){
		// Draw all obstacles
		for(AbstractObstacle items : world.getScroller().getAbstractObstacles())
		{
			if(items instanceof Meteor)
			{
				
				// spriteBatch.draw(sMeteor, items.getX(), items.getY(), items.getWidth(), items.getHeight());
				spriteBatch.draw(sMeteor, items.getX(), items.getY(), items.getMiddleX(), items.getMiddleY(), items.getWidth(), items.getHeight(), 1f, 1f, items.getRotation());
			}
			else if(items instanceof HotAirBalloon)
			{
				if (items.getDirection().equals(DIRECTION.DOWN_RIGHT))
					spriteBatch.draw(hotAirBalloon_flipped, items.getX(), items.getY(), items.getWidth(), items.getHeight());
				else
					spriteBatch.draw(hotAirBalloon, items.getX(), items.getY(), items.getWidth(), items.getHeight());
				
			}
			else if(items instanceof JetPlane)
			{
				if (items.getDirection().equals(DIRECTION.DOWN_RIGHT))
					spriteBatch.draw(jetPlane_flipped, items.getX(), items.getY(), items.getWidth(), items.getHeight());
				else
					spriteBatch.draw(jetPlane, items.getX(), items.getY(), items.getWidth(), items.getHeight());
			}
			else if(items instanceof Projectile) //projectile uses new sprite.
			{
				spriteBatch.draw(bullets, items.getX(), items.getY(), items.getWidth(), items.getHeight());
			}
			else if(items instanceof CargoPlane)// Cargo Plane Boss uses new sprite
			{
				spriteBatch.draw(bossPlane, items.getX(), items.getY(), items.getWidth(), items.getHeight());
			}
			else if(items instanceof Alien){ //Alien now uses new Sprite
				spriteBatch.draw(uFO, items.getX(), items.getY(), items.getWidth(), items.getHeight());
			}
			else if (items instanceof ParaTroop) {
				spriteBatch.draw(skyDiver, items.getX(), items.getY(), items.getWidth(), items.getHeight());
			}
			// Added Helicopter draws facing left and right with animated rotor.
			else if (items instanceof Helicopter) 
			{
				if(items.getDirection().equals(DIRECTION.DOWN_LEFT))
				{
					spriteBatch.draw(chopperAnimation.getKeyFrame(runTime), items.getX(), items.getY() - 15, 86, 15);
					spriteBatch.draw(helicopter, items.getX(), items.getY(), items.getWidth(), items.getHeight());
				}
				else{
					spriteBatch.draw(chopperAnimation.getKeyFrame(runTime), items.getX() + 17, items.getY() - 15, 86, 15);
					spriteBatch.draw(helicopterL, items.getX(), items.getY(), items.getWidth(), items.getHeight());
				}
			}
			
		}
	}
	
	public void drawObjectsBoundaries() {
		
		// Draw boundary for player
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.polygon(world.getRocket().getPolygon().getTransformedVertices());
		
		// Draw the rest
		for (AbstractObstacle items : world.getScroller().getAbstractObstacles()) {
			
			if (items.getCollisionRect() != null) {
				shapeRenderer.rect(items.getCollisionRect().x, items.getCollisionRect().y, items.getCollisionRect().width, items.getCollisionRect().height, items.getMiddleX(), items.getMiddleY(), items.getRotation());
			}
			else if (items.getCollisionCirc() != null) {
				shapeRenderer.circle(items.getCollisionCirc().x, items.getCollisionCirc().y, items.getCollisionCirc().radius);
			}
			else if (items.getCollisionPoly() != null) {
				shapeRenderer.polygon(items.getCollisionPoly().getTransformedVertices());
			}
		}
	}
	
	public void initGameObjects() {
		rocket = world.getRocket();
		objectList = world.getScroller().getAbstractObstacles();
		background = world.getScroller().getFrontBackground();
		
	}
	
	public void initGameAssets() {
		rocketLeft = AssetLoader.rocketLeft;
		rocketMid = AssetLoader.rocket;
		rocketRight = AssetLoader.rocketRight;
		rocketAnimation = AssetLoader.rocketAnimation;
		rocketFireAnimation = AssetLoader.rocketFireAnimation;
		sMeteor = AssetLoader.meteor;
		hotAirBalloon = AssetLoader.hotAirBalloon;
		hotAirBalloon_flipped = AssetLoader.hotAirBalloon_flipped;
		jetPlane = AssetLoader.jetPlane;
		jetPlane_flipped = AssetLoader.jetPlane_flipped;
		skyDiver = AssetLoader.skyDiver;
		helicopter = AssetLoader.helicopter;
		helicopterL = AssetLoader.helicopterL;
		chopperAnimation = AssetLoader.chopperAnimation;
		
		gameOver = AssetLoader.gameOver;
		
		bg = AssetLoader.bg;
		
		bullets = AssetLoader.bulletRed; //different sprites added here.
		bossPlane = AssetLoader.bossPlane;
		uFO = AssetLoader.uFOSide;
		
	}
	
	public void resize(int width, int height) {
		float aspectRatio = (float)width / (float)height;
		float scale = 1f;
		Vector2 crop = new Vector2(0f, 0f);
		
		if(aspectRatio > Constants.ASPECT_RATIO)
        {
            scale = (float)height/(float)Constants.TRUE_HEIGHT;
            crop.x = (width - Constants.TRUE_WIDTH*scale)/2f;
        }
        else if(aspectRatio < Constants.ASPECT_RATIO)
        {
            scale = (float)width/(float)Constants.TRUE_WIDTH;
            crop.y = (height - Constants.TRUE_HEIGHT*scale)/2f;
        }
        else
        {
            scale = (float)width/(float)Constants.TRUE_WIDTH;
        }

        float w = (float)Constants.TRUE_WIDTH*scale;
        float h = (float)Constants.TRUE_HEIGHT*scale;
        viewport = new Rectangle(crop.x, crop.y, w, h);
	}
	
	 private void drawTransition(float delta) 
	 {
		 if (alpha.getValue() > 0) {
	            manager.update(delta);
	            Gdx.gl.glEnable(GL20.GL_BLEND);
	            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	            shapeRenderer.begin(ShapeType.Filled);
	            shapeRenderer.setColor(1, 1, 1, alpha.getValue());
	            shapeRenderer.rect(0, 0, Constants.TRUE_WIDTH, Constants.TRUE_HEIGHT);
	            shapeRenderer.end();
	            Gdx.gl.glDisable(GL20.GL_BLEND);

	     }
	 }
}
