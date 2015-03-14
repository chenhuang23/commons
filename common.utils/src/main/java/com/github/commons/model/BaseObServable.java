package com.github.commons.model;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <pre>
 * desc: 
 * created: 2012-7-26 ÏÂÎç04:06:59
 * author: xiaofeng.zhouxf
 * todo: 
 * history:
 * </pre>
 */
public class BaseObServable<T> implements Observable<T> {
	protected AtomicBoolean changed = new AtomicBoolean(false);

	protected Set<Observer<T>> observerList = new ConcurrentSkipListSet<Observer<T>>();

	public void addObserver(Observer<T> observer) {
		changed.set(observerList.add(observer));
	}

	public int countObservers() {
		return this.observerList.size();
	}

	public void deleteObserver(Observer<T> observer) {
		changed.set(this.observerList.remove(observer));
	}

	public void deleteObservers() {
		this.observerList.clear();
		this.clearChanged();
	}

	public boolean isChanged() {
		return changed.get();
	}

	public void notifyObservers() {
		this.notifyObservers(null);
	}

	public void clearChanged() {
		changed.set(false);
	}

	public void setChanged() {
		changed.set(true);
	}

	public void notifyObservers(T arg) {
		if (!isChanged())
			return;
		for (Observer<T> observer : this.observerList) {
			observer.update(this, arg);
		}
	}
}
