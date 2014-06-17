package com.me.helpers;

// Please define all your constant stuff here

public class Constants {
	
	// Debug flags
	public static final boolean DRAW_BOUNDS = true; // Draw collision detection shapes
	
	// General game constants
	public static final int TRUE_WIDTH = 320; // Game's "true" width = 320px
	public static final int TRUE_HEIGHT = 480; // Game's "true" height = 480px
	public static final float ASPECT_RATIO = (float)TRUE_WIDTH / (float)TRUE_HEIGHT;
	
	public enum DIRECTION {
		DOWN, DOWN_LEFT, DOWN_RIGHT
	}
	
	// General obstacle constants
	public static final int OBSTACLE_MAX_SPEED_X = 400;
	public static final int OBSTACLE_MAX_SPEED_Y = 400; // Never exceed this speed going down
	public static final int OBSTACLE_MAX_GLIDE_SPEED_Y = 80;
	
	// Player constants
	public static final int ROCKET_VELOCITY = 200;
	public static final int ROCKET_WIDTH = 30;
	public static final int ROCKET_HEIGHT = 30;
	public static final int ROCKET_STARTING_X = (TRUE_WIDTH / 2) - (ROCKET_WIDTH / 2); // Half of screen, adjust for rocket width
	public static final int ROCKET_STARTING_Y = TRUE_HEIGHT - ROCKET_HEIGHT; // Assuming we'll always start at the bottom of screen
	public static final float ROCKET_LIFTOFF_STOP_AT_Y = ((5 * TRUE_HEIGHT) / 6); // Stop with 5/6 of the screen left on top
	
	// Rocket Fire constants
	public static final int ROCKET_FIRE_WIDTH = ROCKET_WIDTH; // Should be same width?
	public static final int ROCKET_FIRE_HEIGHT = 30;
	
	// Meteor constants
	public static final int METEOR_MIN_SPEED_Y = 150;
	public static final int METEOR_MAX_SPEED_Y = 225;
	public static final int METEOR_MAX_ROTATE = 15;
	
	
	// Plane constants
	public static final int PLANE_MIN_X_SPEED = 100;
	public static final int PLANE_MAX_X_SPEED = 200;
	public static final int PLANE_MIN_Y_SPEED = 100;
	public static final int PLANE_MAX_Y_SPEED = 150;
	
	// Balloon constants
	public static final int BALLOON_MIN_X_SPEED = 25;
	public static final int BALLOON_MAX_X_SPEED = 100;
	public static final int BALLOON_MIN_Y_SPEED = 100;
	public static final int BALLOON_MAX_Y_SPEED = 300;
	
	
}
