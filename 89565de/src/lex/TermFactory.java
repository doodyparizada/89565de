package lex;

import java.util.HashMap;
import java.util.Map;

import pos.Pos;
// XXX 2 maybe the factory isn't so good after all... :(
// ask doody tomorrow
public class TermFactory {

	// SHOULD HAVE A GET ENTAILING AND GET ENTAILED.
	// XXX THIS WOULD SAVE TIME IN GetAllMatches
	// because a there can be only one term with the same pos,term
	public Term get(String term, Pos pos) {
		String key = term+"_"+pos;
		if (!map.containsKey(key)){
			map.put(key,new Term(term,pos));
		}
		return map.get(key);
	}
	
	private Map<String,Term> map;
	
	
	// SINGLETON PATTERN
	private TermFactory(){
		// init factory
		map = new HashMap<String,Term>();
	}
	
	public static TermFactory instance = new TermFactory();
}
