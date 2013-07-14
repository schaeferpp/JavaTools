package de.schaefer.tools.entity;

public class Triple<E, F, G> {
    private E first;
    private F second;
    private G third;

    public Triple() {

    }

    public Triple(E first, F second, G third) {
	super();
	this.first = first;
	this.second = second;
	this.third = third;
    }

    public E getFirst() {
	return first;
    }

    public void setFirst(E first) {
	this.first = first;
    }

    public F getSecond() {
	return second;
    }

    public void setSecond(F second) {
	this.second = second;
    }

    public G getThird() {
	return third;
    }

    public void setThird(G third) {
	this.third = third;
    }
}
