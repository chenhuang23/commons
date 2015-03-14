package com.github.commons.model;

/**
 * @author: xiaofeng.zhouxf
 * @author mercy
 * @version 1.0.13 Aug 7, 2009
 * @since 1.0.13
 */
public interface Observer<T> {

	// --------------------------------------------------------------------------------------------
	// Instance Method
	// --------------------------------------------------------------------------------------------
	/**
	 * Updates the observable.
	 * 
	 * @since 1.0.13 Aug 7, 2009
	 * @param observable
	 * @param Object
	 */
	public void update(Observable<T> observable, T Object);

}
