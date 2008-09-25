/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bubble.serializer;

/**
 *
 * @author fiscrob
 */
public interface SaverFilter {

/** Called before we begin serializing a particular object. */
public void pushObj(Object obj);

/** Returns true if this object should be saved by the serializer for later reference. */
public boolean saveObj(Object obj);

/** Called when we finish an object. */
public void popObj(Object Obj);


}
