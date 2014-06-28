package com.mygdx.game.states;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import handlers.Animation;

public class Menu extends GameState
{
	private Background bg;
	private Animation animation;
	private GameButton playButton;
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	public Menu()
	{
		
	}
}
