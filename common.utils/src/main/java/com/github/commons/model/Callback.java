package com.github.commons.model;

public interface Callback<MSG> {
	void handle(MSG... e);
}
