package de.schaefer.tools.log;

import java.util.LinkedList;

import de.schaefer.tools.language.LanguagePropertyReader;

public class Logger
{
	public static final int LOG_LEVEL_DEBUG = 1000;
	public static final int LOG_LEVEL_INFO = 1010;
	public static final int LOG_LEVEL_WARN = 1020;
	public static final int LOG_LEVEL_ERROR = 1030;
	public static final int LOG_LEVEL_FATAL = 1040;

	private static Logger logger;

	private LinkedList<LoggerOutput> debugStreams = new LinkedList<LoggerOutput>();
	private LinkedList<LoggerOutput> infoStreams = new LinkedList<LoggerOutput>();
	private LinkedList<LoggerOutput> warnStreams = new LinkedList<LoggerOutput>();
	private LinkedList<LoggerOutput> errorStreams = new LinkedList<LoggerOutput>();
	private LinkedList<LoggerOutput> fatalStreams = new LinkedList<LoggerOutput>();

	private int currentLogLevel = LOG_LEVEL_WARN;
	private boolean logOnLowerStreams = false;

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

	public void logMessage(String message, int loglevel)
	{
		String title = null;
		if (loglevel >= currentLogLevel)
		{
			switch (loglevel)
			{
			case LOG_LEVEL_FATAL:
				title = LanguagePropertyReader.get("logger_fataltitle");
				if (title == null)
				{
					title = "Fatal error!";
				}
				push(message, title, fatalStreams);
				if (!logOnLowerStreams)
					break;
			case LOG_LEVEL_ERROR:
				title = LanguagePropertyReader.get("logger_errortitle");
				if (title == null)
				{
					title = "Error!";
				}
				push(message, title, errorStreams);
				if (!logOnLowerStreams)
					break;
			case LOG_LEVEL_WARN:
				title = LanguagePropertyReader.get("logger_warntitle");
				if (title == null)
				{
					title = "Warning!";
				}
				push(message, title, warnStreams);
				if (!logOnLowerStreams)
					break;
			case LOG_LEVEL_INFO:
				title = LanguagePropertyReader.get("logger_infotitle");
				if (title == null)
				{
					title = "Information";
				}
				push(message, title, infoStreams);
				if (!logOnLowerStreams)
					break;
			case LOG_LEVEL_DEBUG:
				title = LanguagePropertyReader.get("logger_debugtitle");
				if (title == null)
				{
					title = "Debug";
				}
				push(message, title, debugStreams);
				break;
			default:
				title = LanguagePropertyReader.get("logger_undefined");
				if (title == null)
				{
					title = "??";
				}
				push(message, title, warnStreams);
			}
		}
	}

	public void logException(Throwable t, String message, int loglevel)
	{
		String title = null;
		if (loglevel >= currentLogLevel)
		{
			switch (loglevel)
			{
			case LOG_LEVEL_FATAL:
				title = LanguagePropertyReader.get("logger_fataltitle");
				if (title == null)
				{
					title = "Fatal error!";
				}
				push(message, title, makeStacktraceReadable(t), debugStreams);
				if (!logOnLowerStreams)
					break;
			case LOG_LEVEL_ERROR:
				title = LanguagePropertyReader.get("logger_errortitle");
				if (title == null)
				{
					title = "Error!";
				}
				push(message, title, makeStacktraceReadable(t), debugStreams);
				if (!logOnLowerStreams)
					break;
			case LOG_LEVEL_WARN:
				title = LanguagePropertyReader.get("logger_warntitle");
				if (title == null)
				{
					title = "Warning!";
				}
				push(message, title, makeStacktraceReadable(t), debugStreams);
				if (!logOnLowerStreams)
					break;
			case LOG_LEVEL_INFO:
				title = LanguagePropertyReader.get("logger_infotitle");
				if (title == null)
				{
					title = "Information";
				}
				push(message, title, makeStacktraceReadable(t), debugStreams);
				if (!logOnLowerStreams)
					break;
			case LOG_LEVEL_DEBUG:
				title = LanguagePropertyReader.get("logger_debugtitle");
				if (title == null)
				{
					title = "Debug";
				}
				push(message, title, makeStacktraceReadable(t), debugStreams);
				break;
			default:
				title = LanguagePropertyReader.get("logger_undefined");
				if (title == null)
				{
					title = "??";
				}
				push(message, title, makeStacktraceReadable(t), debugStreams);
			}
		}
	}

	public void logException(Throwable t, int loglevel)
	{
		logException(t, "", loglevel);
	}

	private void push(String message, String title,
			LinkedList<LoggerOutput> streams)
	{
		for (LoggerOutput o : streams)
		{
			o.log(title, message);
		}
	}

	private String makeStacktraceReadable(Throwable t)
	{
		StringBuilder res = new StringBuilder();

		for (StackTraceElement elem : t.getStackTrace())
		{
			res.append(String.format("Line: %7d\tin %s.%s()%n",
					elem.getLineNumber(), elem.getClassName(),
					elem.getMethodName()));
		}

		return res.toString();
	}

	private void push(String message, String title, String stacktrace,
			LinkedList<LoggerOutput> streams)
	{
		for (LoggerOutput o : streams)
		{
			o.log(title, message);
		}
	}

	public void addLoggerOutput(int loglevel, LoggerOutput output)
	{
		switch (loglevel)
		{
		case LOG_LEVEL_DEBUG:
			debugStreams.add(output);
			break;
		case LOG_LEVEL_INFO:
			infoStreams.add(output);
			break;
		case LOG_LEVEL_WARN:
			warnStreams.add(output);
			break;
		case LOG_LEVEL_ERROR:
			errorStreams.add(output);
			break;
		case LOG_LEVEL_FATAL:
			fatalStreams.add(output);
			break;
		}
	}

	public void setLogLevel(int loglevel)
	{
		this.currentLogLevel = loglevel;
	}
}
