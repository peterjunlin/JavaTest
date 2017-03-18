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
  private void testJavaLangNumberLiteral(ActionEvent event) {
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
  }

  @FXML
  private void testJavaLangNumberUnsigned(ActionEvent event) {
    int i = -1;
    long lg = Integer.toUnsignedLong(i);
    resultTable.addRow("java.lang.Number Unsigned", "lg = Integer.toUnsignedLong(-1);", Long.toString(lg));
  }
  
  @FXML
  private void testJavaLangNumberRange(ActionEvent event) {
    resultTable.addRow("java.lang.Number", "Integer.MAX_VALUE", Integer.toString(Integer.MAX_VALUE));
    resultTable.addRow("java.lang.Number", "Integer.MIN_VALUE", Integer.toString(Integer.MIN_VALUE));
    resultTable.addRow("java.lang.Number", "Integer.SIZE", Integer.toString(Integer.SIZE));
    resultTable.addRow("java.lang.Number", "Integer.BYTES", Integer.toString(Integer.BYTES));
    resultTable.addRow("java.lang.Number", "Long.MAX_VALUE", Long.toString(Long.MAX_VALUE));
    resultTable.addRow("java.lang.Number", "Long.MIN_VALUE", Long.toString(Long.MIN_VALUE));
    resultTable.addRow("java.lang.Number", "Long.BYTES", Integer.toString(Long.BYTES));
    resultTable.addRow("java.lang.Number", "Float.MAX_VALUE", Float.toString(Float.MAX_VALUE));
    resultTable.addRow("java.lang.Number", "Float.MIN_VALUE", Float.toString(Float.MIN_VALUE));
    resultTable.addRow("java.lang.Number", "Float.BYTES", Integer.toString(Float.BYTES));
    resultTable.addRow("java.lang.Number", "Double.MAX_VALUE", Double.toString(Double.MAX_VALUE));
    resultTable.addRow("java.lang.Number", "Double.MIN_VALUE", Double.toString(Double.MIN_VALUE));
    resultTable.addRow("java.lang.Number", "Double.BYTES", Integer.toString(Double.BYTES));
  }
  
  @FXML
  private void testJavaLangNumberPrecision(ActionEvent event) {
    double d1 = 10/3;
    resultTable.addRow("java.lang.Number - integer division", "double d1 = 10/3;", Double.toString(d1));
    d1 = 10/3.0;
    resultTable.addRow("java.lang.Number", "double d1 = 10/3.0;", Double.toString(d1));
    d1 = 10.0/3;
    resultTable.addRow("java.lang.Number", "double d1 = 10.0/3;", Double.toString(d1));
    d1 = 1000000.0f + 1.2f - 1000000.0f;
    resultTable.addRow("java.lang.Number - float to double lose precisions", "d1 = 1000000.0f + 1.2f - 1000000.0f;", Double.toString(d1));
    d1 = 1_000_000_000_000_000.0d + 1.2d - 1_000_000_000_000_000.0d;
    resultTable.addRow("java.lang.Number - double calculation lose precisions", "d1 = 1_000_000_000_000_000.0d + 1.2f - 1_000_000_000_000_000.0d;", Double.toString(d1));
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
