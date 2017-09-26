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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sakshi
 */
public class CashTransferController implements Initializable {
    @FXML
    private JFXTextField txtSendAccountNo;
    @FXML
    private JFXTextField txtAmount;
    @FXML
    private JFXButton bttnContinue;
     @FXML
    private Text accWarning;

    @FXML
    private Text amountWarning;
     @FXML
    private Text error;
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
    private void transfer(MouseEvent event) {
        
        String accToTransfer=txtSendAccountNo.getText();
        String temp=txtAmount.getText();
        
        boolean a=false,b=false;
        if(accToTransfer.isEmpty()||accToTransfer.length()!=10)
        {
            accWarning.setVisible(true);
            error.setText("Enter a valid account number");
        }
        else
        {
            accWarning.setVisible(false);
            a=true;
        }
        if(temp.isEmpty())
        {
            amountWarning.setVisible(true);
            error.setText("Enter amount");
        }
        else
        {
            amountWarning.setVisible(false);
            b=true;
        }
        if(a&&b)
        {
           
            DBConnect db=new DBConnect();
            StartController s =new StartController();
            long senderBal,receiverBal,amount;
        
            receiverBal=db.getBalance(accToTransfer);
            System.out.print("Receiver balance is"+receiverBal);
            
            amount = Integer.parseInt(temp);
            if(receiverBal>=0)      //valid account
            {
                senderBal=db.getBalance(s.getAccountNumber());
                if(amount>senderBal)
                {
                    showWarningDialog("Transaction failed, account balance is low.");
                    amountWarning.setVisible(true);
                    error.setText("Low accunt balance");
                }
                else
                {
                    senderBal=senderBal-amount;
                    receiverBal=receiverBal+amount;
                    try {
                        db.setBalance(s.getAccountNumber(), senderBal);
                    
                        db.setBalance(accToTransfer, receiverBal);
                        showWarningDialog("Transaction Successful\nCurrent balance is: "+senderBal+" Rs.");
                    } catch (SQLException ex) {
                        showWarningDialog("transaction failed.");
                    }
                }
            }
            else        //wrong account number
            {
                error.setText("Wrong account number");
                accWarning.setVisible(true);
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
    @FXML
    private void backToHome(ActionEvent event) throws IOException {
        
        Stage stage;
        
        stage=(Stage)bttnContinue.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
