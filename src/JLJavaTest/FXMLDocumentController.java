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
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

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
  private void testInstanceLocker(ActionEvent event) throws IOException {
    if (insLock.isSingleInstance()) {
      resultTable.addRow("InstanceLocker", "isSingleInstance()", "true");
    } else {
      resultTable.addRow("InstanceLocker", "isSingleInstance()", "false");
    }
  }
  
  @FXML
  private void testJavaLangBoolean(ActionEvent event) {
    Boolean b = true;
    resultTable.addRow("java.lang.Boolean", "Boolean b = true; \nvalue of b is:", b.toString());
  }
  
  @FXML
  private void testJavaLangNumber(ActionEvent event) {
    int i = 010;
    resultTable.addRow("java.lang.Number", "int i = 010;", Integer.toString(i));
    i = 0xa0;
    resultTable.addRow("java.lang.Number", "int i = 0xa0;", Integer.toString(i));
    i = 0b0010;
    resultTable.addRow("java.lang.Number", "int i = 0b0010;", Integer.toString(i));
    long lg = 12345L;
    resultTable.addRow("java.lang.Number", "long lg = 12345L;", Long.toString(lg));
    lg = 1122_2356_6543L;
    resultTable.addRow("java.lang.Number", "long lg = 1122_2356_6543L;", Long.toString(lg));
    i = -1;
    lg = Integer.toUnsignedLong(i);
    resultTable.addRow("java.lang.Number", "lg = Integer.toUnsignedLong(-1);", Long.toString(lg));
    resultTable.addRow("java.lang.Number", "Integer.BYTES", Integer.toString(Integer.BYTES));
    resultTable.addRow("java.lang.Number", "Integer.SIZE", Integer.toString(Integer.SIZE));
    resultTable.addRow("java.lang.Number", "Integer.MAX_VALUE", Integer.toString(Integer.MAX_VALUE));
    resultTable.addRow("java.lang.Number", "Integer.MIN_VALUE", Integer.toString(Integer.MIN_VALUE));
    double d1 = 10/3;
    resultTable.addRow("java.lang.Number", "double d1 = 10/3;", Double.toString(d1));
    d1 = 10/3.0;
    resultTable.addRow("java.lang.Number", "double d1 = 10/3.0;", Double.toString(d1));
    d1 = 10.0/3;
    resultTable.addRow("java.lang.Number", "double d1 = 10.0/3;", Double.toString(d1));
  }
  
  @FXML
  private void testJavaLangSystem(ActionEvent event) {
    // System.getenv()
    Map<String, String> map1 = System.getenv();
    map1.entrySet().forEach((e) -> {
      resultTable.addRow("System.getenv()", e.getKey(), e.getValue());
    });
    
    // System.getenv("Path")
    resultTable.addRow("System.getenv(\"Path\")", "Path", System.getenv("Path"));
    // Check whether an environment variable exists
    resultTable.addRow("System.getenv(\"Path1\")", "Path1", System.getenv("Path1")==null ? "not exists" : "exists");
    
    // System.getProperties()
    Properties p = System.getProperties();
    Enumeration<?> keys = p.propertyNames();
    while (keys.hasMoreElements()) {
      String key = (String) keys.nextElement();
      String value = (String) p.getProperty(key);
      resultTable.addRow("System.getProperties()", key, value);
    };
    
  }

  @FXML
  private void testJavaLangReflect(ActionEvent event) {
    // Get class name
    Class c = "FXMLDocumentController".getClass();
    resultTable.addRow("java.lang.reflect", "\"FXMLDocumentController\".getClass()", c.getName());
  }
}
