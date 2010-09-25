package jam.code.watersheds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Watersheds {

	private static int basin;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		long st = System.currentTimeMillis();
		BufferedReader br = new BufferedReader(new FileReader(new File(
				"D:/GoogleCodeJam/Watersheds/B-small-practice.in")));
		// new File("D:/GoogleCodeJam/Watersheds/B-large-practice.in")));
		int maps = Integer.parseInt(br.readLine());
		for (int i = 0; i < maps; i++) {
			String[] size = br.readLine().split(" ");
			int h = Integer.parseInt(size[0]);
			int w = Integer.parseInt(size[1]);
			basin = 96;

			Cell[][] cells = new Cell[h][w];
			// Reading the map
			for (int lat = 0; lat < h; lat++) {
				String[] altitudes = br.readLine().split(" ");
				for (int lon = 0; lon < w; lon++) {
					cells[lat][lon] = new Cell();
					cells[lat][lon].altitude = Integer.valueOf(altitudes[lon]);
				}
			}

			for (int lat = 0; lat < h; lat++)
				for (int lon = 0; lon < w; lon++)
					drainage(cells, lat, lon);

			for (int lat = 0; lat < h; lat++)
				for (int lon = 0; lon < w; lon++)
					traverse(cells, cells[lat][lon]);

			for (int lat = 0; lat < h; lat++)
				for (int lon = 0; lon < w; lon++)
					System.out.println(cells[lat][lon].basin.intValue());
		}

		System.out.println("Time taken: " + (System.currentTimeMillis() - st));
	}

	private static final void drainage(final Cell[][] cells, int lat, int lon) {

		int cur = cells[lat][lon].altitude;
		List<int[]> info = new ArrayList<int[]>();
		if ((lat - 1 > -1) && cur > cells[lat - 1][lon].altitude)
			info.add(new int[] { lat - 1, lon });
		if ((lon - 1 > -1) && cur > cells[lat][lon - 1].altitude)
			info.add(new int[] { lat, lon - 1 });
		if ((lon + 1 < cells[lat].length) && cur > cells[lat][lon + 1].altitude)
			info.add(new int[] { lat, lon + 1 });
		if (lat + 1 < cells.length && cur > cells[lat + 1][lon].altitude)
			info.add(new int[] { lat + 1, lon });

		if (!info.isEmpty()) {
			Collections.sort(info, new Comparator<int[]>() {
				@Override
				public int compare(int[] a, int[] b) {
					if (cells[a[0]][a[1]].altitude > cells[b[0]][b[1]].altitude)
						return 1;
					return 0;
				}

			});

			int[] c = info.get(0);
			cells[lat][lon].outFlow = Integer.highestOneBit((c[0] << 8) | c[1]);
			cells[c[0]][c[1]].inFlow.add((lat << 8) | lon);
		} else {
			cells[lat][lon].sink = true;
			cells[lat][lon].basin = basin++;
		}
	}

	private static final void traverse(final Cell[][] cells, final Cell c) {

		for (int i = 0; i < c.inFlow.size(); i++) {
			int lon = c.inFlow.get(i).byteValue();
			int lat = c.inFlow.get(i) >> 8;
			cells[lat][lon].basin = c.basin;
			traverse(cells, cells[lat][lon]);
		}

		return;
	}
}

class Cell {

	Integer altitude;
	List<Integer> inFlow;
	Integer outFlow;
	Integer basin;
	boolean sink;

	{
		inFlow = new ArrayList<Integer>();
	}
}