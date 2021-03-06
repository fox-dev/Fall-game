package Screens;

import static helpers.B2DVars.PPM;
import static helpers.B2DVars.CL;

import java.util.Random;
import java.util.Stack;

import BackgroundHandlers.ParallaxBackground;
import BackgroundHandlers.ParallaxLayer;
import Lights.ConeLight;
import Lights.PointLight;
import Lights.RayHandler;



import TweenAccessors.Value;
import TweenAccessors.ValueAccessor;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MainGame;

import objects.Lamp;
import objects.LedgeLeft;
import objects.LedgeMiddle;
import objects.LedgeRight;
import objects.LeftWall;
import objects.Player;
import objects.RightWall;
import objects.StaticSprite;
import objects.Wall;
import helpers.AssetLoader;
import helpers.B2DVars;
import handlers.AnimatedBackground;
import handlers.Background;
import handlers.GameScreenManager;
import handlers.Middleground;
import handlers.MyContactListener;
import handlers.MyInput;
import handlers.MyInputProcessor;

public class GameScreen extends AbstractScreen {
	
	// ButtonStuff
		Skin skin;
		Stage stage;
		TextButton retryButton;
		TextButton menuButton;
    	//
		
    //Wall Stuff
		boolean restoring;
		

	SpriteBatch sb2;
	
	//debug variable for toggling on/off for rendering box2d objects
	private boolean debug = false;
	
	
	//Fade in for gameOver text, may not need anymore - needs cleanup
	private float fadeIn = 0f;
	//gameOverRunTime used for fading GameOver screen - check, needs cleanup
	private float gameOverRunTime = 0f;
	
	//variable for screenFlash color when player hits a platform
	private Color transitionColor;
	
	//initalize tweenManager for gameOver/gameOver menu effects
	private TweenManager manager, menuManager;
	private Value alpha = new Value(), menuVal = new Value();
	Texture menu = AssetLoader.menu;
	
	//wallInterval variable for setting time interval of wallEvent, always occurs at start of game with = 0.
	private long wallInterval = 0;
	//r variable for randomly assigning new intervals for wallEvent
	int r = randInt(5000, 40000);
	
	//down may not be used, needs cleanup
	boolean down = false;
	
	//boolean for setup of wallEvent
	private boolean wallEvent = false;
	
	
	//initalize rectangle for default gameOver screen, change assets in the future.
	Rectangle glide;
	private float glide_x = 50;
	private boolean glide_CD = false;
	private ShapeRenderer shapeRenderer;
	
	//???Player light
	PointLight d;
	
	//Generic bitmap font for text
	BitmapFont font;
	
	//gameOverFlag for setting game to gameOver state/play state.
	private boolean gameOverFlag;

	//setup initial aspect ratio for game
	float ASPECT_RATIO = (float)MainGame.V_WIDTH/(float)MainGame.V_HEIGHT;
	private Rectangle viewport;
	
	
	float x = 0, y = 0, x2 = 0, y2 = 0;
	long lastSprite = 0;
	long lastSprite2 = 0;
	long nextSprite = 2, nextSprite2 = 3;
	
	int lights = 0;
	
	float runTime, depth = 0, score = 0, lastDepth = 0, lastStop = 0, multi = 1;
	
	private DelayedRemovalArray<StaticSprite> ledgeList = new DelayedRemovalArray<StaticSprite>();
	//private DelayedRemovalArray<Body> obstacleList = new DelayedRemovalArray<Body>();
	private DelayedRemovalArray<ConeLight> lightList = new DelayedRemovalArray<ConeLight>();
	private DelayedRemovalArray<PointLight> lightListP = new DelayedRemovalArray<PointLight>();
	
	private Background[] backgrounds;
	private Middleground middleBgLeft, mLRepeat,  middleBgRight, mRRepeat;
	private Background mainBg, mainBgRepeat;
	
	//private ParallaxLayer l1;
	//private ParallaxBackground b;
	
	
	private World world;
	
	private Box2DDebugRenderer b2dr;
	
	private OrthographicCamera b2dCam;
	
	private RayHandler handler;
	
	private Player player;
	ConeLight td;
		
	private LeftWall leftWall, lWallRepeat;
	private RightWall rightWall, rWallRepeat;
	private Wall[] foreground;
	
	private MyContactListener cl;
	
	int grow = 49;
	float accelX = 0;
	
	//Common vars
	float gravity = -9.81f;
	
	
	
	public GameScreen(final GameScreenManager gsm) {
		super(gsm);
		Gdx.input.setInputProcessor(new MyInputProcessor());
		cam.position.set(
				0,0,
				0
			);
		cam.update();
		
		sb = new SpriteBatch();
		
		transitionColor = new Color();
		prepareTransition(255, 255, 255, .5f);
		
		glide = new Rectangle(0, 0, 100, 100);
		shapeRenderer = new ShapeRenderer();
		
		gameOverFlag = false;
		
		font = new BitmapFont();
		
		world = new World(new Vector2(0, gravity), true);
		
		cl = new MyContactListener();
		world.setContactListener(cl);
		
		
		b2dr = new Box2DDebugRenderer();
		
		init();
		
		//set up box2d cam
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, MainGame.V_WIDTH/PPM , MainGame.V_HEIGHT/PPM );
		box2dLight.RayHandler.useDiffuseLight(true);
		handler = new RayHandler(world, viewport);
		
		handler.setAmbientLight(0.0f, 0.0f, 0.0f,0.1f);
		
		
	
		handler.setShadows(true);
		
		handler.setAmbientLight(0.3f);
	

		
		td = new ConeLight(handler, 40, Color.GRAY,100/PPM, player.getPosition().x, player.getPosition().y + 120/PPM, 270, 15);	
		
				
		sb2 = new SpriteBatch();
		
		AssetLoader.bgm.play();
		AssetLoader.bgm.setLooping(true);
		
		cam.zoom = 1f;
		b2dCam.zoom = 1f;
		
		// button stuff
		 		int xS = 60;
		 		int yS = 25;
		 		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				stage = new Stage(new FitViewport(viewport.width, viewport.height));
				skin = new Skin();
				// Generate a 1x1 white texture and store it in the skin named "white".
				Pixmap pixmap = new Pixmap(xS, yS, Format.RGBA8888);
				pixmap.setColor(Color.WHITE);
				pixmap.fill();

				skin.add("white", new Texture(pixmap));

				// Store the default libgdx font under the name "default".
				BitmapFont bfont = new BitmapFont();
				bfont.scale((float) 0.1);
				skin.add("default", bfont);

				// Configure a TextButtonStyle and name it "default". Skin resources are
				// stored by type, so this doesn't overwrite the font.
				TextButtonStyle textButtonStyle = new TextButtonStyle();
				textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
				textButtonStyle.down = skin.newDrawable("white", Color.LIGHT_GRAY);
				// textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
				textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

				textButtonStyle.font = skin.getFont("default");

				skin.add("default", textButtonStyle);

				// Create a button with the "default" TextButtonStyle. A 3rd parameter
				// can be used to specify a name other than "default".
				retryButton = new TextButton("RETRY", textButtonStyle);
				retryButton.setSize(xS, yS);
				retryButton.setPosition(MainGame.V_WIDTH/2 - xS/2,MainGame.V_HEIGHT/5);

				retryButton.addListener(new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y,
							int pointer, int button) {
						Gdx.app.log("my app", "Pressed"); // ** Usually used to start
															// Game, etc. **//
						return true;
					}

					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						Gdx.app.log("my app", "Released");

						// Gdx.input.setInputProcessor(new MyInputProcessor());
						stage.clear();
						gsm.setScreen(101);
						gsm.set();

					}
				});	
				
				//create the return to menu button
				menuButton = new TextButton("MENU", textButtonStyle);
				menuButton.setSize(xS, yS);
				menuButton.setPosition(MainGame.V_WIDTH/4 - xS/2,MainGame.V_HEIGHT/5);

				menuButton.addListener(new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y,
							int pointer, int button) {
						Gdx.app.log("my app", "Pressed"); // ** Usually used to start
															// Game, etc. **//
						return true;
					}

					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						Gdx.app.log("my app", "Released");

						// Gdx.input.setInputProcessor(new MyInputProcessor());
						stage.clear();
						gsm.setScreen(100);
						gsm.set();

					}
				});	
				
	
				stage.getViewport().setCamera(hudCam);
				stage.addActor(retryButton);
				stage.addActor(menuButton);
		
		
		
		
	}
	
	public void handleInput(){
	
		
		if(!gameOverFlag){
			
			/*Accelerometer Stuff
			if(accelX > -2 && accelX < 2 ){
				Vector2 vel = player.getBody().getLinearVelocity();
				vel.x = 0f;
			
				player.getBody().setLinearVelocity(vel);
			}
			else if(accelX > -1.4){
				Vector2 vel = player.getBody().getLinearVelocity();
				vel.x = -2.3f;
		
				player.getBody().setLinearVelocity(vel);
			}
			else if(accelX < 1.4){
				Vector2 vel = player.getBody().getLinearVelocity();
				vel.x = 2.3f;
			
				player.getBody().setLinearVelocity(vel);
			
			}
			*/
			
		
			if(MyInput.isPressed(MyInput.BUTTON1) && glide_x > 0 && glide_CD == false){
			
				player.stopping();
				lastStop = depth;
				multi = 1;
				glide_x = glide_x - 2;
				if(glide_x <= 0){
					MyInput.setKey(MyInput.BUTTON1, false);
					glide_CD = false;
					player.falling();
				}
				
				
			}
			else{
				if(glide_x <= 50){
					glide_x = (float) (glide_x + 0.2);
				}
				if(glide_CD == true && glide_x <= 50){
					glide_x = (float) (glide_x + 0.5);
				}
				if(glide_CD == true && glide_x >= 50){
					glide_CD = false;
				}
			}
		
			if(MyInput.isDown(MyInput.BUTTON1) && glide_x > 0 && glide_CD == false){

				Vector2 vel = player.getBody().getLinearVelocity();
				Gdx.gl.glClearColor(0, 0, 0, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
					vel.y = -1f;
					player.getBody().setLinearVelocity(vel);
					lastStop = depth;
					multi = 1;
					glide_x = glide_x - 0.32f;
					if(glide_x <= 0){
						MyInput.setKey(MyInput.BUTTON1, false);
						glide_CD = true;
						player.falling();
					}
			}
		
			if(MyInput.isReleased(MyInput.BUTTON1) && glide_CD == false)
			{
				player.falling();
			}
		
			if(MyInput.isDown(MyInput.BUTTON2)){
				
				
				if(MyInput.isDown(MyInput.BUTTON1)){
					Vector2 vel = player.getBody().getLinearVelocity();
					vel.x = -3.5f;
					player.getBody().setLinearVelocity(vel);
				}
				else{
					Vector2 vel = player.getBody().getLinearVelocity();
					vel.x = -2.9f;
					player.getBody().setLinearVelocity(vel);
					
				}
			
			}
			else if(MyInput.isDown(MyInput.BUTTON3)){
				
				if(MyInput.isDown(MyInput.BUTTON1)){
					Vector2 vel = player.getBody().getLinearVelocity();
					vel.x = 3.5f;
					player.getBody().setLinearVelocity(vel);
				}
				else{
					Vector2 vel = player.getBody().getLinearVelocity();
					vel.x = 2.9f;
					player.getBody().setLinearVelocity(vel);
				}
			
			
			}
			else{
				Vector2 vel = player.getBody().getLinearVelocity();
				vel.x = 0f;
				player.getBody().setLinearVelocity(vel);
			}
			
		}
		
		/* No longer needed for resetting game on gameOver.
		else{
			if(MyInput.isPressed(MyInput.BUTTON1)){
				gsm.setScreen(100);
				gsm.set();
				reset();
				
			}
			
		}
		*/
		
	}
	
	public static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    return rand.nextInt((max - min) + 1) + min;
	}
	
	
	
	
	public void update(float dt){
		runTime += dt;
		accelX = Gdx.input.getAccelerometerX();
		
		//System.out.println(leftWall.getBody().getPosition().x);
		
		handleInput();
		
	}
	
	
	public void render(){
		
		/*
		if((int)AssetLoader.bgm.getPosition() == 26){
				AssetLoader.bgm.stop();
				AssetLoader.bgm.play();
		}
		*/
		//clear screens
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(!gameOverFlag){
			world.step(1/60f, 1, 1);
			player.update(1/60f);
		}
		
	
		td.setPosition(player.getPosition().x, player.getPosition().y + 3/PPM);	


		if(mainBg.getYPosition() > B2DVars.TRUE_HEIGHT){
			mainBg.setYPosition(mainBgRepeat.getYPosition() - mainBgRepeat.getHeight());
		}
		if(mainBgRepeat.getYPosition() > B2DVars.TRUE_HEIGHT){
			mainBgRepeat.setYPosition(mainBg.getYPosition() - mainBg.getHeight());
		}
		
		if(middleBgRight.getYPosition() > B2DVars.TRUE_HEIGHT){
			middleBgRight.setYPosition(mRRepeat.getYPosition() - mRRepeat.getHeight());
		}
		if(middleBgLeft.getYPosition() >  B2DVars.TRUE_HEIGHT){
			middleBgLeft.setYPosition(mLRepeat.getYPosition() - mLRepeat.getHeight());
		}
		if(mRRepeat.getYPosition() > B2DVars.TRUE_HEIGHT){
			mRRepeat.setYPosition(middleBgRight.getYPosition() - middleBgRight.getHeight());
		}
		if(mLRepeat.getYPosition() > B2DVars.TRUE_HEIGHT){
			mLRepeat.setYPosition(middleBgLeft.getYPosition() - middleBgLeft.getHeight());
		}
		
		for(Background temp : backgrounds)
		{
			if(temp instanceof AnimatedBackground){
				
				temp.update(runTime);
			}
			else
				temp.update(1/60f);
			temp.render(sb);
			
			
		}
		
		/*for(Middleground temp : middlegrounds)
		{
			if(temp instanceof Middleground)
				temp.update(1/60f);
			
			temp.render(sb);
		}*/
		
		
		Array<Body> tempBodies = new Array<Body>();
		world.getBodies(tempBodies);
		for(Body b : tempBodies){
			if(b.getPosition().y > b2dCam.position.y + MainGame.V_HEIGHT/PPM){
				if(b.getUserData() instanceof StaticSprite)
				{
					ledgeList.removeValue((StaticSprite) b.getUserData(), true);
				}
				
				world.destroyBody(b);
				//obstacleList.removeValue(b, true);
			}
		}
		
		
		for(ConeLight l : lightList){
			if(l.getPosition().y > b2dCam.position.y + MainGame.V_HEIGHT/PPM){
				l.setActive(false);
				handler.lightList.removeValue(l, true);
				lightList.removeValue(l, true);
				lights--;
				
			}
		}
		
		for(PointLight l : lightListP){
			if(l.getPosition().y > b2dCam.position.y + MainGame.V_HEIGHT/PPM){
				l.setActive(false);
				handler.lightList.removeValue(l, true);
				lightListP.removeValue(l, true);
				lights--;
				
			}
		}
		
		
		//p.setPosition(player.getPosition().x, player.getPosition().y);
		
		if(rightWall.getYPosition() > cam.position.y/PPM + MainGame.V_HEIGHT/2/PPM){
			rightWall.setPosition(rWallRepeat.getYPosition() - rightWall.getHeight()/PPM);
		}
		if(leftWall.getYPosition() > cam.position.y/PPM + MainGame.V_HEIGHT/2/PPM){
			leftWall.setPosition(lWallRepeat.getYPosition() - leftWall.getHeight()/PPM);
		}
		if(rWallRepeat.getYPosition() > cam.position.y/PPM + MainGame.V_HEIGHT/2/PPM){
			rWallRepeat.setPosition(rightWall.getYPosition() - rWallRepeat.getHeight()/PPM);
		}
		if(lWallRepeat.getYPosition() > cam.position.y/PPM + MainGame.V_HEIGHT/2/PPM){
			lWallRepeat.setPosition(leftWall.getYPosition() - lWallRepeat.getHeight()/PPM);
		}
		
		for(Wall temp : foreground)
		{
			temp.render(sb);
		}
		
		x = player.getPosition().y;
		y = player.getPosition().y;
		
		//System.out.println(leftWall.getBody().getPosition().x * PPM);
		if(Math.abs(x - x2)/PPM >= nextSprite/PPM)
		{
			if(leftWall.getBody().getPosition().x < 40/PPM || restoring == true){
			
			addObstacles();
			}
		
			
			x2 = x;
			nextSprite = (long) randInt(3, 7);
		}
		
		if(Math.abs(y - y2)/PPM >= nextSprite/PPM)
		{
			
			addPlatforms();
			y2 = y;
			nextSprite2 = (long) randInt(2, 6);
		}
		
		//System.out.println(world.getBodyCount());
		//System.out.println(handler.lightList.size);
		
		
		
		/*
		resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
                (int) viewport.width, (int) viewport.height);
		*/
		
		leftWall.getBody().setLinearVelocity( new Vector2(leftWall.getBody().getLinearVelocity().x,player.getBody().getLinearVelocity().y));
		rightWall.getBody().setLinearVelocity( new Vector2(rightWall.getBody().getLinearVelocity().x,player.getBody().getLinearVelocity().y));
		
		
		wallEvent();
		
		capVelocity();
	
		
		cam.position.set(
                 player.getPosition().x * PPM,
                 (player.getPosition().y) * PPM - CL,
                 0
        );
		 
		cam.update();
		
		sb.setProjectionMatrix(cam.combined);
		
		sb2.setProjectionMatrix(cam.combined);
		
		
		for(StaticSprite ledge : ledgeList)
		{
			ledge.update();
			ledge.render(sb);
		}
		
		if(!gameOverFlag){
			player.render(sb);
		}
				
	
		
		handler.setBlurNum(0);
		handler.setCombinedMatrix(b2dCam.combined);
		handler.updateAndRender();

		if(!gameOverFlag){
			
			drawScoreHud();
		}
		
		if(cl.isPlayerOnGround() == true && cl.isDoubleCheck()){
			gameOverFlag = true;
			
			
		}
		
		if(gameOverFlag){
			gameOverRunTime+= 1/60f;
			
			gameOver();
			
			
		}
		b2dCam.position.set(
                player.getPosition().x,player.getPosition().y - CL/PPM,
                0
        );
		b2dCam.update(); 
		
	
		
		// draw box2d
		if(debug) {
		
			b2dr.render(world, b2dCam.combined);
			
		}
		
	
		
		
	}
	
	public void createPlayer()
	{
		//create player body
		BodyDef bdef = new BodyDef();
		bdef.position.set(160/PPM, 300/PPM);
		bdef.type = BodyType.DynamicBody;
		
		
		Body pBody = world.createBody(bdef);
		
		MassData md = pBody.getMassData();
        md.mass = 1;
		pBody.setMassData(md);
		
				
		//create box shape for player collision box
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((float) (7.5/PPM), 10/PPM);
		//shape.setAsBox((float) (7.5/PPM), 10/PPM);
		
		//create fixture for player
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		//fdef.restitution = 1f;
		fdef.friction = 0f;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
		//fdef.filter.categoryBits = B2DVars.BIT_BALL;
		//fdef.filter.maskBits = B2DVars.BIT_GROUND;
		
		pBody.createFixture(fdef).setUserData("Player");
		shape.dispose();
				
		//create foot sensor
		shape = new PolygonShape();
		shape.setAsBox((float) (5.5 / PPM), 2 / PPM, new Vector2(0, -10/PPM), 0);
		
		//create fixture for foot
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
		fdef.isSensor = true;
	
		pBody.createFixture(fdef).setUserData("foot");
		shape.dispose();
		
		//create footCheck sensor to allow player to not hit side of platforms
		shape = new PolygonShape();
		shape.setAsBox((float) (3 / PPM), 2 / PPM, new Vector2((float) -7.5/PPM, -10/PPM), 0);
		//create fixture for foot
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
		fdef.isSensor = true;
		pBody.createFixture(fdef).setUserData("footLeft");
		shape.dispose();
		
		//create footCheck sensor to allow player to not hit side of platforms
		shape = new PolygonShape();
		shape.setAsBox((float) (3 / PPM), 2 / PPM, new Vector2((float) 7.5/PPM, -10/PPM), 0);
		//create fixture for foot
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
		fdef.isSensor = true;
		pBody.createFixture(fdef).setUserData("footRight");
		shape.dispose();
		
		
		
		
				
		//create player
		player = new Player(pBody);
		pBody.setUserData(player);
	}
	
	public void createWalls()
	{
		//Left wall
		BodyDef bdef = new BodyDef(); 
		bdef.position.set(0/PPM,0+200/PPM);
		bdef.type = BodyType.KinematicBody;
		Body lWall = world.createBody(bdef);
				
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(2/PPM, (MainGame.V_HEIGHT+50/2)/PPM); //half width half height, so 100, 10
				
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		//fdef.filter.maskBits = B2DVars.BIT_BOX | B2DVars.BIT_BALL;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		lWall.createFixture(fdef).setUserData("LeftWall");
		
		leftWall = new LeftWall(lWall);
		leftWall.setPosition(cam.position.y - B2DVars.TRUE_WIDTH/2/PPM);
		lWallRepeat = new LeftWall(lWall);
		lWallRepeat.setPosition(leftWall.getYPosition() - leftWall.getHeight()/PPM);
				
		//Right wall
		bdef = new BodyDef(); 
		bdef.position.set((MainGame.V_WIDTH)/PPM,0+200/PPM);
		bdef.type = BodyType.KinematicBody;
		Body rWall = world.createBody(bdef);
						
		shape.dispose();
		shape = new PolygonShape();
		shape.setAsBox(2/PPM, (MainGame.V_HEIGHT+50/2)/PPM); //half width half height, so 100, 10
						
		fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		//fdef.filter.maskBits = B2DVars.BIT_BOX | B2DVars.BIT_BALL;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		rWall.createFixture(fdef).setUserData("RightWall");
		
		rightWall = new RightWall(rWall);
		rightWall.setPosition(cam.position.y - B2DVars.TRUE_WIDTH/2/PPM);
		rWallRepeat = new RightWall(rWall);
		rWallRepeat.setPosition(rightWall.getYPosition() - rightWall.getHeight()/PPM);
		shape.dispose();
		
		Wall[] temp = {rightWall, rWallRepeat, leftWall, lWallRepeat};
		foreground = temp;
	}
	
	public void addPlatforms()
	{
		if(Math.random() <= .25)
		{
			addLeftPlatform();
			addRightPlatform();
		}
		else
		{
			if(Math.random() < .5)
				addLeftPlatform();
			else
				addRightPlatform();
		}
		
	}
	
	public void addLeftPlatform()
	{
		//adding platforms to the walls
		BodyDef def = new BodyDef(); 
		def.position.set(leftWall.getPosition().x + ((MainGame.V_WIDTH/10)/PPM), (cam.position.y/PPM - (MainGame.V_HEIGHT*2)/PPM));

		def.type = BodyType.StaticBody;
		Body body = world.createBody(def);
				
		PolygonShape shape2 = new PolygonShape();
		shape2.setAsBox((MainGame.V_WIDTH/10)/PPM, 5/PPM); //half width half height, so 100, 10
				
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape2;
		fdef.density = 0.4f;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		body.createFixture(fdef).setUserData("Wall Platform");
		shape2.dispose();
		
		ConeLight t;
		t = new ConeLight(handler, 40, Color.GRAY,800/PPM, body.getPosition().x, body.getPosition().y + 120/PPM, 270, 20);	
		
		
		
		//PointLight d;
		//d = new PointLight(handler, 40, Color.LIGHT_GRAY, grow/PPM,  player.getPosition().x, player.getPosition().y);
		
		t.setSoft(true);
		
		Lamp lamp = new Lamp(t);
		
		LedgeLeft temp = new LedgeLeft(body, leftWall, lamp);
		body.setUserData(temp);
		ledgeList.add(temp);
		lightList.add(t);
	}
	
	public void addRightPlatform()
	{
		//adding platforms to the walls
		BodyDef def = new BodyDef(); 
		def.position.set(rightWall.getPosition().x - ((MainGame.V_WIDTH/10)/PPM), (cam.position.y/PPM - (MainGame.V_HEIGHT*2)/PPM));

		def.type = BodyType.StaticBody;
		Body body = world.createBody(def);
				
		PolygonShape shape2 = new PolygonShape();
		shape2.setAsBox((MainGame.V_WIDTH/10)/PPM, 5/PPM); //half width half height, so 100, 10
				
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape2;
		fdef.density = 0.4f;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		body.createFixture(fdef).setUserData("Wall Platform");
		shape2.dispose();
		
		ConeLight t;
		t = new ConeLight(handler, 40, Color.GRAY,800/PPM, body.getPosition().x, body.getPosition().y + 120/PPM, 270, 20);		
		t.setSoft(true);
		
		Lamp lamp = new Lamp(t);
		
		//PointLight d;
		//d = new PointLight(handler, 40, Color.LIGHT_GRAY, grow/PPM,  player.getPosition().x, player.getPosition().y);
		
		LedgeRight temp = new LedgeRight(body, rightWall, lamp);
		body.setUserData(temp);
		ledgeList.add(temp);
		lightList.add(t);
	}
	
	public void addObstacles(){
		BodyDef def = new BodyDef(); 
		//def.position.set(randInt(0,(320-(Game.V_WIDTH/5)-3))/PPM,(cam.position.y - (Game.V_HEIGHT*2)/PPM));
		//def.position.set(((Game.V_WIDTH/5)+3)/PPM,(cam.position.y - (Game.V_HEIGHT*2)/PPM));
		def.position.set(randInt(((MainGame.V_WIDTH/5)+3),(320-(MainGame.V_WIDTH/5)-3))/PPM,(b2dCam.position.y - (MainGame.V_HEIGHT*2)/PPM));

		def.type = BodyType.StaticBody;
		Body body = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		float width = randInt(5, 8);
		shape.setAsBox((MainGame.V_WIDTH/width)/PPM, 5/PPM); //half width half height, so 100, 10
		width = (MainGame.V_WIDTH/width)/PPM;
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 0.4f;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		body.createFixture(fdef).setUserData("Ground");
		
		
	
		//ConeLight t;
		//t = new ConeLight(handler, 40, Color.GRAY,800/PPM, body.getPosition().x, body.getPosition().y + 120/PPM, 270, 30);
		
		
		PointLight d;
		d = new PointLight(handler, 40, Color.valueOf("A9E2FF"), grow/PPM,  player.getPosition().x, player.getPosition().y);
		
	
		d.setXray(true);
		d.setSoft(true);
		//Light.setContactFilter(B2DVars.BIT_BALL, (short) 0, B2DVars.BIT_BALL);
		//t.isSoft();
		
		LedgeMiddle temp = new LedgeMiddle(body, width, d);
		body.setUserData(temp);
		ledgeList.add(temp);
		//obstacleList.add(body);
		//lightList.add(t);
		shape.dispose();
		
		
	}
	
	public void init(){
		

		//l1 = new ParallaxLayer(AssetLoader.waterFallBG, new Vector2(0f, 0f), new Vector2(0f,0));

		//b = new ParallaxBackground(new ParallaxLayer[]{l1}, 320, 480,new Vector2(0,0), cam);
		
		//create platform
		BodyDef bdef = new BodyDef(); 
		
		//bdef.position.set(160/PPM,120/PPM);
		//bdef.type = BodyType.StaticBody;
		//Body body = world.createBody(bdef);
				
		PolygonShape shape = new PolygonShape();
		//shape.setAsBox(50/PPM, 5/PPM); //half width half height, so 100, 10
				
		FixtureDef fdef = new FixtureDef();
		//fdef.shape = shape;
		//fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		//fdef.filter.maskBits = B2DVars.BIT_BOX | B2DVars.BIT_BALL;
		//fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		//body.createFixture(fdef).setUserData("Ground");
		shape.dispose();
		
		createPlayer();
		//b.setPlayer(player);
		
		TextureRegion[] bgAnim = {AssetLoader.waterFallBG, AssetLoader.waterFallBG2};
		AnimatedBackground bg = new AnimatedBackground(bgAnim, cam, player, .5f);
		AnimatedBackground bg2 = new AnimatedBackground(bgAnim, cam, player, 0, bg.getYPosition() - bg.getHeight(), .5f);
		
		mainBg = bg;
		mainBgRepeat = bg2;
		
		middleBgLeft = new Middleground(AssetLoader.middleBGLeft, cam, player, 0, 0, 1f);
		mLRepeat = new Middleground(AssetLoader.middleBGLeft, cam, player, 0, middleBgLeft.getYPosition() - middleBgLeft.getHeight(), 1f);
		
		middleBgRight = new Middleground(AssetLoader.middleBGRight, cam, player, B2DVars.TRUE_WIDTH, 0, 1f);
		mRRepeat = new Middleground(AssetLoader.middleBGRight, cam, player, B2DVars.TRUE_WIDTH, middleBgRight.getYPosition() - middleBgRight.getHeight(), 1f);
		
		Background[] bgs = {mainBg, mainBgRepeat, middleBgLeft, mLRepeat, middleBgRight, mRRepeat};
		backgrounds = bgs;
		createWalls();
	}
	
	public float getWallPos()
	{
		if(Math.random() > .5){return leftWall.getPosition().x + ((MainGame.V_WIDTH/10)/PPM);}
		else{return rightWall.getPosition().x - ((MainGame.V_WIDTH/10)/PPM);}
	}
	
	public void wallEvent(){
		
		long s = System.currentTimeMillis();
		
		//System.out.println(r);
	
		if(s - wallInterval > r){
			
			r = randInt(10000, 40000);
			
			wallEvent = !wallEvent;
			wallInterval = s;
		}
		if(wallEvent){
			wallEvent = false;
			//System.out.println(leftWall.getBody().getPosition().x);
			//System.out.println("GOING---------------------------------------");
			if(!AssetLoader.caveIn.isPlaying()){
				AssetLoader.caveIn.play();
			}
			leftWall.getBody().setLinearVelocity(new Vector2(10/PPM, player.getBody().getLinearVelocity().y));
			rightWall.getBody().setLinearVelocity(new Vector2(-10/PPM, player.getBody().getLinearVelocity().y));	
		}
			
		else if(leftWall.getBody().getPosition().x > 60/PPM){
			restoring = true;
			//if(!AssetLoader.caveIn.isPlaying()){
			//	AssetLoader.caveIn.play();
			//}
			//System.out.println("RESTORING---------------------------------------");
			leftWall.getBody().setLinearVelocity(new Vector2(-10/PPM, player.getBody().getLinearVelocity().y));
			rightWall.getBody().setLinearVelocity(new Vector2(10/PPM, player.getBody().getLinearVelocity().y));
		}
		else if(leftWall.getBody().getPosition().x <= 0/PPM){
			restoring = false;
			//System.out.println("STOPPED---------------------------------------");
			leftWall.getBody().setLinearVelocity(new Vector2(0/PPM, player.getBody().getLinearVelocity().y));
			rightWall.getBody().setLinearVelocity(new Vector2(0/PPM, player.getBody().getLinearVelocity().y));
		}
		
		
	}
	
	public void resize(int width, int height) {
		float aspectRatio;
		
		aspectRatio = (float)width / (float)height;
		
		float scale = 1f;
		Vector2 crop = new Vector2(0f, 0f);
		
		if(aspectRatio > ASPECT_RATIO)
        {
            scale = (float)height/(float)MainGame.V_HEIGHT;
            crop.x = (width - MainGame.V_WIDTH*scale)/2f;
        }
        else if(aspectRatio < ASPECT_RATIO)
        {
            scale = (float)width/(float)MainGame.V_WIDTH;
            crop.y = (height - MainGame.V_HEIGHT*scale)/2f;
        }
        else
        {
            scale = (float)width/(float)MainGame.V_WIDTH;
        }

        float w = (float)MainGame.V_WIDTH*scale;
        float h = (float)MainGame.V_HEIGHT*scale;
        viewport = new Rectangle(crop.x, crop.y, w, h);
	}
	
	public void reset()
	{
		MyInput.setKey(MyInput.BUTTON1, false);
		lastDepth = 0;
		lastStop = 0;
		score = 0;
	}
	
	public void drawScore(SpriteBatch sb, float x, float y)
	{
		sb.begin();
		float w = font.getBounds("Score: " + (int)score).width;
		float h = font.getBounds("Score: " + (int)score).height;
		font.setUseIntegerPositions(false);
		font.draw(sb,String.valueOf("Score: " + (int)score) , x, y);
		
		sb.end();
	}
	
	public void drawHighScore(SpriteBatch sb, float x, float y)
	{
		if(score > AssetLoader.getHighScore()){
			AssetLoader.setHighScore((int)score);
		}
		sb.begin();
		float w = font.getBounds("HighScore: " +  AssetLoader.getHighScore()).width;
		float h = font.getBounds("HighScore: " +  AssetLoader.getHighScore()).height;
		font.setUseIntegerPositions(false);
		font.draw(sb,String.valueOf("HighScore: " + AssetLoader.getHighScore()) , x, y);
		
		sb.end();
	}
	
	
	
	
	public void drawMulitplier(SpriteBatch sb, float x, float y)
	{
		sb.begin();
		float w = font.getBounds("Multiplier x" + (int)multi).width;
		float h = font.getBounds("Multiplier x" + (int)multi).height;
		font.setUseIntegerPositions(false);
		font.draw(sb,String.valueOf("Multiplier x" + (int)multi) , x, y);
		sb.end();
	}
	
	public void capVelocity(){
		if(Math.abs(player.getBody().getLinearVelocity().y) > 6f){
			
			Vector2 vel = player.getBody().getLinearVelocity();
			vel.y = -6f;
			player.getBody().setLinearVelocity(vel);
			
		}
	}
	
	public void drawScoreHud(){
		if((int)Math.abs(depth - lastStop) > 40)
			multi = 4;
		else if((int)Math.abs(depth - lastStop) > 25)
			multi = 3;
		else if((int)Math.abs(depth - lastStop) > 10)
			multi = 2;
		
		
		if((int)Math.abs(depth - lastDepth) >= 1)
		{
			score += 1 * multi;
			lastDepth = depth;
			
		}
		
		sb.begin();
		
		depth = (float) (Math.abs(player.getPosition().y - 300/PPM)/0.3048);
		float w = font.getBounds((int)depth + "ft").width;
		float h = font.getBounds((int)depth + "ft").height;
		font.setUseIntegerPositions(false);
		font.draw(sb,String.valueOf((int)depth + "ft") , cam.position.x - game.V_WIDTH/2, cam.position.y + game.V_HEIGHT/2);
		sb.end();
		float wScore = font.getBounds("Score: " + (int)score).width;
		float hScore = font.getBounds("Score: " + (int)score).height;
		drawScore(sb, cam.position.x + game.V_WIDTH/2 - wScore, cam.position.y + game.V_HEIGHT/2);
		float wMulti = font.getBounds("Multiplier x" + (int)multi).width;
		drawMulitplier(sb, cam.position.x + game.V_WIDTH/2 - wMulti, cam.position.y + game.V_HEIGHT/2 - hScore);
		
		shapeRenderer.setProjectionMatrix(b2dCam.combined);
		shapeRenderer.begin(ShapeType.Filled);
		if(!glide_CD){
			shapeRenderer.setColor(0f/255f,250f/255f,154f/255f, 1);
		}
		else{
			shapeRenderer.setColor(255f/255f,0f/255f,0f/255f, 1);
		}
		
		shapeRenderer.rect(b2dCam.position.x - (glide_x/2)/PPM , b2dCam.position.y + 10/PPM + CL/PPM, glide_x/PPM, 3/PPM);
		shapeRenderer.end();
		
	}
	
	public void gameOver(){
		
		td.setActive(false);
		drawTransition(1/60f);
		player.getBody().setLinearVelocity(0, 0);
		
		if(gameOverRunTime >= 0.5f){
			sb2.begin();
			
			font.setColor(1f, 1f, 1f, fadeIn);
		
			if(fadeIn <= 1f){
				fadeIn = fadeIn + 0.01f;
				if(fadeIn >= 1f){
					fadeIn = 1f;
				}
			}	
			menuTransition(1/60f);
			float w = font.getBounds("Game Over").width;
			float h = font.getBounds("Game Over").height;
			font.draw(sb2, "Game Over", cam.position.x - w/2 , cam.position.y + h/2 + font.getXHeight());
			sb2.end();
			stage.act();
			sb.setProjectionMatrix(hudCam.combined);
			stage.draw();
			sb.setProjectionMatrix(cam.combined);
			Gdx.input.setInputProcessor(stage);
			float w1 = font.getBounds("Score: " + (int)score).width;
			drawScore(sb, cam.position.x - w1/2, cam.position.y + h/2 - font.getXHeight());
			float w2 = font.getBounds("High Score: " + AssetLoader.getHighScore()).width; //Change methods later to already include this field
			drawHighScore(sb, cam.position.x - w2/2, cam.position.y + h/2 - font.getXHeight()*3);
		}
		
	}
	


	@Override
	public void render(float delta) 
	{
		
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stubc
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	public void dispose(){
		
		Array<Body> temps = new Array<Body>();
		
		AssetLoader.caveIn.stop();
		
		world.getBodies(temps);
		
		for(Body b : temps){
			world.destroyBody(b);
			System.out.println(world.getBodyCount() + "-----------------------------------------------------------" + temps.size);
		}
		temps.clear();
		
		
		handler.dispose();
	
		world.dispose();
		b2dr.dispose();
		font.dispose();
		stage.dispose();
		
	
		sb2.dispose();
		sb.dispose();
		
		shapeRenderer.dispose();
		skin.dispose();
		
	
		
	}
	

	public void prepareTransition(int r, int g, int b, float duration) {
		transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
		alpha.setValue(1);
		menuVal.setValue(cam.position.x - B2DVars.TRUE_WIDTH/2);
		Tween.registerAccessor(Value.class, new ValueAccessor());
		manager = new TweenManager();
		menuManager = new TweenManager();
		Tween.to(alpha, -1, duration).target(0)
				.ease(TweenEquations.easeOutQuad).start(manager);
	}
	
	private void drawTransition(float delta) {
		if (alpha.getValue() > 0) {
			manager.update(delta);
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(transitionColor.r, transitionColor.g,
					transitionColor.b, alpha.getValue());
			shapeRenderer.rect(cam.position.x/PPM - (Gdx.graphics.getWidth()/2)/PPM, cam.position.y/PPM - 2*CL/PPM, 100, 100);
			shapeRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);

		}
	}
	
	public void menuTransition(float delta)
	{
		Tween.to(menuVal, -1, 0.15f).target(cam.position.x - 100).start(menuManager);
		if (menuVal.getValue() <= cam.position.x) {
			menuManager.update(delta);
		}
		//System.out.println(menuVal.getValue()+ ", " + cam.position.x);
		sb2.draw(menu, menuVal.getValue(), cam.position.y - 75, 200, 150); 
	}
	




}
