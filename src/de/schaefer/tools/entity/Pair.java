package de.schaefer.tools.entity;

public class Pair<T, E> {

	T first;
	E second;

	public Pair(T first, E second) {
		super();
		this.first = first;
		this.second = second;
	}

	public T getFirst() {
		return first;
	}

	public void setFirst(T first) {
		this.first = first;
	}

	public E getSecond() {
		return second;
	}

	public void setSecond(E second) {
		this.second = second;
	}
}
