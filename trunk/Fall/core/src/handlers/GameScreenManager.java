package handlers;

import java.util.Stack;

import Screens.AbstractScreen;
import Screens.GameScreen;


import Screens.Menu;

import com.mygdx.game.MainGame;



public class GameScreenManager {
	
	private MainGame game;
	
	private Stack<AbstractScreen> gameScreens;
	
	public static final int playScreen = 101;
	public static final int menu = 100;
	GameScreen g;
	Menu m;

	public GameScreenManager(MainGame game) {
		this.game = game;
		gameScreens = new Stack<AbstractScreen>();
		pushScreen(playScreen);
	}
	
	public MainGame game(){return game;}
	
	public void update(float dt){
	
		
			gameScreens.peek().update(dt);
		
	}
	
	public void render(){
		gameScreens.peek().render();
	}
	
	private AbstractScreen getScreen(int screen){
		if(screen == playScreen){
			g = new GameScreen(this);
			//game.setScreen(g);
			return g;
		}
		if(screen == menu){
			m = new Menu(this);
			//game.setScreen(m);
			return m;
		}
		return null;
	}
	
	public void setScreen(int screen){
		popScreen();
		pushScreen(screen);
	}
	
	public void popScreen(){
		
			AbstractScreen g = gameScreens.pop();
			g.dispose();
			
	}
	
	public void pushScreen(int screen){
		gameScreens.push(getScreen(screen));
	}
	
	public AbstractScreen getScreen()
	{
		return gameScreens.peek();
	}
	
	public void set(){
		
		game.setScreen(gameScreens.peek());
		
	}
	
	
	
	
	

}
