
import com.jangomail.api.JangoMail;
import com.jangomail.api.JangoMailLocator;
import com.jangomail.api.JangoMailSoap;
import java.net.URL;
import javax.mail.Address;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author citibob
 */
public class Test {
    public static void main(String [] args) throws Exception {
        // Make a service
		JangoMail service = new JangoMailLocator();
 
        // Now use the service to get a stub which implements the SDI.
		String usr = "";
		String pwd = "";
		JangoMailSoap soap = service.getJangoMailSoap(new URL("https://api.jangomail.com/api.asmx"));
		
		soap.addGroup(usr, pwd, "TestGroup");
		soap.addGroupField(usr, pwd, "TestGroup", "firstname");
		soap.addGroupField(usr, pwd, "TestGroup", "lastname");
		soap.addGroupMember(usr, pwd, "TestGroup", "test@test.com",
				new String[] {"firstname", "lastname"},
				new String[] {"Joe", "Schmoe"});
		System.out.println("Done!");
    }
}

