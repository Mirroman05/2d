package white.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import white.game.entities.Antiplayer;
import white.game.entities.Player;
import white.game.gfx.Colours;
import white.game.gfx.Font;
import white.game.gfx.Screen;
import white.game.gfx.SpriteSheet;
import white.game.level.Level;

public class Game extends Canvas implements Runnable{

    
    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 160;
    public static final int HEIGHT = WIDTH/12*9;
    public static final int SCALE = 3;
    public static final String NAME="Game";
    
    private JFrame frame;
    
    public boolean running = false;
    public int tickCount = 0;
    
    private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private int[] colours = new int[6*6*6];
    
    private Screen screen;
    public InputHandler input;
    public Level level;
    public Player player;
    public Antiplayer villian;
    
    public Game(){
        setMinimumSize(new Dimension(WIDTH* SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH* SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH* SCALE, HEIGHT * SCALE));
        
        frame = new JFrame(NAME);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void init(){
    	int index = 0;
    	for (int r=0;r<6;r++){
    		for (int g=0;g<6;g++){
    			for (int b=0;b<6;b++){
    				int rr=(r*255/5);
    				int gg=(g*255/5);
    				int bb=(b*255/5);
    				
    				colours[index++]=rr<<16 | gg<<8|bb;
    			}
    		}
    	}
    	
    	screen = new Screen(WIDTH,HEIGHT, new SpriteSheet("/sprite_sheet.png"));
    	input = new InputHandler(this);
    	level = new Level("/levels/newTest.png");
    	player = new Player(level, 0,0,input);
    	villian = new Antiplayer(level,10,10,Colours.get(-1, 000, 500, 555));
    	level.addEntity(player);
    	level.addEntity(villian);
    	
    	
    }
    
    
    public synchronized void start() {
        running = true;
        new Thread(this).start();    
    }
    
    public synchronized void stop() {
        running = false;
    }
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D/60D;
        
        int ticks=0;
        int frames=0;
        
        long lastTimer=System.currentTimeMillis();
        double delta=0;
        
        init();
        
        while(running){
            long now = System.nanoTime();
            delta +=(now-lastTime)/nsPerTick;
            lastTime=now;
            boolean shouldRender=true;
            
            while(delta >= 1){
                ticks++;
                tick();
                delta-=1;
                shouldRender=true;
            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(shouldRender){
                frames++;
                render();
            }
            if(System.currentTimeMillis()-lastTimer >= 1000){
                lastTimer+=1000;
               // System.out.println(ticks+","+frames);
                frames =0;
                ticks=0;
            }
        }
        
    }
    
   
    
    public void tick(){
     tickCount++;
     
    
     
     level.tick();
    }
    
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs==null){
            createBufferStrategy(3);
            return;
        }
        int xOffset = player.x - (screen.width/2);
        int yOffset = player.y - (screen.height/2);
        
        level.RenderTiles(screen, xOffset, yOffset);
        
        for(int x=0; x<level.width;x++){
        	int colour = Colours.get(-1, -1, -1, 000);
        	if(x%10 == 0 && x!=0){
        	  colour = Colours.get(-1, -1, -1, 500);
        	}
        }
       
        level.renderEntities(screen);
        
        for(int y=0;y<screen.height;y++){
        	for(int x=0;x<screen.width;x++){
        		int colourCode = screen.pixels[x+y*screen.width];
        		if(colourCode<255)pixels[x+y*WIDTH]= colours[colourCode];
        	}
        	
        }
        Graphics g = bs.getDrawGraphics();
        
        //g.setColor(Color.BLACK);
       // g.fillRect(0, 0, getWidth(), getHeight());
        
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        
        g.dispose();
        bs.show();
    }

    public static void main(String[] args){
        new Game().start();
    }

    
    
}
