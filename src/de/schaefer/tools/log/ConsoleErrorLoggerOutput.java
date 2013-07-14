package de.schaefer.tools.log;

public class ConsoleErrorLoggerOutput implements LoggerOutput {

    @Override
    public void log(String title, String message) {
	System.err.println("(" + title + ") - " + message);
    }

    @Override
    public void logStacktrace(String title, String message, String stacktrace) {
	System.err.println("(" + title + ") - " + message + "\n" + stacktrace
		+ "\n\n");
    }
}
