package jam.code.oddmanout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class OddManOut {

	public static void main(String[] args) throws Exception {
		// long st = System.currentTimeMillis();
		BufferedReader br = new BufferedReader(new FileReader(new File(
		// "D:/GoogleCodeJam/OddManOut/A-small-practice.in")));
				"D:/GoogleCodeJam/OddManOut/A-large-practice.in")));
		int tc = Integer.valueOf(br.readLine());
		Map<Integer, List<String>> guests = new TreeMap<Integer, List<String>>();
		for (int i = 1; i <= tc; i++) {
			Integer.valueOf(br.readLine());
			String[] iIDs = br.readLine().split(" ");
			List<String> oddMan = new ArrayList<String>();
			for (int j = 0; j < iIDs.length; j++) {
				if (oddMan.contains(iIDs[j]))
					oddMan.remove(iIDs[j]);
				else
					oddMan.add(iIDs[j]);
			}
			if (!oddMan.isEmpty())
				guests.put(i, oddMan);
		}
		for (Entry<Integer, List<String>> entry : guests.entrySet())
			System.out.println("Case #" + entry.getKey() + ": "
					+ entry.getValue().get(0));
		// System.out.println("Time Taken: " + (System.currentTimeMillis() -
		// st));
	}
}
