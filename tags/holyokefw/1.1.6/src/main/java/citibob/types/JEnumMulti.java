/*
Holyoke Framework: library for GUI-based database applications
This file Copyright (c) 2006-2008 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.types;

import java.util.Collection;

/**
 *
 * @author citibob
 */
public class JEnumMulti implements JType {

	protected KeyedModel kmodel;
	protected Object segment;
	protected JType baseJType = JavaJType.jtInteger;

	/** nullText = string to use for null value, or else <null> if this is not nullable. */
	public JEnumMulti(JType baseJType, KeyedModel kmodel, Object segment) {
		this.baseJType = baseJType;
		this.kmodel = kmodel;
		this.segment = segment;
	}
	public KeyedModel getKeyedModel() { return kmodel; }
	public Object getSegment() { return segment; }

	/** Returns the type of the elemnts of the enumeration; usually JavaJType.jtInteger. */
	public JType getBaseJType() { return baseJType; }
	
	/** Java class used to represent this type */
	public Class getObjClass()
		{ return Collection.class; } //baseJType.getObjClass();; }

	public boolean isInstance(Object o)
	{
		return (kmodel.containsKey(o)); //get(o) != null);
	}

	
}
