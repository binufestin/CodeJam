package jam.code.polygraph;

import java.util.LinkedList;
import java.util.List;

public class Inhabitant {

	Inhabitant(int id) {
		this.id = id;
		this.statements = new LinkedList<String[]>();
		this.groupStatements = new LinkedList<String[]>();
		this.derivedStatements = new LinkedList<String[]>();
		this.derivedGroupStatements = new LinkedList<String[]>();
		this.city = "-";
		this.identified = false;

	}

	int id;
	List<String[]> statements;
	List<String[]> groupStatements;
	List<String[]> derivedStatements;
	List<String[]> derivedGroupStatements;
	String city;
	boolean identified;
}
