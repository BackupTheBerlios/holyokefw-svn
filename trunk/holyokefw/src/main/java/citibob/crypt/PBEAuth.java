package citibob.crypt;

/**
 *
 * @author citibob
 */
public interface PBEAuth {

/** Indicates that the last password entered was good.  From now on,
 * just return it again and again. */
public void validatePassword();
		
/** Called repeatedly to get the password.
 @returns null if user declined to give a password (chose to cancel) */
public abstract char[] getPassword();

/** Call when we're all done with this, to erase any stored passwords. */
public abstract void dispose();
	
}
