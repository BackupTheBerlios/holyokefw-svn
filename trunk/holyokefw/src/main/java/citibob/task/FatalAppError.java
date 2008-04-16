/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.task;

/**
 * Indicates a user error that is fatal, should cause termination of the program.
 * @author citibob
 */
public class FatalAppError extends AppError
{
	public FatalAppError(String message, Exception e)
	{
		super(message, e);
	}
	public FatalAppError(Exception e)
	{
		super(e);
	}
}
