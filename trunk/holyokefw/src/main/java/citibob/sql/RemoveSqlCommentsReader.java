/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.sql;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Reader;
import java.io.StringReader;

/**
 * Filters out comments from an SQL stream --- JDBC doesn't like comments!
 * @author citibob
 */
public class RemoveSqlCommentsReader extends FilterReader
{

static final int S_MAIN = 0;
static final int S_INQUOTE = 1;
static final int S_DASH1 = 2;
static final int S_DASH1X = 3;
static final int S_INCOMMENT = 4;
	
int state = S_MAIN;
int save;

public RemoveSqlCommentsReader(Reader in) { super(in); }

public int read() throws IOException
{
	for (;;) {
		int c;
		switch(state) {
			case S_MAIN : c = super.read(); switch(c) {
				case '\'' : state = S_INQUOTE; return c;
				case '-' : state = S_DASH1; continue;
				default : return c;
			}
			case S_INQUOTE : c = super.read(); switch(c) {
				case '\'' : state = S_MAIN; return c;
				default : return c;
			}
			case S_DASH1 : c = super.read(); switch(c) {
				case '-' : state = S_INCOMMENT; continue;
				default : state = S_DASH1X; save = c; return '-';
			}
			case S_DASH1X : state = S_MAIN; return save;
			case S_INCOMMENT : c = super.read(); switch(c) {
				case '\r' :
				case '\n' : state = S_MAIN; return c;
				default : continue;
			}
		}
	}
}


public int read(char[] cbuf, int off, int len)
throws IOException
{
	int c;
	int j = off;
	for (int i=0; ; ++i) {
		if (i >= len) return i;
		c = read();
		if (c < 0) return i;
		cbuf[j++] = (char)c;
	}
}

public static void main(String[] args) throws Exception
{
	Reader in = new RemoveSqlCommentsReader(new StringReader(
		"Hello world: 3-2=4;\n" +
		"How is a comment: -- comment me away.\n" +
		"What about a string: 'hell -- freezes over'\n"));
	int c;
	while ((c = in.read()) >= 0) System.out.write((char)c);
	System.out.flush();
}

}
