/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.crypt;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author citibob
 */
public class StdinPBEAuth extends BasePBEAuth
{

String envVar;		// Env variable to query for password
int ntries = 0;

public StdinPBEAuth(String envVar)
	{this.envVar = envVar; }

void erasePassword()
{
	if (password == null) return;
	for (int i=0; i<password.length; ++i) password[i] = '\0';
}

void setPassword(char[] password)
{
	erasePassword();
	this.password = password;
}

void setPassword(char[] password, int len)
{
	erasePassword();
	char[] newpwd = new char[len];
	for (int i=0; i<len; ++i) newpwd[i] = password[i];
	this.password = password;
	setPassword(newpwd);
}

void readPwdLine() throws IOException
{
	System.out.print("password: ");
	System.out.flush();

	char[] pwd = new char[100];
	InputStreamReader reader = new InputStreamReader(System.in);
	int state = 0;
	int len=0;
forloop:
	for (;;) {
		char c = (char)reader.read();
		switch(c) {
			case '\r' :
			case '\n' :
				if (state == 0) continue;
				else break forloop;
			default :
				pwd[len++] = c;
				state = 1;
		}
	}

	// Re-size to smaller array
	setPassword(pwd, len);
}

/** Called repeatedly to get the password.
 @returns null if user declined to give a password (chose to cancel) */
public char[] getPassword()
{
	// Return last password, if it turned out to be good.
	if (state == PWD_GOOD) return password;

	// See about the password in the environment variable
	if (ntries == 0) {
		String envPassword = System.getenv(envVar);
		++ntries;
		if (envPassword != null) return envPassword.toCharArray();
	}

	for (int nnull = 0; ; ++nnull) {
		try {
			readPwdLine();
		} catch(IOException e) {
			return null;	// Cancel!
		}
		
		// See if user typed a good password!
		if (password.length > 0) return password;

		if (nnull >= 2) return null;	// User hit CR 3 times, trying to exit
	}
}

/** Call when we're all done with this, to erase any stored passwords. */
public void dispose()
{ erasePassword(); }

}
