//ChinZhiQiJoel_140224142_CO3320_AdditionalMaterial
package storyIllustrationGenerator;

// use this class to store image directory and image coordinates
public class PositionOfPath {

	private String path;
	private Coordinates coor;

	public PositionOfPath(String directory, Coordinates coordinates) {
		path = directory;
		coor = coordinates;
	}

	public PositionOfPath(String directory) {
		this(directory, new Coordinates(0, 0));
	}

	public void setCoor(Coordinates c) {
		coor = c;
	}

	public String getPath() {
		return path;
	}

	public Coordinates getCoor() {
		return coor;
	}

	public String toString() {
		return "(" + coor.toString() + "), " + path;
	}
}
