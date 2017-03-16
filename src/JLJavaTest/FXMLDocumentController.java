/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JLJavaTest;

import JLJava.InstanceLocker;
import JLJava.TestingResult;
import JLJava.TestingResultTable;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author jl
 */
public class FXMLDocumentController implements Initializable {
  
  @FXML private TextArea textOutput;
  @FXML private TableView<TestingResult> tableOutput;
  
  private TestingResultTable resultTable;
  private InstanceLocker insLock = null;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // Init result table
    resultTable = new TestingResultTable(tableOutput);
    
    // test single instance lock
    insLock = new InstanceLocker();
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
  
  @FXML
  private void testInstanceLock(ActionEvent event) throws IOException {
    if (insLock.isSingleInstance()) {
      resultTable.addRow("InstanceLocker", "isSingleInstance()", "true");
    } else {
      resultTable.addRow("InstanceLocker", "isSingleInstance()", "false");
    }
  }
  
  @FXML
  private void testJavaLangSystem(ActionEvent event) throws IOException {
    // System.getenv()
    Map<String, String> map1 = System.getenv();
    map1.entrySet().forEach((e) -> {
      resultTable.addRow("System.getenv()", e.getKey(), e.getValue());
    });
    
    // System.getenv("Path")
    resultTable.addRow("System.getenv(\"Path\")", "Path", System.getenv("Path"));
  }
  
}
