package de.schaefer.tools.language;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import javax.naming.ConfigurationException;

import de.schaefer.tools.log.Logger;

public class LanguagePropertyReader {

    private static final boolean DEBUG = false;

    private static File languageConfig;
    private static String languageSign;
    private static HashMap<String, String> dict;
    private static LinkedList<String> availibleLanguages = new LinkedList<String>();

    public static void setLanguageSign(String s) throws ConfigurationException {
	if (availibleLanguages.contains(s)) {
	    languageSign = s;
	    reloadTexts();
	} else {
	    throw new ConfigurationException("Language " + languageSign
		    + " is not supported :(");
	}
    }

    public static void setFile(File config) {
	languageConfig = config;
	try {
	    Scanner sc = new Scanner(languageConfig, "UTF-8");
	    String line;
	    String lang;
	    while (sc.hasNextLine()) {
		line = sc.nextLine();
		if (line.startsWith("#")) {
		    lang = line.substring(line.lastIndexOf('#') + 2);
		    if (DEBUG)
			System.out.println("Language " + lang + " found");

		    availibleLanguages.add(lang);
		}
	    }
	    sc.close();
	    reloadTexts();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
    }

    public static void reloadTexts() {
	if (!(languageSign == null)) {
	    dict = new HashMap<String, String>();
	    try {
		Scanner sc = new Scanner(languageConfig, "UTF-8");
		String line, key, value;
		;
		boolean inLang = false;
		int mid;
		while (sc.hasNextLine()) {
		    line = sc.nextLine();
		    if (line.startsWith("#")) {
			if (line.trim().endsWith(languageSign)) {
			    inLang = true;
			} else {
			    inLang = false;
			}
		    } else if (inLang) {
			mid = line.indexOf(":");
			if (mid > 0) {
			    key = line.substring(0, mid);
			    value = line.substring(mid + 1);

			    if (DEBUG) {
				System.out.println("Translation found: " + key
					+ " := " + value);
			    }

			    dict.put(key, value);
			}
		    }
		}
		sc.close();
	    } catch (FileNotFoundException e) {
		e.printStackTrace();
	    }
	}
    }

    public static String get(String s) {
	String translation = dict.get(s);
	if (translation == null) {
	    throw new NullPointerException("Translation of \"" + s
		    + "\" not defined!");
	}
	return translation;
    }

}