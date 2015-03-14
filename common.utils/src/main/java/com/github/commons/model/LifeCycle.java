package com.github.commons.model;

public interface LifeCycle {

	public void init();

	public void start();

	public void stop();

	public void destroy();

	public int getStatus();
}
