<<<<<<< HEAD
import java.util.Random;
import java.io.*;

class RandomLevel {
	public static char[][] genLevel(int floor) {
		boolean[][] horizontal = new boolean[2][3];
		boolean[][] vertical = new boolean[3][2];
		int[][] roomEnemies = new int[3][3];
		int[][] roomTreasures = new int[3][3];
		char[][] layout = new char[36][36];
		int numberOfEnemies;
		int numberOfTreasures;
		Random r = new Random();

		try {
			BufferedReader reader = new BufferedReader(new FileReader("gamedata/rooms/blank.txt"));

			for (int i = 0; i < 36; i++) {
				String line = reader.readLine();
				for (int j = 0; j < 36; j++) {
					layout[i][j] = line.charAt(j);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (!complete(horizontal, vertical)) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 3; j++) {
					horizontal[i][j] = r.nextBoolean();
				}
			}
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 2; j++) {
					vertical[i][j] = r.nextBoolean();
				}
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				if (horizontal[i][j]) {
					layout[i * 12 + 11][j * 12 + 5] = '.';
					layout[i * 12 + 11][j * 12 + 6] = '.';
					layout[i * 12 + 12][j * 12 + 5] = '.';
					layout[i * 12 + 12][j * 12 + 6] = '.';
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				if (vertical[i][j]) {
					layout[i * 12 + 5][j * 12 + 11] = '.';
					layout[i * 12 + 5][j * 12 + 12] = '.';
					layout[i * 12 + 6][j * 12 + 11] = '.';
					layout[i * 12 + 6][j * 12 + 12] = '.';
				}
			}
		}

		if (floor < 2) {
			numberOfEnemies = 8;
		} else {
			numberOfEnemies = 8 + (int) Math.round((floor - 1) * 1.8);
		}
		numberOfTreasures = (int) Math.round(numberOfEnemies * 0.8);

		for (int i = 0; i < numberOfEnemies; i++) {
			int room = r.nextInt(9);
			roomEnemies[room / 3][room % 3]++;
		}
		roomEnemies[0][0] = 0; // no enemies in starting room

		for (int i = 0; i < numberOfTreasures; i++) {
			int room = r.nextInt(9);
			roomTreasures[room / 3][room % 3]++;
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				while (roomTreasures[i][j] > 0) {
					int x = r.nextInt(10);
					int y = r.nextInt(10);
					layout[(i * 12) + x + 1][(j * 12) + y + 1] = 't';
					roomTreasures[i][j]--;
				}
				while (roomEnemies[i][j] > 0) {
					int x = r.nextInt(10);
					int y = r.nextInt(10);
					layout[(i * 12) + x + 1][(j * 12) + y + 1] = 'e';
					roomEnemies[i][j]--;
				}
			}
		}

		layout[1][1] = 'u';
		layout[(r.nextInt(3) * 12) + r.nextInt(10) + 1][(r.nextInt(3) * 12) + r.nextInt(10) + 1] = 'd';
		return layout;
	}

	// Checks if there is an open path to every room
	public static boolean complete(boolean[][] hz, boolean[][] vt) {
		boolean a = true, b = false, c = false, d = false, e = false, f = false, g = false, h = false, i = false;

		for (int x = 0; x < 8; x++) {
			b = (a && vt[0][0]) || (c && vt[0][1]) || (e && hz[0][1]);
			c = (b && vt[0][1]) || (f && hz[0][2]);
			d = (a && hz[0][0]) || (e && vt[1][0]) || (g && hz[1][0]);
			e = (d && vt[1][0]) || (b && hz[0][1]) || (f && vt[1][1]) || (h && hz[1][1]);
			f = (c && hz[0][2]) || (e && vt[1][1]) || (i && hz[1][2]);
			g = (d && hz[1][0]) || (h && vt[2][0]);
			h = (g && vt[2][0]) || (e && hz[1][1]) || (i && vt[2][1]);
			i = (h && vt[2][1]) || (f && hz[1][2]);
		}
		return a && b && c && d && e && f && g && h && i;
	}

	public static void print2D(char[][] c) {
		for (char[] cc : c) {
			for (int i = 0; i < cc.length; i++)
				System.out.print(cc[i]);
			System.out.print("\n");
		}
	}
=======
import java.util.Random;
import java.io.*;

class RandomLevel {
	public static char[][] genLevel(int floor) {
		boolean[][] horizontal = new boolean[2][3];
		boolean[][] vertical = new boolean[3][2];
		int[][] roomEnemies = new int[3][3];
		int[][] roomTreasures = new int[3][3];
		char[][] layout = new char[36][36];
		int numberOfEnemies;
		int numberOfTreasures;
		Random r = new Random();

		try {
			BufferedReader reader = new BufferedReader(new FileReader("gamedata/rooms/blank.txt"));

			for (int i = 0; i < 36; i++) {
				String line = reader.readLine();
				for (int j = 0; j < 36; j++) {
					layout[i][j] = line.charAt(j);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (!complete(horizontal, vertical)) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 3; j++) {
					horizontal[i][j] = r.nextBoolean();
				}
			}
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 2; j++) {
					vertical[i][j] = r.nextBoolean();
				}
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				if (horizontal[i][j]) {
					layout[i * 12 + 11][j * 12 + 5] = '.';
					layout[i * 12 + 11][j * 12 + 6] = '.';
					layout[i * 12 + 12][j * 12 + 5] = '.';
					layout[i * 12 + 12][j * 12 + 6] = '.';
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				if (vertical[i][j]) {
					layout[i * 12 + 5][j * 12 + 11] = '.';
					layout[i * 12 + 5][j * 12 + 12] = '.';
					layout[i * 12 + 6][j * 12 + 11] = '.';
					layout[i * 12 + 6][j * 12 + 12] = '.';
				}
			}
		}

		if (floor < 2) {
			numberOfEnemies = 8;
		} else {
			numberOfEnemies = 8 + (int) Math.round((floor - 1) * (1.8 + (floor / 10.0)));
		}
		numberOfTreasures = (int) Math.round(numberOfEnemies * 1.7);

		for (int i = 0; i < numberOfEnemies; i++) {
			int room = r.nextInt(9);
			roomEnemies[room / 3][room % 3]++;
		}
		roomEnemies[0][0] = 0; // no enemies in starting room

		for (int i = 0; i < numberOfTreasures; i++) {
			int room = r.nextInt(9);
			roomTreasures[room / 3][room % 3]++;
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				while (roomTreasures[i][j] > 0) {
					int x = r.nextInt(10);
					int y = r.nextInt(10);
					layout[(i * 12) + x + 1][(j * 12) + y + 1] = 't';
					roomTreasures[i][j]--;
				}
				while (roomEnemies[i][j] > 0) {
					int x = r.nextInt(10);
					int y = r.nextInt(10);
					layout[(i * 12) + x + 1][(j * 12) + y + 1] = 'e';
					roomEnemies[i][j]--;
				}
			}
		}

		layout[1][1] = 'u';
		layout[(r.nextInt(3) * 12) + r.nextInt(10) + 1][(r.nextInt(3) * 12) + r.nextInt(10) + 1] = 'd';
		return layout;
	}

	// Checks if there is an open path to every room
	public static boolean complete(boolean[][] hz, boolean[][] vt) {
		boolean a = true, b = false, c = false, d = false, e = false, f = false, g = false, h = false, i = false;

		for (int x = 0; x < 8; x++) {
			b = (a && vt[0][0]) || (c && vt[0][1]) || (e && hz[0][1]);
			c = (b && vt[0][1]) || (f && hz[0][2]);
			d = (a && hz[0][0]) || (e && vt[1][0]) || (g && hz[1][0]);
			e = (d && vt[1][0]) || (b && hz[0][1]) || (f && vt[1][1]) || (h && hz[1][1]);
			f = (c && hz[0][2]) || (e && vt[1][1]) || (i && hz[1][2]);
			g = (d && hz[1][0]) || (h && vt[2][0]);
			h = (g && vt[2][0]) || (e && hz[1][1]) || (i && vt[2][1]);
			i = (h && vt[2][1]) || (f && hz[1][2]);
		}
		return a && b && c && d && e && f && g && h && i;
	}

	public static void print2D(char[][] c) {
		for (char[] cc : c) {
			for (int i = 0; i < cc.length; i++)
				System.out.print(cc[i]);
			System.out.print("\n");
		}
	}
>>>>>>> origin/master
}