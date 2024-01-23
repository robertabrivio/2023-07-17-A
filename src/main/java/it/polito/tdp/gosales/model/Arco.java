package it.polito.tdp.gosales.model;

public class Arco implements Comparable<Arco>{
	private Products p1;
	private Products p2;
	private int peso;
	public Arco(Products p1, Products p2, int peso) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.peso = peso;
	}
	public Products getP1() {
		return p1;
	}
	public void setP1(Products p1) {
		this.p1 = p1;
	}
	public Products getP2() {
		return p2;
	}
	public void setP2(Products p2) {
		this.p2 = p2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Arco o) {
		return o.getPeso()-this.peso;
	}
	@Override
	public String toString() {
		return "Arco da " + p1.getNumber() + " a " + p2.getNumber() + ", peso=" + peso;
	}
	
	

}
