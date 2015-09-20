package com.mirre.spin2.api;

import java.util.ArrayList;
import java.util.List;

public abstract class Followable<T, Q> implements Follower<T> {
	
	protected List<Follower<Q>> followers = new ArrayList<Follower<Q>>();
	
	
	public int attach(Follower<Q> follower){
		this.followers.add(follower);
		return this.followers.indexOf(follower);
	}
	
	public void deattach(Follower<Q> follower){
		this.followers.remove(follower);
	}

	@Override
	public void close(){
		followers.forEach((follower) -> {
			follower.close();
		});
	}
	
	@Override
	public void update(){
		followers.forEach((follower) -> {
			follower.update();
		});
	}
	
	
}
