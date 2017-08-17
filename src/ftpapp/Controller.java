package ftpapp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    @FXML
    private TextArea txt_log;

    public Session session;
    public Get get;
    public Delete delete;
    protected FTPClient ftp;
    public List listObj;

    @FXML
    private void connectAction(ActionEvent ae) {
        ftp = new FTPClient();
        listObj = new List(view_remote, view_local, localItems, remoteItems);
        System.out.println(System.getProperty("user.home"));

        try {

            if (txt_servername.getText().isEmpty()) {
                txt_log.appendText("Error: Server name needed\n");
                throw new IOException();
            }
            if (txt_port.getText().isEmpty()) {
                txt_log.appendText("Error: Valid port number needed\n");
                throw new IOException();
            }
            if (txt_username.getText().isEmpty()) {
                txt_log.appendText("Error: Username needed\n");
                throw new IOException();
            }
            if (txt_password.getText().isEmpty()) {
                txt_log.appendText("Error: Password needed\n");
                throw new IOException();
            }

            session = new Session(ftp, txt_username.getText(), txt_password.getText(), txt_servername.getText(), Integer.parseInt((txt_port.getText())), txt_log);
            if (session.login()) {
                txt_login_status.setText("Connected!");
                circle_login_status.setFill(Color.GREEN);

                listObj.remoteFileList(ftp);
                listObj.localFileAndDirectoryList(System.getProperty("user.home"));
            }
        } catch (IOException e) {

        }
    }

    @FXML
    private void disconnectAction(ActionEvent ae) {
        try {
            if (txt_login_status.getText().equals("Not Connected")) {
                throw new Exception();
            }
            ftp.disconnect();
            txt_log.appendText("Disconnected\n");
            txt_login_status.setText("Not Connected");
            circle_login_status.setFill(Color.RED);
        } catch (Exception e) {
            listenForScroll(txt_log);
            txt_log.appendText("Error: Not connected\n");
        }
    }

    @FXML
    private void downloadAction(ActionEvent ae) {

        try {
            ObservableList<String> localDirectoryList = view_local.getSelectionModel().getSelectedItems();
            ObservableList<String> remoteDirectoryList = view_remote.getSelectionModel().getSelectedItems();
            String localDirectory = localDirectoryList.get(0);
            String remoteDirectory = remoteDirectoryList.get(0);
            get = new Get(ftp, txt_log, localDirectory, remoteDirectory);
        }
        catch (Exception e) {
            listenForScroll(txt_log);
            txt_log.appendText("Error: File transfer did not complete. Please select valid file and directory from list.\n");
        }
    }

    @FXML
    private void uploadAction(ActionEvent ae) {

    }


    @FXML
    private void deleteRemoteAction(ActionEvent ae) {
        try {
            ObservableList<String> remoteDirectoryList = view_remote.getSelectionModel().getSelectedItems();
            String remoteDirectory = remoteDirectoryList.get(0);
            delete = new Delete(ftp, txt_log, remoteDirectory);
        }
        catch (Exception e) {
            listenForScroll(txt_log);
            txt_log.appendText("Error: File transfer did not complete. Please select valid file and directory from list.\n");
        }
    }

    private void listenForScroll(TextArea ta) {
        txt_log.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,
                                Object newValue) {
                txt_log.setScrollTop(Double.MAX_VALUE);
            }
        });
    }
}
