package core.planet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import api.planet.Planet;
import utils.Pair;


public class GraphPlanet extends Planet {

	private static final long serialVersionUID = -4070449450015425414L;
	transient private Graphics2D g2d;
	private int count = 0;
	private ArrayList<Pair<Integer, Integer>> ij = new ArrayList<>();
	

    public GraphPlanet(Planet planet) {
    	super(planet);

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		g2d = image.createGraphics();

		// backgroud
		g2d.setColor(new Color(9, 14, 46));
		g2d.fillRect(0, 0, width, height);

		// base of planet
		g2d.setColor(Color.DARK_GRAY);
		Ellipse2D ellipse = new Ellipse2D.Double(0, 0, width, height);
		g2d.fill(ellipse);
		
		//points of ellipse
		for (int i = 0; i <width; i++)
			for (int j = 0; j < height; j++) {
				if(ellipse.contains(i,j))
				ij.add(new Pair<>(i,j));
				}

		// planet
		g2d.setColor(IRON);
		ferro = setPlanet(element[5], ellipse);
		
		g2d.setColor(GOLD);
		oro = setPlanet(element[4], ellipse);

		g2d.setColor(EARTH);
		terra = setPlanet(element[3], ellipse);

		g2d.setColor(GRASS);
		erba = setPlanet(element[2], ellipse);

		g2d.setColor(SEA);
		acqua = setPlanet(element[1], ellipse);
	
		g2d.dispose();
		ij=null;
		
		//make serializable the image
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {ImageIO.write((RenderedImage)image, "png", bos);} catch (IOException e) {e.printStackTrace();}
		serializableImage=bos.toByteArray();
		
		ossigeno = Float.valueOf(element[0]);
		}


	private float setPlanet(int element, Ellipse2D ellipse) {
		for(int i1=count;i1<element+count;i1++) {
			int i=ij.get(i1).getX();
			int j=ij.get(i1).getY();
				g2d.fillRect(i, j, 1,1);
				}
		float value = (element * 100) / ((width / 2) * (width / 2) * PI.floatValue());
		count+=element;
		return value;
		}
	}
