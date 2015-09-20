package com.mirre.spin2.api;

public interface Follower<T> {
	public void close();
	public void update();
	public boolean isReady();
	
	@SuppressWarnings("unchecked")
	public default T get(){
		return (T) this;
	}
}
