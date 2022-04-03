package core.planet;

import api.planet.Planet;
import utils.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class LinearPlanet extends Planet {

    private static final long serialVersionUID = -8370050928529614202L;
    transient private Graphics2D g2d;
    private ArrayList<ArrayList<Pair<Integer, Integer>>> lines = new ArrayList<>();
    private ArrayList<Pair<Integer, Integer>> ij = new ArrayList<>();
    private int count = 0, temp = 6;

    public LinearPlanet(int width, int height) {
        super(width, height);

        g2d = image.createGraphics();

        // backgroud
        g2d.setColor(new Color(9, 14, 46));
        g2d.fillRect(0, 0, width, height);

        // base of planet
        g2d.setColor(Color.DARK_GRAY);
        Ellipse2D ellipse = new Ellipse2D.Double(0, 0, width, height);
        g2d.fill(ellipse);

        //points of ellipse
        for (int i = 0; i < width; i++) {
            ArrayList<Pair<Integer, Integer>> line = new ArrayList<>();
            for (int j = 0; j < height; j++)
                if (ellipse.contains(i, j))
                    line.add(new Pair<>(i, j));
            lines.add(line);
        }
        Collections.shuffle(lines);
        for (ArrayList<Pair<Integer, Integer>> line : lines)
            for (Pair<Integer, Integer> point : line) {
                ij.add(point);
            }
        lines = null;

        //rewrite areas with ij.size()
        area = Float.valueOf(ij.size());
        areaC = ij.size();

        // planet
        g2d.setColor(IRON);
        ferro = setCasual(ellipse);

        g2d.setColor(GOLD);
        oro = setCasual(ellipse);

        g2d.setColor(EARTH);
        terra = setCasual(ellipse);

        g2d.setColor(GRASS);
        erba = setCasual(ellipse);

        g2d.setColor(SEA);
        acqua = setCasual(ellipse);

        g2d.dispose();
        ij = null;

        //make serializable the image
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write((RenderedImage) image, "png", bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serializableImage = bos.toByteArray();

        int valO = random.nextInt(100);
        element[0] = valO;
        ossigeno = Float.valueOf(valO);

    }

    private float setCasual(Ellipse2D ellipse) {
        int[] _values = new int[2];
        if (temp > 2)
            _values[0] = random.nextInt(areaC);
        else
            _values[0] = area.intValue() - count;
        areaC -= _values[0];

        for (int i1 = count; i1 < _values[0] + count; i1++) {
            int i = ij.get(i1).getX();
            int j = ij.get(i1).getY();
            _values[1]++;
            g2d.fillRect(j, i, 1, 1);
        }
        element[temp - 1] = _values[1];
        float value = (_values[1] * 100) / ((width / 2) * (width / 2) * PI.floatValue());
        count += _values[0];
        temp--;
        return value;
    }
}

