///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package citibob.text;
//
///**
// *
// * @author fiscrob
// */
//public class OverrideSFormatMap implements SFormatMap
//{
//
//	SFormatMap[] maps;
//	
//	public OverrideSFormatMap(SFormatMap map0)
//	{
//		maps = new SFormatMap[] {map0, new DefaultSFormatMap()};
//	}
//
//public SFormat newSFormat(JType t, String colName)
//{
//	for (int i=maps.length; i >= 0; --i) {
//		SFormat fmt = maps[i].newSFormat(t, colName);
//		if (fmt != null) return fmt;
//	}
//	return null;
//}
//public SFormat[] newSFormats(JTypeTableModel model);
//public SFormat[] newSFormats(JTypeTableModel model, String[] scol, SFormat[] sfmt);
//
//
//}
