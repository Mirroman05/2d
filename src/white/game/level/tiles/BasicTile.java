package white.game.level.tiles;

import white.game.gfx.Screen;
import white.game.level.Level;

public class BasicTile extends Tile{

	protected int tileId;
	protected int tileColour;
	
	public BasicTile(int id, int x, int y, int tileColour, int levelColour) {
		super(id, false, false,levelColour);
		this.tileId = x+y*32;
		this.tileColour= tileColour;
	}

	public void tick(){
		
	}
	
	public void render(Screen screen, Level level, int x, int y) {
		
	screen.render(x, y, tileId, tileColour,0x00,1);
	}

}
