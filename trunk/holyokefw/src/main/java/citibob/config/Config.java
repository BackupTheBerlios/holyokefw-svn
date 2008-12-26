/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author citibob
 */
public interface Config {

public InputStream openStream(String name) throws IOException;

public byte[] getStreamBytes(String name) throws IOException;

}
