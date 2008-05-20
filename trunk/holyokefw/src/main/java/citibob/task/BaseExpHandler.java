package citibob.task;

public abstract class BaseExpHandler implements ExpHandler
{

protected Throwable getRootCause(Throwable t)
{
	Throwable cause = t.getCause();
	while (cause != null) {
		t = cause;
		cause = t.getCause();
	}
	return t;
}
	
}
