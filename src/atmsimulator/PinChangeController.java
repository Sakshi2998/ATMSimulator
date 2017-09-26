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
public class PinChangeController implements Initializable {
    @FXML
    private JFXTextField NewPin;
    @FXML
    private JFXTextField Pin;
    @FXML
    private JFXTextField Renter;
    @FXML
    private JFXButton changePin;
      @FXML
    private StackPane stack;
      
        @FXML
    private Text pinWarning;

    @FXML
    private Text newWarning;

    @FXML
    private Text reWarning;

    @FXML
    private Text error;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

   

    @FXML
    private void changePin(ActionEvent event) {
        
        String pin=Pin.getText();
        String newPin=NewPin.getText();
        String rePin=Renter.getText();
        boolean a=false,b=false,c=false;
        if(rePin.isEmpty())
         {
             reWarning.setVisible(true);
             error.setText("Re-enter the pin");
         }
         else if(!rePin.equals(newPin))
        {
            reWarning.setVisible(true);
            error.setText("new pin and re-entered pin dont match");
        }
        else
        {
            reWarning.setVisible(false);
            a=true;
        }
         if(newPin.isEmpty()||newPin.length()!=4)
        {
            newWarning.setVisible(true);
            error.setText("Enter a valid 4 digit pin");
        }
        else
        {
            newWarning.setVisible(false);
            b=true;
        }
         
        
       
        if(pin.isEmpty()||pin.length()!=4)
        {
            error.setText("Enter a valid pin");
            pinWarning.setVisible(true);
        }
        else
        {
            pinWarning.setVisible(false);
            c=true;
        }
        if(a&&b&&c)
        {
            error.setText("");
            StartController s=new StartController();
            DBConnect db=new DBConnect();
      
            String accNo=s.getAccountNumber();
            if(db.getPin(accNo).equals(pin))
            {
                try {
                    db.setPin(accNo,newPin);
                    showWarningDialog("Pin successfully changed");

                } catch (SQLException ex) {
                    showWarningDialog("Failed.");
                }
            
            }
            else
            {
                pinWarning.setVisible(true);
                error.setText("wrong pin");
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
        
        stage=(Stage)changePin.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
