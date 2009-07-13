/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.hokserver.admin;

import citibob.crypt.*;
import java.io.IOException;

/**
 *
 * @author citibob
 */
public class ConsolePBEAuth extends BasePBEAuth
{


public ConsolePBEAuth(String envVar)
{ super(envVar); }

void erasePassword()
{
	if (password == null) return;
	for (int i=0; i<password.length; ++i) password[i] = '\0';
}

//void setPassword(char[] password)
//{
//	erasePassword();
//	this.password = password;
//}
//
//void setPassword(char[] password, int len)
//{
//	erasePassword();
//	char[] newpwd = new char[len];
//	for (int i=0; i<len; ++i) newpwd[i] = password[i];
//	this.password = password;
//	setPassword(newpwd);
//}
//
//void readPwdLine() throws IOException
//{
//	System.out.print("password: ");
//	System.out.flush();
//
//	char[] pwd = new char[100];
//	InputStreamReader reader = new InputStreamReader(System.in);
//	int state = 0;
//	int len=0;
//forloop:
//	for (;;) {
//		char c = (char)reader.read();
//		switch(c) {
//			case '\r' :
//			case '\n' :
////				break forloop;
//				if (state == 0) continue;
//				else break forloop;
//			default :
//				pwd[len++] = c;
//				state = 1;
//		}
//	}
//
//	// Re-size to smaller array
//	setPassword(pwd, len);
//}

/** Called repeatedly to get the password.
 @returns null if user declined to give a password (chose to cancel) */
public char[] queryPassword()
{
	for (int nnull = 0; ; ++nnull) {
		java.io.Console console = System.console();
		System.err.print("password: ");
		System.err.flush();
		char[] password = console.readPassword();
		
		// See if user typed a good password!
		if (password.length > 0) return password;

		if (nnull >= 2) return null;	// User hit CR 3 times, trying to exit
	}
}

/** Call when we're all done with this, to erase any stored passwords. */
public void dispose()
{
	erasePassword();
}

public static void main(String[] args) throws Exception
{
	ConsolePBEAuth auth = new ConsolePBEAuth("");
	for (;;) {
		char[] pwd = auth.getPassword();
		System.out.println("x Password is " + new String(pwd));
	}
}

}
