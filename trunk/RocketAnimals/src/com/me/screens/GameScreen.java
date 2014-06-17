package com.me.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.GameWorld.GameRenderer;
import com.me.GameWorld.GameWorld;
import com.me.helpers.Constants;
import com.me.helpers.InputHandler;

public class GameScreen implements Screen {
	
	private GameWorld world;
	private GameRenderer renderer;
	private float runTime = 0;
	
	public GameScreen() {
	
		
		// Get the screen details
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 136;
		float gameHeight = screenHeight / (screenWidth / gameWidth);
		int midPointY = (int) (gameHeight / 2);
		
		world  = new GameWorld(midPointY);
		Gdx.input.setInputProcessor(new InputHandler(world, world.getRocket()));
		renderer = new GameRenderer(world, (int) gameHeight, midPointY);
	}

	// THIS IS THE MAIN LOOP!!
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		runTime += delta;
		world.update(delta);
		renderer.render(delta, runTime);
	}

	@Override
	public void resize(int width, int height) {
		System.out.println("GameScreen resize");
		System.out.println("RESIZING TO (" + width + ", " + height + ")");
		
		renderer.resize(width, height);
	}

	@Override
	public void show() {
		System.out.println("GameScreen show");
	}

	@Override
	public void hide() {
		System.out.println("GameScreen hide");
	}

	@Override
	public void pause() {
		System.out.println("GameScreen pause");
	}

	@Override
	public void resume() {
		System.out.println("GameScreen resume");
	}

	@Override
	public void dispose() {
		
	}

}
