/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.task;

/**
 * Application should wrap exceptions in this to distinguish that it's
 * a user error, not just a random exception.
 * @author citibob
 */
public class AppError extends Exception
{
	public AppError(String message, Exception e)
	{
		super(message);
		initCause(e);
	}
	public AppError(Exception e)
	{
		this(e.getMessage(), e);
	}
}
