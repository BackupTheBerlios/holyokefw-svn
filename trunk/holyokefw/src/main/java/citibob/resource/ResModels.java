/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.app.App;
import citibob.resource.ResKey;
import citibob.resource.ResSet;
import citibob.resource.ResUtil;
import citibob.resource.Resource;
import citibob.resource.UpgradePlan;
import citibob.resource.Upgrader;
import citibob.sql.SqlRunner;
import citibob.swing.table.BaseJTypeTableModel;
import citibob.text.AbstractSFormat;
import citibob.types.JType;
import citibob.types.JavaJType;
import citibob.types.JavaJType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import citibob.resource.ResModels.ResRow;

/**
 *
 * @author citibob
 */
public class ResModels
{

App app;
int sysVersion;
static class ResRow {
	Resource res;
	ArrayList<ResKey> keys = new ArrayList();
}
Map<Resource,ResRow> rowMap = new HashMap();
ArrayList<ResRow> rows = new ArrayList();

public ResModels.ResModel resMod;
public ResModels.ResKeyModel rkMod;
public ResModels.AvailModel availMod;
public ResModels.UPlanModel uplanMod;

public ResModels(SqlRunner str, App xapp, int sysVersion)
{
	this.app = xapp;
	ResSet rset = app.getResSet();
	this.sysVersion = sysVersion;

	availNames = new String[] {"version", "lastmodified"};
	availTypes = new JType[] {JavaJType.jtInteger,
			app.getSqlTypeSet().getSqlType(java.sql.Types.TIMESTAMP, 0, 0, true)};

	
	
	// Read <ResKey> pairs and convert it to rows...
	SortedSet<ResKey> relKey = rset.getRelevant();
	ResUtil.fetchAvailableVersions(str, relKey);		// Puts into rel...
	Resource lastRes = null;
	ResRow cur = null;
	for (ResKey rk : relKey) {
		if (lastRes != rk.res) {
			if (lastRes != null) {
				rows.add(cur);
				rowMap.put(cur.res, cur);
			}
			cur = new ResRow();
			cur.res = rk.res;
		}
		cur.keys.add(rk);
		lastRes = rk.res;
	}
	if (cur != null) {
		rows.add(cur);
		rowMap.put(cur.res, cur);
	}
	
	resMod = new ResModel();
	rkMod = new ResKeyModel();
	availMod = new AvailModel();
	uplanMod = new UPlanModel();
}

public ResModel newResModel()
	{ return new ResModel(); }
/** Lists ResKeys for a model */
public ResKeyModel newResKeyModel(Resource res)
	{ return new ResKeyModel(); }
//	ResRow row = rowMap.get(res);
//	return new ResKeyModel(row.keys);
//}
public AvailModel newAvailModel(ResKey rk)
	{ return new AvailModel(); }
//	return new AvailModel(rk.availVersions);
//}
//public UpgradePlansModel newUpgradePlansModel(ResKey rk, int reqVersion)
//{
//	// Scope out upgrade plans first
//	
//	return new UPlanModel()
//}
// =======================================================================
static final String[] resNames = {"Resource", "name", "reqversion"};
static final JType[] resTypes = {new JavaJType(Resource.class), JavaJType.jtString, JavaJType.jtInteger};
public class ResModel extends BaseJTypeTableModel<ResRow>
{
	public ResModel()
	{
		super(resNames, resTypes, null);
		data = rows;
	}
	public Object getValueAt(int irow, int icol)
	{
		ResRow r = data.get(irow);
		switch(icol) {
			case 0 : return r.res;
			case 1 : return r.res.getName();
			case 2 : return r.res.getRequiredVersion(sysVersion);
		}
		return null;
	}
}
// =======================================================================
static final String[] rkNames = {"ResKey", "name"};
static final JType[] rkTypes = {new JavaJType(ResKey.class), JavaJType.jtString};
public class ResKeyModel extends BaseJTypeTableModel<ResKey>
{
	public ResKeyModel() {
		super(rkNames, rkTypes, null);		
	}
	public void setData(Resource res)
	{
		ResRow row = rowMap.get(res);
		this.data = row.keys;
		fireTableDataChanged();
	}
	public Object getValueAt(int irow, int icol)
	{
		ResKey rk = data.get(irow);
		switch(icol) {
			case 0 : return rk;
			case 1 : return rk.uversionName;
		}
		return null;
	}
}
// =======================================================================
String[] availNames;		// Set above in constructor
JType[] availTypes;
public class AvailModel extends BaseJTypeTableModel<Integer>
{
	public AvailModel() {
		super(availNames, availTypes, null);
	}
	public void setData(int[] vers)
	{
		data = new ArrayList();
		for (int v : vers) data.add(v);
		fireTableDataChanged();
	}
	public void setData(ArrayList<Integer> versions) {
		this.data = versions;
		fireTableDataChanged();
	}
	public Object getValueAt(int irow, int icol)
	{
		Integer version = data.get(irow);
		switch(icol) {
			case 0 : return version;
		}
		return null;
	}
}
// =======================================================================
static final String[] uplanNames = {"UpgradePlan",
	"uversionid0", "uversionName0",
	"uversionid1", "uversionName1"};
static final JType[] uplanTypes = {new JavaJType(UpgradePlan.class),
	JavaJType.jtInteger, JavaJType.jtString,
	JavaJType.jtInteger, JavaJType.jtString};
public static class UPlanModel extends BaseJTypeTableModel<UpgradePlan>
{
	public UPlanModel() {
		super(uplanNames, uplanTypes, null);
	}
	public void setData(ArrayList<UpgradePlan> uplans) {
		this.data = uplans;
		fireTableDataChanged();
	}
	public Object getValueAt(int irow, int icol)
	{
		UpgradePlan up = data.get(irow);
		switch(icol) {
			case 0 : return up;
			case 1 : return up.uversionid0();
			case 2 : return up.uversionName0();
			case 3 : return up.uversionid1();
			case 4 : return up.uversionName1();
		}
		return null;
	}
}
public static class PathSFormat extends AbstractSFormat
{
	public String valueToString(Object val)
	{
		if (val == null) return super.nullText;
		UpgradePlan up = (UpgradePlan)val;
		Upgrader[] path = up.getPath();
		if (path == null || path.length < 1) return "<none>";
		StringBuffer sbuf = new StringBuffer(""+path[0].version0());
		for (int i=0; i<path.length; ++i) {
			sbuf.append(" -> " + path[i].version1());
		}
		return sbuf.toString();
	}
	
}
// =======================================================================

}
