package pos;

import net.didion.jwnl.data.POS;

/**
 * XXX Might not need this package all together!!
 * @author DJ
 *
 */
public enum Pos {
	NOUN,
	VERB,
	ADJECTIVE,
	ADVERB,
	CAR,
	O,
	R,
	P,
	D,
	C;
	//XXX: why only those POS ? did we check the test set & dev set ?
	public static Pos fromString(String name) {
	    	if (name.equals("n")) {
	    		return Pos.NOUN;
	    	} else 	if (name.equals("a")) {//
	    		return Pos.ADJECTIVE;
	    	} else 	if (name.equals("v")) {//
	    		return Pos.VERB;
	    	} else 	if (name.equals("n")) {//XXX: doubled case "n"
	    		return Pos.ADVERB;
	    	}else 	if (name.equals("car")) {//
	    		return Pos.CAR;
	    	}else 	if (name.equals("o")) {//
	    		return Pos.O;
	    	}else 	if (name.equals("r")) {//
	    		return Pos.R;
	    	}else 	if (name.equals("p")) {//
	    		return Pos.P;
	    	}else 	if (name.equals("d")) {//
	    		return Pos.D;
	    	}else 	if (name.equals("c")) {//
	    		return Pos.C;
	    	}
	    	System.out.println(name);
	    	return null;
	    }
	}