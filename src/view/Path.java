package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Path extends Canvas {

	Image path;
	int h, w;
	double i, j;
	GraphicsContext gc;

	public Path() {
		try {
			path = new Image(new FileInputStream("./Path.png"));
		} catch (FileNotFoundException e) {

		}
	}

	public void setVals(int h, int w, double i, double j) {
		this.h = h;
		this.i = i;
		this.w = w;
		this.j = j;
		gc = getGraphicsContext2D();
		gc.clearRect(0, 0, getWidth(), getHeight());
	}

	public void drawPath(String solution) {
		String[] split = solution.split(",");
		double canvasSizeH = getHeight() / h;
		double canvasSizeW = getWidth() / w;
		gc.drawImage(path, j, i, 90, 90);
		for (String s : split) {
			if (s.equals("Up")) {
				j = j - canvasSizeH;

			}
			if (s.equals("Down")) {
				j = j + canvasSizeH;

			}
			if (s.equals("Left")) {
				i = i - canvasSizeW;

			}
			if (s.equals("Right")) {
				i = i + canvasSizeW;

			}
			gc.drawImage(path, j, i, 90, 90);
		
		}

	}

}
