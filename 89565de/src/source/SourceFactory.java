package source;

import java.util.LinkedList;
import java.util.List;

public class SourceFactory {


	public List<Source> getSources() {
		return sources;
	}
	public void clear() {
		sources.clear();
	}
	public void register(Source s) {
		sources.add(s);
	}
	private List<Source> sources;

	/*
	 * Singleton
	 */

	private SourceFactory() {
		sources = new LinkedList<Source>();
	}
	public static SourceFactory getInstance() {
		if (instance == null) {
			instance = new SourceFactory();
		}
		return instance;
	}
	private static SourceFactory instance = null;

	public Source getSource(String name) {
		for(Source source:sources){
			if(source.getName().equals(name)){
				return source;
			}
		}
		return null;
	}
}
