package core.items.weapons;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import api.items.UpgradeableItem;
import api.items.weapons.Weapon;
import api.planet.Planet;
import api.planet.Valore;
import utils.ColorImage;

public class Gun extends Weapon implements UpgradeableItem  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1734975860630334156L;
	private String type = "";
	private int count = 50;
	private int bullets;
	private int blade[] = new int[3];// 0 - gold, 1 - iron, 2 - rock
	private boolean luck;
	private float [] bladeP=new float[3];
	private float [] handleP=new float[4];
	
	public Gun(Planet planet) throws IOException {

		int gold = Math.round(planet.getValue(Valore.ORO));// round the float to the nearest integer.
		int iron = Math.round(planet.getValue(Valore.FERRO));
		int rock = Math.round((planet.getValue(Valore.TERRA) + planet.getValue(Valore.ACQUA)) / 2);
		
		cost=random.nextInt(350);
		startResistance();
		resistance+=700;
		bullets=random.nextInt(101)+50;
		
		int temp = 0;
		if(count>0)temp = random.nextInt(26);defence+=temp; if(count-temp>0)count-=temp; else {count=0;temp=0;}
		if(count>0)temp = random.nextInt(26);damage=temp;if(count-temp>0)count-=temp; else {count=0;temp=0;}
		if(count>0)temp = random.nextInt(31);speed=temp;if(count-temp>0)count-=temp; else {count=0;temp=0;}
		
		count = 20;
		if (count > 0)temp = random.nextInt(10);blade[0] = temp;if (gold > 10)blade[0] += (gold - temp);
		if (count - temp > 0)count -= temp;else {count = 0;temp = 0;}
		
		if (count > 0)temp = random.nextInt(10);blade[1] = temp;if (iron > 10)blade[1] += (iron - temp);
		if (count - temp > 0)count -= temp;else {count = 0;temp = 0;}
		
		if (count > 0)temp = random.nextInt(10);blade[2] = temp;if (rock > 10)blade[2] += ((rock - temp));
		if (count - temp > 0)count -= temp;else {count = 0;temp = 0;}
		

		
		Float percentage=0F;
		for(int i=0;i<blade.length;i++) {
			percentage+=Float.valueOf(blade[i]);
		}
		for(int i=0;i<blade.length;i++) {
			bladeP[i]=(Float.valueOf(blade[i])*100f)/percentage;
		}
		percentage=0F;
		for(int i=0;i<handle.length;i++) {
			percentage+=Float.valueOf(handle[i]);
		}
		for(int i=0;i<handle.length;i++) {
			handleP[i]=(Float.valueOf(handle[i])*100f)/percentage;
		}
		
		
		if(bladeP[1]>40f) {damage+=25;type+="_iron_";}
		if(bladeP[0]>30f) {resistance/=2;luck=true;type+="_gold_";}
		if(bladeP[2]>40f) {resistance+=400;defence+=7;speed-=20;type+="_strong_";}
		if(bladeP[2]<10f) {resistance-=200;speed+=30;type+="_fast_";}
		name="Gun"+type;
		
		drawImage();
		
		//makeid
		final int prime = 31;
		Long result = 1l;
		Float tmp=0f;
		for(int i=0;i<handleP.length;i++) {
			tmp=handleP[i];
			tmp+=Float.valueOf(i)+1f;
			result=result*Math.round(tmp);
			
		}
		tmp=0f;
		for(int i=0;i<bladeP.length;i++) {
			tmp=bladeP[i];
			tmp+=Float.valueOf(i)+1f;
			result*=Math.round(tmp);
		}
		result=result*prime;
		id=Long.valueOf(12+""+result);
		
	}
	public void powerUpHandle(int gold, int iron, int rock) throws IOException {
		handle[0]+=gold/100;
		handle[1]+=iron/100;
		handle[2]+=rock/100;
		drawImage();
		resistance+=rock/50;
		defence+=rock/100;
		speed+=iron/1800;
		speed-=rock/1500;
		damage+=rock/4000+iron/4000;
		Float percentage=0F;
		percentage=0F;
		for(int i=0;i<handle.length;i++) {
			percentage+=Float.valueOf(handle[i]);
		}
		for(int i=0;i<handle.length;i++) {
			handleP[i]=(Float.valueOf(handle[i])*100f)/percentage;
		}
		
	}
	public void powerUpblade(int gold, int iron, int rock) throws IOException {
		blade[0]+=gold/100;
		blade[1]+=iron/100;
		blade[2]+=rock/100;
		drawImage();
		resistance+=rock/300;
		defence+=rock/600;
		speed+=iron/800;
		speed-=rock/700;
		damage+=rock/1500+iron/1100;
		Float percentage=0F;
		for(int i=0;i<blade.length;i++) {
			percentage+=Float.valueOf(blade[i]);
		}
		for(int i=0;i<blade.length;i++) {
			bladeP[i]=(Float.valueOf(blade[i])*100f)/percentage;	
		}
	}
	public Long getId() {
		return id;
	}
	public boolean isLucky() {
		return luck;
	}
	public boolean isEmpty() {
		return bullets==0;
	}
	public void subBullet(int value) {
		bullets-=value;
	}
	public void addBullet(int value) {
		bullets+=value;
	}
	public int getBullets() {
		return bullets;
	}
	public void setBullet(int value) {
		bullets=value;
	}
	private void drawImage() throws IOException {
		weapon = ImageIO.read(new File("models/gun.png"));
		int width = weapon.getWidth();
		int height = weapon.getHeight();
		
		//void image to the handle
		BufferedImage handle = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = handle.createGraphics();

		// 1 set transparent background
		g2d.setColor(new Color(0, 0, 0, 0));
		g2d.fillRect(0, 0, width, height);
		
		//handle area
		Path2D area = new Path2D.Double();
		area.moveTo(368,180);
		area.lineTo(350,185);
		area.lineTo(335,194);
		area.lineTo(324,207);
		area.lineTo(315,223);
		area.lineTo(312,237);
		area.lineTo(311,254);
		area.lineTo(310,256);
		area.lineTo(320,273);
		area.lineTo(320,285);
		area.lineTo(333,290);
		area.lineTo(350,308);
		area.lineTo(359,333);
		area.lineTo(377,350);
		area.lineTo(375,382);
		area.lineTo(381,399);
		area.lineTo(463,373);
		area.lineTo(460,354);
		area.lineTo(436,286);
		area.lineTo(447,215);
		area.lineTo(425,184);
		area.lineTo(410,173);
		area.lineTo(400,178);
		area.closePath();
		
		//set clip of Graphics2D object and paint the handle
		g2d.setClip(area);
		g2d.drawImage(weapon, 0, 0, null);
		
		//reset Graphics2D object with the weapon image
		g2d = weapon.createGraphics();
		g2d.setClip(area);
		g2d.setBackground(new Color(0, 0, 0, 0));
		
		//clear the handle from the weapon image to color only the barrel of the gun
		g2d.clearRect(0, 0, width, height);
		
		ColorImage.colorImg(handle, -10000000, this.handle, new Color[] { GOLD,IRON,ROCK,WOOD });
		ColorImage.colorImg(weapon, -10000000, blade, new Color[] { GOLD,IRON,ROCK });
		
		//paint the handle to the weapon image
		g2d.drawImage(handle, 0, 0, null);
		g2d.dispose();
		
		//make serializable the image
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {ImageIO.write((RenderedImage)weapon, "png", bos);} catch (IOException e) {e.printStackTrace();}
		serializableImage=bos.toByteArray();
	}
	public String toString() {
		String string = String.format("%nnome: %s %nvalori della pistola:%nresistenza: %d%nprotezione: %d%nforza: %d%nvelocità: %d"
				+ "%n%ncaratteristiche fisiche:%n%ncanna:%noro: %.2f%%%nferro %.2f%%%nroccia %.2f%%"
				+ "%n%nimpugnatura: %noro: %.2f%%%nferro %.2f%%%nroccia %.2f%%%nlegno %.2f%%%n%nproiettili rimasti: %d"
				+ "",name,resistance,defence,damage,speed,bladeP[0],bladeP[1],bladeP[2],handleP[0],handleP[1],handleP[2],handleP[3],bullets);
		return string;
		}
}
