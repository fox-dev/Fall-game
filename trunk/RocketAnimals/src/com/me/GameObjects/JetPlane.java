package com.me.GameObjects;

import com.badlogic.gdx.math.Polygon;
import com.me.helpers.AssetLoader;
import com.me.helpers.Constants.DIRECTION;


public class JetPlane extends AbstractObstacle
{
	private final float[] jetPlane_polygon_right = {
			0,		47,
			23,		53,
			80, 	52,
			82, 	56,
			101, 	57,
			104, 	53,
			137, 	53,
			157, 	41,
			136, 	24,
			43, 	26,
			16, 	0,
			5,		0,
			13, 	35,
			1, 		40,
	};
	
	private final float[] jetPlane_polygon_left = {
			158,	47,
			135,	53,
			78, 	52,
			76, 	56,
			57, 	57,
			54, 	53,
			21, 	53,
			1, 		41,
			22, 	24,
			115, 	26,
			142, 	0,
			153,	0,
	        145, 	35,
			157, 	40,
	};
	
	protected boolean isSpawned;
	
	public JetPlane(float x, float y, int width, int height, float scrollSpeed, float glideSpeed, DIRECTION direction) 
	{
		super(x, y, width, height, scrollSpeed, glideSpeed, direction);
		
		collisionPoly = new Polygon();
		
		polygonRightVertices = jetPlane_polygon_right;
		polygonLeftVertices = jetPlane_polygon_left;
		
		// Face the hitbox according to direction
		if (direction == DIRECTION.DOWN_LEFT) {
			collisionPoly.setVertices(jetPlane_polygon_left);
		}
		else {
			collisionPoly.setVertices(jetPlane_polygon_right);
		}

		collisionPoly.setScale((float)((double)width / AssetLoader.jetPlane.getRegionWidth()), (float)((double)height / AssetLoader.jetPlane.getRegionHeight()));
	}
	
}
