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
/*
 * SqlBatch.java
 *
 * Created on August 28, 2007, 9:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sql;

import java.sql.*;
import java.util.*;

/**
 * A way to run SQL queries --- either in batch, or one at a time.
 * @author citibob
 */
public interface SqlRun {

/** Adds SQL to the next batch to run.
 Multiple ResultSets returned, and it can create
 additional SQL as needed.
 @param rr one of RssRunnable, RsRunnable, UpdRunnable */
public void execSql(String sql, SqlTasklet rr);

/** Adds Sql to next batch to run, without any processing code. */
public void execSql(String sql);

/** Adds processing code to run without any SQL. */
public void execUpdate(UpdTasklet r);

/** Adds processing code to run without any SQL. */
public void execUpdate(UpdTasklet2 r);

/** Executes all (potentially) buffered SQL up to now. */
public void flush() throws Exception;

// TODO: Review all these methods below, see if they can be simplified.
public void enterRecursion();
public void exitRecursion();
public int getRecursionDepth();
public void push();
public void pop() throws Exception;

///** @deprecated
// Gets the SqlRunner for the next batch --- used inside SqlRunnable
// to run things in sequence. */
//public SqlRunner next();

///** While SqlRunnables are running --- store a value for retrieval by later SqlRunnable. */
//public void put(Object key, Object val);
//
///** While SqlRunnables are running --- retrieve a previously stored value. */
//public Object get(Object key);

// =================================================================
// Something that keeps track of the "current available" SqlRunner

}

