package com.mirre.spin2.api;

import com.mirre.spin2.api.GameState;

public enum GameState {
	PLAYER_READY, PLAYER_UNREADY, SPIN_RUNNING, CLIENT_DISCONNECTED, UPDATE, UNKNOWN;
	
	public static GameState parse(String fromClient){
		try{
			return GameState.valueOf(fromClient);
		}catch(Exception e){
			return GameState.UNKNOWN;
		}
	}
}