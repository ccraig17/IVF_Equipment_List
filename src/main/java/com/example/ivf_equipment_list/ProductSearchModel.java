package com.example.ivf_equipment_list;
/*
   The ProductSearchModel Class represents the (Model) data/Obj in the Database and the Items to be searched
   The wrapper class is used for integers in placed of the Primary datatype "int"
   The Wrapper class will then be passed into an "ObservableList"  as an OBJECT which requires Objects.
   The attributes/fields of the ProductSearchModel Class are lined/setup as they are in the database (according to the order of the columns)
*/
public class ProductSearchModel {
    private Integer id;
    private String name;
    private String model;
    private String serialNumber;
    private String PMDueDate;
    private String description;

    public ProductSearchModel(Integer id, String name, String model, String serialNumber, String PMDueDate, String description) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.serialNumber = serialNumber;
        this.PMDueDate = PMDueDate;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPMDueDate() {
        return PMDueDate;
    }

    public void setPmDueDate(String PMDueDate) {
        this.PMDueDate = PMDueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
