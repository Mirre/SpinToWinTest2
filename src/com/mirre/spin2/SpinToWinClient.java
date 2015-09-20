package com.mirre.spin2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.mirre.spin2.api.GameState;
import com.mirre.spin2.view.SpinViewController;

public class SpinToWinClient  {

	private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private Timer timer;
    private final SpinViewController controller;
    private GameState state = GameState.UPDATE;


	public SpinToWinClient(String ip, int port, SpinViewController controller) throws UnknownHostException, IOException {
		this.socket = new Socket(ip, port);
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.controller = controller;
		out.println("To Server");
		open();
		System.out.println("Test");
	}
	
	
	public void update() {
		String fromServer;
		try {
			if((fromServer = in.readLine()) != null) {
				controller.recieve(this, fromServer);
			}
		} catch (IOException e) {
			timer.cancel();
			System.exit(1);
		}
	}
	
	public void open(){
		int delay = 0;
		int period = 50;
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				sendToServer(GameState.UPDATE.toString());
				update();
			}
		}, delay, period);
	}
	
	public void close(){
		timer.cancel();
		sendToServer(GameState.CLIENT_DISCONNECTED.toString());
		System.exit(1);
	}
	
	public void sendToServer(String toServer){
		out.println(toServer);
	}
	
	public Wheel createWheel(ImageView view, Image image){
		return new Wheel(this, view, image);
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}
}
