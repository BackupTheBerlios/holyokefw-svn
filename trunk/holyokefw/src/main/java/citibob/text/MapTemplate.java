package citibob.text;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class MapTemplate extends HashMap
{

URL url;		// The template file

public MapTemplate(URL file)
	{ this.url = file; }

public MapTemplate(File file) throws MalformedURLException
{
//	this.url = file.toURL();
	this.url = file.toURI().toURL();
}

public void write(Writer out) throws IOException
{
	Reader in = new BufferedReader(new InputStreamReader(url.openStream()));
	try {
		all: for (;;) {
			// Go ahead and do your thing...
			for (;;) {
				int c = in.read();
				if (c == '%') break;
				if (c == -1) break all;
				out.write(c);
			}

			// We've encountered a field name; read it
			StringBuffer keyBuf = new StringBuffer();
			for (;;) {
				int c = in.read();
				if (c == '%') break;
				if (c == -1) break all;
				keyBuf.append((char)c);
			}
			String key = keyBuf.toString();
			if (key.length() == 0) {	// %% outputs a percent sign.
				out.write("%");
			} else {
				// Look up the key and write it out
				String value = (String)get(key);
				if (value == null) out.write("&lt;null&gt;");
				else out.write(value);
			}
		}
	} finally {
		in.close();
	}
}

}
