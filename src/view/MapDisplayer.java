package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


public class MapDisplayer extends Canvas {

	int[][] map;
	int max;
	int min;
	Image plane;
	Image des;
	int xDes;
	int yDes;
	int airplaneX;
	int airPlaneY;
	double w;
	double h;
	double mapH;
	double mapW;
	public boolean flag = false;
	MainWindowController m;
	Image aircraft;

	public MapDisplayer() {
		try {
			aircraft = new Image(new FileInputStream("./download.png"));
		} catch (IOException e) {
			plane = null;

		}
	}

	public void putPlaneIcon() {
		setOnMouseClicked((e) -> {
			setPlanePosition(e.getX(), e.getY());
		});
	}

	public void setPlanePosition(double x, double y) {

		if (!flag) {
			try {
				plane = new Image(new FileInputStream("./destination.png"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			GraphicsContext gc = getGraphicsContext2D();
			gc.drawImage(plane, x, y, 15, 15);
			yDes = (int) Math.ceil((y) / h) - 1;
			xDes = (int) Math.ceil((x) / w) - 1;
			flag = true;
		} else {
			
			drawMap();
			try {
				plane = new Image(new FileInputStream("./destination.png"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			GraphicsContext gc = getGraphicsContext2D();
			gc.drawImage(plane, x, y, 15, 15);
			yDes = (int) Math.ceil((y) / h) - 1;
			xDes = (int) Math.ceil((x) / w) - 1;
			flag = true;
		}
		System.out.println("  X   " + x + "  Y " + y);

	}

	public void setMax(int max) {
		this.max = max;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}

	public void drawMap() {
		if (map != null) {
			mapH = getHeight();
			mapW = getWidth();
			w = mapW / map[1].length;
			h = mapH / map.length;
			
			GraphicsContext g = getGraphicsContext2D();
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					int x = (map[i][j] - this.min) * 255;
					int y = this.max - this.min;
					int val = (x / y);

					g.setFill(Color.rgb(255 - val, val, 0));
					g.fillRect(j * w, i * h, w, h);

				}
			}

		}
	}
}

