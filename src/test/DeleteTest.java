package test;

import javafx.scene.control.TextArea;
import model.Delete;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;
import org.junit.Assert;

import javax.security.auth.login.LoginException;
import java.io.IOException;


public class DeleteTest {

    // Test Delete() Class with all null values
    @Test(expected = Exception.class )
    public void testSessionNull() throws LoginException, IOException {

        Delete testDelete = new Delete(null, null, null);
    }

    // Test Delete() Class with random input
    @Test(expected = Exception.class )
    public void testSessionBadInput() throws LoginException, IOException {

        TextArea statusMsg = null;
        statusMsg.appendText("testing status message");
        Delete testDelete = new Delete(new FTPClient(), statusMsg, "My Documents");
    }
}


