package com.example.sqliteassignment;

public class Events {

    private int id;
    private String eventName, ProfilePic;
    private String eventTime;
    private String eventInformation, TimeUpdated;
    private String eventVenue;
    private String username;

    private String productName, productDesc, productDateCreated, productDateUpdated, CategoryTblName;
    private int ProductID, productPrice, productListPrice, productRetailPrice, UserID, TransactionID;
    public Events(int id, String eventName, String eventTime, String eventInformation, String eventVenue) {
        this.id = id;
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.eventInformation = eventInformation;
        this.eventVenue = eventVenue;
    }

    public String getTimeUpdated() {
        return TimeUpdated;
    }

    public void setTimeUpdated(String timeUpdated) {
        TimeUpdated = timeUpdated;
    }

    public Events(String eventName, String eventTime, String eventInformation, String eventVenue, String TimeUpdated, String profilePic) {
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.eventInformation = eventInformation;
        this.eventVenue = eventVenue;
        this.TimeUpdated = TimeUpdated;
        this.ProfilePic = profilePic;
    }
    public Events(String eventName, String eventTime, String eventInformation, String eventVenue) {
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.eventInformation = eventInformation;
        this.eventVenue = eventVenue;
    }
    public Events(String productname, String productdesc, int productprice, int productlistprice, int productretailprice, String dateupdated, String categoryName){
        this.productName = productname;
        this.productDesc = productdesc;
        this.productPrice = productprice;
        this.productListPrice = productlistprice;
        this.productRetailPrice = productretailprice;
        this.productDateUpdated = dateupdated;
        this.CategoryTblName = categoryName;
    }
    public Events(String categoryName){
        this.eventName = categoryName;
    }

    public String getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(String profilePic) {
        ProfilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Events(int id, String productsName, String productsDesc, int productsPrice, int productsListPrice, int productsRetailPrice, String productsDateCreated, String productsDateUpdated, String CategorysName){
        this.id = id;
        this.productName = productsName;
        this.productDesc = productsDesc;
        this.productPrice = productsPrice;
        this.productListPrice = productsListPrice;
        this.productRetailPrice = productsRetailPrice;
        this.productDateCreated = productsDateCreated;
        this.productDateUpdated = productsDateUpdated;
        this.CategoryTblName = CategorysName;
    }
    public Events(String productsName, String productsDesc, int productsPrice, int productsListPrice, int productsRetailPrice, String productsDateCreated, String productsDateUpdated, String CategoryTblsName){
        this.productName = productsName;
        this.productDesc = productsDesc;
        this.productPrice = productsPrice;
        this.productListPrice = productsListPrice;
        this.productRetailPrice = productsRetailPrice;
        this.productDateCreated = productsDateCreated;
        this.productDateUpdated = productsDateUpdated;
        this.CategoryTblName = CategoryTblsName;
    }
    public Events(int userID, int productID, int transactionID){
        this.UserID = userID;
        this.UserID = productID;
        this.TransactionID = transactionID;
    }

    public Events(String userName, String ProductNames, int productPrice){
        this.username = userName;
        this.productName = ProductNames;
        this.productPrice = productPrice;
    }

    public String getCategoryTblName() {
        return CategoryTblName;
    }

    public void setCategoryTblName(String categoryTblName) {
        CategoryTblName = categoryTblName;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductListPrice(int productListPrice) {
        this.productListPrice = productListPrice;
    }

    public void setProductRetailPrice(int productRetailPrice) {
        this.productRetailPrice = productRetailPrice;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(int transactionID) {
        TransactionID = transactionID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getProductName(){
        return productName;
    }
    public String getProductDesc(){
        return productDesc;
    }
    public int getProductPrice(){
        return productPrice;
    }
    public int getProductListPrice(){
        return productListPrice;
    }
    public int getProductRetailPrice(){
        return productRetailPrice;
    }
    public String getProductDateCreated(){
        return productDateCreated;
    }
    public String getProductDateUpdated(){
        return productDateUpdated;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }


    public void setProductDateCreated(String productDateCreated) {
        this.productDateCreated = productDateCreated;
    }

    public void setProductDateUpdated(String productDateUpdated) {
        this.productDateUpdated = productDateUpdated;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventInformation() {
        return eventInformation;
    }

    public void setEventInformation(String eventInformation) {
        this.eventInformation = eventInformation;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }
}
