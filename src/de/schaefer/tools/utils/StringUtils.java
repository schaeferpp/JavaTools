package de.schaefer.tools.utils;

public final class StringUtils {
    /**
     * Start inclusive, end exclusive
     * 
     * @param s
     * @param start
     * @param end
     * @return
     */
    public static final String removeBetween(String s, int start, int end) {
	StringBuilder res = new StringBuilder(s.substring(0, start + 1));
	res.append(s.substring(end));

	return res.toString();
    }

    public static final String insertAtAndRemoveRest(String s, int index,
	    String t) {
	StringBuilder res = new StringBuilder(s.substring(0, index));
	res.append(t);

	return res.toString();
    }
}
