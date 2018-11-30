//ChinZhiQiJoel_140224142_CO3320_AdditionalMaterial

//to test for user input interface

package storyIllustrationGenerator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.JButton;

import edu.stanford.nlp.ling.LabeledWord;
import edu.stanford.nlp.trees.Tree;

public class Main {

	// interface
	private JFrame frame;
	private JButton button;
	private JTextField textField;
	private JTextArea textInstruction, feedback;

	// record text into a new .txt file
	private StringBuilder allUserInputs;
	private String fileName;
	private int imgGeneratedCount;

	private int width = 1200;// for aesthetics of the instruction shown
	private int height = 1000;

	public static void main(String[] args) {
		Main m = new Main();
		m.run();

	}

	public Main() {
		allUserInputs = new StringBuilder();
		imgGeneratedCount = 0;
		// to uniquely identify a file and its images
		Calendar c = Calendar.getInstance();
		fileName = System.getProperty("user.dir") + "_" + c.get(Calendar.DATE) + "_" + c.get(Calendar.HOUR_OF_DAY) + "_"
				+ c.get(Calendar.MINUTE) + "_" + c.get(Calendar.SECOND);
		System.out.println(fileName);
	}

	public void run() {
		// run a interface until input entered and button pressed
		// submit feedback via https://goo.gl/forms/HqKVqI3S9TpoU3t33
		frame = new JFrame();
		button = new JButton();
		TextListener textListener = new TextListener();
		ExitListener exitListener = new ExitListener();
		textField = new JTextField();
		textInstruction = new JTextArea();
		feedback = new JTextArea();

		// organised to see synonym word groups
		textInstruction.setText("To generate an image, form a simple sentence using only the pronouns or nouns:\n"
				+ "dog, cur, doggy, mongrel, hound, pooch, puppy, " + "cat, kitten, mouser, tabby, " + "squirrel, "
				+ "bird, passerine, warbler, " + "butterfly, " + "she, girl, woman, " + "he, man, boy, "
				+ "children, kids, " + "park, " + "merry-go-round, " + "swing, " + "lamp, lamppost, light, " + "tree, "
				+ "ground, path, pathway, pavement, sidewalk, walkway, " + "bench, seat;\n"
				+ "\nup to one of the following adjectives:\ntired, quick;\n"
				+ "\nand up to one of the following prepositions:\nunder, below, on, over, above, beside, after, before, in.\n");

		textInstruction.setFont(new Font("Arial", Font.PLAIN, 30));
		textInstruction.setBackground(null);
		textInstruction.setEditable(false);
		textInstruction.setBorder(null);
		textInstruction.setLineWrap(true);

		feedback.setText("");
		feedback.setFont(new Font("Arial", Font.PLAIN, 30));
		feedback.setForeground(Color.RED);
		feedback.setBackground(null);
		feedback.setEditable(false);
		feedback.setBorder(null);
		feedback.setLineWrap(true);

		textField.setSize(new Dimension(width, 100));
		textField.setFont(new Font("Arial", Font.PLAIN, 50));

		button.setText("Go!");
		button.setFont(new Font("Arial", Font.PLAIN, 30));
		button.addActionListener(textListener);

		// Gridbaglayout for the interface
		frame.setLayout(new GridBagLayout());
		frame.setPreferredSize(new Dimension(width, height));

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.gridx = 0;
		gbc.gridy = 0;
		frame.getContentPane().add(textInstruction, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.gridx = 0;
		gbc.gridy = 3;
		frame.getContentPane().add(feedback, gbc);

		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 1;
		frame.getContentPane().add(textField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		frame.getContentPane().add(button, gbc);

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(exitListener);
		frame.pack();
		frame.setVisible(true);
	}

	public String getUserEnteredText() {
		return allUserInputs.toString();
	}

	// produce output after pressing enter, with some error feedback to user
	private class TextListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			String input = textField.getText();
			allUserInputs.append(input);
			allUserInputs.append(",");
			System.out.println("Text input: " + input);

			try {
				LoadImage loadImage = calculatePos(input);
				JButton refresh = new JButton();
				refresh.addActionListener(new refreshListener());
				refresh.setText("refresh");

				frame.setContentPane(loadImage);
				frame.add(refresh, BorderLayout.SOUTH);// for convenience of user testing
				frame.repaint();
				frame.pack();

				// takes a snapshot of the output frame
				BufferedImage img = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D g2D = img.createGraphics();
				frame.paint(g2D);
				imgGeneratedCount++;
				ImageIO.write(img, "png", new File(fileName + "(" + imgGeneratedCount + ")" + ".png"));
			} catch (FileNotFoundException fnfe) {
				System.out.println("File not found Error: " + fnfe.getMessage());
				feedback.setText("File not found");
				frame.repaint();
			} catch (SQLException sqle) {
				System.out.println("SQL Error: " + sqle.getMessage());
			} catch (IOException ioe) {
				System.err.println(ioe);
				System.err.println("There might be invalid object or adjective");
				feedback.setText("There might be invalid object or adjective");
				frame.repaint();
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	// creates a new window
	private class refreshListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			run();
		}
	}

	private class ExitListener implements WindowListener {
		public void windowActivated(WindowEvent we) {
		}

		public void windowClosed(WindowEvent we) {
		}

		public void windowClosing(WindowEvent we) {// enters when window closed by user
			// create a file with all entered sentences
			try {
				System.out.println(getUserEnteredText());
				PrintWriter pw = new PrintWriter(fileName + ".txt");
				pw.write(getUserEnteredText());
				pw.flush();
				pw.close();
			} catch (IOException ioe) {
				System.err.println("File output has error");
			} catch (Exception e) {
				System.err.println("File output process has error");
			}
			System.exit(0);
		}

		public void windowDeactivated(WindowEvent we) {
		}

		public void windowDeiconified(WindowEvent we) {
		}

		public void windowIconified(WindowEvent we) {
		}

		public void windowOpened(WindowEvent we) {
		}

	}

	public LoadImage calculatePos(String s) throws FileNotFoundException, SQLException, IOException, Exception {

		AnnotateWord readText1 = new AnnotateWord(s);
		readText1.storeAllWords();
		AccessDB adb = new AccessDB("student", "student");

		Tree t = readText1.getTree();
		List<List<LabeledWord>> np = new Phrase().getNounPhrases(t);

		// -----------------------------------------------------------------------------------------------------------
		// setup of images path directory
		// -----------------------------------------------------------------------------------------------------------

		/*
		 * Processes the retrieval of the right image. Firstly, get the noun or pronoun
		 * (subject), then the verb or adjective or particle(action) directory should
		 * follow as (../noun or pronoun/verb or adjective).ext where ext represents the
		 * extension, default as png. These also assume that a phrase will at most have
		 * only one subject and action, though it is not true for all phrases due to the
		 * removal of the consideration of coordinating and subordinating conjunctions
		 * if there is no action: default image should be under ../noun or pronoun/nil
		 * 
		 * Secondly,adverbs and prepositions will decide the location(s) of the image.
		 */

		// subjects
		ArrayList<String> paths = new ArrayList<String>();
		ArrayList<Boolean> needImage = new ArrayList<Boolean>();
		for (int i = 0; i < np.size(); i++) {
			Phrase temp = new Phrase();
			needImage.add(true);
			ArrayList<String> subjectInNP = new ArrayList<String>();
			ArrayList<String> objectInNP = temp.getNounsInPhrase(np.get(i));
			ArrayList<String> personInNP = temp.getPronounsInPhrase(np.get(i));
			if (objectInNP != null) {
				for (int j = 0; j < objectInNP.size(); j++) {
					subjectInNP.add(objectInNP.get(j));
				}
			} else if (personInNP != null) {
				for (int j = 0; j < personInNP.size(); j++) {
					subjectInNP.add(personInNP.get(j));
				}
			} else
				needImage.set(i, false);

			for (int j = 0; j < subjectInNP.size(); j++) {
				paths.add(adb.getWord(subjectInNP.get(j)));
			}
		}

		// actions
		// more concerned with the actions by nouns and pronouns hence the use of NP
		int needImageOffset = 0;
		for (int i = 0; i < np.size(); i++) {
			if (!needImage.get(i)) {
				needImageOffset++;
				continue;
			}

			ArrayList<String> actionInNP = new Phrase().getAdjectivesInPhrase(np.get(i));
			if (actionInNP.isEmpty())
				actionInNP = new Phrase().getVerbsInPhrase(np.get(i));// does not check on verb phrases, only
																		// working on noun phrases for this
																		// prototype
			if (actionInNP.isEmpty())
				actionInNP = new Phrase().getParticlesInPhrase(np.get(i));
			if (actionInNP.isEmpty())
				actionInNP.add("nil");

			System.out.println("action: " + actionInNP.toString());
			for (int j = 0; j < actionInNP.size(); j++) {
				int pos = i - needImageOffset;
				paths.set(pos, paths.get(pos) + "\\" + actionInNP.get(j));
			}
		}

		// for background
		boolean withoutBackground = true;
		boolean needBackground = true;
		ArrayList<Boolean> offset = new ArrayList<Boolean>();// background does not need offset
		int index = 0;
		for (int i = 0; i < paths.size(); i++) {
			String[] temp = paths.get(i).split("\\\\");
			if (inBackground(adb.getWord(temp[0]))) {// contains reference to the park or the word park
				if (adb.getWord(temp[0]).equals("park"))
					needBackground = false;// contains the word park and not references
				withoutBackground = false;
				index = i;
			}
		}

		if (withoutBackground) {// does not contain the word park
			// do nothing
		} else {
			if (needBackground)
				paths.add(0, "park\\nil");
			else {
				String temp = paths.get(index);
				paths.remove(index);
				paths.add(0, temp);
			}
			offset.add(false);
		}

		// assign a position for each path
		ArrayList<PositionOfPath> pop = new ArrayList<PositionOfPath>();
		for (int i = 0; i < paths.size(); i++) {
			pop.add(new PositionOfPath(paths.get(i)));
		}

		// location of image
		List<List<LabeledWord>> pp = new Phrase().getPrepositionalPhrases(t);
		ArrayList<String> objInPrep = new ArrayList<String>();
		for (int i = 0; i < pp.size(); i++) {
			Phrase posn = new Phrase();
			Phrase obj = new Phrase();
			ArrayList<String> prepositionInNP = posn.getPrepositionalsInPhrase(pp.get(i));
			if (prepositionInNP.isEmpty())
				prepositionInNP = posn.getAdverbsInPhrase(pp.get(i));

			for (int j = 0; j < prepositionInNP.size(); j++) {
				objInPrep = obj.getNounsInPhrase(pp.get(i));// assumed one preposition to one object
				// first check which object to reference our position to
				for (int k = 0; k < paths.size(); k++) {
					String[] temp = paths.get(k).split("\\\\");
					if (objInPrep.get(0).equals(temp[0])) {
						// check if it references to a part of background
						// if a part of background: pop.set(k-1, element);
						// else: pop.set(k, element);
						if (inBackground(temp[0])) {
							if (k > 0) {
								Coordinates c = prepLocation(prepositionInNP.get(j), true);// secondly decide the way we
																							// position the object
								pop.get(k - 1).setCoor(c);
								pop.remove(k);
								paths.remove(k);// for consistency
							}
						} else {
							if (k > 0) {
								ImagePath ipTemp = new ImagePath();
								ipTemp.setPath(paths.get(k - 1));
								LoadImage liTemp = new LoadImage(ipTemp.getPaths());

								int x = pop.get(k - 1).getCoor().getX();
								int y = pop.get(k - 1).getCoor().getY();
								int width = liTemp.getMaxW();
								int height = liTemp.getMaxH();
								range[0] = new Coordinates(x, y);
								range[1] = new Coordinates(x + width, y + height);

								Coordinates c = prepLocation(prepositionInNP.get(j), false, width, height);
								pop.get(k).setCoor(new Coordinates(x, y));
								pop.get(k - 1).setCoor(c);
							}

						}

					} else {
						if (inBackground(temp[0])) {
							pop.get(0).setCoor(new Coordinates(0, 0));
						} else {
							pop.get(k).setCoor(new Coordinates(295, 635));
						}

					}
				}
			}
		}

		// set up for image file path
		ImagePath ip = new ImagePath();
		ArrayList<String> finalisedPath = new ArrayList<String>();
		for (int i = 0; i < pop.size(); i++)
			finalisedPath.add(pop.get(i).getPath());
		ip.setPaths(finalisedPath);

		ArrayList<Coordinates> coor = new ArrayList<Coordinates>();
		for (int i = 0; i < pop.size(); i++)
			coor.add(pop.get(i).getCoor());
		if (!withoutBackground)
			for (int i = 1; i < pop.size(); i++)
				offset.add(true);
		else
			for (int i = 0; i < pop.size(); i++)
				offset.add(false);

		// -----------------------------------------------------------------------------------------------------------
		// draw the images into a window
		// -----------------------------------------------------------------------------------------------------------

		LoadImage li = new LoadImage(ip.getPaths(), coor, offset);
		if (!withoutBackground)
			li.setPreferredSize(new Dimension(li.getMaxW(), li.getMaxH()));
		else
			li.setPreferredSize(new Dimension(width, height));

		return li;
	}

	/*
	 * floor: x = 95 ~ 825 (default location) y = 635 bench: x = 115 ~ 390 y = 470 ~
	 * 635 Tree left: trunk x = 180 ~ 340 trunk y = 295 ~ 635 Tree right: trunk x =
	 * 533 ~ 660 trunk y = 295 ~ 635 lamp post: x = 680 ~ 815 y = 1 ~ 635 railing: x
	 * = 400 ~ 710 y = 510 ~ 635
	 * 
	 * also consider that y match max of image, x match the min of image
	 */
	Coordinates[] range = new Coordinates[2];

	public boolean inBackground(String object) {
		boolean inScene = true;
		if (object.equals("park")) {
			// do nothing
		} else if (object.equals("tree")) {
			int random = ((int) (Math.random() * 2));
			if (random == 0) {
				range[0] = new Coordinates(180, 340);
				range[1] = new Coordinates(410, 635);
			} else {
				range[0] = new Coordinates(530, 295);
				range[1] = new Coordinates(660, 635);
			}
		} else if (object.equals("bench")) {
			range[0] = new Coordinates(115, 470);
			range[1] = new Coordinates(390, 635);
		} else if (object.equals("lamp")) {
			range[0] = new Coordinates(680, 1);
			range[1] = new Coordinates(815, 635);
		} else if (object.equals("ground")) {
			range[0] = new Coordinates(95, 635);
			range[1] = new Coordinates(825, 635);
		} else {
			inScene = false;
		}
		return inScene;
	}

	public Coordinates prepLocation(String pp, boolean inBg) {
		return prepLocation(pp, inBg, 0, 0);
	}
	
	/*
	 * under = take lowest value of Y 
	 * on = take upper value of Y
	 */
	public Coordinates prepLocation(String pp, boolean inBg, int w, int h) {
		Coordinates c = new Coordinates(200, 200);
		if (pp.equals("under") || pp.equals("below")) {
			// taking lowest value for y
			int y;
			if (inBg)
				y = range[1].getY();
			else
				y = range[0].getY() + h;
			int x = (int) range[0].getX();
			c = new Coordinates(x, y);
		} else if (pp.equals("on") || pp.equals("over") || pp.equals("above")) {
			int y = (int) range[0].getY();
			int x = (int) range[0].getX();
			c = new Coordinates(x, y);
		} else if (pp.equals("beside") || pp.equals("after") || pp.equals("before")) {
			int x;
			if (pp.equals("before"))
				x = range[0].getX();
			else
				x = range[1].getX();

			int y;
			if (inBg)
				y = range[1].getY();
			else
				y = range[0].getY();
			c = new Coordinates(x, y);
		} else if (pp.equals("in")) {
			// do nothing because it should overlap already
			// we can consider doing something in future
		} else {
			// a preposition that is not used here
		}
		return c;
	}

}
