package test;

import javafx.scene.control.TextArea;
import model.Get;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class GetTest {

    // Test Get() Class with all null values
    @Test(expected = Exception.class )
    public void testGetNull() throws LoginException, IOException {

        Get testGet = new Get(null, null, null, null);
    }

    // Test Get() Class with random input
    @Test(expected = Exception.class )
    public void testGetBadInput() throws LoginException, IOException {

        TextArea statusMsg = null;
        statusMsg.appendText("testing status message");
        Get testGet = new Get(new FTPClient(), statusMsg, "Home/", "My Documents");
    }

}
