package white.game.level.tiles;

import white.game.gfx.Colours;
import white.game.gfx.Screen;
import white.game.level.Level;



public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID  = new BasicSolidTile(0,0,0,Colours.get(000, -1, -1, -1),0xFF000000);
	public static final Tile STONE = new BasicSolidTile(1,1,0,Colours.get(-1, 333, 444, -1), 0xFF555555);
	public static final Tile GRASS = new BasicTile(2,2,0,Colours.get(-1, 131, 141, -1),0xFF00FF00);
	public static final Tile WATER = new BasicTile(3,3,0,Colours.get(015, 014, -1, -1),0xFF009DE6);
	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	private int levelColour;
	
	public Tile(int id, boolean isSolid, boolean isEmitter, int levelColour){
		this.id=(byte)id;
		if(tiles[id]!=null) throw new RuntimeException("Duplicate tile id on " + id);
		this.solid= isSolid;
		this.emitter = isEmitter;
		this.levelColour = levelColour;
		tiles[id]=this;
		
	}
	
	public byte getId(){
		return id;
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	public boolean isEmitter(){
		return emitter;
	}
	
	public abstract void render(Screen screen, Level level, int x, int y);

	public int getLevelColour() {
		
		return levelColour;
	}


}
