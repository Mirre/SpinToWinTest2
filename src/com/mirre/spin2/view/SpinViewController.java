package com.mirre.spin2.view;

import java.io.IOException;

import com.mirre.spin2.Wheel;
import com.mirre.spin2.api.Controller;
import com.mirre.spin2.api.FileLocation;
import com.mirre.spin2.api.GameState;
import com.mirre.spin2.SpinToWinClient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

@FileLocation(location = "view/SpinView.fxml")
public class SpinViewController extends Controller {

	@FXML
	private GridPane gridPane;
	
	@FXML
	private ImageView imageView, pointImage;
	
	@FXML
	private Button readyButton;
	
	@FXML
	private Label idLabel;
	
	private Wheel wheel;
	private int id = 0;
	
	public SpinViewController(){}
	
	@FXML
	@Override
	public void initialize(){
		try {
			SpinToWinClient client = new SpinToWinClient("85.8.44.212", 25565, SpinViewController.this);
			client.open();
			wheel = client.createWheel(imageView, this.loadImage("Wheel.png"));
			wheel.setOnSpinComplete(() -> {
				readyButton(client);
			});
			readyButton.setOnAction((event) -> {
				readyButton(client);
				if(client.getState().equals(GameState.PLAYER_READY)){
					client.sendToServer(GameState.PLAYER_READY.toString());
				}else{
					client.sendToServer(GameState.PLAYER_UNREADY.toString());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		pointImage.setImage(this.loadImage("Pointer.png"));
		idLabel.setText("You are player " + id + ".");
	}
	
	private void readyButton(SpinToWinClient client){
		if(client.getState().equals(GameState.SPIN_RUNNING)){
			return;
		}else if(readyButton.getText().equals("Ready")){
			client.setState(GameState.PLAYER_READY);
			readyButton.setText("Unready");
		}else{
			client.setState(GameState.PLAYER_UNREADY);
			readyButton.setText("Ready");
		}
	}
	
	public void recieve(SpinToWinClient client, String fromServer){
		System.out.println("Recieved: " + fromServer);
		if(fromServer.startsWith("READY:")){
			String[] s = fromServer.replace("READY:", "").split(":");
			wheel.spin(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
		}else if(fromServer.startsWith("ID:")){
			id = Integer.parseInt(fromServer.replace("ID:", ""));
			idLabel.setText("You are player " + id + ".");
		}
	}
	
	
}
