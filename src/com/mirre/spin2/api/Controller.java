package com.mirre.spin2.api;

import java.io.IOException;
import java.io.InputStream;
import com.mirre.spin2.SpinToWinGame;
import com.mirre.spin2.api.FileLocation.LocationAnnonationParser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;


public abstract class Controller {
	
	private static final Class<?> MAIN_CLASS = SpinToWinGame.class;
	private String imagesLocation = "images/";
	
	public Controller(){}
	
	public abstract void initialize();
	
	public static <T> T loadController(Class<?> controllerClass, Class<T> returnType){
		String data = LocationAnnonationParser.parse(controllerClass);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MAIN_CLASS.getResource(data));
			return loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Image loadImage(String imageName){
		Image image = null;
		try {
			InputStream stream = this.getClass()
					.getResource(imagesLocation + imageName)
					.openStream();
			image = new Image(stream);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(image != null){
			return image;
		}
		throw new NullPointerException("No image found.. or other issues.");
	}
	
	public static Node loadController(Class<?> controllerClass){
		return loadController(controllerClass, Node.class);
		
	}

	public String getImagesLocation() {
		return imagesLocation;
	}

	public void setImagesLocation(String imagesLocation) {
		this.imagesLocation = imagesLocation;
	}
}
