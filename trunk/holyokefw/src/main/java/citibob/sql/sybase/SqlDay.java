package citibob.sql.sybase;

public class SqlDay extends citibob.sql.ansi.SqlDay
{
	public SqlDay(boolean nullable) {
		super("yyyy-MM-dd", nullable);
	}
	public SqlDay() { this(true); }
	
	/** Convert an element of this type to an Sql string for use in a query */
	public String toSql(Object o)
	{
		return o == null ? "null" :
			('\'' + dsfmt.valueToString(o) + '\'');
	}

}
