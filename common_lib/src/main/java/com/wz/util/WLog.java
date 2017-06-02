package com.wz.util;

import android.util.Log;

import com.wz.BuildConfig;


/**
 * com.wz.selection.util
 *
 * @author wangzhe
 * @version 1.0
 * @date 2017/2/17 15:54
 */
public class WLog {
    public static final int VERBOSE = Log.VERBOSE;
    public static final int DEBUG = Log.DEBUG;
    public static final int INFO = Log.INFO;
    public static final int WARN = Log.WARN;
    public static final int ERROR = Log.ERROR;
    public static final int NONE = ERROR + 1;

    // Current log level
    public static int sLogLevel = Log.DEBUG;

    static {
        if (!BuildConfig.DEBUG) {
            sLogLevel = NONE;
        }
    }

    /**
     * Set the current log level.
     *
     * @param logLevel
     */
    public static void setLogLevel(int logLevel) {
        sLogLevel = logLevel;
        Log.i("CordovaLog", "Changing log level to " + logLevel);
    }

    /**
     * Set the current log level.
     *
     * @param logLevel
     */
    public static void setLogLevel(String logLevel) {
        if ("VERBOSE".equals(logLevel)) sLogLevel = VERBOSE;
        else if ("DEBUG".equals(logLevel)) sLogLevel = DEBUG;
        else if ("INFO".equals(logLevel)) sLogLevel = INFO;
        else if ("WARN".equals(logLevel)) sLogLevel = WARN;
        else if ("ERROR".equals(logLevel)) sLogLevel = ERROR;
        Log.i("WLog", "Changing log level to " + logLevel + "(" + sLogLevel + ")");
    }

    /**
     * Determine if log level will be logged
     *
     * @param logLevel
     * @return true if the parameter passed in is greater than or equal to the current log level
     */
    public static boolean isLoggable(int logLevel) {
        return (logLevel >= sLogLevel);
    }

    /**
     * Verbose log message.
     *
     * @param tag
     * @param s
     */
    public static void v(String tag, String s) {
        if (WLog.VERBOSE >= sLogLevel) Log.v(tag, s);
    }

    /**
     * Debug log message.
     *
     * @param tag
     * @param s
     */
    public static void d(String tag, String s) {
        if (WLog.DEBUG >= sLogLevel) Log.d(tag, s);
    }

    /**
     * Info log message.
     *
     * @param tag
     * @param s
     */
    public static void i(String tag, String s) {
        if (WLog.INFO >= sLogLevel) Log.i(tag, s);
    }

    /**
     * Warning log message.
     *
     * @param tag
     * @param s
     */
    public static void w(String tag, String s) {
        if (WLog.WARN >= sLogLevel) Log.w(tag, s);
    }

    /**
     * Error log message.
     *
     * @param tag
     * @param s
     */
    public static void e(String tag, String s) {
        if (WLog.ERROR >= sLogLevel) Log.e(tag, s);
    }

    /**
     * Verbose log message.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void v(String tag, String s, Throwable e) {
        if (WLog.VERBOSE >= sLogLevel) Log.v(tag, s, e);
    }

    /**
     * Debug log message.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void d(String tag, String s, Throwable e) {
        if (WLog.DEBUG >= sLogLevel) Log.d(tag, s, e);
    }

    /**
     * Info log message.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void i(String tag, String s, Throwable e) {
        if (WLog.INFO >= sLogLevel) Log.i(tag, s, e);
    }

    /**
     * Warning log message.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void w(String tag, String s, Throwable e) {
        if (WLog.WARN >= sLogLevel) Log.w(tag, s, e);
    }

    /**
     * Error log message.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void e(String tag, String s, Throwable e) {
        if (WLog.ERROR >= sLogLevel) Log.e(tag, s, e);
    }

    /**
     * Verbose log message with printf formatting.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void v(String tag, String s, Object... args) {
        if (WLog.VERBOSE >= sLogLevel) Log.v(tag, String.format(s, args));
    }

    /**
     * Debug log message with printf formatting.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void d(String tag, String s, Object... args) {
        if (WLog.DEBUG >= sLogLevel) Log.d(tag, String.format(s, args));
    }

    /**
     * Info log message with printf formatting.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void i(String tag, String s, Object... args) {
        if (WLog.INFO >= sLogLevel) Log.i(tag, String.format(s, args));
    }

    /**
     * Warning log message with printf formatting.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void w(String tag, String s, Object... args) {
        if (WLog.WARN >= sLogLevel) Log.w(tag, String.format(s, args));
    }

    /**
     * Error log message with printf formatting.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void e(String tag, String s, Object... args) {
        if (WLog.ERROR >= sLogLevel) Log.e(tag, String.format(s, args));
    }

}
