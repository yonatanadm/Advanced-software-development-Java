package view;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Aircraft extends Canvas implements Observer {

	Image aircraftImage;
	Point currentPosition;
	double x;
	double y;
	double pixelSize;
	int[] mapSize;
	DoubleProperty longitude, latitude;
	int mapH;
	int mapW;
	double nx;
	double nxlong;
	double min;
	double max;
	double minlong;
	double maxlong;

	public Aircraft() {

		currentPosition = new Point();
		try {
			aircraftImage = new Image(new FileInputStream("./download.png"));
		} catch (IOException e) {
		}
		longitude = new SimpleDoubleProperty();
		latitude = new SimpleDoubleProperty();

	}

	public void setAircraft(){
        y=21;
        x=157;
        min = 0.325281;
        max=0.99;      
        minlong = -0.942627;
        maxlong = -0.3;
		GraphicsContext gca = getGraphicsContext2D();
		gca.drawImage(aircraftImage, 0,0, 40, 40);
    }

	public void position() 
	{
		GraphicsContext gca = getGraphicsContext2D();
		gca.clearRect(0, 0, getWidth(), getHeight());
		gca.drawImage(aircraftImage, ((((longitude.doubleValue() + x)-minlong)*300) /(maxlong-minlong)), ((((latitude.doubleValue() - y)-min) *300) /(max-min)), 30, 30);
		 nxlong= ((((longitude.doubleValue() + x)-minlong)*300) /(maxlong-minlong));
		 nx= ((((latitude.doubleValue() - y)-min) *300) /(max-min));	
	}
	@Override
	public void update(Observable o, Object arg) {

	}

}
