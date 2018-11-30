//ChinZhiQiJoel_140224142_CO3320_AdditionalMaterial
package storyIllustrationGenerator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

// displaying of image and image dimensions
@SuppressWarnings("serial")
public class LoadImage extends JPanel {

	private BufferedImage[] imgArr;
	Path[] paths;
	private ArrayList<Coordinates> coor;
	private ArrayList<Boolean> yOffset;

	public LoadImage(Path[] fileArr, ArrayList<Coordinates> coordinates, ArrayList<Boolean> offset)
			throws IOException, Exception {
		paths = fileArr;
		coor = coordinates;
		yOffset = offset;
		imgArr = new BufferedImage[fileArr.length];

		for (int i = 0; i < fileArr.length; i++)
			imgArr[i] = ImageIO.read(new File(fileArr[i].toString()));
	}

	// defaulting the position
	public LoadImage(Path[] fileArr) throws IOException, Exception {
		this(fileArr, new ArrayList<Coordinates>(), new ArrayList<Boolean>());
		for (int i = 0; i < imgArr.length; i++) {
			coor.add(new Coordinates(0, 0));
			yOffset.add(false);
		}

	}

	// getting the images from directory
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < imgArr.length; i++) {
			if (yOffset.get(i)) {
				g.drawImage(imgArr[i], coor.get(i).getX(), coor.get(i).getY() - imgArr[i].getHeight(), this);
			} else {
				g.drawImage(imgArr[i], coor.get(i).getX(), coor.get(i).getY(), this);
			}
		}
	}

	// -----------------------------------------------------------------------------------------------------------
	// size and dimensions
	// -----------------------------------------------------------------------------------------------------------

	// getting maximum width and height for the display frame dimensions
	public int getMaxW() {
		int maxWidth = imgArr[0].getWidth();
		for (BufferedImage img : imgArr) {
			if (maxWidth < img.getWidth())
				maxWidth = img.getWidth();
		}
		return maxWidth;
	}

	public int getMaxH() {
		int maxHeight = imgArr[0].getHeight();
		for (BufferedImage img : imgArr) {
			if (maxHeight < img.getHeight())
				maxHeight = img.getHeight();
		}
		return maxHeight;
	}

}
