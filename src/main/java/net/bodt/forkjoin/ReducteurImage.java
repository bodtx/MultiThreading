package net.bodt.forkjoin;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ReducteurImage {

	
	/** 
	 * Redimensionne une image.
	 * 
	 * @param source Image à redimensionner.
	 * @param width Largeur de l'image cible.
	 * @param height Hauteur de l'image cible.
	 * @throws IOException 
	 */
	public static File scale(File imageFile, int width, int height) throws IOException {
		if(imageFile.getName().toLowerCase().indexOf("jpg") != -1 ){

			BufferedImage bi = ImageIO.read(imageFile);
			
		    /* On crée une nouvelle image aux bonnes dimensions. */
		    BufferedImage buf = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
	
		    /* On dessine sur le Graphics de l'image bufferisée. */
		    Graphics2D g = buf.createGraphics();
		    g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		    g.drawImage(bi, 0, 0, width, height, null);
		    g.dispose();
	
		    /* On retourne l'image bufferisée, qui est une image. */
		    File resized = new File(imageFile+"_resized.jpg");
		    ImageIO.write(buf, "JPG", resized);
		    System.out.println(imageFile.getName() +" resizée ");
		    return resized;
		    
			
		}
		else
			System.err.println(imageFile.getName() +" n'est pas un jpeg ");
		return null;
	}
	

	
	
}
