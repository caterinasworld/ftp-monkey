package ftpapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;

public class Controller {

    @FXML
    private Circle circle_login_status;

    @FXML
    private Label txt_login_status;

    @FXML
    private TextField txt_servername;

    @FXML
    private TextField txt_port;

    @FXML
    private TextField txt_username;

    @FXML
    private PasswordField txt_password;

    @FXML
    private ListView<String> view_remote;

    @FXML
    private ListView<String> view_local;

    @FXML
    protected ObservableList<String> localItems = FXCollections.observableArrayList();

    @FXML
    private ObservableList<String> remoteItems = FXCollections.observableArrayList();

    @FXML
    private Button btn_disconnect;

    public Session session;

    protected FTPClient ftp;

    public List listObj;

    @FXML
    private void connectAction(ActionEvent ae) {
        ftp = new FTPClient();
        listObj = new List(view_remote, view_local, localItems, remoteItems);
        System.out.println(System.getProperty("user.home"));

        try {
            session = new Session(ftp, txt_username.getText(), txt_password.getText(), txt_servername.getText(), Integer.parseInt((txt_port.getText())));

            if (session.login()) {
                txt_login_status.setText("Connected!");
                circle_login_status.setFill(Color.GREEN);
                listObj.remoteFileList(ftp);
                listObj.localFileAndDirectoryList(System.getProperty("user.home"));
            }
        }
        catch (Exception e) {

            System.out.println("Error: Bad login credentials");
        }
    }

    @FXML
    private void disconnectAction(ActionEvent ae) {
        try {
            ftp.disconnect();
            System.out.println("Disconnecting");
            txt_login_status.setText("Not Connected");
            circle_login_status.setFill(Color.RED);
        }
        catch (Exception e) {
            System.out.println("Error: No connection to disconnect");
        }
    }

    @FXML
    private void downloadAction(ActionEvent ae) {

    }

    @FXML
    private void uploadAction(ActionEvent ae) {

    }


    @FXML
    private void deleteRemoteAction(ActionEvent ae) {

    }

}
