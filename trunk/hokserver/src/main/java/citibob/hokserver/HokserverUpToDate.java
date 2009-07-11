/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.hokserver;

import citibob.app.App;
//import citibob.resource.Upgrader;
import citibob.resource.schema.DbbUpToDate;
import java.io.IOException;

/**
 *
 * @author citibob
 */
public class HokserverUpToDate extends DbbUpToDate
{

public HokserverUpToDate(App app) throws IOException {
	super(app, "main");
//	upgraders = new Upgrader[] {};
}

}
