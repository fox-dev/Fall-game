package handlers;

import helpers.AssetLoader;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {
	
	private boolean playerOnGround;
	private boolean doubleCheck;
	
	//called when two fixtures start to collide
	public void beginContact(Contact c){
		
		Fixture fa = c.getFixtureA(); //Player or Foot
		Fixture fb = c.getFixtureB(); //Ground, RightWall, LeftWall, Wall Platform
		
		System.out.println(fa.getUserData() + ", " + fb.getUserData());
		
		if(fa.getUserData() != null && fa.getUserData().equals("footLeft") && fb.getUserData() != null && fb.getUserData().equals("Ground")){
			doubleCheck = true;
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("footRight") && fb.getUserData() != null && fb.getUserData().equals("Ground")){
			doubleCheck = true;
		}
		if(fa.getUserData() != null && fa.getUserData().equals("foot")  && fb.getUserData() != null && fb.getUserData().equals("Ground")){
				AssetLoader.hit.play();
				playerOnGround = true;
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot")  && fb.getUserData() != null && fb.getUserData().equals("Wall Platform")){
				AssetLoader.hit.play();
				playerOnGround = true;
			
		}
		
		
		
		
		
		
	}
	
	//called when two fixtures no longer collide
	public void endContact(Contact c){
		
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		//System.out.println("End Contact");
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot")){
			playerOnGround = false;
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("footLeft")){
			
			doubleCheck = false;
			playerOnGround = false;
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("footRight")){
			
			doubleCheck = false;
			playerOnGround = false;
		}
	}
	
	public boolean isPlayerOnGround(){return playerOnGround;}
	public boolean isDoubleCheck(){return doubleCheck;}
	
	
	
	//Collision detection
	//presolve (can change the way its handled
	//Collision handling
	//postsolve
	public void preSolve(Contact c, Manifold m){}
	public void postSolve(Contact c, ContactImpulse ci){}
	

}
