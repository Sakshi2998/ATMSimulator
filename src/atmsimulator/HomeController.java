/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmsimulator;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sakshi
 */
public class HomeController implements Initializable {
    @FXML
    private Text txtWelcom;
    @FXML
    private Text txtOperation;
    @FXML
    private ImageView imgAxisBank;
    @FXML
    private JFXButton btnBalanceEnq;
    @FXML
    private JFXButton btnCashTrans;
    @FXML
    private JFXButton btnCashWithdrawal;
    @FXML
    private JFXButton btnChangePin;
    @FXML
    private Text txtNeedHelp;

    /**
     * Initializes the controller class.
     */
    
     Stage stage;
        Parent root;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
       DBConnect db=new DBConnect();
       StartController s=new StartController();
       txtWelcom.setText("Welcome, "+db.getName(s.getAccountNumber()));
    }    

    @FXML
    private void balanceEnquiry(ActionEvent event) throws IOException {
        
        
       
        stage=(Stage)btnBalanceEnq.getScene().getWindow();

        root = FXMLLoader.load(getClass().getResource("BalanceEnq.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void cashTransfer(ActionEvent event) throws IOException {
        
        stage=(Stage)btnBalanceEnq.getScene().getWindow();

        root = FXMLLoader.load(getClass().getResource("cashTransfer.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void cashwithdrawal(ActionEvent event) throws IOException {
        
        stage=(Stage)btnBalanceEnq.getScene().getWindow();

        root = FXMLLoader.load(getClass().getResource("cashWithdrawal.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void changepin(ActionEvent event) throws IOException {
        stage=(Stage)btnBalanceEnq.getScene().getWindow();

        root = FXMLLoader.load(getClass().getResource("PinChange.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
 @FXML
    void logout(ActionEvent event) throws IOException {
stage=(Stage)btnBalanceEnq.getScene().getWindow();

        root = FXMLLoader.load(getClass().getResource("Start.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void help(MouseEvent event) {
    }
    
}
