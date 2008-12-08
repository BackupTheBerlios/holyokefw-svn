/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

/**
 *
 * @author citibob
 */
public abstract class PBEAuthenticator {

public static final int PWD_NONE = 0;		// No password yet
public static final int PWD_GOOD = 1;		// Last password was good (assumption when pwd entered until proven otherwise)
public static final int PWD_BAD = 2;		// Last password was bad
	
int state = PWD_NONE;

/** Indicates that the last password entered was bad. */
public void invalidatePassword()
{
	state = PWD_BAD;
}
		
/** Called repeatedly to get the password. */
public abstract char[] getPassword();

/** Call when we're all done with this, to erase any stored passwords. */
public abstract void dispose();
	
}
