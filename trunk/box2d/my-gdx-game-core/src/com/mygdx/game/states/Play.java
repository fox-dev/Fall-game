package com.mygdx.game.states;

import static handlers.B2DVars.PPM;

import java.util.Iterator;
import java.util.Random;

import handlers.B2DVars;
import handlers.GameStateManager;






import handlers.MyContactListener;
import handlers.MyInput;
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
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.mygdx.main.Game;



public class Play extends GameState{
	private boolean debug = true;
	
	
	//private BitmapFont font = new BitmapFont();
	
	private Array<Body> obstacleList = new Array<Body>();
	private Array<PointLight> lightList = new Array<PointLight>();
	private Iterator<Body> iterator;
	private Iterator<PointLight> iterator2;
	
	long lastSprite = 0;
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private OrthographicCamera b2dCam;
	
	RayHandler handler;
	
	private Body playerBody;
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
		
		
		
		//create player
		bdef.position.set(160/PPM, 300/PPM);
		bdef.type = BodyType.DynamicBody;
		playerBody = world.createBody(bdef);
		
		shape.setAsBox(5/PPM, 5/PPM);
		fdef.shape = shape;
		//fdef.restitution = 1f;]
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
		playerBody.createFixture(fdef).setUserData("Player");
		
		
		
		/*
		//create ball
		bdef.position.set(153/PPM, 220/PPM);
		body = world.createBody(bdef);
		
		
		CircleShape cshape = new CircleShape();
		cshape.setRadius(5/PPM);
		fdef.shape = cshape;
		//fdef.restitution = 0.2f;
		fdef.filter.categoryBits = B2DVars.BIT_BALL;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
		body.createFixture(fdef).setUserData("Ball");
		*/
		
		
		//create foot sensor
		shape.setAsBox(2 / PPM, 2 / PPM, new Vector2(0, -5/PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
		fdef.isSensor = true;
		playerBody.createFixture(fdef).setUserData("foot");
		
		
		
		//set up box2d cam
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
		
		
		
		handler = new RayHandler(world);
		handler.setCombinedMatrix(b2dCam.combined);
		
		//new PointLight(handler, 50, Color.CYAN, 500/PPM, playerBody.getPosition().x, playerBody.getPosition().y);
		
		
	
		
	}
		
	
	public void handleInput(){
		if(MyInput.isPressed(MyInput.BUTTON1)){
			//System.out.println("pressed z");
			if(cl.isPlayerOnGround()){
				playerBody.applyForceToCenter(0,200,true);
			}
		}
		
		if(MyInput.isDown(MyInput.BUTTON2)){
			//System.out.println("hold x");
			Vector2 vel = playerBody.getLinearVelocity();
			vel.x = -1f;
			playerBody.setLinearVelocity(vel);
			
		}
		if(MyInput.isDown(MyInput.BUTTON3)){
			//System.out.println("hold c");
			Vector2 vel = playerBody.getLinearVelocity();
			vel.x = 1f;
			playerBody.setLinearVelocity(vel);
			
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
		def.position.set(randInt(0,300)/PPM,(cam.position.y - (Game.V_HEIGHT*2)/PPM));
		def.type = BodyType.KinematicBody;
		Body body = world.createBody(def);
		body = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50/PPM, 5/PPM); //half width half height, so 100, 10
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		//fdef.filter.maskBits = B2DVars.BIT_BOX | B2DVars.BIT_BALL;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		body.createFixture(fdef).setUserData("Ground");
		body.setLinearVelocity(0f,0.2f);
		PointLight t = new PointLight(handler, 50, Color.CYAN, 200/PPM, body.getPosition().x, body.getPosition().y);
		
		lightList.add(t);
		obstacleList.add(body);
		
		//System.out.println("Y object: " + body.getPosition().y);
		
		
		
		
		
	}
	
	public void update(float dt){
		iterator = obstacleList.iterator();
		iterator2 = lightList.iterator();
		while(iterator.hasNext()){
			Body o = iterator.next();
			PointLight s = iterator2.next();
			if(o.getPosition().y > playerBody.getPosition().y + Game.V_HEIGHT/PPM){
				world.destroyBody(o);
			
				iterator.remove();
				iterator2.remove();
			}
			
			s.setPosition(o.getPosition());
			
			
			
		}
		
		long now = System.currentTimeMillis(); // or some other function to get the current time
		  if (now - lastSprite > 1000) {
			  addObstacles();
		    lastSprite = now;
		  }
		  
		handleInput();
		if(Math.abs(playerBody.getLinearVelocity().y) > 5f){
		//playerBody.applyForceToCenter(0,200,true);
			Vector2 vel = playerBody.getLinearVelocity();
			vel.y = -6f;
			playerBody.setLinearVelocity(vel);
		}
		
		
		world.step(dt, 6, 2);
		
		
		

	
	}
	
	public void render(){
		//clear screens
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		/*
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		font.draw(sb, "play state", 100, 100);
		sb.end();
		*/
		
		cam.position.set(playerBody.getPosition().x,
				playerBody.getPosition().y
				,
				0);
		cam.update();
		
		b2dCam.position.set(
				playerBody.getPosition().x,playerBody.getPosition().y - 100/PPM
				,
				0
			);
		b2dCam.update();
			
		sb.setProjectionMatrix(cam.combined);
		sb.setProjectionMatrix(hudCam.combined);
		
		handler.setCombinedMatrix(b2dCam.combined);
			
		
		//System.out.println("x: " + cam.position.x/PPM);
		//System.out.println("y: " + cam.position.y*100);
		System.out.println(playerBody.getLinearVelocity().y);
		
		
		handler.updateAndRender();
		
		// draw box2d
		if(debug) {
			b2dr.render(world, b2dCam.combined);
		}
		
	}
	
	public void dispose(){}
		
}
