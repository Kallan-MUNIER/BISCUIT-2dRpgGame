package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import entities.Player;
import objects.SuperObject;
import sounds.Sound;
import tiles.TilesManagement;
import time.TimeController;
import time.TimeState;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	
	// Paramètre de l'écran
	private final int originalTileSize = 32; // Taille en pixel d'une case
	private final int scale = 3;
	
	private final int tileSize = originalTileSize * scale; // Taille d'une case: 96xpx x 48==96px
	private final int maxScreenCol = 16;
	private final int maxScreenRow = 12;
	
	private final int screenWidth = tileSize*maxScreenCol; // 768px
	private final int screenHeight = tileSize*maxScreenRow; // 576px
	
	private final int maxWorldCol = 21;
	private final int maxWorldRow = 19;
	private final int worldWidth = tileSize*maxWorldCol;
	private final int worldHeight = tileSize*maxWorldRow;
	
	private int fps = 60;
	
	private TilesManagement tilesManagement = new TilesManagement(this);
	private KeyHandler keyHandler = new KeyHandler();
	private Sound sound = new Sound();
	private Thread gameThread;
	private CollisionChecker collisionChecker = new CollisionChecker(this);
	private Player player = new Player(this, keyHandler);
	private AssetSetter assetSetter = new AssetSetter(this);
	private ArrayList<SuperObject> objects = new ArrayList<>();
	private TimeController timeController = new TimeController(TimeState.NIGHT_TO_DAY, fps*60*8, fps*60*8, fps*60*2, fps*60*2);
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		
		this.addKeyListener(keyHandler);
	}
	
	public void setupGame() {
		assetSetter.setObject();
		playMusic();
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				timer = 0;
				drawCount = 0;
			}
		}
	}
	
	public void update() {
		player.update();
	}
	
	public void playMusic() {
		sound.setFile(0);
		sound.play();
		sound.loop();
	}
	
	public void stopMusic() {
		sound.stop();
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		Graphics2D graphics2d = (Graphics2D) graphics;
		
		// Affiche la map
		tilesManagement.draw(graphics2d);
		
		// Affiche les objets
		for(SuperObject object : objects) {
			object.draw(graphics2d);
		}
		
		// Affiche le joueur
		player.draw(graphics2d);
		
		// Affiche le temps
		timeController.draw(graphics2d, this);
		
		Font font = new Font("Serif", Font.PLAIN, 16);
		 
		graphics2d.setFont(font);
		graphics2d.setColor(Color.WHITE);
		
		if(keyHandler.isDebugActif()) {
			graphics2d.drawString("Click 'A' for remove debug", 10, 20);
		}
		else {
			graphics2d.drawString("Click 'A' for debug", 10, 20);
		}
		
		graphics2d.dispose();
	}

	public int getTileSize() {
		return tileSize;
	}

	public int getMaxScreenCol() {
		return maxScreenCol;
	}

	public int getMaxScreenRow() {
		return maxScreenRow;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public int getMaxWorldCol() {
		return maxWorldCol;
	}

	public int getMaxWorldRow() {
		return maxWorldRow;
	}

	public int getWorldWidth() {
		return worldWidth;
	}

	public int getWorldHeight() {
		return worldHeight;
	}

	public Player getPlayer() {
		return player;
	}

	public CollisionChecker getCollisionChecker() {
		return collisionChecker;
	}

	public int getOriginalTileSize() {
		return originalTileSize;
	}

	public TilesManagement getTileManager() {
		return tilesManagement;
	}

	public AssetSetter getAssetSetter() {
		return assetSetter;
	}

	public ArrayList<SuperObject> getObjects() {
		return objects;
	}

	public KeyHandler getKeyHandler() {
		return keyHandler;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getScale() {
		return scale;
	}

	public Sound getSound() {
		return sound;
	}
}