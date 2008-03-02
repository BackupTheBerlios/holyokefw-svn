/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A set of upgraders needed to apply to system to bring it current with front-end.
 * @author citibob
 */
public class UpgradePlanSet
{

int sysVersion;

/** Required version was available --- up to date. */
public ArrayList<RtResKey> upToDate = new ArrayList();
	
/** Optional ResourceKeys that exist and can be upgraded to current version,
 or required resources that can be upgraded or created (whether or not exists). */
public ArrayList<UpgradePlan> uplans = new ArrayList();

/** Required resources that exist in a previous version but cannot be
 upgraded to current version --- cannot proceed without taking care of these! */
public ArrayList<RtResKey> reqNotUpgradeable = new ArrayList();

/** Required resources that do not exist and cannot be created.  Signals
 an error in the application front-end. */
public ArrayList<RtResKey> reqCannotCreate = new ArrayList();

/** Optional resources that exist in a previous version but cannot be
 upgraded to current version --- OK to proceed. */
public ArrayList<RtResKey> optNotUpgradeable = new ArrayList();


void printRK(PrintStream out, String name, List<RtResKey> keys)
{
	if (keys.size() == 0) return;
	out.println(name + ":");
	for (RtResKey rk : keys) {
		out.println("    " + rk + " required version = " +
			rk.res.getRequiredVersion(sysVersion));
	}
}
void printUP(PrintStream out, String name, List<UpgradePlan> keys)
{
	if (keys.size() == 0) return;
	out.println(name + ":");
	for (Object rk : keys) {
		out.println("    " + rk);
	}
}
public void print(PrintStream out)
{
	printRK(out, "Up to Date", upToDate);
	printUP(out, "Planned Upgrades", uplans);
	printRK(out, "Required resources, cannot upgrade", reqNotUpgradeable);
	printRK(out, "Required resources, cannot create", reqCannotCreate);
	printRK(out, "Optional resources, cannot upgrade", optNotUpgradeable);
}

public UpgradePlanSet(ResData rdata, int sysVersion)
{
	this.sysVersion = sysVersion;
	outer : for (RtResKey rk : rdata.relevant) {
System.out.println("Considering upgrade of rk = " + rk);
		int reqVersion = rk.res.getRequiredVersion(sysVersion);
		
		// Look at all existing versions, from highest number to lowest.
		UpgradePlan uplan = null;
		for (int i=rk.availVersions.size()-1; i >= 0; --i) {
			RtVers vers = rk.availVersions.get(i);
			// See if required version is already available
			if (vers.version == reqVersion) {
				upToDate.add(rk);
				continue outer;
			}
			
			// See if we can make required version from this version
			uplan = rk.getUpgradePlan(vers.version, reqVersion);
			if (uplan != null) {
				uplans.add(uplan);
				continue outer;
			}
		}
		
		if (rk.availVersions.size() > 0) {
			// A previous version exists, but we cannot upgrade.
			// Throw in right bin.
			if (!rk.res.isRequired()) {
				optNotUpgradeable.add(rk);
			} else {
				reqNotUpgradeable.add(rk);
			}
		} else {
			// No previous version exists.
			// For required resources, see if we can create it.
			if (rk.res.isRequired()) {
				// See if we can create required version
				uplan = rk.getCreatorPlan(reqVersion);
				if (uplan != null) {
					uplans.add(uplan);
					continue outer;
				} else {
					// Required resource but cannot create --- system error.
					reqCannotCreate.add(rk);
				}
			} else {
				// Resource is optional and does not exist ----
				// that's entirely OK!
			}
		}
	}
}

}
