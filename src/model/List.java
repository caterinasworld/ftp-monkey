package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.IOException;


public class List {

    @FXML
    private ListView<String> view_remote;

    @FXML
    private ListView<String> view_local;

    @FXML
    private ObservableList<String> localItems;// = FXCollections.observableArrayList();

    @FXML
    private ObservableList<String> remoteItems;// = FXCollections.observableArrayList();

    public List(ListView<String> view_remote, ListView<String> view_local, ObservableList<String> localItems, ObservableList<String> remoteItems) {
        this.view_remote = view_remote;
        this.view_local = view_local;
        this.localItems = localItems;
        this.remoteItems = remoteItems;
    }



    public void remoteFileList(FTPClient ftp)  {
        try {

            FTPFile[] filesArray = ftp.listFiles();

            for (FTPFile fileItem : filesArray){
                String info = fileItem.getName();
                System.out.println(fileItem.getName());
                remoteItems.add(info);
            }
            view_remote.setItems(remoteItems);
            view_remote.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void localFileAndDirectoryList(String dirName){

        File dirTolist = new File(dirName);

        //Get all of the files from a direcory
        File [] fileArray = dirTolist.listFiles();

        for(File fileObj  :  fileArray){
            System.out.println(fileObj.getAbsolutePath());
            localItems.add(fileObj.getAbsolutePath());
            view_local.setItems(localItems);
        }
        view_local.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

}
