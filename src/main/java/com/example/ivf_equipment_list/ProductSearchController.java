package com.example.ivf_equipment_list;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

/*
 The Controller (ProductSearchController Class) for the FXML (productSearch.fxml) MUST implement "Initializable" in order to connect and call the Main Method
 The ONLY method in the "Initializable" Class (void initialize(URL url, ResourceBundle resourceBundle)) is called.
 The attributes for "ProductSearchController" class are from the fx:id assigned in "Scene Builder."
 Each Attribute uses the class it belongs to, e.g. productSearchModelTableView is a "Table View" and therefore uses "TableView" class.
 Each of the attributes' Class will have TWO TYPES: <Class of the Object TypeData, Column Data Type> followed by fx:id assigned in "Scene Builder."
 An "ObservableList<T>" is used to instantiate and hold the List of objects/items using "FxCollections.observableArrayList()
 The "ObservableList" will allow our LISTENER to track of changes
 Problem: If you are getting "WARNING: Can not retrieve property 'name' in PropertyValueFactory: javafx.scene.control.cell.PropertyValueFactory@6771b7fb with provided class type:"
 Solution: make sure the "getters" and "fields/attributes" are: 1.NOT Misspelled 2.the "Getter" in the class appears just as the column in the Database
 */
public class ProductSearchController implements Initializable {
    @FXML
    private TableView<ProductSearchModel> productTableView;
    @FXML
    private TableColumn<ProductSearchModel, Integer> idProductTableColumn;
    @FXML
    private TableColumn<ProductSearchModel, String> nameTableColumn;
    @FXML
    private TableColumn<ProductSearchModel, String> modelTableColumn;
    @FXML
    private TableColumn<ProductSearchModel, String> serialNumberTableColumn;
    @FXML
    private TableColumn<ProductSearchModel, Date> pmDueDateTableColumn;
    @FXML
    private TableColumn<ProductSearchModel, String> descriptionTableColumn;
    @FXML
    private TextField keywordtextField;

    ObservableList<ProductSearchModel> productSearchModelObservableList = FXCollections.observableArrayList();

    //create initialize Method to implement Initializable interface. - has on Method.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //here the database connection is used, along with the SQL Query for the ALL the Column names.
        Datasource connectNow = new Datasource();
        Connection connectDB = connectNow.getConnect();

        //SQL Query - Executed in the backend database.
        String productViewQuery = "SELECT ID, Name, Model, SerialNumber, PMDueDate, Description FROM Equipment_List";

                try{
                    //Statement used to connect to Database and ResultSet used to collect the returning Results into the "queryOut" from the Database Query statement "productViewQuery."
                    Statement statement = connectDB.createStatement();
                    ResultSet queryOut = statement.executeQuery(productViewQuery);

                    //the while-loop loops thru the results from the "productViewQuery" for each column and populates the "ObservableList"
                    //make returning data for each column readable by creating variable to ADD into "new ProductSearchModel()" to then ADD to the "productSearchModelObservableList" object.

                        while(queryOut.next()){
                            Integer queryId = queryOut.getInt("id");
                            String queryName = queryOut.getString("name");
                            String queryModel = queryOut.getString("model");
                            String querySerialNumber = queryOut.getString("serialNumber");
                            //String queryPMDueDate = queryOut.getString("PMDueDate");
                            String queryPMDueDate = String.valueOf(queryOut.getDate("PMDueDate"));
                            String queryDescription = queryOut.getString("description");

                            //Populates the ObservableList
                            productSearchModelObservableList.add(new ProductSearchModel(queryId, queryName, queryModel, querySerialNumber, queryPMDueDate, queryDescription ));
                        }

                        //Assign the "cellValue" for the columns with".setCellValueFactory(new PropertyValueFactory<>("Column Name")
                        //PropertyValueFactory corresponds to the new ProductSearchModel fields created from the result query from the database
                        //This process is setting the value for each of the columns in the Table View after the data is retrieved from the database
                        //The table columns are the ones annotated above
                            idProductTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                            nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                            modelTableColumn.setCellValueFactory(new PropertyValueFactory<>("Model"));
                            serialNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("SerialNumber"));
                            pmDueDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("PMDueDate")); //******ERROR HERE!*******
                            descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));

                            //Next the "productTableView"; adding/populate the items/List to this View by adding "productSearchModelObservableList" to the productSearchModelViewTable
                           productTableView.setItems(productSearchModelObservableList);

                     //initial filtered list
                    FilteredList<ProductSearchModel> filteredData = new FilteredList<>(productSearchModelObservableList, b -> true);
                        keywordtextField.textProperty().addListener((observable,oldValue, newValue) -> {
                            filteredData.setPredicate(productSearchModel ->{
                            // **Testing for NULL** if no search value then display all records or whatever records it current have (no change)
                            if(newValue.isEmpty() || newValue.isBlank() || newValue==null) return true;

                            //converts the searchKeyword to lowercase to simply search
                            String searchKeyword = newValue.toLowerCase();
                            //.indexOf(searchKeyword) searches the ObservableList by index, if the index in greater than -1 or !=-1, the item DOES NOT exist in the list
                            if(productSearchModel.getName().toLowerCase().indexOf(searchKeyword) > -1){
                                return true; //means we found a match in Equipment name
                            }else if(productSearchModel.getModel().toLowerCase().indexOf(searchKeyword) !=-1){
                                return true; //means we found a match in Model
                            } else if (productSearchModel.getSerialNumber().toLowerCase().indexOf(searchKeyword) >-1) {
                                return true;//means we found a match in Equipment Serial Number
                            } else if (productSearchModel.getPMDueDate().toString().toLowerCase().indexOf(searchKeyword) !=-1) {
                                return true; //means we found a match in Equipment PM Due Date (**PM Due Date Converted to String**)
                            } else if (productSearchModel.getDescription().toLowerCase().indexOf(searchKeyword) >-1) {
                                return true;
                            }else return false; //means NO MATCH FOUND!
                            });
                        });

                    //SortedList<Class Name> Wrap and Sort(in order) the filteredData
                    //then bind the sortedData results with Table View making results in SYNC/Dynamic.
                    SortedList<ProductSearchModel> sortedData = new SortedList<>(filteredData);

                    //binds the sortedData to the productTableView to make them in-sync
                    sortedData.comparatorProperty().bind(productTableView.comparatorProperty());
                        //Apply the filtered and sorted data to the Table View during initialization
                        productTableView.setItems(sortedData);

                }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            }

    }
}









