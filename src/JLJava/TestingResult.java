/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JLJava;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author jl
 */
public class TestingResult {
  private final SimpleStringProperty testItem;
  private final SimpleStringProperty testKey;
  private final SimpleStringProperty testValue;
  
  public TestingResult(String testItem1, String testKey1, String testValue1) {
    this.testItem = new SimpleStringProperty(testItem1);
    this.testKey = new SimpleStringProperty(testKey1);
    this.testValue = new SimpleStringProperty(testValue1);
  }
        
  public void setTestItem(String value) { testItem.set(value); }
  public String getTestItem() { return testItem.get(); }

  public void setTestKey(String value) { testKey.set(value); }
  public String getTestKey() { return testKey.get(); }

  public void setTestValue(String value) { testValue.set(value); }
  public String getTestValue() { return testValue.get(); }
}
