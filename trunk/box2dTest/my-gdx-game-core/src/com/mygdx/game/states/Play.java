package com.mygdx.game.states;

import static handlers.B2DVars.PPM;
import handlers.B2DVars;
import handlers.GameStateManager;






import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.main.Game;



public class Play extends GameState{
	
	//private BitmapFont font = new BitmapFont();
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private OrthographicCamera b2dCam;
	
	public Play(GameStateManager gsm){
		super(gsm);
		
		world = new World(new Vector2(0, -9.81f), true);
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
		fdef.filter.maskBits = B2DVars.BIT_BOX | B2DVars.BIT_BALL;
		body.createFixture(fdef);
		
		//create falling box
		bdef.position.set(160/PPM, 200/PPM);
		bdef.type = BodyType.DynamicBody;
		body = world.createBody(bdef);
		
		shape.setAsBox(5/PPM, 5/PPM);
		fdef.shape = shape;
		//fdef.restitution = 1f;]
		fdef.filter.categoryBits = B2DVars.BIT_BOX;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
		body.createFixture(fdef);
		
		
		//create ball
		bdef.position.set(153/PPM, 220/PPM);
		body = world.createBody(bdef);
		
		
		CircleShape cshape = new CircleShape();
		cshape.setRadius(5/PPM);
		fdef.shape = cshape;
		fdef.filter.categoryBits = B2DVars.BIT_BALL;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
		body.createFixture(fdef);
		
		//set up box2d cam
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
		
		
		
	
		
	}
		
	
	public void handleInput(){}
	
	public void update(float dt){
		
		world.step(dt, 6, 2);
	}
	
	public void render(){
		/*
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		font.draw(sb, "play state", 100, 100);
		sb.end();
		*/
		
		//clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//draw box2d world
		b2dr.render(world, b2dCam.combined);
	}
	
	public void dispose(){}
		
}
