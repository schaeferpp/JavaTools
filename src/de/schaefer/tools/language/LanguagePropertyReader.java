package de.schaefer.tools.language;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import javax.naming.ConfigurationException;

import de.schaefer.tools.log.Logger;

public class LanguagePropertyReader {

    // private static File languageConfig;
    private static InputStream langInputStream;
    private static String languageSign;
    private static HashMap<String, String> dict;
    private static LinkedList<String> availibleLanguages = new LinkedList<String>();
    private static Class<? extends Object> clazz;
    private static String resource;
    private static File languageFile;

    public static void setLanguageSign(String s) throws ConfigurationException,
	    IOException {
	if (availibleLanguages.contains(s)) {
	    languageSign = s;
	    reloadTexts();
	} else {
	    throw new ConfigurationException("Language " + languageSign
		    + " is not supported");
	}
    }

    private static void reassignStream() throws FileNotFoundException {
	if (clazz != null && resource != null) {
	    langInputStream = clazz.getResourceAsStream(resource);
	    if(langInputStream == null) {
		throw new FileNotFoundException("Unable to get inputstream for reading language file.");
	    }
	    Logger.DEBUG.logMessage("Stream reassigned");
	} else {
	    langInputStream = new FileInputStream(languageFile);
	}
    }

    public static void setFile(File config) throws IOException {
	languageFile = config;
	reassignStream();
    }

    public static void setInputStream(Class<? extends Object> clazz,
	    String resource) throws IOException {
	LanguagePropertyReader.clazz = clazz;
	LanguagePropertyReader.resource = resource;
	reloadLanguages();
	reloadTexts();
    }

    private static void reloadLanguages() throws IOException {
	reassignStream();
	Scanner sc = new Scanner(langInputStream, "UTF-8");
	String line;
	String lang;
	availibleLanguages = new LinkedList<String>();
	while (sc.hasNextLine()) {
	    line = sc.nextLine();
	    if (line.startsWith("#")) {
		lang = line.substring(line.lastIndexOf('#') + 2);
		Logger.DEBUG.logMessage("Language " + lang + " found.");

		availibleLanguages.add(lang);
	    }
	}
	sc.close();
    }

    public static void reloadTexts() throws IOException {
	reassignStream();
	if (!(languageSign == null)) {
	    dict = new HashMap<String, String>();
	    Scanner sc = new Scanner(langInputStream, "UTF-8");
	    String line, key, value;

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
			dict.put(key, value);
		    }
		}
	    }
	    sc.close();
	}
    }

    public static String get(String s) {
	String translation = null;
	if (dict != null) {
	    translation = dict.get(s);
	}
	return translation;
    }

}