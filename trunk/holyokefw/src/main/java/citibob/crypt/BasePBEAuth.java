package citibob.crypt;

/**
 *
 * @author citibob
 */
public abstract class BasePBEAuth implements PBEAuth
{

public static final int PWD_NONE = 0;		// No password yet
public static final int PWD_GOOD = 1;		// Last password was good (assumption when pwd entered until proven otherwise)
public static final int PWD_BAD = 2;		// Last password was bad
	
protected int state = PWD_NONE;
protected char[] password;

/** Indicates that the last password entered was good.  From now on,
 * just return it again and again. */
public void validatePassword()
{
	state = PWD_GOOD;
}
		
}
