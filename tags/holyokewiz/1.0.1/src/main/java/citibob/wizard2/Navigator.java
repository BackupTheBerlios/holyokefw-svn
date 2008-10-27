/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.wizard2;

public interface Navigator
{
	public Wiz getWiz(String name);
//	
	public Wiz getStart();
//	
//	public Wiz getNextWiz(Wiz startWiz, int nav);
	
	

//	public Wiz getCur();
	
	/** @param nav the Direction we're navigating from the previous Wizard.
	 * @param stateName the name of the state the Wiz itself sugests we go to.
	 * This will be null in "normal" circumstances, but it can be used to do
	 * a detour.
	 * @return The next wiz.  If we're done, returns null.
	 */
	public Wiz getNext(Wiz wiz, int nav); //, String stateName);
	
//	public Wiz get(String stateName);
	
	/** For display of "(screen n of m)" */
//	public int getNumWiz();
	
//	public int getCurNum();
	
}