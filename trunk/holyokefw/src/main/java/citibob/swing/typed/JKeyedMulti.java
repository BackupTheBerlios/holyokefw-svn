/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.typed;

import citibob.jschema.Column;
import citibob.jschema.Schema;
import citibob.jschema.SchemaSet;
import citibob.text.KeyedMultiSFormat;
import citibob.types.JEnum;
import citibob.types.JEnumMulti;
import citibob.types.KeyedModel;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author citibob
 */
public class JKeyedMulti extends JTypedDropdown
{

public JKeyedMulti()
{
	JKeyedMultiPanel tw = new JKeyedMultiPanel();
	super.setPopupWidget(tw);
}


// --------------------------------------------------------------
/** Convenience method */
public void setKeyedModel(JEnumMulti jenum)
	{ setKeyedModel(jenum.getKeyedModel(), jenum.getSegment()); }
/** Convenience method: sets dropdown equal to the type of the column;
 column must be of type JEnum. */
public void setKeyedModel(Column col)
	{ setKeyedModel((JEnumMulti)col.getType()); }
/** Convenience method: sets dropdown equal to the type of the column.
 Column must be of type JEnum*/
public void setKeyedModel(Schema schema, String colName)
	{ setKeyedModel(schema.getCol(colName)); }
public void setKeyedModel(SchemaSet sset, String schemaName, String colName)
	{ setKeyedModel(sset.get(schemaName), colName); }


public void setKeyedModel(KeyedModel kmodel)
	{ setKeyedModel(kmodel, null); }

public void setKeyedModel(KeyedModel kmodel, Object segment)
{
	JKeyedMultiPanel tw = (JKeyedMultiPanel)popupWidget;
	tw.setKeyedModel(kmodel, segment);
	super.setJType(new JEnum(kmodel), new KeyedMultiSFormat(kmodel));
}
public void setSegment(Object segment)
{
	JKeyedMultiPanel tw = (JKeyedMultiPanel)popupWidget;
	tw.setSegment(segment);
}

public static void main(String[] args) throws Exception
{
	KeyedModel kmodel = KeyedModel.intKeys("Item 0", "Item 1", "item 2");
	
	JKeyedMulti multi = new JKeyedMulti();
	multi.setKeyedModel(kmodel);
	multi.setPreferredSize(new Dimension(200,20));
	
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	panel.add(multi);
	frame.getContentPane().add(panel);
	frame.pack();
	frame.setVisible(true);
}



}
