package com.me.GameObjects;

import com.badlogic.gdx.math.Polygon;
import com.me.helpers.AssetLoader;
import com.me.helpers.Constants;

public class HotAirBalloon extends AbstractObstacle{
	
	private final float[] hotAirBalloon_polygon = {
			23, 	138,
			23, 	119,
			26, 	99,
			1, 		48,
			0, 		35,
			1, 		30,
			30, 	1,
			41, 	0,
			52, 	1,
			82, 	30,
			83, 	35,
			81, 	48,
			56, 	99,
			59, 	119,
			59, 	138,
	};
	
	public HotAirBalloon(float x, float y, int width, int height,
			float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
			
	}
	
	public HotAirBalloon(float x, float y, int width, int height,
			float scrollSpeed, float xSpeed, Constants.DIRECTION direction) {
		super(x, y, width, height, scrollSpeed, xSpeed, direction);
		
		collisionPoly = new Polygon();
		
		polygonLeftVertices = hotAirBalloon_polygon;
		polygonRightVertices = hotAirBalloon_polygon;
		
		collisionPoly.setVertices(hotAirBalloon_polygon);
		collisionPoly.setScale((float)((double)width / AssetLoader.hotAirBalloon.getRegionWidth()), (float)((double)height / AssetLoader.hotAirBalloon.getRegionHeight()));
	}
}
