/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import java.util.ArrayList;

/**
 * A set of upgraders needed to apply to system to bring it current with front-end.
 * @author citibob
 */
public class UpgradePlanSet
{

/** Required version was available --- up to date. */
ArrayList<RtResKey> upToDate = new ArrayList();
	
/** Optional ResourceKeys that exist and can be upgraded to current version,
 or required resources that can be upgraded or created (whether or not exists). */
ArrayList<UpgradePlan> uplans = new ArrayList();

/** Required resources that exist in a previous version but cannot be
 upgraded to current version --- cannot proceed without taking care of these! */
ArrayList<RtResKey> reqNotUpgradeable = new ArrayList();

/** Required resources that do not exist and cannot be created.  Signals
 an error in the application front-end. */
ArrayList<RtResKey> reqCannotCreate = new ArrayList();

/** Optional resources that exist in a previous version but cannot be
 upgraded to current version --- OK to proceed. */
ArrayList<RtResKey> optNotUpgradeable = new ArrayList();


public UpgradePlanSet(ResData rdata, int sysVersion)
{
	outer : for (RtResKey rk : rdata.relevant) {
		int reqVersion = rk.res.getRequiredVersion(sysVersion);
		
//		// Check to see if required version is available
//		for (RtVers vers : rk.availVersions) {
//			if (vers.version == reqVersion) {
//				upToDate.add(rk);
//				continue outer;
//			}
//		}
		
		// It's not available.  Check for an upgrade plan starting
		// from highest-number existing version
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
		
		// Version we need doesn't exist and we can't upgrade from
		// previous version and this resource is not optional.
		// See if we can create it.
		if (!rk.res.isOptional()) {
			// See if we can create required version
			uplan = rk.getCreatorPlan(reqVersion);
			if (uplan != null) uplans.add(uplan);
				continue outer;
			}
			
		}
		
		
		else {
			
		}
	}
}

}
