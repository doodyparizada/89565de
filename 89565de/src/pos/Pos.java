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
	ADVERB;
	
	public static Pos fromString(String name) {
	    	if (name.equals("n")) {
	    		return Pos.NOUN;
	    	} else 	if (name.equals("a")) {
	    		return Pos.ADJECTIVE;
	    	} else 	if (name.equals("v")) {
	    		return Pos.VERB;
	    	} else 	if (name.equals("n")) {
	    		return Pos.ADVERB;
	    	}
	    	return null;
	    }	
	}