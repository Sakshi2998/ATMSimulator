/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmsimulator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sakshi
 */
public class CashWithdrawalController implements Initializable {
    @FXML
    private JFXTextField withdrawAmount;
     @FXML
    private StackPane stack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void backToHome(ActionEvent event) throws IOException {
      
        Stage stage;
        
        stage=(Stage)withdrawAmount.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
        @FXML
    void withdraw(ActionEvent event) {
        String amt=withdrawAmount.getText();
        if(amt.isEmpty())
        {
            showWarningDialog("Enter amount");
        }
        else
        {
            long withdrawAmt=Integer.parseInt(withdrawAmount.getText());
            DBConnect db= new DBConnect();
            StartController s=new StartController();
            String accNo=s.getAccountNumber();
            long balance=db.getBalance(accNo);
            if(withdrawAmt>balance)
            {
                showWarningDialog("Not enough balance.");
            }
            else
            {
                balance=balance-withdrawAmt;
                try {
                    db.setBalance(accNo, balance);
                    System.out.println("Success");
                   showWarningDialog("Collect your cash.\nYour current balance is: "+db.getBalance(accNo));
                } catch (SQLException ex) {
                    showWarningDialog("Transaction failed");
                }

            }
        }
    }
void showWarningDialog(String content)
   {
        JFXDialogLayout layout=new JFXDialogLayout();
             //  layout.setHeading(new Text(title));
               layout.setBody(new Text(content));
               layout.setPrefWidth(250);
               layout.setPrefHeight(150);
               JFXButton button=new JFXButton("OK");
               button.setButtonType(JFXButton.ButtonType.RAISED);
               
               button.setStyle("-fx-background-color:#a40032;-fx-text-fill:#fff");
               
               
                JFXDialog dialog=new JFXDialog(stack,layout, JFXDialog.DialogTransition.CENTER);
               button.setOnAction(new EventHandler<ActionEvent>() {

                   @Override
                   public void handle(ActionEvent event) {
                       dialog.close();
                       
                   }
               });
               layout.setActions(button);
               dialog.show();
   }
    
    
}
