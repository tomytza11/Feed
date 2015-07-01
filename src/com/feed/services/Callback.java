package com.feed.services;

public interface Callback {
	<T> void finished(T data);

	void onError(Exception e);
}
