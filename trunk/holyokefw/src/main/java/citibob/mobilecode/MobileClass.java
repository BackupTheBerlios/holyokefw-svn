/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.mobilecode;

import java.io.Serializable;

/**
 *
 * @author fiscrob
 */
public class MobileClass implements Serializable
{

	public String className;
	public byte[] bytecode;
	
	/** Need no-arg constructor for jserial. */
	protected MobileClass() {}
	
	public MobileClass(String className, byte[] bytecode) {
		this.className = className;
		this.bytecode = bytecode;
	}
	
}
