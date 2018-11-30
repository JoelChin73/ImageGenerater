//ChinZhiQiJoel_140224142_CO3320_AdditionalMaterial
package storyIllustrationGenerator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

// directory of image
public class ImagePath {

	private Path[] paths;

	public ImagePath() {
	}

	// -----------------------------------------------------------------------------------------------------------
	// setup of images
	// -----------------------------------------------------------------------------------------------------------

	// get the images file needed
	// single file
	public void setPath(String pic) {
		ArrayList<String> filenames = new ArrayList<String>();
		filenames.add(pic);
		setPaths(filenames);
	}

	// more than 1 file
	public void setPaths(ArrayList<String> pics) {
		paths = new Path[pics.size()];
		for (int i = 0; i < pics.size(); i++) {
			pics.set(i, pics.get(i).toLowerCase());
			paths[i] = Paths.get(System.getProperty("user.dir"), "images", pics.get(i) + ".png");
		}
	}

	public Path[] getPaths() {
		return paths;
	}
}
