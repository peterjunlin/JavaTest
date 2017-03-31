/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JLJavaTest;

import JLJava.InstanceLocker;
import JLJava.JLNumber;
import JLJava.TestingResult;
import JLJava.TestingResultTable;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
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
    String testItem = "java.lang.Number literal";
    int i = 010;
    resultTable.addRow(testItem, "int i = 010;", Integer.toString(i));
    i = 0xa0;
    resultTable.addRow(testItem, "int i = 0xa0;", Integer.toString(i));
    i = 0b0010;
    resultTable.addRow(testItem, "int i = 0b0010;", Integer.toString(i));
    long lg = 12345L;
    resultTable.addRow(testItem, "long lg = 12345L;", Long.toString(lg));
    lg = 1122_2356_6543L;
    resultTable.addRow(testItem, "long lg = 1122_2356_6543L;", Long.toString(lg));
  }

  @FXML
  private void testJavaLangNumberUnsigned(ActionEvent event) {
    int i = -1;
    long lg = Integer.toUnsignedLong(i);
    resultTable.addRow("java.lang.Number Unsigned", "lg = Integer.toUnsignedLong(-1);", Long.toString(lg));
  }
  
  @FXML
  private void testJavaLangNumberRange(ActionEvent event) {
    String testItem = "java.lang.Number range";
    resultTable.addRow(testItem, "Integer.MAX_VALUE", Integer.toString(Integer.MAX_VALUE));
    resultTable.addRow(testItem, "Integer.MIN_VALUE", Integer.toString(Integer.MIN_VALUE));
    resultTable.addRow(testItem, "Integer.SIZE", Integer.toString(Integer.SIZE));
    resultTable.addRow(testItem, "Integer.BYTES", Integer.toString(Integer.BYTES));
    resultTable.addRow(testItem, "Long.MAX_VALUE", Long.toString(Long.MAX_VALUE));
    resultTable.addRow(testItem, "Long.MIN_VALUE", Long.toString(Long.MIN_VALUE));
    resultTable.addRow(testItem, "Long.BYTES", Integer.toString(Long.BYTES));
    resultTable.addRow(testItem, "Float.MAX_VALUE", Float.toString(Float.MAX_VALUE));
    resultTable.addRow(testItem, "Float.MIN_VALUE", Float.toString(Float.MIN_VALUE));
    resultTable.addRow(testItem, "Float.BYTES", Integer.toString(Float.BYTES));
    resultTable.addRow(testItem, "Double.MAX_VALUE", Double.toString(Double.MAX_VALUE));
    resultTable.addRow(testItem, "Double.MIN_VALUE", Double.toString(Double.MIN_VALUE));
    resultTable.addRow(testItem, "Double.BYTES", Integer.toString(Double.BYTES));
    resultTable.addRow(testItem, "Double.POSITIVE_INFINITY", Double.toString(Double.POSITIVE_INFINITY));
    resultTable.addRow(testItem, "Double.NEGATIVE_INFINITY", Double.toString(Double.NEGATIVE_INFINITY));
  }
  
  @FXML
  private void testJavaLangNumberConversion(ActionEvent event) {
    String testItem = "java.lang.Number conversion";
    resultTable.addRow(testItem, "Double.isFinite(100000000)", Boolean.toString(Double.isFinite(100000000)));
    resultTable.addRow(testItem, "Double.isNaN(100000000)", Boolean.toString(Double.isNaN(100000000)));
    resultTable.addRow(testItem, "Double.parseDouble(12.3)", Double.toString(Double.parseDouble("12.3")));
    String s1 = "abc123";
    resultTable.addRow(testItem, "JLNumber.isNumeric(" + s1 + ")", Boolean.toString(JLNumber.isNumeric(s1)));
  }
  
  @FXML
  private void testJavaLangNumberPrecision(ActionEvent event) {
    double d1 = 10/3;
    resultTable.addRow("java.lang.Number - integer division", "double d1 = 10/3;", Double.toString(d1));
    d1 = 10/3.0;
    resultTable.addRow("java.lang.Number - integer division", "double d1 = 10/3.0;", Double.toString(d1));
    d1 = 10.0/3;
    resultTable.addRow("java.lang.Number - integer division", "double d1 = 10.0/3;", Double.toString(d1));
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
    String testItem = "java.lang.reflect";
    // Get class name
    Class c = "FXMLDocumentController".getClass();
    resultTable.addRow(testItem, "\"FXMLDocumentController\".getClass()", c.getName());
  }
  
  @FXML
  private void testJavaLangCharacter(ActionEvent event) {
    String testItem = "java.lang.Character";
    Character c;
    c = '\056';
    resultTable.addRow(testItem, "Character octal literal", "\\056 =>ASCII "  + Integer.toString((int)c));
    c = '\u009A';
    resultTable.addRow(testItem, "Character octal literal", "\\u009A =>ASCII "  + Integer.toString((int)c));
    resultTable.addRow(testItem, "Character.BYTES", Integer.toString(Character.BYTES));
    resultTable.addRow(testItem, "Character.MAX_VALUE", Integer.toString(Character.MAX_VALUE));
    resultTable.addRow(testItem, "Character.MIN_VALUE", Integer.toString(Character.MIN_VALUE));
  }
  
  @FXML
  private void testJavaUtilObjects(ActionEvent event) {
    String testItem = "java.util.Objects";
    String s1 = null;
    resultTable.addRow(testItem, "Objects.isNull(null)", Boolean.toString(Objects.isNull(s1)));
  }
  
  @FXML
  private void testEnum(ActionEvent event) {
    String testItem = "Enum";
    StringBuilder sb = new StringBuilder();
    
    resultTable.addRow(testItem, "Weekdays.Monday.toString()", Weekdays.Monday.toString());
    resultTable.addRow(testItem, "Weekdays.Monday.ia()", Integer.toString(Weekdays.Monday.ia()));
    
    for (Weekdays w: Weekdays.values()) {
      sb.append(w.toString());
    }
    resultTable.addRow(testItem, "Weekdays values", sb.toString());
    
  }
  enum Weekdays {
    Monday(1,2,3),
    Tuesday(2,3,4);
    
    private final int ia;
    private final int ib;
    private final int ic;
    Weekdays(int a, int b, int c) {
      ia = a;
      ib = b;
      ic = c;
    }
    public int ia() {
      return ia;
    }
    public int ib() {
      return ib;
    }
    public int ic() {
      return ic;
    }
  }
  
  @FXML
  private void testJavaLangVariable(ActionEvent event) {
    String testItem = "Variable";
    
    Integer i = null;
    resultTable.addRow(testItem, "Objects.isNull(i)", "i is " + (Objects.isNull(i) ? "null" : "not null"));
    
    {
      int k = 2;
      resultTable.addRow(testItem, "Declare block variable: {int k = 2;}", Integer.toString(k));
    }
    int k = 3;
    resultTable.addRow(testItem, "Declare function variable with same name as block variable", Integer.toString(k));
    
    for (i=0; i<10; i++) {
      resultTable.addRow(testItem, "Access parent variable", Integer.toString(k));
    }
    
    Integer k1 = 1;
    Integer k2 = k1;
    resultTable.addRow(testItem, "Object assignment by reference", k1==k2 ? "k1 == k2" : "k1 != k2");
  }  
  
  @FXML
  private void testJavaTryCatch(ActionEvent event) {
    String testItem = "try/catch/finally";
    String result = testTryCatchReturn();
    resultTable.addRow(testItem, "Returned value", result);
  }
  private String testTryCatchReturn() {
    String testItem = "try/catch/finally";
    try {
      throw new IOException("thrown exception");
    } catch (IOException e) {
      resultTable.addRow(testItem, "exception message", e.getMessage());
      return "return from (catch)";
    } finally {
      resultTable.addRow(testItem, "finally", "pass finally");
//      return "return from (finally)";
    }
  }
  
  @FXML
  private void testConstant(ActionEvent event) {
    String testItem = "constant";
    final double ABC = 12.3456;
    resultTable.addRow(testItem, "final double ABC = 12.3456;", Double.toString(ABC));
  }

  @FXML
  private void testLambdaExpression(ActionEvent event) {
    String testItem = "Lambda expression";
    FunctionalInterface f = g -> {return (g+1);};

    int k = f.singleFunction(10);
    resultTable.addRow(testItem, "call lambda", Integer.toString(k));
  }
  private interface FunctionalInterface {
    int singleFunction(int f);
  }
  
  @FXML
  private void testIterator(ActionEvent event) {
    String testItem = "Iterator";
    ArrayList arr1 = new ArrayList();
    arr1.add(1);
    arr1.add(2);
    arr1.add(3);
    arr1.add(4);
    arr1.add(5);
    arr1.add(6);
    
    StringBuilder sb = new StringBuilder();
    for (Object i: arr1) {
      sb.append(Integer.toString((Integer)i));
      sb.append(" ");
    }
    resultTable.addRow(testItem, "for (Object i: arr1)", sb.toString());
    
    Iterator ir = arr1.iterator();
    sb.setLength(0);
    while (ir.hasNext()) {
      Integer i = (Integer)ir.next();
      sb.append(i.toString());
      sb.append(" ");
    }
    resultTable.addRow(testItem, "while (ir.hasNext())", sb.toString());
  }
  
  @FXML
  private void testProperty(ActionEvent event) {
    String testItem = "Property";
    Bike bike1 = new Bike();
    bike1.setBrand("TreeLin");
    resultTable.addRow(testItem, "set and get property", bike1.getBrand());
  }
  private class Bike {
    private String brand = "";
    public void setBrand(String s1) {
      brand = s1;
    }
    public String getBrand() {
      return brand;
    }
  }
  
  @FXML
  private void testPassParameters(ActionEvent event) {
    String testItem = "Pass Parameters";
    int i1 = 0;
    testPrimitiveParam(i1);
    resultTable.addRow(testItem, "pass primitive type (immutable)", Integer.toString(i1));

    Integer i2 = 0;
    testPrimitiveClass(i2);
    resultTable.addRow(testItem, "pass primitive class (immutable)", i2.toString());
    
    TestP i3 = new TestP();
    testObjectParam(i3);
    resultTable.addRow(testItem, "pass mutable object", Integer.toString(i3.i));
    
    Class1 c1 = new Class1();
    int i4 = testFunctionParam(c1);
    resultTable.addRow(testItem, "pass interface object", Integer.toString(i4));
    
    String s1 = testMultipleParams("hello", "abc", "def");
    resultTable.addRow(testItem, "pass variable number of parameters", s1);
  }
  private void testPrimitiveParam(int i) {
    i = i + 2;
  }
  private void testPrimitiveClass(Integer i) {
    i = 3;
  }
  private void testObjectParam(TestP i) {
    i.i = 3;
  }
  private class TestP {
    public int i = 0;
  }
  private int testFunctionParam(Interface1 i) {
    return (i.getValue());
  }
  private interface Interface1 {
    public int getValue();
  }
  private class Class1 implements Interface1 {
    @Override
    public int getValue() {
      return 3;
    }
  }
  private String testMultipleParams(String... args) {
    StringBuilder sb = new StringBuilder();
    sb.append("Number of arguments:");
    sb.append(args.length);
    int i = 1;
    for (String arg1: args) {
      sb.append(" (");
      sb.append(Integer.toString(i));
      sb.append(")");
      sb.append(arg1);
      i++;
    }
    return sb.toString();
  }
  
  @FXML
  private void testInterface(ActionEvent event) {
    String testItem = "Interface and abstract class";
    ClassTest t1 = new ClassTest();
    
    resultTable.addRow(testItem, "abstract class variable", Integer.toString(t1.i));
    resultTable.addRow(testItem, "interface method", t1.getValue());
    resultTable.addRow(testItem, "abstract class method", t1.getWeight());
  }
  private interface ITest {
    public String getValue();
  }
  private abstract class AbstractTest implements ITest {
    public int i = 123;
    
    @Override
    public String getValue() {
      return "value";
    }
    
    public abstract String getWeight();
  }
  private class ClassTest extends AbstractTest {
    @Override
    public String getWeight() {
      return "weight";
    }
  }
  
  @FXML
  private void testGeneric(ActionEvent event) {
    String testItem = "Generic";
    GenericTest<Integer> g = new GenericTest<>();
    
    resultTable.addRow(testItem, "generic class and generic function", getGenericTestResult(g, 1, 2));
  }
  private <T> String getGenericTestResult(GenericTest<T> g, T t1, T t2) {
    return Boolean.toString(g.compare(t1, t2));
  }
  private class GenericTest<T> {
    public boolean compare(T t1, T t2) {
      return (t1==t2);
    }
  }
}
