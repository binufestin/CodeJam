package jam.code.w2cj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class W2CodeJam {

	private static Map<Character, List<Adjacent>> cMap = new HashMap<Character, List<Adjacent>>();

	public static void main(String[] args) throws NumberFormatException,
			IOException {

		long st = System.currentTimeMillis();

		String q = "welcome to code jam";
		char[] qa = q.toCharArray();
		for (int i = 0; i < qa.length; i++) {
			if (cMap.get(qa[i]) == null)
				cMap.put(qa[i], new ArrayList<Adjacent>());
			if (i == 0)
				cMap.get(qa[i]).add(new Adjacent(qa[i + 1], null));
			else if (i == (qa.length - 1))
				cMap.get(qa[i]).add(new Adjacent(null, qa[i - 1]));
			else
				cMap.get(qa[i]).add(new Adjacent(qa[i + 1], qa[i - 1]));
		}

		BufferedReader br = new BufferedReader(new FileReader(new File(
				"D:/GoogleCodeJam/Welcome2CodeJam/C-small-practice.in")));
		int tc = Integer.parseInt(br.readLine());

		for (int i = 0; i < tc; i++) {
			String seq = br.readLine();
			char[] seqa = seq.toCharArray();
			List<Character> valid = new ArrayList<Character>();
			for (int j = 0; j < seqa.length; j++)
				if (q.indexOf(seqa[j]) > -1 && isValidPosition(seq, seqa, j))
					;
		}

		System.out.println(System.currentTimeMillis() - st);
	}

	private static final boolean isValidPosition(String seq, char[] seqa,
			int pos) {
		List<Adjacent> adj = cMap.get(seqa[pos]);
		for (Adjacent a : adj) {
			// if(a.next != null && seq.indexOf(ch))
		}
		return true;
	}
}

class Adjacent {

	public Adjacent(Character next, Character previous) {
		this.next = next;
		this.previous = previous;
	}

	Character next;
	Character previous;
}