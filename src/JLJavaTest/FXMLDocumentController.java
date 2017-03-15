/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JLJavaTest;

import JLJava.InstanceLock;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

/**
 *
 * @author jl
 */
public class FXMLDocumentController implements Initializable {
  
  @FXML
  private TextArea textOutput;
  
  private InstanceLock insLock = null;
    
  @FXML
  private void testInstanceLock(ActionEvent event) throws IOException {
    if (insLock.isSingleInstance()) {
      textOutput.setText("This application is a single instance!");
    } else {
      textOutput.setText("This application is not a single instance!");
    }
  }
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    insLock = new InstanceLock();
    try {
      if (!insLock.lock()) {
        Alert alertBox = new Alert(Alert.AlertType.ERROR);
        alertBox.setContentText("Another instance is running");
        alertBox.showAndWait();
        System.exit(1);
      }
    } catch (IOException e) {
      Alert alertBox = new Alert(Alert.AlertType.ERROR);
      alertBox.setContentText(e.getLocalizedMessage());
      alertBox.showAndWait();
    }
  }  
  
}
