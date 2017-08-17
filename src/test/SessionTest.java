package test;

import javafx.scene.control.TextArea;
import model.Session;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

public class SessionTest {

    // Test Session() Class with all null values
    @Test(expected = Exception.class)
    public void testSessionNull() {

        Session session = new Session(null, null, null, null, 0, null);
    }

    // Test Session() Class with some bad or nonexistent input
    @Test(expected = Exception.class)
    public void testSessionBadInput() {

        Session session = new Session(new FTPClient(), "testUsername", "testPassword", "127.0.0.1", 21, null);
    }

    // Test Session() Class with good input
    @Test(expected = Exception.class)
    public void testSessionGoodInput() {

        TextArea txt_log = null;
        txt_log.appendText("");
        //Session session = new Session(new FTPClient(), "FTP-User", "12345", "192.168.1.5", 21, txt_log);
        Session session = new Session(new FTPClient(), "FTP-User", "12345", "0.0.0.0", 21, txt_log);

    }

}