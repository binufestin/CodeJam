package jam.code.polygraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class PolygraphInterpreter {

	public static void main(String[] args) throws Exception {

		long st = System.currentTimeMillis();
		BufferedReader br = new BufferedReader(new FileReader(new File(
		// "D:/GoogleCodeJam/Polygraph/D-large-practice.in")));
				"D:/GoogleCodeJam/Polygraph/D-small-practice.in")));
		int tc = Integer.valueOf(br.readLine());

		for (int i = 1; i <= tc; i++) {
			String kbase[] = br.readLine().split(" ");

			// Visualizing all the inhabitants on our way and taking a note of
			// them
			Map<Integer, Inhabitant> inhabitants = new TreeMap<Integer, Inhabitant>();
			for (int j = 1; j <= Integer.valueOf(kbase[0]); j++) {
				Inhabitant inhabitant = new Inhabitant(j);
				inhabitants.put(j, inhabitant);
			}

			List<String> statements = new ArrayList<String>();
			// Memorizing all the statements by inhabitants
			for (int j = 0; j < Integer.valueOf(kbase[1]); j++) {
				String stmt = br.readLine();
				String[] statement = stmt.split(" ");
				if (!statements.contains(stmt)) {
					if (statement.length % 2 == 0) {
						inhabitants.get(Integer.valueOf(statement[0])).groupStatements
								.add(statement);
						statements.add(stmt);
					} else {
						inhabitants.get(Integer.valueOf(statement[0])).statements
								.add(statement);
						statements.add(stmt);
					}
				}
			}

			// Deriving statements/groupStatements for each Inhabitant
			for (Entry<Integer, Inhabitant> e : inhabitants.entrySet())
				deriveGroupStatements(e.getValue());

			// Analyzing the statements by each inhabitant and trying to
			// identify each of the inhabitant's city
			for (Entry<Integer, Inhabitant> inhaEntry : inhabitants.entrySet()) {
				List<String> tt = new LinkedList<String>();
				List<String> lv = new LinkedList<String>();
				List<String> ucA = new LinkedList<String>();
				List<String> ucB = new LinkedList<String>();

				for (String[] gs : inhaEntry.getValue().groupStatements) {
					if (gs[1].equals("S")) {
						if ((ucB.contains(gs[2]) && ucA.contains(gs[3]))
								|| (ucB.contains(gs[3]) && ucA.contains(gs[2]))) {
							inhabitants.get(Integer.valueOf(gs[0])).identified = true;
							inhabitants.get(Integer.valueOf(gs[0])).city = "L";
							lv.add(gs[0]);
							break;
						}

						if (ucB.contains(gs[2]) || ucB.contains(gs[3])) {
							ucB.add(gs[2]);
							ucB.add(gs[3]);
						} else {
							ucA.add(gs[2]);
							ucA.add(gs[3]);
						}

						if (gs[2].equals(gs[0])) {
							inhabitants.get(Integer.valueOf(gs[3])).identified = true;
							inhabitants.get(Integer.valueOf(gs[3])).city = "T";
							tt.add(gs[3]);
						} else if (gs[3].equals(gs[0])) {
							inhabitants.get(Integer.valueOf(gs[2])).identified = true;
							inhabitants.get(Integer.valueOf(gs[2])).city = "T";
							tt.add(gs[2]);
						}
					} else {
						if ((ucB.contains(gs[2]) && ucB.contains(gs[3]))
								|| (ucA.contains(gs[2]) && ucA.contains(gs[3]))) {
							inhabitants.get(Integer.valueOf(gs[0])).identified = true;
							inhabitants.get(Integer.valueOf(gs[0])).city = "L";
							lv.add(gs[0]);
							break;

						}

						if (ucB.contains(gs[2])) {
							ucB.add(gs[2]);
							ucA.add(gs[3]);
						} else {
							ucA.add(gs[2]);
							ucB.add(gs[3]);
						}

						if (gs[2].equals(gs[0])) {
							inhabitants.get(Integer.valueOf(gs[3])).identified = true;
							inhabitants.get(Integer.valueOf(gs[3])).city = "L";
							lv.add(gs[3]);
						} else if (gs[3].equals(gs[0])) {
							inhabitants.get(Integer.valueOf(gs[2])).identified = true;
							inhabitants.get(Integer.valueOf(gs[2])).city = "L";
							lv.add(gs[2]);
						}
					}
				}

			}

			// if (i == 93)
			// System.out.println("98");
			while (identifyUncertainInhabitants(inhabitants))
				;

			System.out.print("Case #" + i + ":");
			for (Entry<Integer, Inhabitant> in : inhabitants.entrySet())
				System.out.print(" " + in.getValue().city);
			System.out.println();
		}

		System.out.println("Time Taken: " + (System.currentTimeMillis() - st));
	}

	private static void deriveGroupStatements(Inhabitant inhabitant) {
		List<String[]> statements = inhabitant.statements;
		List<String[]> groupStatements = inhabitant.groupStatements;
		for (int i = 0; i < statements.size() - 1; i++) {
			for (int j = i; j < statements.size() - 1; j++) {
				String[] gs = new String[4];
				gs[0] = statements.get(j)[0];
				gs[2] = statements.get(j)[2];
				gs[3] = statements.get(j + 1)[2];

				if (statements.get(j)[1].equals(statements.get(j + 1)[1]))
					gs[1] = "S";
				else
					gs[1] = "D";
				if (!groupStatements.contains(gs))
					groupStatements.add(gs);
			}
		}
	}

	private static boolean identifyUncertainInhabitants(
			Map<Integer, Inhabitant> inhabitants) {
		boolean gotSomeGuys = false;

		for (Entry<Integer, Inhabitant> in : inhabitants.entrySet()) {
			if (in.getValue().city.equals("-")) {
				for (String[] gs : in.getValue().groupStatements) {
					if (gs[1].equals("S") || gs[1].equals("D")) {
						if (inhabitants.get(Integer.valueOf(gs[2])).identified
								&& inhabitants.get(Integer.valueOf(gs[3])).identified
								&& (inhabitants.get(Integer.valueOf(gs[2])).city
										.equals(inhabitants.get(Integer
												.valueOf(gs[3])).city))) {
							inhabitants.get(Integer.valueOf(gs[0])).city = "T";
							inhabitants.get(Integer.valueOf(gs[0])).identified = true;
							gotSomeGuys = true;
						} else if (inhabitants.get(Integer.valueOf(gs[2])).identified
								&& inhabitants.get(Integer.valueOf(gs[3])).identified) {
							inhabitants.get(Integer.valueOf(gs[0])).city = "L";
							inhabitants.get(Integer.valueOf(gs[0])).identified = true;
							gotSomeGuys = true;
						}
					}
				}

				for (String[] s : in.getValue().statements) {
					if (inhabitants.get(Integer.valueOf(s[2])).identified
							&& s[1].equals(inhabitants.get(Integer
									.valueOf(s[2])).city)) {
						inhabitants.get(Integer.valueOf(s[0])).city = "T";
						inhabitants.get(Integer.valueOf(s[0])).identified = true;
						gotSomeGuys = true;
					} else if (inhabitants.get(Integer.valueOf(s[2])).identified) {
						inhabitants.get(Integer.valueOf(s[0])).city = "L";
						inhabitants.get(Integer.valueOf(s[0])).identified = true;
						gotSomeGuys = true;
					}
				}
			} else {
				// List<String> ucA = new LinkedList<String>();
				// List<String> ucB = new LinkedList<String>();
				boolean truth = in.getValue().city.equals("T");

				for (String[] gs : in.getValue().groupStatements) {
					if ((gs[1].equals("S") && truth)
							|| (gs[1].equals("D") && !truth)) {
						if (inhabitants.get(Integer.valueOf(gs[2])).identified) {
							if (!inhabitants.get(Integer.valueOf(gs[3])).identified) {
								inhabitants.get(Integer.valueOf(gs[3])).identified = true;
								inhabitants.get(Integer.valueOf(gs[3])).city = inhabitants
										.get(Integer.valueOf(gs[2])).city;
								gotSomeGuys = true;
							}
						} else if (inhabitants.get(Integer.valueOf(gs[3])).identified) {
							if (!inhabitants.get(Integer.valueOf(gs[2])).identified) {
								inhabitants.get(Integer.valueOf(gs[2])).identified = true;
								inhabitants.get(Integer.valueOf(gs[2])).city = inhabitants
										.get(Integer.valueOf(gs[3])).city;
								gotSomeGuys = true;
							}
						} // else if (ucB.contains(gs[2]) ||
						// ucB.contains(gs[3])) {
						// ucB.add(gs[2]);
						// ucB.add(gs[3]);
						// } else {
						// ucA.add(gs[2]);
						// ucA.add(gs[3]);
						// }

					} else {
						if (inhabitants.get(Integer.valueOf(gs[2])).identified) {
							if (!inhabitants.get(Integer.valueOf(gs[3])).identified) {
								inhabitants.get(Integer.valueOf(gs[3])).identified = true;
								inhabitants.get(Integer.valueOf(gs[3])).city = inhabitants
										.get(Integer.valueOf(gs[2])).city
										.equals("T") ? "L" : "T";
								gotSomeGuys = true;
							}
						} else if (inhabitants.get(Integer.valueOf(gs[3])).identified) {
							if (!inhabitants.get(Integer.valueOf(gs[2])).identified) {
								inhabitants.get(Integer.valueOf(gs[2])).identified = true;
								inhabitants.get(Integer.valueOf(gs[2])).city = inhabitants
										.get(Integer.valueOf(gs[3])).city
										.equals("T") ? "L" : "T";
								gotSomeGuys = true;
							}
						} // else if (ucB.contains(gs[2])) {
						// ucB.add(gs[2]);
						// ucA.add(gs[3]);
						// } else {
						// ucA.add(gs[2]);
						// ucB.add(gs[3]);
						// }
					}
				}

				/*
				 * for (Entry<Integer, Inhabitant> inhabitant : inhabitants
				 * .entrySet()) { if (inhabitant.getValue().identified) { if
				 * (ucA.contains(String.valueOf(inhabitant.getKey()))) { for
				 * (String inha : ucA) {
				 * inhabitants.get(Integer.valueOf(inha)).city = inhabitant
				 * .getValue().city;
				 * inhabitants.get(Integer.valueOf(inha)).identified = true; }
				 * ucA.clear(); } else if
				 * (ucB.contains(String.valueOf(inhabitant .getKey()))) { for
				 * (String inha : ucB) {
				 * inhabitants.get(Integer.valueOf(inha)).city = inhabitant
				 * .getValue().city;
				 * inhabitants.get(Integer.valueOf(inha)).identified = true; }
				 * ucB.clear(); }
				 * 
				 * } }
				 */
				for (String[] s : in.getValue().statements) {
					if ((s[1].equals("T") && truth)
							|| (s[1].equals("L") && !truth)) {
						if (!inhabitants.get(Integer.valueOf(s[2])).identified) {
							inhabitants.get(Integer.valueOf(s[2])).city = "T";
							inhabitants.get(Integer.valueOf(s[2])).identified = true;
							gotSomeGuys = true;
						}
					} else if ((s[1].equals("L") && truth)
							|| (s[1].equals("T") && !truth)) {
						if (!inhabitants.get(Integer.valueOf(s[2])).identified) {
							inhabitants.get(Integer.valueOf(s[2])).city = "L";
							inhabitants.get(Integer.valueOf(s[2])).identified = true;
							gotSomeGuys = true;
						}
					}
				}
			}
		}
		return gotSomeGuys;
	}
}
