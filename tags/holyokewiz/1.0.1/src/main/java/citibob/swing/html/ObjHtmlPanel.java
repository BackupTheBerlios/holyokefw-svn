/*
Holyoke Framework: library for GUI-based database applications
This file Copyright (c) 2006-2008 by Robert Fischer

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
 * HtmlPanel.java
 *
 * Created on October 8, 2006, 5:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.html;

import citibob.swing.typed.Swinger;
import java.io.*;
import java.net.URL;

import org.xamjwg.html.*;
import org.xamjwg.html.parser.*;
import org.xamjwg.html.gui.*;
import org.xml.sax.InputSource;
import org.xamjwg.html.renderer.*;

import javax.swing.*;
import java.util.*;
import java.net.URL;
import citibob.swing.typed.*;
import org.xamjwg.html.domimpl.*;
import citibob.text.*;
import citibob.types.*;
import java.awt.Component;

/**
 *
 * @author citibob
 */
public class ObjHtmlPanel extends ObjHtmlPanelMVC
{
	Map widgetMap = new HashMap();
	public Map getMap() { return widgetMap; }


	/** This allows the auto pref setter to work properly. */
	public Component[] getComponents()
	{
		Component[] ret = new Component[widgetMap.size()];
		int i=0;
		for (Object obj : widgetMap.values()) {
			ret[i++] = (Component)obj;
		}
		return ret;
	}

	
//	public HtmlPanel(Reader htmlIn, Map widgetMap)
//	throws org.xml.sax.SAXException, java.io.IOException
//	{
//		super();
//		setDocument(htmlIn, widgetMap, null);
//	}
//	
//	public HtmlPanel(Reader htmlIn, Map widgetMap, String uri)
//	throws org.xml.sax.SAXException, java.io.IOException
//	{
//		super();
//		setDocument(htmlIn, widgetMap, uri);
//	}
	
	/** Widget must be already configured. */
	private JTypedTextField addTextField(String name, JTypedTextField widget)
	{
		widgetMap.put(name, widget);

		// Set text field size to 150 pixels wide x default height
		java.awt.Dimension d = widget.getPreferredSize();
		d.width = 150;
		widget.setSize(d);
		return widget;
	}
	public JTypedTextField addTextField(String name, Swinger swinger)
	{
		JTypedTextField widget = new JTypedTextField();
		swinger.configureWidget(widget);
		return addTextField(name, widget);
	}
	public JTypedTextField addTextField(String name, JType jType, citibob.text.SFormat sformat)
	{
		JTypedTextField widget = new JTypedTextField();
		widget.setJType(jType, sformat);
		return addTextField(name, widget);
	}
	public JTypedTextField addTextField(String name, Class klass, java.text.Format fmt)
	{
		JTypedTextField widget = new JTypedTextField();
		widget.setJType(klass, new FormatSFormat(fmt));
		return addTextField(name, widget);
	}
	public JTypedTextField addTextField(String name, Class klass, String fmtString)
		{ return addTextField(name, klass, FormatUtils.newFormat(klass, fmtString)); }



	public JButton addJButton(String name, String text)
	{
		JButton widget = new JButton(text);
		widget.setOpaque(false);
		addWidget(name, widget);
		return widget;
	}

	public JComponent addWidget(String name, JComponent widget)
	{
		widgetMap.put(name, widget);
		widget.setSize(widget.getPreferredSize());
		return widget;		
	}
	
	/** Simple convenience method... only works on a brand new HtmlPanel instance. */
	public void setDocument(Reader htmlIn, String uri, HtmlRendererContext rendererContext)
	throws org.xml.sax.SAXException, java.io.IOException
	{
		if (uri == null) uri = "http://localhost";
		
		// InputSourceImpl constructor with URI recommended
		// so the renderer can resolve page component URLs.
		InputSource is = new InputSourceImpl(htmlIn, uri);
		
		HtmlParserContext parserContext = new NullHtmlParserContext();
		ObjHtmlPanel htmlPanel = this;
//		HtmlRendererContext rendererContext = new NullHtmlRendererContext(htmlPanel);
		DocumentBuilderImpl builder = new DocumentBuilderImpl(parserContext, rendererContext);
		HTMLDocumentImpl document = builder.parse(is);
//		JFrame frame = new JFrame();
//		frame.getContentPane().add(htmlPanel);
		LayoutArgs layoutArgs = new LayoutArgs(widgetMap);
//		layoutArgs.getWidgetMap().put("obj", new JButton("My Object"));
		htmlPanel.setLayoutArgs(layoutArgs);
		htmlPanel.setDocument(document, rendererContext);//, layoutArgs);
	}

	public Object getValue(String name)
	{
		Object o = widgetMap.get(name);
		if (o == null) return null;
		if (!(o instanceof TypedWidget)) return null;
		TypedWidget w = (TypedWidget)o;
		return w.getValue();
	}

	/** Gets all the TypedWidget values from the form. */
	public void getAllValues(Map map)
	{
		for (Iterator ii=getMap().entrySet().iterator(); ii.hasNext(); ) {
			Map.Entry e = (Map.Entry)ii.next();
			if (!(e.getValue() instanceof TypedWidget)) continue;
			TypedWidget tw = (TypedWidget)e.getValue();
			map.put(e.getKey(), tw.getValue());
		}
	}
/** Convenience method: loads HTML from a file of the same name as the
class. */
protected void loadHtml()//HtmlRendererContext rendererContext)
throws org.xml.sax.SAXException, java.io.IOException
{
	loadHtml(getClass());
}

protected void loadHtml(Class klass)//HtmlRendererContext rendererContext)
throws org.xml.sax.SAXException, java.io.IOException
{
	String resourceName = klass.getName().replace('.', '/') + ".html";
	loadHtmlResource(resourceName);
//System.out.println("HtmlDialog: loading resourceName " + resourceName);
//	Reader in = null;
//	try {
//		in = new InputStreamReader(klass.getClassLoader().getResourceAsStream(resourceName));
//		org.xamjwg.html.HtmlRendererContext rendererContext =
//			new MyRendererContext();
//		setDocument(in, null, rendererContext);
//		in.close();
//	} finally {
//		try { in.close(); } catch(Exception e) {}
//	}
}

protected void loadHtmlResource(String resourceName)
throws org.xml.sax.SAXException, java.io.IOException
{
System.out.println("HtmlDialog: loading resourceName " + resourceName);
	Reader in = null;
	try {
		in = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(resourceName));
		loadHtml(in);
	} finally {
		try { in.close(); } catch(Exception e) {}
	}
}


protected void loadHtml(Reader htmlIn)
throws org.xml.sax.SAXException, java.io.IOException
{
	org.xamjwg.html.HtmlRendererContext rendererContext = new MyRendererContext();
	setDocument(htmlIn, null, rendererContext);
	htmlIn.close();
}


//protected void loadHtml()
//throws org.xml.sax.SAXException, java.io.IOException
//{
//
//}
// ====================================================================
class MyRendererContext extends org.xamjwg.html.gui.NullHtmlRendererContext
{
	public MyRendererContext() { super(null); }
	public void navigate(URL href, String target) {
		fireLinkSelected(href, target);
	}
}

}
