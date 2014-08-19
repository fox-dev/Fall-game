package Screens;

import static helpers.B2DVars.PPM;
import static helpers.B2DVars.CL;
import objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MainGame;

import handlers.GameScreenManager;
import handlers.MyInput;
import helpers.B2DVars;

public class Menu extends AbstractScreen {
	
	public static final float STEP = 1 / 60f; 
	public float runTime = 0f;
	
	Boolean b = false;
	Boolean c =  false;
	
	float ASPECT_RATIO = (float)MainGame.V_WIDTH/(float)MainGame.V_HEIGHT;
	private Rectangle viewport;
		
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private Player player;
	
	private OrthographicCamera b2dCam;
	
	BitmapFont font;

	public Menu(GameScreenManager gsm) {
		super(gsm);
		
		world = new World(new Vector2(0, 0f), true);
		
		b2dr = new Box2DDebugRenderer();
		
		font = new BitmapFont();
		
		createPlayer();
		
		//set up box2d cam
				b2dCam = new OrthographicCamera();
				b2dCam.setToOrtho(false, MainGame.V_WIDTH/PPM , MainGame.V_HEIGHT/PPM );
		
	}

	public void render(){
		runTime+=1/60f;
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
                (int) viewport.width, (int) viewport.height);

		
		
		world.step(1/60f, 1, 1);
		player.update(1/60f);
		
		player.renderSample(sb);
		
		cam.position.set(
				0,0,
				0
			);
		cam.update();
		
		sb.begin();
		float w = font.getBounds("START").width;
		float h = font.getBounds("START").height;
		font.draw(sb, "START", cam.position.x - w/2 , cam.position.y + h/2);
		sb.end();
		
		player.getBody().setTransform(cam.position.x, cam.position.y  + 100/PPM, 90);
		
		
		System.out.println(runTime);
		System.out.println(player.getRT());
		
		
		if((int)runTime % 2 == 0 ){
			b = true;
		}
		
		if((int)runTime % 4 == 0 ){
			c = true;
		}
		
		
		
		if(b == true){
			 b = false;
		     player.stopping();
		}
		
		if(c == true){
			 c = false;
		     player.falling();
		}
		
		
			
		
		
		
		
	
		
		
		sb.setProjectionMatrix(cam.combined);
		
		
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

	

	public void update(float dt) {
		System.out.println("MENU");
		
		handleInput();
		
		
	}


	
	public void dispose() {
		System.out.println("Disposing!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		Array<Body> temps = new Array<Body>();
		
		world.getBodies(temps);
		
		for(Body b : temps){
			world.destroyBody(b);
			System.out.println(world.getBodyCount() + "-----------------------------------------------------------" + temps.size);
		}
		temps.clear();
		
		world.dispose();
		b2dr.dispose();
		font.dispose();
		
		
	}
	
	public void handleInput(){
		if(MyInput.isPressed(MyInput.BUTTON1)){
			System.out.println("pressed z");
		
			gsm.setScreen(101);
			gsm.set();
			
			//if(cl.isPlayerOnGround()){
				//playerBody.applyForceToCenter(0,200,true);
				//player.getBody().applyForceToCenter(0,200,true);
	
			//}
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
		
		//create fixture for player
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		//fdef.restitution = 1f;
		fdef.friction = 0f;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
		
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
				
		//create player
		player = new Player(pBody);
		pBody.setUserData(player);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
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
		
		
	

}
