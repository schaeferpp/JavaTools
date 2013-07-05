package de.schaefer.tools.log;

import java.util.LinkedList;

import de.schaefer.tools.language.LanguagePropertyReader;

public class Logger
{

	public enum Level
	{
		DEBUG, INFO, WARN, ERROR, FATAL
	}

	private static Logger logger;

	private LinkedList<LoggerOutput> debugStreams = new LinkedList<LoggerOutput>();
	private LinkedList<LoggerOutput> infoStreams = new LinkedList<LoggerOutput>();
	private LinkedList<LoggerOutput> warnStreams = new LinkedList<LoggerOutput>();
	private LinkedList<LoggerOutput> errorStreams = new LinkedList<LoggerOutput>();
	private LinkedList<LoggerOutput> fatalStreams = new LinkedList<LoggerOutput>();

	private Logger()
	{

	}

	public static synchronized Logger getLogger()
	{
		if (logger == null)
		{
			logger = new Logger();
		}
		return logger;
	}

	public void logMessage(String message, Logger.Level loglevel)
	{
		String title = null;
		StringBuilder messageBuilder = new StringBuilder("Message: ");
		switch (loglevel)
		{
		case DEBUG:
			title = LanguagePropertyReader.get("debugtitle");
			if (title == null)
			{
				System.out.println("error");
			} else {
				System.out.println(title);
			}
			break;
		case INFO:
			break;
		case WARN:
			break;
		case ERROR:
			break;
		case FATAL:
			break;
		}
	}

	public void logException(Throwable t, String message, Logger.Level loglevel)
	{

	}

	public void logException(Throwable t, Logger.Level loglevel)
	{

	}

	private void log()
	{

	}
}
