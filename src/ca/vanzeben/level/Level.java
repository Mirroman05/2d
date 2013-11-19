package ca.vanzeben.level;

import ca.vanzeben.game.gfx.Colours;
import ca.vanzeben.game.gfx.Screen;
import ca.vanzeben.level.tiles.Tile;

public class Level {
    
	private byte[]tiles;
	public int width;
	public int height;
	
	public Level(int width, int height){
		tiles = new byte[width*height];
		this.width = width;
		this.height = height;
		this.generateLevel();
	}
	
	public void generateLevel(){
		for(int y=0;y<height;y++){
        	for(int x=0;x<width;x++){
        		if(x*y%10<5){
        		tiles[x+y*width]=Tile.GRASS.getId();
        		}else{
        			tiles[x+y*width]=Tile.STONE.getId();
        		}
        	}
        }
	}
	
	public void tick(){
		
	}
	
	public void RenderTiles(Screen screen, int xOffset, int yOffset){
		if(xOffset<0)xOffset=0;
		if(xOffset>((width<<3)-screen.width))xOffset =((width<<3)-screen.width);
		if(yOffset<0)yOffset=0;
		if(yOffset>((height<<3)-screen.height))yOffset =((height<<3)-screen.height);
		
		screen.setOffset(xOffset, yOffset);
		
		for(int y = 0;y<height;y++){
			for(int x = 0;x<width;x++){
				getTile(x,y).render(screen,this,x<<3,y<<3);
			}
		}
		
		
	}

	private Tile getTile(int x, int y) {
		if(x<0||x>width||y<0||y>height) return Tile.VOID;
		return Tile.tiles[tiles[x+y*width]];
	}
}
