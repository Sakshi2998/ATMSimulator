/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmsimulator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTabPane;
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
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sakshi
 */
public class StartController implements Initializable {
    
    private static String accountNumber;
    
    DBConnect db;
     @FXML
    private StackPane stack;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private Tab loginTab;
    @FXML
    private JFXButton lContinue;
    @FXML
    private JFXTextField lAccountNo;
    @FXML
    private Text lError;
    @FXML
    private Text lRegister;
    @FXML
    private JFXPasswordField lPin;
    @FXML
    private Text lAccWarning;
    @FXML
    private Text lPinWarning;
    @FXML
    private Tab registerTab;
    @FXML
    private JFXButton rRegister;
    @FXML
    private JFXTextField rAccountNo;
    @FXML
    private JFXPasswordField rPin;
    @FXML
    private JFXPasswordField rRePin;
    @FXML
    private Text rLogin;
    @FXML
    private Text rAccWarning;
    @FXML
    private Text rPinWarning;
    @FXML
    private JFXCheckBox rTerms;
    @FXML
    private Text rError;
    @FXML
    private JFXTextField rName;
      @FXML
    private Text rNameWarning;

  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void switchToRegister(MouseEvent event) {
        
        tabPane.getSelectionModel().select(1);
    }

    @FXML
    private void switchToLogin(MouseEvent event) {
        tabPane.getSelectionModel().select(0);
    }
    
      @FXML
    void login(ActionEvent event) throws IOException {
        
       
        String accountNo= lAccountNo.getText().trim();
        String pin=lPin.getText().trim();
        boolean an=!accountNo.trim().isEmpty();
       boolean p=!pin.trim().isEmpty();
      
           if(!an||accountNo.length()!=10)
           {
               lAccWarning.setVisible(true);
               lError.setText("Enter a valid account number");
                  
           }
           else
           {
               lAccWarning.setVisible(false);
              
                if(!p||pin.length()!=4)
                {
                    lPinWarning.setVisible(true);
                    lError.setText("Enter a valid pin");
                }
                else
                {
                    //validation successful
                    lPinWarning.setVisible(false);
                    lError.setText("");
                    //verification
                     db=new DBConnect();
                     try
                     {
                     if(db.getPin(accountNo).equals(pin))
                     {
                        //verification successful switch to home
                        accountNumber=accountNo;
                        Stage stage;
                        Parent root;
                        stage=(Stage)lContinue.getScene().getWindow();

                        root = FXMLLoader.load(getClass().getResource("Home.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                     }
                     else
                     {
                         lPinWarning.setVisible(true);
                         lError.setText("Wrong pin");
                     }
                     }
                     catch(NullPointerException e)
                     {
                         lAccWarning.setVisible(true);
                         lError.setText("Account number doesnot exist");
                     }
                }
           }
       }


    @FXML
    void register(ActionEvent event) throws IOException,SQLException {
         
       String name=rName.getText();
       String accountNo=rAccountNo.getText();
       String pin=rPin.getText();
       String repin=rRePin.getText();
       boolean n,a,p;
       n=false;
       a=false;
       p=false;
       
      
       if(pin.isEmpty()||repin.isEmpty())
       {
            rPinWarning.setVisible(true);
            if(repin.isEmpty())
            {
                rError.setText("Re-enter the pin");
            }
            if(pin.isEmpty())
            {
                rError.setText("Enter pin");
            }
        }
        else
        {
            if(pin.length()!=4)
            {
               rPinWarning.setVisible(true);
               rError.setText("Pin should be of 4 digits");
            }
            else if(!pin.equals(repin))
            {
               rPinWarning.setVisible(true);
               rError.setText("pin and re-entered pin dont match");
            }
           else
           {
                rPinWarning.setVisible(false);
                rError.setText(""); 
                p=true;
           }
       }
        if(accountNo.length()!=10)
       {
            rAccWarning.setVisible(true);
            rError.setText("Enter a valid 10 digit account number");
       }
       else
       {
            rAccWarning.setVisible(false);
            a=true;
       }  
         if(name.isEmpty())
       {
           rNameWarning.setVisible(true);
           rError.setText("Enter Name");
       }
       else
       {
           rNameWarning.setVisible(false);
           n=true;
       }  
       if(n&&a&&p)
       {
           //signup success
           db=new DBConnect();
           int s=db.newUser(name, accountNo, pin);
           if(s==0)
           {
               System.out.print("Success");
               tabPane.getSelectionModel().select(loginTab);
               showWarningDialog("Registration Succesful Login to continue.");
           }
           else if(s==1)
           {
               System.out.println("Already exists");
                showWarningDialog("User already exists");
           }
           else
           {
               System.out.println("Internal error");
               showWarningDialog("An error has occoured try again later.");
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
           String getAccountNumber()
           {
               
               return accountNumber;
           }

     @FXML
    void setRegisterEnabled(ActionEvent event) {
        if(rTerms.isSelected())
            rRegister.setDisable(false);
        else
            rRegister.setDisable(true);
    }
}
