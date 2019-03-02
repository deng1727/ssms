/*
 * @(#)DatedFileAppender.java        1.6 04/12/07
 *
 * Copyright (c) 2003-2005 ASPire Technologies, Inc.
 * 6/F,IER BUILDING, SOUTH AREA,SHENZHEN HI-TECH INDUSTRIAL PARK Mail Box:11# 12#.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ASPire Technologies, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Aspire.
 */
/*
 * DatedFileAppender Version 1.0.2
 *
 * Public domain source code.
 *
 * This software was written and placed in the public
 * domain by Geoff Mottram on August 28, 2004.
 *
 * Change Log:
 *
 * Version 1.0.2, September 15, 2004
 *
 *     1) Replaced calls to Calendar.getTimeInMillis() and
 *	  Calendar.getTimeInMillis() for backwards compatibility with
 *        JVM's prior to 1.4 in which these methods are protected.
 *
 * Version 1.0.1, September 8, 2004 (Documentation changes only)
 *
 *     1) Added documentation on the issue of I/O buffering.
 *     2) Fixed example log4j.properties BufferSize entry.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHOR BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THIS SOFTWARE OR THE USE OR OTHER DEALINGS IN THIS SOFTWARE.
 */
package com.aspire.common.log.proxy;

import java.io.*;
import java.text.*;
import java.util.*;

import org.apache.log4j.*;
import org.apache.log4j.spi.*;
import com.aspire.common.config.ServerInfo;

/**
 * DatedFileAppender appends Log4j logging events to a different log file
 * every day. The log file name consists of a prefix (defaults to
 * "application."), today's date (by setting datePattern) and a suffix (defaults to
 * ".log"). DatedFileAppender creates a new log file with the first message it
 * logs on a given day and continues to use that same file throughout the day.
 *
 * <p>Two static utility methods are provided for Calendar manipuation:
 * {@link #datestamp datestamp(Calendar calendar)} (which creates a string
 * in the form of specified in "datePattern") and {@link #tomorrow tomorrow(Calendar calendar)}
 * (which sets <code>calendar</code> to the start of the next day).</p>
 *
 * <p>DatedFileAppender supports buffered writes to its log file. By default, no
 * buffering is used and log entries are written to the log file as soon as they
 * are received. This insures that no new entries will be added to a log file after
 * midnight of any given day. On a heavily loaded system there could be cases where
 * the logger is preempted while writing an entry just before midnight such that the
 * log file would not be physically updated until a little after midnight. Any log
 * file related cron jobs should be run a reasonable time after midnight to be on
 * the safe side.</p>
 *
 * <p>If you enable buffering by setting the <code>BufferedIO=true</code> property
 * in your log4j.properties file, there is no guarantee that DatedFileAppender has
 * finished writing to a daily log file after midnight. A new message must be logged
 * after midnight to force the previous day's log file to be flushed and closed.</p>
 *
 * <p>If you are logging so much data that you require write buffering, you will
 * probably log a message soon after midnight, forcing a log file roll-over.
 * To be absolutely sure, any cron based external log file processing that is done
 * could wait for the new day's log file to appear before starting its work on what
 * is now yesterday's log file.</p>
 *
 * The following sample log4j.properties file explains the configuration
 * options for this class and may be used to configure Log4j as the Tomcat
 * system logger:</p>
 *
 * <p>The following sample log4j.properties file explains the configuration
 * options for this class and may be used to configure Log4j as the Tomcat
 * system logger:</p>
 * <pre>
 #
 # log4j.properties
 #
 # Configures Log4j as the Tomcat system logger
 # using the custom DatedFileAppender.
 #

 #
 # Configure the logger to output ERROR or INFO level messages into
 # a Tomcat-style rolling, dated log file ("tomcat.DATE.log").
 #
 #log4j.rootLogger=ERROR, T
 log4j.rootLogger=INFO, T

 #
 # Configure the appender "T".
 #
 # Note that the default file name prefix is being overridden
 # to be "tomcat." instead of "application.".
 #
 log4j.appender.T=com.aspire.common.log.proxy.DatedFileAppender
 log4j.appender.T.layout=org.apache.log4j.PatternLayout
 log4j.appender.T.layout.ConversionPattern=%m%n
 #
 # If you don't want to use the DatedFileAppender default values
 # seen below, uncomment that line and change the setting.
 #
 # Directory: If the directory name does not start with a leading slash,
 # the directory name will be relative to your web application's root path.
 #log4j.appender.T.Directory=/log/biz
 #
 # Prefix: The log file name prefix.
 #log4j.appender.T.Prefix=application.
 log4j.appender.T.Prefix=tomcat.
 #
 # Suffix: The log file name suffix.
 #log4j.appender.T.Suffix=.log
 #
 # Append: true to append when opening a log file (good when restarting
 # Tomcat) or false to truncate.
 #log4j.appender.T.Append=true
 # DatePattern:the date pattern,only surpport day now
 #log4j.appender.T.DatePattern=yyyyMMdd
 # BufferedIO: true to use a buffered output stream to the log file (improves
 # performance when logging a lot of data but not so good if the system
 # crashes or you want to watch the logs in real time) or false to write
 # flush each message out to the log file.
 #
 # The default behavior of using non-buffered writes insures that a day's
 # log file will not be written to after midnight. When buffering is enabled,
 # a new message must be written to a log after midnight to force the previous
 # day's log file to be flushed and closed.
 #
 #log4j.appender.T.BufferedIO=false
 #
 # BufferSize: sets the size of the buffer to use if BufferedIO is true.
 # The default size is 8K.
 #log4j.appender.T.BufferSize=8192

 #
 # Application logging options
 #
 #log4j.logger.org.apache=DEBUG
 #log4j.logger.org.apache=INFO
 #log4j.logger.org.apache.struts=DEBUG
 #log4j.logger.org.apache.struts=INFO
 * </pre>
 *
 * @author Geoff Mottram
 * */
public class DatedFileAppender
    extends FileAppender
{

    // ----------------------------------------------------- Instance Variables

    /**
     * The directory in which log files are created.
     * this is relative to the web app's root path.
     */
    private String m_directory = "logs";

    /**
     * The prefix that is added to log file filenames.
     */
    private String m_prefix = "tomcat.";

    /**
     * The suffix that is added to log file filenames.
     */
    private String m_suffix = ".log";

    /**
     * The File representation of the directory in which log files are created.
     */
    private File m_path = null;

    /**
     * A calendar object for manipulating dates and times.
     */
    private Calendar m_calendar = null;

    /**
     * The number of milliseconds since 1/1/1970 when tomorrow starts (local time).
     */
    private long m_tomorrow = 0L;
    /**
     * the pattern of date,allow variation within the day's precision
     */
    private String datePattern;

    // ----------------------------------------------------------- Constructors

    /** The default constructor will create a Tomcat FileLogger
      with the following characteristics:
      <ul>
      <li>directory: "logs"</li>
      <li>prefix: "tomcat."</li>
      <li>suffix: ".log"</li>
      </ul>
     */
    public DatedFileAppender()
    {
    }

    /** Creates a new <code>DatedFileAppender</code>
      with the specified characteristics.
      @param directory the direction relative to app root path.
      @param prefix the prefix that is added to log file filenames.
      @param suffix the suffix that is added to log file filenames.
     */
    public DatedFileAppender(String directory, String prefix, String suffix)
    {
        m_directory = directory;
        m_prefix = prefix;
        m_suffix = suffix;
        activateOptions();
    }

    // ------------------------------------------------------------- Properties

    /**
     * @return the directory in which we create log files.
     */
    public String getDirectory()
    {
        return m_directory;
    }

    /**
     * Set the directory in which we create log files.
     *
     * @param directory The new log file directory
     */
    public void setDirectory(String directory)
    {
        m_directory = directory;
    }

    /**
     * @return the log file prefix.
     */
    public String getPrefix()
    {
        return m_prefix;
    }

    /**
     * Set the log file prefix.
     *
     * @param prefix The new log file prefix
     */
    public void setPrefix(String prefix)
    {
        m_prefix = prefix;
    }

    /**
     * @return the log file suffix.
     */
    public String getSuffix()
    {
        return m_suffix;
    }

    /**
     * Set the log file suffix.
     *
     * @param suffix The new log file suffix
     */
    public void setSuffix(String suffix)
    {
        m_suffix = suffix;
    }

    // --------------------------------------------------------- Public Methods


    /**
       Called once all options have been set on this Appender.
       Calls the underlying FileLogger's start() method.
     */
    public void activateOptions()
    {
        if (m_prefix == null)
        {
            m_prefix = "";
        }
        if (m_suffix == null)
        {
            m_suffix = "";
        }
        if ( (m_directory == null) || (m_directory.length() == 0))
        {
            m_directory = ".";
        }
        m_path = new File(m_directory);
        String base = ServerInfo.getAppRootPath();
        if (base != null)
        {
            m_path = new File(base, m_directory);
        }
        m_path.mkdirs();
        if (m_path.canWrite())
        {
            m_calendar = Calendar.getInstance(); // initialized
        }
    }

    /**
       Called by AppenderSkeleton.doAppend() to write a log message formatted
       according to the layout defined for this appender.
     *@param event LoggingEvent
     */
    public void append(LoggingEvent event)
    {
        if (this.layout == null)
        {
            errorHandler.error("No layout set for the appender named [" + name +
                               "].");
            return;
        }
        if (this.m_calendar == null)
        {
            errorHandler.error(
                "Improper initialization for the appender named [" + name +
                "].");
            return;
        }
        long n = System.currentTimeMillis();
        if (n >= m_tomorrow)
        {
            // Next line only works with newer (1.4 or so) versions of Java (method is protected in older versions)
            //m_calendar.setTimeInMillis(n);		// set Calendar to current time
            m_calendar.setTime(new Date(n)); // set Calendar to current time
            String datestamp = datestamp(m_calendar); // convert to string "yyyy-mm-dd"
            tomorrow(m_calendar); // set the Calendar to the start of tomorrow
            // Next line only works with newer (1.4 or so) versions of Java (method is protected in older versions)
            //m_tomorrow = m_calendar.getTimeInMillis();	// time in milliseconds when tomorrow starts
            m_tomorrow = m_calendar.getTime().getTime(); // time in milliseconds when tomorrow starts
            File newFile = new File(m_path, m_prefix + datestamp + m_suffix);
            this.fileName = newFile.getAbsolutePath();
            super.activateOptions(); // close current file and open new file
        }
        if (this.qw == null)
        { // should never happen
            errorHandler.error(
                "No output stream or file set for the appender named [" +
                name + "].");
            return;
        }
        subAppend(event);
    }

    /**
     * Formats a datestamp as yyyy-mm-dd using a Calendar source object.
     *
     * @param calendar a Calendar containing the date to format.
     *
     * @return a String in the form "yyyy-yy-dd".
     */
    public String datestamp(Calendar calendar)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        return sdf.format(calendar.getTime());
    }

    /**
     * Sets a calendar to the start of tomorrow,
     * with all time values reset to zero.
     *
     * <p>Takes advantage of the fact that the Java Calendar implementations
     * are mercifully accommodating in handling non-existent dates. For example,
     * June 31 is understood to mean July 1. This allows you to simply add one
     * to today's day of the month to generate tomorrow's date. It also works
     * for years, so that December 32, 2004 is converted into January 1, 2005.</p>
     * @param calendar the calendar
     */
    public static void tomorrow(Calendar calendar)
    {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH) + 1;
        calendar.clear(); // clear all fields
        calendar.set(year, month, day); // set tomorrow's date
    }

    public String getDatePattern()
    {
        return datePattern;
    }

    public void setDatePattern(String datePattern)
    {
        this.datePattern = datePattern;
    }
}
