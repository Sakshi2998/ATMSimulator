/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmsimulator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sakshi
 */
public class BalanceEnqController implements Initializable {
    @FXML
    private Text accountNo;
    @FXML
    private Text balance;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        DBConnect db=new DBConnect();
        StartController s=new StartController();
        String acc=s.getAccountNumber();
       accountNo.setText(acc);
        balance.setText(""+db.getBalance(acc));
        
    }    

    @FXML
    private void backToHome(ActionEvent event) throws IOException {
        Stage stage;
        
        stage=(Stage)accountNo.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
}
