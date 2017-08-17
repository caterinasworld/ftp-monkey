package model;

import java.io.*;

import javafx.scene.control.TextArea;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.security.auth.login.LoginException;


public class Delete {
    protected FTPClient ftp;

    public Delete(FTPClient ftpAdd, TextArea statusMessage, String remoteDirectory) throws LoginException, IOException {
        if (ftpAdd == null) {
            System.out.println("Error: Must have valid login");
            throw new LoginException();
        }
        ftp = ftpAdd;

        boolean deleteSuccess = ftp.deleteFile(remoteDirectory);

        if (!deleteSuccess) {
            deleteSuccess = ftp.removeDirectory(remoteDirectory);
        }
        if (deleteSuccess) {
            statusMessage.appendText(remoteDirectory + " was deleted successfully.\n");
        }else
            statusMessage.appendText(remoteDirectory + " was not found.\n");
    }
}
