package api.planet;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Random;

import core.planet.GraphPlanet;


public abstract class Planet implements Serializable {
	

	private static final long serialVersionUID = -5200252737705187164L;
	protected final Double PI=Math.PI;
	protected int width, height, areaC;
	protected Float ossigeno=0F, ferro=0F, oro=0F, terra=0F, acqua=0F, erba=0F, area=0F;
	protected int element[]=new int[6];
	transient protected BufferedImage image;
	protected byte[] serializableImage=null;
	protected final Color EARTH = new Color(94, 47, 13);
	protected final Color GRASS = new Color(95, 102, 2);
	protected final Color GOLD = new Color(80, 64, 20);
	protected final Color IRON = new Color(102, 92, 92);
	protected final Color SEA = new Color(2, 68, 199);
	protected Random random = new Random();
	
	public Planet(Planet planet) {  
		this.width=planet.width;
		this.height=planet.height;
		this.element=planet.element;
		this.ferro=planet.ferro;
		this.oro=planet.oro;
		this.terra=planet.terra;
		this.acqua=planet.acqua;
		this.erba=planet.erba;
		this.area=planet.area;
		this.areaC=planet.areaC;
		this.image=planet.image;
		this.random=planet.random;
		}

	public Planet(int width, int height) {
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		area=PI.floatValue()*(width/2)*(width/2);
		areaC=area.intValue();
		}

	public byte[] getPlanetImage(){
		return serializableImage;
		}
	
	public Planet createGraph() {
		return new GraphPlanet(this);
		}
	
	public String toString() {
		String string = String.format(
				"ferro: %.2f%%%noro: %.2f%%%nterra: %.2f%%%nerba: %.2f%%%nacqua: %.2f%%%nossigeno: %.2f", ferro, oro,
				terra, erba, acqua, ossigeno);
		return string;
		}
	
	public Float getValue(Valore value) {
		Float f = 0f;
		switch (value) {
		case OSSIGENO:f = ossigeno;break;
		case ACQUA:f = acqua;break;
		case TERRA:f = terra;break;
		case ERBA:f = erba;break;
		case ORO:f = oro;break;
		case FERRO:f = ferro;break;
		}
		return f;
		}

}
