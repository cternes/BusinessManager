package org.businessmanager.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.primefaces.event.ResizeEvent;

public class ImageUtil {

	/**
	 * Copy & Resize a given image to new width and height.
	 * 
	 * @param img
	 *            the source image
	 * @param newW
	 *            the width of the target image
	 * @param newH
	 *            the height of the target image
	 * @return the target image
	 */
	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		int w = img.getWidth();
		int h = img.getHeight();
		try {
			BufferedImage dimg = new BufferedImage(newW, newH,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = dimg.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
			g.dispose();
			return dimg;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	public static byte[] resize(InputStream inputStream, int newWidth, int newHeight) {
		if(inputStream != null) {
			try {
				BufferedImage orgImage = ImageIO.read(inputStream);
				BufferedImage newImage = resize(orgImage, newWidth, newHeight);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ImageIO.write(newImage, "jpg", outputStream);
				return outputStream.toByteArray();
			} catch (IOException e) {
				return null;
			}
			
		}
		return null;
	}

	public static byte[] getBytesFromImage(String sourceFile)
			throws IOException {
		File file = new File(sourceFile);
		BufferedImage image;
		image = ImageIO.read(file);
		image = ImageUtil.resize(image, 216, 194);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", outputStream);
		outputStream.flush();
		byte[] data = outputStream.toByteArray();
		outputStream.close();
		return data;
	}

	/**
	 * Resizes an image but keeps it's aspect ration.
	 * 
	 * @param image
	 *            an image which should be resized
	 * @param newWidth
	 *            the new target width
	 * @param newHeight
	 *            the new target height
	 * @return the resized image
	 */
	public static BufferedImage ensureDimension(BufferedImage image,
			int newWidth, int newHeight) {
		int width = image.getWidth();
		int height = image.getHeight();

		double ratio = (double) width / (double) height;

		if (ratio >= 1) {
			// Querformat
			image = ImageUtil.resize(image, newWidth, (int) (newWidth / ratio));
		} else {
			// Hochformat
			image = ImageUtil.resize(image, (int) (newHeight * ratio),
					newHeight);
		}

		return image;
	}
	

}
