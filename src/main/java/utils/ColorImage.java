package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
public class ColorImage {

	public static ArrayList<Pair<Integer, Integer>> 
	   colorImg(BufferedImage image, int colorLimit, int[] values, Color[] colors) {
		if(values.length!=colors.length)throw new IllegalArgumentException();
		final int length = values.length;
		int temp = 0;
		for (int i = 0; i < values.length; i++)
			temp += values[i];
		float[] percentages = new float[values.length];
		for (int i = 0; i < values.length; i++)
			percentages[i] = (Float.valueOf(values[i]) * 100f) / Float.valueOf(temp);
		temp=0;
		for (int i = 0; i < length; i++)
			temp += percentages[i];
		Graphics2D g2d = image.createGraphics();
		int width = image.getWidth();
		int height = image.getHeight();
		ArrayList<Pair<Integer, Integer>> points = new ArrayList<>();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++)
				if (image.getRGB(i, j) < colorLimit)
					points.add(new Pair<>(i, j));
		}
		int []pixels = new int[values.length];
		for(int i=0;i<pixels.length;i++) 
			pixels[i]=(int)(points.size()*percentages[i])/100;
		Collections.shuffle(points);
		temp = 0;
		Pair<Integer,Integer> point;
		for(int i=0;i<pixels.length;i++) {
			g2d.setColor(colors[i]);
			for(int j=temp;j<temp+pixels[i];j++) {
			        point=points.get(j);
			        g2d.fillRect(point.getX(), point.getY(), 1, 1);
			        }
			temp+=pixels[i];
		}
		return points;
	}
}
