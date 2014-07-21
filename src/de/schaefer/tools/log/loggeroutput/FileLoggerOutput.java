package de.schaefer.tools.log.loggeroutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.schaefer.tools.log.Logger;

public class FileLoggerOutput implements LoggerOutput {

    File outputFile;
    PrintWriter fileWriter;

    public FileLoggerOutput(File f) {
	this.outputFile = f;

	try {
	    outputFile.createNewFile();
	} catch (FileNotFoundException e) {
	    Logger.ERROR.logException(e, "Error while writing to Filelogger");
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @Override
    public void log(String title, String message) {
	try {
	    fileWriter = new PrintWriter(new FileOutputStream(outputFile, true));
	    fileWriter.println(String.format("%s - %s :: %s",
		    new SimpleDateFormat("yyyy-MM-dd mm:HH:ss")
			    .format(new Date()), title, message));
	    System.out.println(String.format("%s - %s :: %s",
		    new SimpleDateFormat("yyyy-MM-dd mm:HH:ss")
			    .format(new Date()), title, message));
	    fileWriter.println("--------------------------------");
	    fileWriter.println("");
	    fileWriter.close();
	} catch (FileNotFoundException e) {
	    Logger.ERROR.logException(e, "Error while writing to Filelogger");
	}
    }

    @Override
    public void logStacktrace(String title, String message, String stacktrace) {
	try {
	    System.out.println("Test");
	    fileWriter = new PrintWriter(new FileOutputStream(outputFile, true));
	    fileWriter.println(String.format("%s :: %s", title, message));
	    fileWriter.println(String.format("%s :: %s", title, message));
	    fileWriter.println("--------------------------------");
	    fileWriter.println("");
	    fileWriter.close();
	} catch (FileNotFoundException e) {
	    Logger.ERROR.logException(e, "Error while writing to Filelogger");
	}
    }

}
