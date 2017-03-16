/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JLJava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author jl
 */
public class TestingResultTable {
  private final ObservableList<TestingResult> olstTestingResult = FXCollections.observableArrayList();
  
  public TestingResultTable(TableView<TestingResult> tableView1) {
    tableInit(tableView1, olstTestingResult);
  }

  /**
   * Initialize table view to be used for testing output.
   * Create table columns - Testing Item, Result.
   * @param table1 The TableView object to be initialized.
   * @param olstTestingResult1 The ObservableList is bound to.
   */
  private void tableInit(TableView table1, ObservableList<TestingResult> olstTestingResult1) {
    // Init tableView
    table1.setEditable(false);
    table1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    table1.setItems(olstTestingResult1);
    
    TableColumn<TestingResult,String> testItemCol = new TableColumn("Test Item");
    testItemCol.setCellValueFactory(new PropertyValueFactory("testItem"));
    testItemCol.minWidthProperty().set(100);
    testItemCol.maxWidthProperty().set(200);
    
    TableColumn<TestingResult,String> testKeyCol = new TableColumn("Test Key");
    testKeyCol.setCellValueFactory(new PropertyValueFactory("testKey"));
    testKeyCol.minWidthProperty().set(100);
    testKeyCol.maxWidthProperty().set(200);
    
    TableColumn<TestingResult,String> testValueCol = new TableColumn("Test Value");
    testValueCol.setCellValueFactory(new PropertyValueFactory("testValue"));
    testValueCol.minWidthProperty().set(100);
    
    table1.setItems(olstTestingResult1);
    table1.getColumns().setAll(testItemCol, testKeyCol, testValueCol);    
  }
  
  /**
   * Add testing result to the table view.
   * @param testItem
   * @param testKey
   * @param testValue
   */
  public void addRow(String testItem, String testKey, String testValue) {
    olstTestingResult.add(new TestingResult(testItem, testKey, testValue));
  }
  
}
