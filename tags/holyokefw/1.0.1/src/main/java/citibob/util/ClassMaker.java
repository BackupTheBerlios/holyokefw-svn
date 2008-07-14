/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.util;

/**
 *
 * @author fiscrob
 */
public class ClassMaker<TT> implements ObjMaker<TT>
{
	Class klass;
	public ClassMaker(Class klass)
	{
		this.klass = klass;
	}
	public TT newInstance() throws InstantiationException, IllegalAccessException {
		return (TT)klass.newInstance();
	}
}
