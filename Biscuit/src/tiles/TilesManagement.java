package tiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import main.GamePanel;

public class TilesManagement {

	private GamePanel gamePanel;
	private ArrayList<Tile> tiles = new ArrayList<>();
	private int[][] mapTiles;
	
	public TilesManagement(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		mapTiles = new int[gamePanel.getMaxWorldCol()][gamePanel.getMaxWorldRow()];

		getTileImage();
		loadMap("/maps/testMap.txt");
	}

	private void getTileImage() {
		try {
			for(TileType type : TileType.values()) {
				tiles.add(new Tile(type));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Tile getTile(int x, int y) {
		return tiles.get(mapTiles[x][y]);
	}
	
	public void loadMap(String filePath) {
		
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gamePanel.getMaxWorldCol() && row < gamePanel.getMaxWorldRow()) {
				
				String line = br.readLine();
				String[] numbers = line.split(" ");
				
				while(col < gamePanel.getMaxWorldCol()) {
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTiles[col][row] = num;
					col++;
				}
				
				col = 0;
				row++;
			}
			
			br.close();
			
		} catch(Exception e) {
			
		}
	}
	
	public void draw(Graphics2D graphics2d) {
		
		for(Tile tile : tiles) {
			tile.updateSpriteCounter();
		}
		
		int col = 0;
		int row = 0;
		
		while(col < gamePanel.getMaxWorldCol() && row < gamePanel.getMaxWorldRow()) {
			
			int worldX = col*gamePanel.getTileSize();
			int worldY = row*gamePanel.getTileSize();
			int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
			int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();
			
			if(!(screenX < -gamePanel.getTileSize() || screenY < -gamePanel.getTileSize() || screenX > gamePanel.getWidth() || screenY > gamePanel.getHeight())) {
				Tile tile = getTile(col, row);
				graphics2d.drawImage(tile.getImage(), screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
				if(gamePanel.getKeyHandler().isDebugActif()) {
					
					if(tile.isCollision()) {
						graphics2d.setColor(new Color(255, 50, 50, 75));
						graphics2d.fillRect(screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize());
					}
				}
			}
			
			col++;
			if(col == gamePanel.getMaxWorldCol()) {
				col = 0;
				row++;
			}
		}
	}
}