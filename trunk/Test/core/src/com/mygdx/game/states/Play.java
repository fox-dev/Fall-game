package com.mygdx.game.states;

import static handlers.B2DVars.PPM;

import java.util.Iterator;
import java.util.Random;

import gameobjects.Player;
import handlers.AssetLoader;
import handlers.B2DVars;
import handlers.GameStateManager;






import handlers.MyContactListener;
import handlers.MyInput;
import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.mygdx.main.Game;



public class Play extends GameState{
	private boolean debug = true;
	float x = 0, y = 0, x2 = 0, y2 = 0;
	long lastSprite = 0;
	long lastSprite2 = 0;
	long nextSprite = 0, nextSprite2 = 0;
	
	int lights = 0;
	
	
	//private BitmapFont font = new BitmapFont();
	
	private Array<Body> obstacleList = new Array<Body>();
	private Array<ConeLight> lightList = new Array<ConeLight>();
	private Iterator<Body> iterator;
	private Iterator<ConeLight> iterator2;
	
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private OrthographicCamera b2dCam;
	
	RayHandler handler;
	
	private AssetLoader assets;
	
	//private Body playerBody;
	private Player player;
	
	private Body leftWall;
	private Body rightWall;
	
	private MyContactListener cl;
	
	public Play(GameStateManager gsm){
		super(gsm);
		
		world = new World(new Vector2(0, -9.81f), true);
		
		cl = new MyContactListener();
		world.setContactListener(cl);
		
		b2dr = new Box2DDebugRenderer();
		
		
		//static body - don't move, unaffected by forces (ground)
		//kinematic body - don't get affected by forces (moving platform)
		//dynamic body - always get affected by forces (player)
		
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
		shape.setAsBox(2/PPM, (Game.V_HEIGHT+50/2)/PPM); //half width half height, so 100, 10
		
		fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		//fdef.filter.maskBits = B2DVars.BIT_BOX | B2DVars.BIT_BALL;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		leftWall.createFixture(fdef).setUserData("LeftWall");

		//Right wall
		bdef = new BodyDef(); 
		bdef.position.set((Game.V_WIDTH)/PPM,0+200/PPM);
		bdef.type = BodyType.KinematicBody;
		rightWall = world.createBody(bdef);
		
		shape = new PolygonShape();
		shape.setAsBox(2/PPM, (Game.V_HEIGHT+50/2)/PPM); //half width half height, so 100, 10
		
		fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		//fdef.filter.maskBits = B2DVars.BIT_BOX | B2DVars.BIT_BALL;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		rightWall.createFixture(fdef).setUserData("RightWall");
		
		
		
		//set up box2d cam
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH/PPM , Game.V_HEIGHT/PPM );
		
		
		
		handler = new RayHandler(world);
		handler.setAmbientLight(0.0f, 0.0f, 0.0f,1.0f);
	
		//ConeLight t;
		//t = new ConeLight(handler, 10, Color.CYAN, 100/PPM, body.getPosition().x, body.getPosition().y,270,35);
		
		
		//new PointLight(handler, 50, Color.CYAN, 500/PPM, playerBody.getPosition().x, playerBody.getPosition().y);
		
	}
		
	
	public void handleInput(){
		if(MyInput.isPressed(MyInput.BUTTON1)){
			//System.out.println("pressed z");
			//if(cl.isPlayerOnGround()){
				//playerBody.applyForceToCenter(0,200,true);
				player.getBody().applyForceToCenter(0,200,true);
	
			//}
		}
		if(MyInput.isDown(MyInput.BUTTON1)){
			//Vector2 vel = playerBody.getLinearVelocity();
			Vector2 vel = player.getBody().getLinearVelocity();
		 	vel.y = -1f;
			//playerBody.setLinearVelocity(vel);
		 	player.getBody().setLinearVelocity(vel);
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
		def.position.set(randInt(((Game.V_WIDTH/5)+3),(320-(Game.V_WIDTH/5)-3))/PPM,(b2dCam.position.y - (Game.V_HEIGHT*2)/PPM));

		def.type = BodyType.StaticBody;
		Body body = world.createBody(def);
		body = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((Game.V_WIDTH/5)/PPM, 5/PPM); //half width half height, so 100, 10
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 0.4f;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		body.createFixture(fdef).setUserData("Ground");
	
		ConeLight t;
		t = new ConeLight(handler, 10, Color.GRAY,1000/PPM, body.getPosition().x, body.getPosition().y + 120/PPM, 270, 36);
		//Light.setContactFilter(B2DVars.BIT_BALL, (short) 0, B2DVars.BIT_BALL);
		
		obstacleList.add(body);
		lightList.add(t);
		
		//System.out.println("Y object: " + body.getPosition().y);
		

		
	}
	
	public void addPlatforms()
	{
		addWallPlatform();
	}
	
	public void update(float dt){
		//System.out.println("Wally: "+ leftWall.getPosition().y);
		//System.out.println("Playery: "+ player.getposition().y);
		//System.out.println("Difference: " +  (leftWall.getPosition().y - player.getposition().y));
		iterator = obstacleList.iterator();
		iterator2 = lightList.iterator();
		while(iterator.hasNext()){
			Body o = iterator.next();
			ConeLight s = iterator2.next();
			if(o.getPosition().y > b2dCam.position.y + Game.V_HEIGHT/PPM){
				world.destroyBody(o);
				s.setActive(false);
				iterator.remove();
				iterator2.remove();
				handler.lightList.removeValue(s, true);
				lights--;
				
			}
			
		}
		
		
		long now = System.currentTimeMillis(); // or some other function to get the current time
		long now2 = System.currentTimeMillis();
		
		if(now - lastSprite >= nextSprite)
		{
			addObstacles();
			lastSprite = now;
			nextSprite = (long) nextSprite();
		}
		
		if(now2 - lastSprite2 >= nextSprite2)
		{
			addPlatforms();
			lastSprite2 = now2;
			nextSprite2 = (long) nextSprite();
		}
		
		 /*if (now - lastSprite > 1000) {
			 
			 x = player.getposition().y;
			  
			  
			System.out.println("x: " + x);
		    lastSprite = now;
		 }
		  
		  if (now - lastSprite2 > 2000) {
				
			 y = player.getposition().y;
			  
			System.out.println("y: " + y);
		    lastSprite2 = now2;
		  }
		  
		  if(Math.abs(x-y)/PPM > 3/PPM && (x != 0f) && (y != 0f)){
			  if(Math.random() > .5)
				  addObstacles();
			  if(Math.random() > .3)
				  addPlatforms();
			  
			  //lastSprite2 = now2;
			  System.out.println("Abs: " + Math.abs(x-y));
			  x = 0f;
			  y = 0f;
		  }*/
		  
		handleInput();
		if(Math.abs(player.getBody().getLinearVelocity().y) > 10f){
		//playerBody.applyForceToCenter(0,200,true);
			Vector2 vel = player.getBody().getLinearVelocity();
			vel.y = -10f;
			player.getBody().setLinearVelocity(vel);
			
		}
		
		
		
		handler.render();
		//System.out.println("Vp: " + player.getBody().getLinearVelocity().y);
		
		leftWall.setTransform(0, player.getBody().getPosition().y, 0);
		leftWall.setLinearVelocity(player.getBody().getLinearVelocity());
		rightWall.setTransform((Game.V_WIDTH)/PPM, player.getBody().getPosition().y, 0);
		rightWall.setLinearVelocity(player.getBody().getLinearVelocity());
		
		
		//System.out.println("Size: " + handler.lightList.size);
		
		world.step(dt, 1, 1);
		
		player.update(dt);

	
	}
	
	public void render(){
		//clear screens
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		/*
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		font.draw(sb, "play state", 100, 100);
		sb.end();
		*/
		
		cam.position.set(
				player.getposition().x * PPM,
				(player.getposition().y) * PPM - 100,
				0
			);
		cam.update();
		
		b2dCam.position.set(
				player.getposition().x,player.getposition().y - 100/PPM

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
		shape.setAsBox(20/PPM, 20/PPM);
		
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
		shape.setAsBox(5 / PPM, 2 / PPM, new Vector2(0, -20/PPM), 0);
		
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
	
	public void addWallPlatform()
	{
		//adding platforms to the walls
		BodyDef def = new BodyDef(); 
		def.position.set(getWallPos(), (b2dCam.position.y - (Game.V_HEIGHT*2)/PPM));

		def.type = BodyType.StaticBody;
		Body body = world.createBody(def);
				
		PolygonShape shape2 = new PolygonShape();
		shape2.setAsBox((Game.V_WIDTH/8)/PPM, 5/PPM); //half width half height, so 100, 10
				
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
		if(Math.random() > .5){return leftWall.getPosition().x + ((Game.V_WIDTH/8)/PPM) / 2;}
		else{return rightWall.getPosition().x - ((Game.V_WIDTH/8)/PPM) / 2;}
	}
	
	public float nextSprite()
	{
		return randInt(2000,4000);
	}
		
}
