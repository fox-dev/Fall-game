package helpers;

public class B2DVars {
	
	//True Width and Height
	public static final float TRUE_WIDTH = 320;
	public static final float TRUE_HEIGHT = 480;
	
	//pixel per meter ration
	public static final float PPM = 100;
	
	//Camera location for player
	public static final float CL = 170;
	
	//category bits, use even bits
	public static final short BIT_GROUND = 2; //0000 0000 0000 0010
	public static final short BIT_PLAYER = 4; // 0000 0000 0000 0100
	public static final short BIT_BALL = 8; //0000 0000 0000 1000
	public static final short BIT_LIGHT = 10;
	
	public static final short IGNORED = 0x001;
	
	
}