package jam.code.alienlanguage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class AlienLanguage {

	public static void main(String[] args) throws Exception {
		long st = System.currentTimeMillis();
		// System.out.println("Start");
		BufferedReader br = new BufferedReader(new FileReader(new File(
				"D:/GoogleCodeJam/AlienLanguage/A-large-practice.in")));
		// "D:/GoogleCodeJam/AlienLanguage/A-small-practice.in")));
		String[] fl = br.readLine().split(" ");
		int l = Integer.valueOf(fl[0]);
		int p = Integer.valueOf(fl[1]);
		int tc = Integer.valueOf(fl[2]);
		List<String> patterns = new LinkedList<String>();
		while (p > 0) {
			patterns.add(br.readLine());
			--p;
		}
		Map<Integer, List<String>> testCases = new TreeMap<Integer, List<String>>();
		for (int i = 1; i <= tc; i++) {
			List<String> tcNodes = new LinkedList<String>();
			testCases.put(i, tcNodes);
			String testCase = br.readLine();
			// System.out.println("test case: " + testCase);
			char[] tcChars = testCase.toCharArray();
			int s = 0;
			boolean group = false;
			for (int j = 0; j < tcChars.length; j++) {
				if (tcChars[j] == '(') {
					s = j + 1;
					group = true;
				} else if (tcChars[j] == ')') {
					tcNodes.add(new String(tcChars, s, j - s));
					group = false;
				} else if (!group)
					tcNodes.add(new String(tcChars, j, 1));
			}
			// for (String str : tcNodes) {
			// System.out.println(str);
			// }
			// System.out.println("end");
		}

		Map<Integer, Integer> matches = new TreeMap<Integer, Integer>();
		for (int i = 1; i <= tc; i++)
			matches.put(i, 0);
		for (Entry<Integer, List<String>> entry : testCases.entrySet()) {
			List<String> tca = entry.getValue();
			for (String str : patterns) {
				char[] ca = str.toCharArray();
				boolean f = true;
				for (int i = 0; i < l; i++) {
					if (tca.get(i).indexOf(ca[i]) > -1)
						continue;
					else {
						f = false;
						break;
					}
				}
				if (f)
					matches
							.put(entry.getKey(),
									matches.get(entry.getKey()) + 1);
			}
		}
		for (Entry<Integer, Integer> entry : matches.entrySet())
			System.out.println("Case #" + entry.getKey() + ": "
					+ entry.getValue());
		System.out.println("Time Taken: " + (System.currentTimeMillis() - st));
	}
}
