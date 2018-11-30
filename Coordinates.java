//ChinZhiQiJoel_140224142_CO3320_AdditionalMaterial
package storyIllustrationGenerator;

// image x and y values
public class Coordinates {

	private int x;
	private int y;

	public Coordinates(int xVal, int yVal) {
		x = xVal;
		y = yVal;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String toString() {
		return x + ", " + y;
	}
}
