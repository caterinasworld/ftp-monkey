package model;

import java.io.*;
import java.util.Observable;

import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import javax.security.auth.login.LoginException;

import static com.sun.javafx.PlatformUtil.isMac;
import static com.sun.javafx.PlatformUtil.isWindows;


public class Get {
    protected FTPClient ftp;

    public Get(FTPClient ftpAdd, TextArea statusMessage, String localDirectory, String remoteDirectory) throws LoginException, IOException {
        if (ftpAdd == null) {
            System.out.println("Error: Must have valid login");
            throw new LoginException();
        }

        ftp = ftpAdd;

        System.out.print("Local File: " + localDirectory + "\n");
        System.out.print("Remote File: " + remoteDirectory + "\n");


        //ftp.connect("192.168.1.5", 21);
        //ftp.connect("10.200.212.36", 21);
        //ftp.login("FTP-User", "12345");

        boolean transferSuccess;

        //enter passive mode
        ftp.enterLocalPassiveMode();
        //use binary file type transfer
        ftp.setFileType(FTP.BINARY_FILE_TYPE);

        //TODO
        //must get text from user
        //String serverFile = "ip.txt";
        String serverFile = remoteDirectory;

        localDirectory += File.separator + remoteDirectory;

        System.out.print(localDirectory);

        //TODO
        //Destination where file will be saved
        //File localDestination = new File("C:/Users/troyc/Desktop/FTP Holder/ip.txt");
        File localDestination = new File(localDirectory);

        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(localDestination));
        InputStream inputStream = ftp.retrieveFileStream(serverFile);
        byte[] inputStreamBytes = new byte[4096];
        int readBytes = -1;
        while ((readBytes = inputStream.read(inputStreamBytes)) != -1) {
            outputStream.write(inputStreamBytes, 0, readBytes);
        }



        transferSuccess = ftp.completePendingCommand();
        if (transferSuccess) {
            //System.out.println(serverFile + " has been transferred successfully.");
            statusMessage.appendText(serverFile + " has been downloaded successfully.\n");
        }
        outputStream.close();
        inputStream.close();
    }
}