/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: info@xamjwg.org
*/
/*
 * Copyright (c) 2003 World Wide Web Consortium,
 * (Massachusetts Institute of Technology, Institut National de
 * Recherche en Informatique et en Automatique, Keio University). All
 * Rights Reserved. This program is distributed under the W3C's Software
 * Intellectual Property License. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 */

package org.w3c.dom.html2;

/**
 * List item. See the LI element definition in HTML 4.01.
 * <p>See also the <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document Object Model (DOM) Level 2 HTML Specification</a>.
 */
public interface HTMLLIElement extends HTMLElement {
    /**
     * List item bullet style. See the type attribute definition in HTML 4.01. 
     * This attribute is deprecated in HTML 4.01.
     */
    public String getType();
    /**
     * List item bullet style. See the type attribute definition in HTML 4.01. 
     * This attribute is deprecated in HTML 4.01.
     */
    public void setType(String type);

    /**
     * Reset sequence number when used in <code>OL</code>. See the value 
     * attribute definition in HTML 4.01. This attribute is deprecated in 
     * HTML 4.01.
     */
    public int getValue();
    /**
     * Reset sequence number when used in <code>OL</code>. See the value 
     * attribute definition in HTML 4.01. This attribute is deprecated in 
     * HTML 4.01.
     */
    public void setValue(int value);

}
