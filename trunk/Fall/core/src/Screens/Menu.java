package Screens;

import static helpers.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MainGame;

import handlers.GameScreenManager;
import handlers.MyInput;

public class Menu extends AbstractScreen {
	
	public static final float STEP = 1 / 60f;
	private float accum;
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private OrthographicCamera b2dCam;
	
	BitmapFont font;

	public Menu(GameScreenManager gsm) {
		super(gsm);
		
		world = new World(new Vector2(0, -9.81f), true);
		
		b2dr = new Box2DDebugRenderer();
		
		font = new BitmapFont();
		
		
		//set up box2d cam
				b2dCam = new OrthographicCamera();
				b2dCam.setToOrtho(false, MainGame.V_WIDTH/PPM , MainGame.V_HEIGHT/PPM );
		
	}

	public void render(){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
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
		
		sb.setProjectionMatrix(cam.combined);
	}
	
	@Override
	public void render(float delta) {
		
	
		
	}
	
	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		
		
		world.step(dt, 1, 1);
		
	}


	
	public void dispose() {
		System.out.println("Disposing!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		world.dispose();
		b2dr.dispose();
		font.dispose();
		//sb.dispose();
		
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
		
		if(MyInput.isPressed(MyInput.BUTTON1)){
			
			gsm.setScreen(101);
			gsm.set();
		}
	}
		
		
	

}
