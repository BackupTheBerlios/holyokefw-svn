/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006-2007 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package citibob.app;
import citibob.gui.FrameSet;
import citibob.swing.typed.SwingerMap;
import java.util.*;
import citibob.sql.*;
import citibob.task.*;
import citibob.mail.*;
import citibob.jschema.*;
import citibob.jschema.log.QueryLogger;
import citibob.reports.Reports;
import citibob.resource.ResSet;
import citibob.swing.prefs.*;
import citibob.text.SFormatMap;
import citibob.version.Version;
import java.io.File;
import java.util.prefs.Preferences;

public abstract class App
{

// =================================================================
// Configuration, Properts and Preferences

protected File configDir;
/** Directory containing configuration files, etc. for this application. */
public java.io.File configDir() { return configDir; }

protected Properties props;
/** Gets properties loaded from an application configuration file. */
public Properties props() { return props; }

protected ResSet resSet;
/** Access to versioned resources outside the Jar file (eg. templates, etc) */
public ResSet resSet() { return resSet; }

protected SwingPrefs swingPrefs;
/** Set of Swing preference setters used to store GUI configurations in
 * Java preferences. */
public SwingPrefs swingPrefs() { return swingPrefs; }

/** Convenience method, used to set up Swing prefernces on a Swing/AWT
 * widget tree.
 * @param c The widget tree
 * @param base Unique name to give the widget tree.
 */
public void setUserPrefs(java.awt.Component c, String base)
	{ swingPrefs().setPrefs(c, userRoot().node(base)); }
public void setUserPrefs(java.awt.Component c)
	{ setUserPrefs(c, c.getClass().getSimpleName()); }

protected Preferences userRoot;
/** @returns Root user preferences node for this application */
public java.util.prefs.Preferences userRoot() { return userRoot; }

protected Preferences systemRoot;
/** @returns Root system preferences node for this application */
public java.util.prefs.Preferences systemRoot() { return systemRoot; }
// =================================================================
// Connection Pools, Exception Handlers and Runners

protected ConnPool pool;
/** Connection pool of the default database */
public ConnPool pool() { return pool; }

protected FrameSet frameSet;
/** Set of windows managed by this application */
public citibob.gui.FrameSet frameSet() { return frameSet; }

protected ExpHandler expHandler;
/** Handler for all unhandled exceptions */
public ExpHandler expHandler() { return expHandler; }

protected SqlRun sqlRun;
/** Used for batched SQL queries, or to obtain any non-GUI database handle. */
public SqlRun sqlRun() { return sqlRun; }
//public void pushSqlBatch() {}
//public void popSqlBatch() {}


//protected JobRun appRun;
///** Run non-GUI tasks --- use sqlBatch() if you just want a database task and
// don't care how it's run. */
//public JobRun appRun() { return appRun; }

protected SwingJobRun guiRun;
/** Run GUI tasks */
public SwingJobRun guiRun() { return guiRun; }

// ============================================================================
// Type conversions, Swingers, SFormats
protected SchemaSet schemaSet;
/** Schema in our default database. */
public SchemaSet schemaSet() { return schemaSet; }
/** Convenience method */
public SqlSchema getSchema(String name)
	{ return schemaSet().get(name); }

protected SwingerMap swingerMap;
/** conversion of JTypes to Swing renderers */
public SwingerMap swingerMap() { return swingerMap; }

protected SFormatMap sFormatMap;
/** Simpler SFormats for each JType */
public citibob.text.SFormatMap sFormatMap() { return sFormatMap; }

protected SqlTypeSet sqlTypeSet;
/** Get default conversion between database types and SqlType objects */
public citibob.sql.SqlTypeSet sqlTypeSet() { return sqlTypeSet; }

protected DbChangeModel dbChange;
/** Database change model --- throws event when a table is changed. */
public DbChangeModel dbChange() { return dbChange; }

// ============================================================================
// Misc
protected QueryLogger queryLogger;
/** Allow to log all database changes */
public citibob.jschema.log.QueryLogger queryLogger() { return queryLogger; }

protected MailSender mailSender;
/** Send email */
public MailSender mailSender() { return mailSender; }

protected Reports reports;
/** Object for report generation */
public citibob.reports.Reports reports() { return reports; }

protected TimeZone timeZone;
/** Default time zone for desktop application */
public TimeZone timeZone() { return timeZone; }

// =======================================================================
// Versioning
protected Version version;
/** Major number change indicates change in database schema.  Medium numbers
 indicate change in GUI preferences. */
public Version version() { return version; }

protected int sysVersion;
/** SVN version number for this program. */
public int sysVersion() { return sysVersion; }


}
