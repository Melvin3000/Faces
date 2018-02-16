package com.Melvin3000.Faces;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Faces {
	
	/**
	 * turn a plaintext list of players into a 64x64 map
	 * probably throws a bunch of exceptions
	 */
	public static void main(String[] args) throws Exception {

		List<String> names = Files.readAllLines(new File("names.txt").toPath());
		List<BufferedImage> faces = new ArrayList<BufferedImage>();
		
		for (String name : names) {
			faces.add(getFace(getSkin(getUUID(name))));
			System.out.println("Downloading [" + name + "]");
		}
		
		BufferedImage image = new BufferedImage(64, 64, 5);
		Graphics g = image.getGraphics();
		
		
		int x = 0;
		int y = 0;
		
		for (int i = 0; i < names.size(); i++) {
			
			BufferedImage face = faces.get(i);
			g.drawImage(face, x, y, null);	
			
			x += 8;
			if (x >= 64) {
				x = 0;
				y += 8;
			}			
		}
		
		ImageIO.write(image, "png", new File("final.png"));
		System.out.println("Finished!");
	}
	
	
	/**
	 * maybe returns the uuid properly
	 */
	public static String getUUID(String name) {
		
		List<String> result = ConnectionUtils.get("https://api.mojang.com/users/profiles/minecraft/" + name);
		if (result.isEmpty()) {
			return null;
		}
		
		return result.get(0).substring(7, 39);
	}
	
	/**
	 * returns full skin as bufffered image
	 */
	public static BufferedImage getSkin(String uuid) throws Exception {
		URL url = new URL("https://crafatar.com/skins/" + uuid);
		return ImageIO.read(url);
	}
	
	/**
	 * crop face and toss that hat on top 
	 */
	public static BufferedImage getFace(BufferedImage image) throws IOException {
		
		BufferedImage face = image.getSubimage(8, 8, 8, 8);
		BufferedImage hat = image.getSubimage(40, 8, 8, 8);
		
		Graphics g = face.getGraphics();
		g.drawImage(hat, 0, 0, null);
		
		return face;
	}
	
}
