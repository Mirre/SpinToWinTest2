package com.mirre.spin2;

import com.mirre.spin2.api.GameState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Wheel {

	private final ImageView view;
	private SpinToWinClient client;
	private Runnable spinComplete = () -> {
		return;
	};
	
	public Wheel(SpinToWinClient spinToWinClient, ImageView view, Image image){
		view.setImage(image);
		this.view = view;
		this.client = spinToWinClient;
	}
	
	public void spin(int winner, int cycles){
		if(client.getState().equals(GameState.SPIN_RUNNING)){
			return;
		}
		client.setState(GameState.SPIN_RUNNING);
		System.out.println("Winner: " + winner);
		System.out.println("Cycles: " + cycles);
		Timeline timeline = new Timeline(
			    new KeyFrame(Duration.ZERO,
			    		new EventHandler<ActionEvent>() {
			    		private double i = 0;
			        	@Override public void handle(ActionEvent actionEvent) {
			        		i++;
			        		view.setRotate(i);
			        	}
			    	}),
			    new KeyFrame(Duration.seconds(0.005)));
		timeline.setOnFinished((event) -> {
			playEnd(cycles);
		});
		
		System.out.println("Estimated Time: " + ((640 + cycles) * 0.005) + " seconds.");
		timeline.setCycleCount(cycles);
		timeline.play();
	}
	
	public void playEnd(int cycles){
		System.out.println("Start END");
		Timeline end = new Timeline(
			    new KeyFrame(Duration.ZERO,
			    		new EventHandler<ActionEvent>() {
			    		private double i = cycles, speed = 1, counter = 0, decreaseCounter = 1;
			        	@Override public void handle(ActionEvent actionEvent) {
			        		counter++;
			        		if(counter >= decreaseCounter * 66){
			        			speed -= 0.1;
			        			decreaseCounter++;
			        		}
			        		if(counter == 640){
			        			view.setRotate(cycles + 360);
			        			System.out.println(view.getRotate() - cycles);
			        		}else{
			        			i += speed;
			        			view.setRotate(i);
			        		}
			        	}
			    	}),
			    new KeyFrame(Duration.seconds(0.005)));
		end.setOnFinished((event) -> {
			client.setState(GameState.UPDATE);
			spinComplete.run();
		});
		//360: speed 1, 180: speed 0.5, 90; speed 0.25
		end.setCycleCount(640);
		end.play();
	}
	
	public void setOnSpinComplete(Runnable runnable){
		this.spinComplete = runnable;
	}
}
