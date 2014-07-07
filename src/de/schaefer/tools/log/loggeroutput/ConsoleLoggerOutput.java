package de.schaefer.tools.log.loggeroutput;

public class ConsoleLoggerOutput implements LoggerOutput {

	@Override
	public void log(String title, String message) {
		System.out.println("(" + title + ") - " + message);
	}

	@Override
	public void logStacktrace(String title, String message, String stacktrace) {
		System.out.println("(" + title + ") - " + message + "\n" + stacktrace
				+ "\n\n");
	}
}
