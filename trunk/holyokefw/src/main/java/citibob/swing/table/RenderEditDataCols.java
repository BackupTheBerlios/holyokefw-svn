/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.swing.typed.JTypedTextField;
import citibob.swing.typed.Swinger;
import citibob.swing.typed.Swinger.RenderEdit;
import citibob.swing.typed.SwingerMap;
import citibob.swingers.DefaultRenderEdit;
import citibob.swingers.KeyedRenderEdit;
import citibob.swingers.SFormatRenderer;
import citibob.swingers.TypedWidgetEditor;
import citibob.text.FormatSFormat;
import citibob.text.FormatUtils;
import citibob.text.SFormat;
import citibob.types.JType;
import citibob.types.KeyedModel;
import java.text.Format;
import java.text.NumberFormat;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * Assigns Renderers and Editors by assigning a single data
 * type to each column.
 * @author fiscrob
 */
public class RenderEditDataCols extends DataCols<RenderEdit>
{

StyledTableModel model;		// Used for isEditable
//RenderEdit[] redits;

public RenderEditDataCols(StyledTableModel model)
{
	super(RenderEdit.class, model);
	this.model = model;
//	redits = new RenderEdit[model.getModel().getColumnCount()];
}

public RenderEditDataCols(StyledTableModel styledModel, SwingerMap smap)
{
	this(styledModel);
	
	// Set up the RenderEdits...
	if (smap == null) return;
	JTypeTableModel model = styledModel.getModel();
	for (int i=0; i<data.length; ++i) {
		JType jType = model.getJType(0, i);
		if (jType == null) continue;
		Swinger swinger = smap.newSwinger(jType);
		if (swinger == null) return;
		setFormat(i, swinger);
	}
}
protected int findColumnU(String colNameU)
	{ return model.getModel().findColumnU(colNameU); }


// -----------------------------------------------------------
// Re-useable in other classes
// -----------------------------------------------------------
public void setFormat(int col, RenderEdit re)
	{ data[col] = re; }
public void setFormat(int col, Swinger swinger)
	{ data[col] = swinger.newRenderEdit(); }
public void setFormat(int col, KeyedModel kmodel)
	{ setFormat(col, new KeyedRenderEdit(kmodel)); }
/** Sets a text-based renderer and editor pair at once, for a column,
without going through Swingers or anything.  Works for simpler text-based
renderers and editors ONLY. */
public void setFormat(int colNo, SFormat sformat)
{
	TableCellRenderer rr = new SFormatRenderer(sformat);
	JTypedTextField tw = new JTypedTextField();
	tw.setJType((JType)null, sformat);		// We don't really know about JTypes at CitibobJTable anyway
	TableCellEditor ee = new TypedWidgetEditor(tw);
	RenderEdit re = new DefaultRenderEdit(rr, ee);
	setFormat(colNo, re);
}
public void setFormat(int colNo, java.text.Format fmt, int horizAlign)
{
//	SFormat sfmt = new FormatSFormat(fmt, "", horizAlign);
	SFormat sfmt = FormatUtils.toSFormat(fmt, horizAlign);
	setFormat(colNo, sfmt);
}
public void setFormat(int colNo, java.text.Format fmt)
{
//	SFormat sfmt = (fmt instanceof NumberFormat ?
//		new FormatSFormat(fmt, "", SFormat.RIGHT) :
//		new FormatSFormat(fmt, "", SFormat.LEFT));
	SFormat sfmt = FormatUtils.toSFormat(fmt);
	setFormat(colNo, sfmt);
}

/** Sets up a renderer and editor based on a format string.  Works for a small
number of well-known types, this is NOT general. */
public void setFormat(int colNo, String fmtString)
{
	Class klass = model.getModel().getColumnClass(colNo);
//	Format fmt = FormatUtils.newFormat(klass, fmtString);
	SFormat sfmt = FormatUtils.toSFormat(klass, fmtString);
	setFormat(colNo, sfmt);
}

// -----------------------------------------------------------
// Allow setting of the RenderEdit by column name in the underlying table
public void setFormatU(String underlyingName, Swinger.RenderEdit re)
	{ setFormat(findColumnU(underlyingName), re); }

/** Sets a render/edit on a colum, by UNDERLYING column name. */
public void setFormatU(String underlyingName, Swinger swinger)
	{ setFormat(findColumnU(underlyingName), swinger); }


public void setFormatU(String underlyingName, KeyedModel kmodel)
	{ setFormat(findColumnU(underlyingName), kmodel); }

/** Sets a render/edit on a colum, by UNDERLYING column name. */
public void setFormatU(String underlyingName, SFormat sfmt)
	{ setFormat(findColumnU(underlyingName), sfmt); }

public void setFormatU(String underlyingName, java.text.Format fmt, int horizAlign)
	{ setFormat(findColumnU(underlyingName), fmt, horizAlign); }

public void setFormatU(String underlyingName, java.text.Format fmt)
	{ setFormat(findColumnU(underlyingName), fmt); }

public void setFormatU(String underlyingName, String fmtString)
	{ setFormat(findColumnU(underlyingName), fmtString); }


}