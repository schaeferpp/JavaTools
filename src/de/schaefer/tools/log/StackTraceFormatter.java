package de.schaefer.tools.log;

import java.util.Arrays;

public enum StackTraceFormatter {

	FULL, JAVA_STYLE, CURRENT_FUNCTION, SIMPLE;

	public String format(StackTraceElement[] elems, String message) {
		switch (this) {
		case CURRENT_FUNCTION:
			break;
		case FULL:
			break;
		case JAVA_STYLE:
			StringBuilder sb = new StringBuilder();
			sb.append("StackTrace in thread "
					+ Thread.currentThread().getName() + " " + message + "\n");
			for (StackTraceElement e : elems) {
				if (!e.toString().contains("java.lang.Thread.getStackTrace"))
					sb.append("\tat " + e + "\n");
			}
			return sb.toString();
		case SIMPLE:
			return Arrays.toString(elems);
		}
		return null;
	}

	public String format(StackTraceElement[] elems) {
		return format(elems, "");
	}

}
