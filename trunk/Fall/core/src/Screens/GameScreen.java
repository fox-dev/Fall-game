package Screens;





import static helpers.B2DVars.PPM;

import java.util.Iterator;
import java.util.Random;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.mygdx.game.MainGame;

import objects.Player;
import helpers.B2DVars;
import handlers.GameScreenManager;
import handlers.MyContactListener;
import handlers.MyInput;

public class GameScreen extends AbstractScreen {
	
	public static final float STEP = 1 / 60f;
	private float accum;
	
	float ASPECT_RATIO = (float)MainGame.V_HEIGHT/(float)MainGame.V_WIDTH;
	private Rectangle viewport;
	
	private boolean debug = true;
	float x = 0, y = 0, x2 = 0, y2 = 0;
	long lastSprite = 0;
	long lastSprite2 = 0;
	long nextSprite = 2, nextSprite2 = 3;
	
	int lights = 0;
	
	float runTime;
	
	private DelayedRemovalArray<Body> obstacleList = new DelayedRemovalArray<Body>();
	private DelayedRemovalArray<ConeLight> lightList = new DelayedRemovalArray<ConeLight>();
	private Iterator<Body> iterator;
	private Iterator<ConeLight> iterator2;
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private OrthographicCamera b2dCam;
	
	private RayHandler handler;
	
	//private Body playerBody;
	private Player player;
		
	private Body leftWall;
	private Body rightWall;

	private MyContactListener cl;
	
	public GameScreen(GameScreenManager gsm) {
		super(gsm);
		
		
		world = new World(new Vector2(0, -9.81f), true);
		
		cl = new MyContactListener();
		world.setContactListener(cl);
		
		b2dr = new Box2DDebugRenderer();
		
		//create platform
		BodyDef bdef = new BodyDef(); 
		bdef.position.set(160/PPM,120/PPM);
		bdef.type = BodyType.StaticBody;
		Body body = world.createBody(bdef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50/PPM, 5/PPM); //half width half height, so 100, 10
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		//fdef.filter.maskBits = B2DVars.BIT_BOX | B2DVars.BIT_BALL;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		body.createFixture(fdef).setUserData("Ground");
		
		createPlayer();
		
		//Left wall
		bdef = new BodyDef(); 
		bdef.position.set(0/PPM,0+200/PPM);
		bdef.type = BodyType.KinematicBody;
		leftWall = world.createBody(bdef);
		
		shape = new PolygonShape();
		shape.setAsBox(2/PPM, (MainGame.V_HEIGHT+50/2)/PPM); //half width half height, so 100, 10
		
		fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		//fdef.filter.maskBits = B2DVars.BIT_BOX | B2DVars.BIT_BALL;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		leftWall.createFixture(fdef).setUserData("LeftWall");
		
		//Right wall
		bdef = new BodyDef(); 
		bdef.position.set((MainGame.V_WIDTH)/PPM,0+200/PPM);
		bdef.type = BodyType.KinematicBody;
		rightWall = world.createBody(bdef);
				
		shape = new PolygonShape();
		shape.setAsBox(2/PPM, (MainGame.V_HEIGHT+50/2)/PPM); //half width half height, so 100, 10
				
		fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		//fdef.filter.maskBits = B2DVars.BIT_BOX | B2DVars.BIT_BALL;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		rightWall.createFixture(fdef).setUserData("RightWall");
		
		//set up box2d cam
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, MainGame.V_WIDTH/PPM , MainGame.V_HEIGHT/PPM );
		
		handler = new RayHandler(world);
		handler.setAmbientLight(0.0f, 0.0f, 0.0f,1.0f);
		
		
		
		
	}
	
	public void handleInput(){
		if(MyInput.isPressed(MyInput.BUTTON1)){
			//System.out.println("pressed z");
			//gsm.setScreen(100);
			//gsm.set();
			//if(cl.isPlayerOnGround()){
				//playerBody.applyForceToCenter(0,200,true);
				//player.getBody().applyForceToCenter(0,200,true);
	
			//}
		}
		if(MyInput.isDown(MyInput.BUTTON1)){
			//Vector2 vel = playerBody.getLinearVelocity();
			
		
			Vector2 vel = player.getBody().getLinearVelocity();
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		 	vel.y = -1f;
		 	player.stopping();
			player.getBody().setLinearVelocity(vel);
		 	//player.getBody().setLinearVelocity(vel);
		}
		
		if(!MyInput.isDown(MyInput.BUTTON1))
		{
			player.falling();
		}
		
		if(MyInput.isDown(MyInput.BUTTON2)){
			//System.out.println("hold x");
			//Vector2 vel = playerBody.getLinearVelocity();
			Vector2 vel = player.getBody().getLinearVelocity();
			vel.x = -1f;
			//playerBody.setLinearVelocity(vel);
			player.getBody().setLinearVelocity(vel);
			
		}
		if(MyInput.isDown(MyInput.BUTTON3)){
			//System.out.println("hold c");
			//Vector2 vel = playerBody.getLinearVelocity();
			Vector2 vel = player.getBody().getLinearVelocity();
			vel.x = 1f;
			//playerBody.setLinearVelocity(vel);
			player.getBody().setLinearVelocity(vel);
			
		}
	}
	
	public static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    return rand.nextInt((max - min) + 1) + min;
	}
	
	public void addObstacles(){
		BodyDef def = new BodyDef(); 
		//def.position.set(randInt(0,(320-(Game.V_WIDTH/5)-3))/PPM,(cam.position.y - (Game.V_HEIGHT*2)/PPM));
		//def.position.set(((Game.V_WIDTH/5)+3)/PPM,(cam.position.y - (Game.V_HEIGHT*2)/PPM));
		def.position.set(randInt(((MainGame.V_WIDTH/5)+3),(320-(MainGame.V_WIDTH/5)-3))/PPM,(b2dCam.position.y - (MainGame.V_HEIGHT*2)/PPM));

		def.type = BodyType.StaticBody;
		Body body = world.createBody(def);
		body = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((MainGame.V_WIDTH/5)/PPM, 5/PPM); //half width half height, so 100, 10
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 0.4f;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		body.createFixture(fdef).setUserData("Ground");
	
		ConeLight t;
		t = new ConeLight(handler, 10, Color.GRAY,1000/PPM, body.getPosition().x, body.getPosition().y + 120/PPM, 270, 36);
		Light.setContactFilter(B2DVars.BIT_BALL, (short) 0, B2DVars.BIT_BALL);
		
		obstacleList.add(body);
		lightList.add(t);
		
		//System.out.println("Y object: " + body.getPosition().y);
	}
	
	
	@Override
	public void update(float dt){
		runTime += dt;
		//System.out.println("Runtime: " + runTime);
		System.out.println("GAMESCREEN");
		
		//System.out.println("Wally: "+ leftWall.getPosition().y);
		//System.out.println("Playery: "+ player.getPosition().y);
		//System.out.println("Difference: " +  (leftWall.getPosition().y - player.getPosition().y));
		
		
		iterator = obstacleList.iterator();
		iterator2 = lightList.iterator();
		while(iterator.hasNext()){
			Body o = iterator.next();
			ConeLight s = iterator2.next();
			if(o.getPosition().y > b2dCam.position.y + MainGame.V_HEIGHT/PPM){
				world.destroyBody(o);
				s.setActive(false);
				iterator.remove();
				iterator2.remove();
				handler.lightList.removeValue(s, true);
				lights--;
				
			}
			
		}
		
		x = player.getPosition().y;
		y = player.getPosition().y;
		
		if(Math.abs(x - x2)/PPM >= nextSprite/PPM)
		{
			addObstacles();
			x2 = x;
			nextSprite = (long) randInt(3, 7);
		}
		
		if(Math.abs(y - y2)/PPM >= nextSprite/PPM)
		{
			addPlatforms();
			y2 = y;
			nextSprite2 = (long) randInt(2, 6);
		}
		  
		handleInput();
		if(Math.abs(player.getBody().getLinearVelocity().y) > 10f){
		//playerBody.applyForceToCenter(0,200,true);
			Vector2 vel = player.getBody().getLinearVelocity();
			vel.y = -10f;
			player.getBody().setLinearVelocity(vel);
			
		}
		
		
		Vector2 n = new Vector2(0,player.getBody().getLinearVelocity().y);
		
		/*
		if((leftWall.getPosition().y - player.getPosition().y) > 0){
			leftWall.setTransform(0, player.getBody().getPosition().y, 0);
			rightWall.setTransform((Game.V_WIDTH)/PPM, player.getBody().getPosition().y, 0);
		} 
		*/
		
		//System.out.println("Vp: " + player.getBody().getLinearVelocity().y);
		
		
		leftWall.setTransform(leftWall.getPosition().x, player.getBody().getPosition().y, 0);
		leftWall.setLinearVelocity(n);
		rightWall.setTransform(rightWall.getPosition().x, player.getBody().getPosition().y, 0);
		rightWall.setLinearVelocity(n);
		
		
		
		if(runTime > 3 && runTime < 10){
		System.out.println("GOING---------------------------------------");
			leftWall.setLinearVelocity(new Vector2(10/PPM, player.getBody().getLinearVelocity().y));
			rightWall.setLinearVelocity(new Vector2(-10/PPM, player.getBody().getLinearVelocity().y));
		}
		
		if(runTime > 10 && leftWall.getPosition().x >= 0 && rightWall.getPosition().x <= game.V_WIDTH/PPM ){
			System.out.println("RESTORING---------------------------------------");
				leftWall.setLinearVelocity(new Vector2(-10/PPM, player.getBody().getLinearVelocity().y));
				rightWall.setLinearVelocity(new Vector2(10/PPM, player.getBody().getLinearVelocity().y));
		}
		
		
		
		
		
		//System.out.println("Size: " + handler.lightList.size);
		
		world.step(dt, 1, 1);
		
		player.update(runTime);
	}
	
	
	
	public void render(){
		//clear screens
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		
		resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
                (int) viewport.width, (int) viewport.height);
		
		
		
		/*
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		font.draw(sb, "play state", 100, 100);
		sb.end();
		*/
		
		
		
		 cam.position.set(
                 player.getPosition().x * PPM,
                 (player.getPosition().y) * PPM - 100,
                 0
         );
		 
		cam.update();
		
		b2dCam.position.set(
                 player.getPosition().x,player.getPosition().y - 100/PPM

                 ,
                 0
         );
		b2dCam.update(); 
			
		sb.setProjectionMatrix(cam.combined);
		player.render(sb);
		
		
		handler.setCombinedMatrix(b2dCam.combined);
		handler.updateAndRender();
			
		
		//System.out.println("x: " + cam.position.x/PPM);
		//System.out.println("y: " + cam.position.y*100);
		//System.out.println(playerBody.getLinearVelocity().y);
	
		
		// draw box2d
		if(debug) {
			b2dr.render(world, b2dCam.combined);
		}
		
		
	}
	
	public void dispose(){
		
		handler.dispose();
		b2dr.dispose();
		for(Body body : obstacleList)
		{
			world.destroyBody(body);
		}
		world.dispose();
		
		obstacleList = new DelayedRemovalArray<Body>();
		
		lightList = new DelayedRemovalArray<ConeLight>();
		
		//iterator = obstacleList.iterator();
		//iterator2 = lightList.iterator();
		
		
		//sb.dispose();
		//private Array<Body> obstacleList = new Array<Body>();
		//private Array<ConeLight> lightList = new Array<ConeLight>();
		//private Iterator<Body> iterator;
		//private Iterator<ConeLight> iterator2;
		
	}
	
	public void createPlayer()
	{
		//create player body
		BodyDef bdef = new BodyDef();
		bdef.position.set(160/PPM, 300/PPM);
		bdef.type = BodyType.DynamicBody;
		//playerBody = world.createBody(bdef);
		Body pBody = world.createBody(bdef);
				
		//create box shape for player collision box
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(10/PPM, 10/PPM);
		
		//create fixture for player
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		//fdef.restitution = 1f;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
		//playerBody.createFixture(fdef).setUserData("Player");
		pBody.createFixture(fdef).setUserData("Player");
		shape.dispose();
				
		//create foot sensor
		shape = new PolygonShape();
		shape.setAsBox(5 / PPM, 2 / PPM, new Vector2(0, -10/PPM), 0);
		
		//create fixture for foot
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
		fdef.isSensor = true;
		//playerBody.createFixture(fdef).setUserData("foot");
		pBody.createFixture(fdef).setUserData("foot");
				
		//create player
		player = new Player(pBody);
		pBody.setUserData(player);
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
		def.position.set(leftWall.getPosition().x + ((MainGame.V_WIDTH/10)/PPM), (b2dCam.position.y - (MainGame.V_HEIGHT*2)/PPM));

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
		
		ConeLight t;
		t = new ConeLight(handler, 10, Color.GRAY,1000/PPM, body.getPosition().x, body.getPosition().y + 120/PPM, 270, 36);		
		
		obstacleList.add(body);
		lightList.add(t);
	}
	
	public void addRightPlatform()
	{
		//adding platforms to the walls
		BodyDef def = new BodyDef(); 
		def.position.set(rightWall.getPosition().x - ((MainGame.V_WIDTH/10)/PPM), (b2dCam.position.y - (MainGame.V_HEIGHT*2)/PPM));

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
		
		ConeLight t;
		t = new ConeLight(handler, 10, Color.GRAY,1000/PPM, body.getPosition().x, body.getPosition().y + 120/PPM, 270, 36);		
		
		obstacleList.add(body);
		lightList.add(t);
	}
	
	public float getWallPos()
	{
		if(Math.random() > .5){return leftWall.getPosition().x + ((MainGame.V_WIDTH/10)/PPM);}
		else{return rightWall.getPosition().x - ((MainGame.V_WIDTH/10)/PPM);}
	}
	
	public void resize(int width, int height) {
		float aspectRatio;
		if(Gdx.graphics.getHeight() < Gdx.graphics.getWidth()){
			aspectRatio = (float)width / (float)height;
		}
		else{
			aspectRatio = (float)height / (float)width;
		}
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

	@Override
	public void render(float delta) 
	{
		accum += delta;
		while(accum >= STEP){
			accum -= STEP;
			gsm.update(STEP);
			gsm.render();
			MyInput.update();
		}
		
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
	


	



}
