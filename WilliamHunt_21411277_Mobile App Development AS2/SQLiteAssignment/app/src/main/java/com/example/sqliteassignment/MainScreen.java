package com.example.sqliteassignment;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainScreen extends AppCompatActivity{

    public ArrayList<String> categoryDropDownItems, UserNames, currentProducts, profilePics, productNames, productDetails, ListOfNames;
    public static String UserNameText, profilePictureString;
    private String DisplayBasketContents;

    private String DisplayProductTotal;
    private Events e1;
    private int clickcount = 0;
    LinearLayout.LayoutParams params;
    private String Picture1 = "Picture1", Picture2 = "Picture2", Picture3 = "Picture3";
    public static String EditCategory;
    public String ProductsTotal, BasketString;
    public int BasketTotal;
    public String productName = "", productDesc = "", productPrice="";

    private Boolean ShowCategoryEdit = false, ShowProductEdit = false;
    CategoriesDBClass db;
    ProductsDBClass productDB;
    BasketDBClass basketDB;
    List<String> eventsList;
    List<String> productList;
    public static String AddCategories;
    public static String strCategoryName, strUserName;
    public static String SelectedCategoryItem, SelectedProductItem, SelectedProductName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        LinearLayout layout = findViewById(R.id.LayoutBeneathDropDown);
        Spinner dropdown = findViewById(R.id.DropDownSpinner);
        dropdown.setBackgroundResource(R.drawable.dropdown);
        db = new CategoriesDBClass(this);
        db.BasketCheck();
        db.OrderCheck();
        db.ProfileCheck();
        basketDB = new BasketDBClass(this);
        ListOfNames = new ArrayList<>();
        //basketDB.CreateTable();
        //basketDB.CreateTable();
        //db.BasketCheck();
        //db.CreateTable();
        //basketDB.CreateTable();
        productDB = new ProductsDBClass(this);
        //productDB.CreateTable();
        productDetails = new ArrayList<>();
        System.out.println("UserName " + UserNameText);
        //productDB.CreateTable();
        //productDB.CreateTable();
        categoryDropDownItems = db.getCategoryNames();
        int buttonStyle = R.style.layout;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryDropDownItems);
        dropdown.setAdapter(adapter);
        //productList = productDB.getAll();
        Intent LogOutIntent = new Intent(this, Login.class);
        Button LogOutBtn = new Button(MainScreen.this);
       /* params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1
        );
        */
       // Button LogOutBtn = new Button(new ContextThemeWrapper(MainScreen.this, buttonStyle), null, buttonStyle);
        params = (LinearLayout.LayoutParams) layout.getLayoutParams();
        params.setMargins(0, 2, 0, 0);
        params.gravity = 0;
        LogOutBtn.setLayoutParams(params);
        LogOutBtn.setBackgroundColor(getResources().getColor(R.color.black));
        LogOutBtn.setTextColor(Color.parseColor("#FFFFFF"));
        LogOutBtn.setText("Log Out");
        if(!Login.isAdmin) {
            layout.addView(LogOutBtn);
            Bundle intentExtras = getIntent().getExtras();
            UserNameText = intentExtras.getString("Username");
        }
        if(Login.isAdmin) {
            Button EditCategories = new Button(this);
            EditCategories.setLayoutParams(params);
            EditCategories.setBackgroundColor(getResources().getColor(R.color.black));
            EditCategories.setTextColor(Color.parseColor("#FFFFFF"));
            Button EditProducts = new Button(this);
            EditProducts.setLayoutParams(params);
            EditProducts.setBackgroundColor(getResources().getColor(R.color.black));
            EditProducts.setTextColor(Color.parseColor("#FFFFFF"));
            EditCategories.setText("Edit Categories");
            EditProducts.setText("Edit Products");
            Button ReturnBtn = new Button(MainScreen.this);
            ReturnBtn.setLayoutParams(params);
            ReturnBtn.setBackgroundColor(getResources().getColor(R.color.black));
            ReturnBtn.setTextColor(Color.parseColor("#FFFFFF"));
            ReturnBtn.setText("Return");
            Button UserBtn = new Button(this);
            UserBtn.setLayoutParams(params);
            UserBtn.setBackgroundColor(getResources().getColor(R.color.black));
            UserBtn.setTextColor(Color.parseColor("#FFFFFF"));
            UserBtn.setText("Edit Users");
            Button delUser = new Button(this);
            delUser.setLayoutParams(params);
            delUser.setBackgroundColor(getResources().getColor(R.color.black));
            delUser.setTextColor(Color.parseColor("#FFFFFF"));
            Button OrderBtn = new Button(this);
            OrderBtn.setLayoutParams(params);
            OrderBtn.setBackgroundColor(getResources().getColor(R.color.black));
            OrderBtn.setTextColor(Color.parseColor("#FFFFFF"));
            Button CreateFileForOrders = new Button(this);
            CreateFileForOrders.setLayoutParams(params);
            CreateFileForOrders.setBackgroundColor(getResources().getColor(R.color.black));
            CreateFileForOrders.setTextColor(Color.parseColor("#FFFFFF"));
            Button ViewAllOrders = new Button(this);
            ViewAllOrders.setLayoutParams(params);
            ViewAllOrders.setBackgroundColor(getResources().getColor(R.color.black));
            ViewAllOrders.setTextColor(Color.parseColor("#FFFFFF"));
            Button UpdateProducts = new Button(this);
            UpdateProducts.setLayoutParams(params);
            UpdateProducts.setBackgroundColor(getResources().getColor(R.color.black));
            UpdateProducts.setTextColor(Color.parseColor("#FFFFFF"));
            UpdateProducts.setText("Update Product Details");
            ViewAllOrders.setText("View All Orders");
            CreateFileForOrders.setText("Create File To Store Orders In");
            OrderBtn.setText("Check Orders");
            delUser.setText("Delete Users");
            Spinner UserCheck = new Spinner(MainScreen.this);
            UserCheck.setBackgroundResource(R.drawable.dropdown);
            dbClass dbcl = new dbClass(MainScreen.this);
            UserNames = dbcl.getUserNames();
            ArrayAdapter<String> userAd = new ArrayAdapter<>(MainScreen.this, android.R.layout.simple_spinner_dropdown_item, UserNames);
            UserCheck.setAdapter(userAd);
            layout.addView(EditCategories);layout.addView(EditProducts);
            layout.addView(UserBtn);layout.addView(delUser);layout.addView(OrderBtn);
            layout.addView(UpdateProducts);
            layout.addView(LogOutBtn);
            UpdateProducts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Spinner productNames = new Spinner(MainScreen.this);
                    productNames.setBackgroundResource(R.drawable.dropdown);
                    ListOfNames = productDB.GetProductNames();
                    SelectedProductName = ListOfNames.get(0);
                    ArrayAdapter<String> newadapter2 = new ArrayAdapter<>(MainScreen.this, android.R.layout.simple_spinner_dropdown_item, ListOfNames);
                    productNames.setAdapter(newadapter2);

                    EditText UpdatedProductName = new EditText(MainScreen.this);
                    UpdatedProductName.setHintTextColor(Color.parseColor("#FFFFFF"));
                    UpdatedProductName.setTextColor(Color.parseColor("#FFFFFF"));
                    UpdatedProductName.setBackgroundColor(Color.parseColor("#3498db"));
                    EditText productDesc = new EditText(MainScreen.this);
                    productDesc.setHintTextColor(Color.parseColor("#FFFFFF"));
                    productDesc.setBackgroundColor(Color.parseColor("#3498db"));
                    productDesc.setTextColor(Color.parseColor("#FFFFFF"));
                    EditText productPrice = new EditText(MainScreen.this);
                    productPrice.setHintTextColor(Color.parseColor("#FFFFFF"));
                    productPrice.setBackgroundColor(Color.parseColor("#3498db"));
                    productPrice.setTextColor(Color.parseColor("#FFFFFF"));
                    EditText productListPrice = new EditText(MainScreen.this);
                    productListPrice.setHintTextColor(Color.parseColor("#FFFFFF"));
                    productListPrice.setBackgroundColor(Color.parseColor("#3498db"));
                    productListPrice.setTextColor(Color.parseColor("#FFFFFF"));
                    EditText productRetailPrice = new EditText(MainScreen.this);
                    productRetailPrice.setHintTextColor(Color.parseColor("#FFFFFF"));
                    productRetailPrice.setBackgroundColor(Color.parseColor("#3498db"));
                    productRetailPrice.setTextColor(Color.parseColor("#FFFFFF"));
                    Spinner CategoryName = new Spinner(MainScreen.this);
                    CategoryName.setBackgroundResource(R.drawable.dropdown);
                    Button Update = new Button(MainScreen.this);
                    Update.setLayoutParams(params);
                    Update.setBackgroundColor(getResources().getColor(R.color.black));
                    Update.setTextColor(Color.parseColor("#FFFFFF"));
                    Update.setText("Update Product Details");
                    productNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            SelectedProductName = (String) adapterView.getItemAtPosition(i);
                            ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                            productDetails = productDB.getProductDetails();
                            UpdatedProductName.setText(SelectedProductName);
                            productDesc.setText(productDetails.get(0));
                            productPrice.setText(productDetails.get(1));
                            productListPrice.setText(productDetails.get(2));
                            productRetailPrice.setText(productDetails.get(3));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    categoryDropDownItems = db.getCategoryNames();
                    categoryDropDownItems.remove("Basket");
                    categoryDropDownItems.remove("Profile");
                    categoryDropDownItems.remove("Order");
                    ArrayAdapter<String> newadapter = new ArrayAdapter<>(MainScreen.this, android.R.layout.simple_spinner_dropdown_item, categoryDropDownItems);
                    CategoryName.setAdapter(newadapter);


                    layout.removeAllViews();
                    layout.addView(productNames);
                    layout.addView(UpdatedProductName);
                    layout.addView(productDesc);layout.addView(productPrice);
                    layout.addView(productListPrice);layout.addView(productRetailPrice);
                    layout.addView(CategoryName);layout.addView(Update);
                    layout.addView(ReturnBtn);
                    CategoryName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            strCategoryName = (String) adapterView.getItemAtPosition(i);
                            ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                            System.out.println(strCategoryName);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    productDetails = productDB.getProductDetails();
                    UpdatedProductName.setText(SelectedProductName);
                    productDesc.setText(productDetails.get(0));
                    productPrice.setText(productDetails.get(1));
                    productListPrice.setText(productDetails.get(2));
                    productRetailPrice.setText(productDetails.get(3));
                    Update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String productname = UpdatedProductName.getText().toString();
                            String productdesc = productDesc.getText().toString();
                            Integer strPPrice = Integer.parseInt(productPrice.getText().toString());
                            Integer strPListPrice = Integer.parseInt(productListPrice.getText().toString());
                            Integer strPRetailPrice = Integer.parseInt(productRetailPrice.getText().toString());
                            //strCategoryName = AddCategoryID.getText().toString();
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
                            Date currentTime = Calendar.getInstance().getTime();
                            String date = dateFormat.format(calendar.getTime());
                            String Datetime = date + " " + currentTime;
                            if(!productname.isEmpty() && !productdesc.isEmpty() && !strPPrice.toString().isEmpty() && !strPListPrice.toString().isEmpty() && !strPRetailPrice.toString().isEmpty() && !productDB.productExists(productname))
                            {
                                Events e1 = new Events(productname, productdesc, strPPrice, strPListPrice, strPRetailPrice, Datetime, strCategoryName);
                                productDB.updateEvents(e1);
                                ListOfNames = productDB.GetProductNames();
                                ArrayAdapter<String> newadapter2 = new ArrayAdapter<>(MainScreen.this, android.R.layout.simple_spinner_dropdown_item, ListOfNames);
                                productNames.setAdapter(newadapter2);
                            }
                        }
                    });
                }
            });
            EditCategories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layout.removeAllViews();
                    EditText AddCategoriesTxt = new EditText(MainScreen.this);
                    AddCategoriesTxt.setHintTextColor(Color.parseColor("#FFFFFF"));
                    AddCategoriesTxt.setBackgroundColor(Color.parseColor("#3498db"));
                    AddCategoriesTxt.setTextColor(Color.parseColor("#FFFFFF"));
                    AddCategoriesTxt.setHint("Add Categories Name To Database");
                    Button AddCategoriesBtn = new Button(MainScreen.this);
                    AddCategoriesBtn.setLayoutParams(params);
                    AddCategoriesBtn.setBackgroundColor(getResources().getColor(R.color.black));
                    AddCategoriesBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    EditText EditCategoriesTxt = new EditText(MainScreen.this);
                    EditCategoriesTxt.setHintTextColor(Color.parseColor("#FFFFFF"));
                    EditCategoriesTxt.setBackgroundColor(Color.parseColor("#3498db"));
                    EditCategoriesTxt.setTextColor(Color.parseColor("#FFFFFF"));
                    EditCategoriesTxt.setHint("Delete Categories");
                    Button DeleteCategoriesBtn = new Button(MainScreen.this);
                    DeleteCategoriesBtn.setLayoutParams(params);
                    DeleteCategoriesBtn.setBackgroundColor(getResources().getColor(R.color.black));
                    DeleteCategoriesBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    DeleteCategoriesBtn.setText("Delete Category");
                    AddCategoriesBtn.setText("Submit Category");
                    layout.addView(AddCategoriesTxt);
                    layout.addView(AddCategoriesBtn);
                    layout.addView(EditCategoriesTxt);
                    layout.addView(DeleteCategoriesBtn);
                    layout.addView(ReturnBtn);
                    DeleteCategoriesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String deleteCategory = EditCategoriesTxt.getText().toString();
                            db.deleteEvents(deleteCategory);
                            if(categoryDropDownItems.contains(deleteCategory)){
                                Toast.makeText(MainScreen.this, "Category Deleted", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(MainScreen.this, "Category Doesn't Exist", Toast.LENGTH_SHORT).show();
                            }
                            categoryDropDownItems = db.getCategoryNames();
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainScreen.this, android.R.layout.simple_spinner_dropdown_item, categoryDropDownItems);
                            dropdown.setAdapter(adapter);
                        }
                    });
                    AddCategoriesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AddCategories = AddCategoriesTxt.getText().toString();
                            if (!AddCategories.isEmpty() && !categoryDropDownItems.contains(AddCategories) && db.SearchCategory()) {
                                Toast.makeText(MainScreen.this, "Category Added", Toast.LENGTH_SHORT).show();
                                Events e1 = new Events(AddCategories);
                                db.addEvents(e1);
                                categoryDropDownItems = db.getCategoryNames();
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainScreen.this, android.R.layout.simple_spinner_dropdown_item, categoryDropDownItems);
                                dropdown.setAdapter(adapter);
                            }
                            else if(AddCategories.isEmpty()){
                                Toast.makeText(MainScreen.this, "Enter Information", Toast.LENGTH_SHORT).show();
                            }
                            else if(categoryDropDownItems.contains(AddCategories)){
                                Toast.makeText(MainScreen.this, "Category Already Exists", Toast.LENGTH_SHORT).show();
                            }
                            else if(!db.SearchCategory()){
                                Toast.makeText(MainScreen.this, "Category already Exists", Toast.LENGTH_SHORT).show();
                            }
                    /*for (int i = 0 ; i < eventsList.size() ; i++) {
                        //Log.d("Value Is", String.valueOf(eventsList.get(i)));
                        Log.d("value is", eventsList.get(i).toString());
                    }*/
                            //Log.d("Value Of ", CategoriesDBClass.cursor.getString(CategoriesDBClass.cursor.getColumnIndexOrThrow(CategoriesDBClass.CategoryName)));
                    /*String DisplayAllItemsInList = "";

                    for (String s : eventsList) {
                        DisplayAllItemsInList += s + " ";
                    }
                    String DisplayBasketTotal = DisplayAllItemsInList + "\n";
                    System.out.println(DisplayBasketTotal);*/

                        }
                    });
                }
            });
            EditProducts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layout.removeAllViews();
                    EditText AddProductName = new EditText(MainScreen.this);
                    AddProductName.setHintTextColor(Color.parseColor("#FFFFFF"));
                    AddProductName.setBackgroundColor(Color.parseColor("#3498db"));
                    AddProductName.setTextColor(Color.parseColor("#FFFFFF"));
                    EditText AddProductDesc = new EditText(MainScreen.this);
                    AddProductDesc.setHintTextColor(Color.parseColor("#FFFFFF"));
                    AddProductDesc.setBackgroundColor(Color.parseColor("#3498db"));
                    AddProductDesc.setTextColor(Color.parseColor("#FFFFFF"));
                    EditText AddProductPrice = new EditText(MainScreen.this);
                    AddProductPrice.setHintTextColor(Color.parseColor("#FFFFFF"));
                    AddProductPrice.setBackgroundColor(Color.parseColor("#3498db"));
                    AddProductPrice.setTextColor(Color.parseColor("#FFFFFF"));
                    EditText AddProductListPrice = new EditText(MainScreen.this);
                    AddProductListPrice.setHintTextColor(Color.parseColor("#FFFFFF"));
                    AddProductListPrice.setBackgroundColor(Color.parseColor("#3498db"));
                    AddProductListPrice.setTextColor(Color.parseColor("#FFFFFF"));
                    EditText AddProductRetailPrice = new EditText(MainScreen.this);
                    AddProductRetailPrice.setHintTextColor(Color.parseColor("#FFFFFF"));
                    AddProductRetailPrice.setBackgroundColor(Color.parseColor("#3498db"));
                    AddProductRetailPrice.setTextColor(Color.parseColor("#FFFFFF"));
                    Spinner AddCategoryID = new Spinner(MainScreen.this);
                    AddCategoryID.setBackgroundResource(R.drawable.dropdown);
                    Spinner ProductsID = new Spinner(MainScreen.this);
                    ProductsID.setBackgroundResource(R.drawable.dropdown);
                    productNames = new ArrayList<>();
                    productNames = productDB.GetProductNames();
                    categoryDropDownItems = db.getCategoryNames();
                    categoryDropDownItems.remove("Basket");
                    categoryDropDownItems.remove("Profile");
                    categoryDropDownItems.remove("Order");
                    ArrayAdapter<String> productnameadapter = new ArrayAdapter<>(MainScreen.this, android.R.layout.simple_spinner_dropdown_item, productNames);
                    ArrayAdapter<String> newadapter = new ArrayAdapter<>(MainScreen.this, android.R.layout.simple_spinner_dropdown_item, categoryDropDownItems);
                    AddCategoryID.setAdapter(newadapter);
                    ProductsID.setAdapter(productnameadapter);
                    //AddCategoryID.setOnItemSelectedListener(MainScreen.this);
                    AddCategoryID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                @Override
                                                                public void onItemSelected(AdapterView<?> parent, View view,
                                                                                           int position, long id) {
                                                                    //Log.v("item", (String) parent.getItemAtPosition(position));
                                                                    strCategoryName = (String) parent.getItemAtPosition(position);
                                                                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                                                                }

                                                                @Override
                                                                public void onNothingSelected(AdapterView<?> adapterView) {

                                                                }
                                                            });
                    ProductsID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            //Log.v("item", (String) parent.getItemAtPosition(position));
                            SelectedProductName = (String) parent.getItemAtPosition(position);
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    Button deleteProduct = new Button(MainScreen.this);
                    deleteProduct.setLayoutParams(params);
                    deleteProduct.setBackgroundColor(getResources().getColor(R.color.black));
                    deleteProduct.setTextColor(Color.parseColor("#FFFFFF"));
                    deleteProduct.setText("Delete Product");
                    deleteProduct.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            productDB.deleteEvents();
                            productNames = productDB.GetProductNames();
                            ArrayAdapter<String> productnameadapter = new ArrayAdapter<>(MainScreen.this, android.R.layout.simple_spinner_dropdown_item, productNames);
                            ProductsID.setAdapter(productnameadapter);
                        }});
                    AddProductName.setHint("Add Product Name");
                    AddProductDesc.setHint("Add Product Description");
                    AddProductPrice.setHint("Add Product Price");
                    AddProductListPrice.setHint("Add Product List Price");
                    AddProductRetailPrice.setHint("Add Product Retail Price");
                    Button SubmitProductDetails = new Button(MainScreen.this);
                    SubmitProductDetails.setLayoutParams(params);
                    SubmitProductDetails.setBackgroundColor(getResources().getColor(R.color.black));
                    SubmitProductDetails.setTextColor(Color.parseColor("#FFFFFF"));
                    SubmitProductDetails.setText("Submit New Product");
                    layout.addView(AddProductName);layout.addView(AddProductDesc);
                    layout.addView(AddProductPrice);layout.addView(AddProductListPrice);
                    layout.addView(AddProductRetailPrice);layout.addView(AddCategoryID);
                    layout.addView(SubmitProductDetails);
                    layout.addView(ProductsID);layout.addView(deleteProduct);
                    layout.addView(ReturnBtn);
                    SubmitProductDetails.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String strPName = AddProductName.getText().toString();
                            String strPDesc = AddProductDesc.getText().toString();
                            Integer strPPrice = 0;
                            Integer strPListPrice=0;
                            Integer strPRetailPrice=0;
                            try
                            {
                                strPPrice = Integer.parseInt(AddProductPrice.getText().toString());
                                strPListPrice = Integer.parseInt(AddProductListPrice.getText().toString());
                                strPRetailPrice = Integer.parseInt(AddProductRetailPrice.getText().toString());
                            }
                            catch (NumberFormatException e)
                            {
                                // handle the exception
                                strPPrice=0;
                                strPListPrice = 0;
                                strPRetailPrice = 0;
                            }
                            //strCategoryName = AddCategoryID.getText().toString();
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
                            Date currentTime = Calendar.getInstance().getTime();
                            String date = dateFormat.format(calendar.getTime());
                            String Datetime = date + " " + currentTime;
                            if(!strPName.isEmpty() && !strPDesc.isEmpty() && !strPPrice.toString().isEmpty() && !strPListPrice.toString().isEmpty() && !strPRetailPrice.toString().isEmpty() && !strCategoryName.isEmpty() && !productDB.productExists(strPName)){
                                System.out.println("ADD DATA");
                                Events e1 = new Events(strPName, strPDesc, strPPrice, strPListPrice, strPRetailPrice, date, Datetime, strCategoryName);
                                productDB.addEvents(e1);
                            }
                        }
                    });
                }
            });
            UserBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layout.removeAllViews();
                    EditText editEmail = new EditText(MainScreen.this);
                    editEmail.setHintTextColor(Color.parseColor("#FFFFFF"));
                    editEmail.setBackgroundColor(Color.parseColor("#3498db"));
                    editEmail.setTextColor(Color.parseColor("#FFFFFF"));
                    EditText editPassword = new EditText(MainScreen.this);
                    editPassword.setHintTextColor(Color.parseColor("#FFFFFF"));
                    editPassword.setBackgroundColor(Color.parseColor("#3498db"));
                    editPassword.setTextColor(Color.parseColor("#FFFFFF"));
                    EditText editHobbies = new EditText(MainScreen.this);
                    editHobbies.setHintTextColor(Color.parseColor("#FFFFFF"));
                    editHobbies.setBackgroundColor(Color.parseColor("#3498db"));
                    editHobbies.setTextColor(Color.parseColor("#FFFFFF"));
                    EditText editPostcode = new EditText(MainScreen.this);
                    editPostcode.setHintTextColor(Color.parseColor("#FFFFFF"));
                    editPostcode.setBackgroundColor(Color.parseColor("#3498db"));
                    editPostcode.setTextColor(Color.parseColor("#FFFFFF"));
                    Button submitUpdate = new Button(MainScreen.this);
                    submitUpdate.setLayoutParams(params);
                    submitUpdate.setBackgroundColor(getResources().getColor(R.color.black));
                    submitUpdate.setTextColor(Color.parseColor("#FFFFFF"));
                    editEmail.setHint("Edit Email");
                    editPassword.setHint("Edit Password");
                    editHobbies.setHint("Edit Hobbies");
                    editPostcode.setHint("Edit Postcode");
                    submitUpdate.setText("Submit");
                    layout.addView(UserCheck);layout.addView(editEmail);
                    layout.addView(editPassword); layout.addView(editHobbies);
                    layout.addView(editPostcode);layout.addView(submitUpdate);
                    layout.addView(ReturnBtn);
                    //AddCategoryID.setOnItemSelectedListener(MainScreen.this);
                    submitUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String strEmail = editEmail.getText().toString();
                            String strPassword = editPassword.getText().toString();
                            String strHobbies = editHobbies.getText().toString();
                            String strPostcode = editPostcode.getText().toString();
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
                            Date currentTime = Calendar.getInstance().getTime();
                            String date = dateFormat.format(calendar.getTime());
                            String Datetime = date + " " + currentTime;
                            if(!strEmail.isEmpty() && !strPassword.isEmpty() && !strHobbies.isEmpty() && !strPostcode.isEmpty() && !dbcl.CheckIfEmailExists(strEmail)) {
                                Events e1 = new Events(strEmail, strPassword, strHobbies, strPostcode);
                                dbcl.updateEvents(e1);
                                UserNameText = strEmail;
                                dbcl.updateTimeUpdated(Datetime);
                                UserNames = dbcl.getUserNames();
                                ArrayAdapter<String> userAd = new ArrayAdapter<>(MainScreen.this, android.R.layout.simple_spinner_dropdown_item, UserNames);
                                UserCheck.setAdapter(userAd);
                            }
                        }
                    });
                }
            });
            delUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layout.removeAllViews();
                    layout.addView(UserCheck);
                    Button deleteUser = new Button(MainScreen.this);
                    deleteUser.setLayoutParams(params);
                    deleteUser.setBackgroundColor(getResources().getColor(R.color.black));
                    deleteUser.setTextColor(Color.parseColor("#FFFFFF"));
                    deleteUser.setText("Delete User");
                    layout.addView(deleteUser);layout.addView(ReturnBtn);
                    deleteUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {
                            dbcl.deleteEvents();
                            basketDB.deleteEvents();
                            OrderDBClass orderDB = new OrderDBClass(MainScreen.this);
                            orderDB.deleteEvents();
                            UserNames = dbcl.getUserNames();
                            ArrayAdapter<String> userAd = new ArrayAdapter<>(MainScreen.this, android.R.layout.simple_spinner_dropdown_item, UserNames);
                            UserCheck.setAdapter(userAd);
                        }
                    });
                }
            });
            OrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layout.removeAllViews();
                    ViewAllOrders.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            layout.removeAllViews();
                            OrderDBClass orderDB = new OrderDBClass(MainScreen.this);
                            TextView txt = new TextView(MainScreen.this);
                            txt.setBackgroundColor(Color.parseColor("#3498db"));
                            txt.setTextColor(Color.parseColor("#FFFFFF"));
                            ArrayList<String> AllOrders = orderDB.getAllOrders();
                            String listString = "";

                            for (String s : AllOrders) {
                                listString += s + "\n";
                            }
                            txt.setText(listString);
                            layout.addView(txt);
                            layout.addView(ReturnBtn);
                        }
                    });
                    CreateFileForOrders.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            OrderDBClass orderDB = new OrderDBClass(MainScreen.this);
                            ArrayList<String> AllOrders = orderDB.getAllOrders();
                            String listString = "";

                            for (String s : AllOrders) {
                                listString += s + "\n";
                            }
                            String filename = "OrderFile";
                            File file = new File(getFilesDir().getAbsolutePath(), filename); //Create a new file called file1
                            try{ //Try to write into the file
                                FileOutputStream outputStream;
                                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                                outputStream.write(listString.getBytes()); //write string1 data into file1 and close it.
                                outputStream.close();
                                //list.add(filename);
                                System.out.println("Done" + file.getAbsolutePath());
                            }
                            catch(Exception e){//If can't write to file catch exception
                                e.printStackTrace();
                            }
                        }
                    });
                    layout.addView(ViewAllOrders);layout.addView(CreateFileForOrders);
                    layout.addView(ReturnBtn);
                }
            });

            ReturnBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layout.removeAllViews();
                    layout.addView(EditCategories);layout.addView(EditProducts);
                    layout.addView(UserBtn);layout.addView(delUser);layout.addView(UpdateProducts);
                    layout.addView(OrderBtn);
                    layout.addView(LogOutBtn);
                }
            });
            UserCheck.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    //Log.v("item", (String) parent.getItemAtPosition(position));
                    strUserName = (String) parent.getItemAtPosition(position);
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        else{
            //layout.addView(DisplayProducts);
            dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    //Log.v("item", (String) parent.getItemAtPosition(position));
                    SelectedCategoryItem = (String) parent.getItemAtPosition(position);
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                    ArrayList<String> getAllProductsFromChosenCategory = productDB.GetProducts();
                    layout.removeAllViews();
                    if(SelectedCategoryItem.equals("Basket")){
                        TextView txt = new TextView(MainScreen.this);
                        txt.setBackgroundColor(Color.parseColor("#3498db"));
                        txt.setTextColor(Color.parseColor("#FFFFFF"));
                        txt.setTextSize(25);
                        String found = printBasketContents();
                        txt.setText(found);
                        Spinner delBasketItemDropDownMenu = new Spinner(MainScreen.this);
                        delBasketItemDropDownMenu.setBackgroundResource(R.drawable.dropdown);
                        String productNames = "";
                        BasketString = basketDB.getProductNamesInBasket(productNames);
                        List<String> items =  Arrays.asList(BasketString.split("-"));
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainScreen.this, android.R.layout.simple_spinner_dropdown_item, items);
                        delBasketItemDropDownMenu.setAdapter(adapter);
                        delBasketItemDropDownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                SelectedProductItem = (String) adapterView.getItemAtPosition(i);
                                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                                SelectedProductItem = SelectedProductItem.trim();
                                System.out.println(SelectedProductItem);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        Button delBasketItem = new Button(MainScreen.this);
                        delBasketItem.setLayoutParams(params);
                        delBasketItem.setBackgroundColor(getResources().getColor(R.color.black));
                        delBasketItem.setTextColor(Color.parseColor("#FFFFFF"));
                        delBasketItem.setText("Delete Drop Down Item From Basket");
                        Button ClearBasketBtn = new Button(MainScreen.this);
                        ClearBasketBtn.setLayoutParams(params);
                        ClearBasketBtn.setBackgroundColor(getResources().getColor(R.color.black));
                        ClearBasketBtn.setTextColor(Color.parseColor("#FFFFFF"));
                        ClearBasketBtn.setText("Clear Basket");
                        delBasketItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                               int productPrice = 0;
                               int basketPrice = 0;
                               int newBasketPrice = 0;
                               productPrice = productDB.GetProductsPrice();
                               basketPrice = basketDB.getCurrentBasketPrice();
                               newBasketPrice = basketPrice - productPrice;
                               String productlist = "";
                               BasketString = basketDB.getProductNamesInBasket(productlist);
                               String itemToDel = String.format("%s -", SelectedProductItem);
                               System.out.println("itemToDel " + itemToDel);
                               String productNames = BasketString.replaceFirst(itemToDel, "");
                               System.out.println("This " + productNames + " This " + newBasketPrice);
                               basketDB.UpdateBasketPrice(productNames, newBasketPrice);
                               String productNamess = "";
                               String allProducts = "";
                               allProducts = basketDB.getProductNamesInBasket(productNamess);
                               List<String> items =  Arrays.asList(allProducts.split("-"));
                               ArrayAdapter<String> adapter = new ArrayAdapter<>(MainScreen.this, android.R.layout.simple_spinner_dropdown_item, items);
                               delBasketItemDropDownMenu.setAdapter(adapter);
                               String found = printBasketContents();
                               txt.setText(found);
                               //basketDB.deleteEvents();
                            }
                        });
                        ClearBasketBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                basketDB.ClearRowUnderCurrentUser();
                                String productNamess = "";
                                String allProducts = "";
                                ProductsTotal = "";
                                BasketTotal = 0;
                                allProducts = basketDB.getProductNamesInBasket(productNamess);
                                List<String> items =  Arrays.asList(allProducts.split("-"));
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainScreen.this, android.R.layout.simple_spinner_dropdown_item, items);
                                delBasketItemDropDownMenu.setAdapter(adapter);
                                String found = printBasketContents();
                                txt.setText(found);
                            }
                        });
                        Button OrderBtn = new Button(MainScreen.this);
                        OrderBtn.setLayoutParams(params);
                        OrderBtn.setBackgroundColor(getResources().getColor(R.color.black));
                        OrderBtn.setTextColor(Color.parseColor("#FFFFFF"));
                        OrderBtn.setText("Order Contents in Basket");
                        String finalListString = found;
                        OrderBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(finalListString.length()>3){
                                    OrderDBClass OrderDB = new OrderDBClass(MainScreen.this);
                                    ArrayList<String> productsInBasket = basketDB.getCurrentProducts();
                                    String EmptyProduct = "";

                                    for (String getProduct : productsInBasket) {
                                        EmptyProduct += getProduct + " ";
                                    }
                                    int ValueInBasket = basketDB.getCurrentBasketPrice();
                                    Events e2 = new Events(UserNameText, EmptyProduct, ValueInBasket);
                                    OrderDB.addEvents(e2);
                                    BasketTotal = 0;
                                    ProductsTotal = "";
                                    basketDB.DeleteRowUnderCurrentUsername();
                                    txt.setText("");
                                    String productNamess = "";
                                    String allProducts = "";
                                    allProducts = basketDB.getProductNamesInBasket(productNamess);
                                    List<String> items =  Arrays.asList(allProducts.split("-"));
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainScreen.this, android.R.layout.simple_spinner_dropdown_item, items);
                                    delBasketItemDropDownMenu.setAdapter(adapter);
                                }
                            }
                        });
                        layout.addView(txt);
                        layout.addView(delBasketItemDropDownMenu);
                        layout.addView(ClearBasketBtn);
                        layout.addView(OrderBtn);
                        layout.addView(delBasketItem);


                    } else if (SelectedCategoryItem.equals("Order")) {
                        TextView txt = new TextView(MainScreen.this);
                        txt.setBackgroundColor(Color.parseColor("#3498db"));
                        txt.setTextColor(Color.parseColor("#FFFFFF"));
                        txt.setTextSize(25);
                        OrderDBClass orderDB = new OrderDBClass(MainScreen.this);
                        ArrayList<String> OrdersUnderCurrentUser = orderDB.getAllOrdersUnderCurrentUser();
                       /*for(int p = 0; p<ContentsInBasket.size(); p++){
                           DisplayBasketContents = ContentsInBasket.get(p);
                       }
                        txt.setText(DisplayBasketContents);*/
                        String listString = "";

                        for (String s : OrdersUnderCurrentUser) {
                            listString += s + "\n";
                        }
                        txt.setText(listString);
                        layout.addView(txt);
                    }
                    else if(SelectedCategoryItem.equals("Profile")){
                        layout.removeAllViews();
                        EditText Email = new EditText(MainScreen.this);
                        Email.setHintTextColor(Color.parseColor("#FFFFFF"));
                        Email.setBackgroundColor(Color.parseColor("#3498db"));
                        Email.setTextColor(Color.parseColor("#FFFFFF"));
                        Button UpdateEmail = new Button(MainScreen.this);
                        UpdateEmail.setLayoutParams(params);
                        UpdateEmail.setBackgroundColor(getResources().getColor(R.color.black));
                        UpdateEmail.setTextColor(Color.parseColor("#FFFFFF"));
                        EditText Password = new EditText(MainScreen.this);
                        Password.setHintTextColor(Color.parseColor("#FFFFFF"));
                        Password.setBackgroundColor(Color.parseColor("#3498db"));
                        Password.setTextColor(Color.parseColor("#FFFFFF"));
                        Button UpdatePassword = new Button(MainScreen.this);
                        UpdatePassword.setLayoutParams(params);
                        UpdatePassword.setBackgroundColor(getResources().getColor(R.color.black));
                        UpdatePassword.setTextColor(Color.parseColor("#FFFFFF"));
                        EditText Hobbies = new EditText(MainScreen.this);
                        Hobbies.setHintTextColor(Color.parseColor("#FFFFFF"));
                        Hobbies.setBackgroundColor(Color.parseColor("#3498db"));
                        Hobbies.setTextColor(Color.parseColor("#FFFFFF"));
                        Button UpdateHobbies = new Button(MainScreen.this);
                        UpdateHobbies.setLayoutParams(params);
                        UpdateHobbies.setBackgroundColor(getResources().getColor(R.color.black));
                        UpdateHobbies.setTextColor(Color.parseColor("#FFFFFF"));
                        EditText Postcode = new EditText(MainScreen.this);
                        Postcode.setHintTextColor(Color.parseColor("#FFFFFF"));
                        Postcode.setBackgroundColor(Color.parseColor("#3498db"));
                        Postcode.setTextColor(Color.parseColor("#FFFFFF"));
                        Button UpdatePostcode = new Button(MainScreen.this);
                        UpdatePostcode.setLayoutParams(params);
                        UpdatePostcode.setBackgroundColor(getResources().getColor(R.color.black));
                        UpdatePostcode.setTextColor(Color.parseColor("#FFFFFF"));
                        ImageView profilePicture = new ImageView(MainScreen.this);
                        Spinner profilePictureDropDownMenu = new Spinner(MainScreen.this);
                        profilePictureDropDownMenu.setBackgroundResource(R.drawable.dropdown);
                        profilePics = new ArrayList<String>();
                        profilePics.add(Picture1);profilePics.add(Picture2);profilePics.add(Picture3);
                        String Image1 = "R.drawable.ic_launcher_foreground";
                        String Image2 = "R.drawable.ic_launcher_background";
                        String Image3 = "R.mipmap.ic_launcher_round";
                        dbClass dbcl = new dbClass(MainScreen.this);
                        String getProfilePic = dbcl.getProfilePic();

                        if(getProfilePic.equals(Picture1)){

                                profilePicture.setImageResource(R.drawable.ic_launcher_background);

                        }else if(getProfilePic.equals(Picture2)){

                                profilePicture.setImageResource(R.drawable.ic_launcher_foreground);

                        }else if(getProfilePic.equals(Picture3)){

                                profilePicture.setImageResource(R.mipmap.ic_launcher_round);

                        }
                               // profilePicture.setImageResource(Integer.parseInt(Image1));
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainScreen.this, android.R.layout.simple_spinner_dropdown_item, profilePics);
                        profilePictureDropDownMenu.setAdapter(adapter);
                        profilePictureDropDownMenu.setSelection(0,false);
                        profilePictureDropDownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                profilePictureString = (String) adapterView.getItemAtPosition(i);
                                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                                String getProfilePic = dbcl.getProfilePic();
                                if(profilePictureString.equals(Picture1)){
                                    getProfilePic = dbcl.getProfilePic();
                                    System.out.println("1 "+getProfilePic);
                                    dbcl.updateProfilePic(getProfilePic, Picture1);
                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
                                    Date currentTime = Calendar.getInstance().getTime();
                                    String date = dateFormat.format(calendar.getTime());
                                    String Datetime = date + " " + currentTime;
                                    dbcl.updateTimeUpdated(Datetime);
                                }else if(profilePictureString.equals(Picture2)){
                                    getProfilePic = dbcl.getProfilePic();
                                    System.out.println("2 "+getProfilePic);
                                    dbcl.updateProfilePic(getProfilePic, Picture2);
                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
                                    Date currentTime = Calendar.getInstance().getTime();
                                    String date = dateFormat.format(calendar.getTime());
                                    String Datetime = date + " " + currentTime;
                                    dbcl.updateTimeUpdated(Datetime);
                                } else if(profilePictureString.equals(Picture3)){
                                    getProfilePic = dbcl.getProfilePic();
                                    System.out.println("3 "+getProfilePic);
                                    dbcl.updateProfilePic(getProfilePic, Picture3);
                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
                                    Date currentTime = Calendar.getInstance().getTime();
                                    String date = dateFormat.format(calendar.getTime());
                                    String Datetime = date + " " + currentTime;
                                    dbcl.updateTimeUpdated(Datetime);
                                }
                                if(getProfilePic.equals(Picture1)){

                                    profilePicture.setImageResource(R.drawable.ic_launcher_background);

                                }else if(getProfilePic.equals(Picture2)){

                                    profilePicture.setImageResource(R.drawable.ic_launcher_foreground);

                                }else if(getProfilePic.equals(Picture3)){

                                    profilePicture.setImageResource(R.mipmap.ic_launcher_round);

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        Email.setHint(UserNameText);
                        String passWord = dbcl.getPassword();
                        String hobbies = dbcl.getHobbies();
                        String postcode = dbcl.getPostcode();
                        Password.setHint(passWord);
                        Hobbies.setHint(hobbies);
                        Postcode.setHint(postcode);
                        UpdateEmail.setText("Update Email");UpdatePassword.setText("Update Password");
                        UpdateHobbies.setText("Update Hobbies");UpdatePostcode.setText("Update Postcode");
                        layout.addView(Email);layout.addView(UpdateEmail);
                        layout.addView(Password);layout.addView(UpdatePassword);
                        layout.addView(Hobbies);layout.addView(UpdateHobbies);
                        layout.addView(Postcode);layout.addView(UpdatePostcode);
                        layout.addView(profilePicture);
                        layout.addView(profilePictureDropDownMenu);
                        UpdateEmail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String strEmail = Email.getText().toString();

                                if(!dbcl.CheckIfEmailExists(strEmail)) {
                                    System.out.println(strEmail);
                                    OrderDBClass orderDB = new OrderDBClass(MainScreen.this);
                                    orderDB.updateEmail(strEmail);
                                    basketDB.updateEmail(strEmail);
                                    dbcl.updateEmail(UserNameText, strEmail);
                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
                                    Date currentTime = Calendar.getInstance().getTime();
                                    String date = dateFormat.format(calendar.getTime());
                                    String Datetime = date + " " + currentTime;
                                    dbcl.updateTimeUpdated(Datetime);
                                    UserNameText = strEmail;
                                }
                            }
                        });
                        UpdatePassword.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String strPassword = Password.getText().toString();
                                String passWord = dbcl.getPassword();
                                dbcl.updatePassword(passWord, strPassword);
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
                                Date currentTime = Calendar.getInstance().getTime();
                                String date = dateFormat.format(calendar.getTime());
                                String Datetime = date + " " + currentTime;
                                dbcl.updateTimeUpdated(Datetime);
                            }
                        });
                        UpdateHobbies.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String strHobbies = Hobbies.getText().toString();
                                String hobbies = dbcl.getHobbies();
                                dbcl.updateHobbies(hobbies, strHobbies);
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
                                Date currentTime = Calendar.getInstance().getTime();
                                String date = dateFormat.format(calendar.getTime());
                                String Datetime = date + " " + currentTime;
                                dbcl.updateTimeUpdated(Datetime);
                            }
                        });
                        UpdatePostcode.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String strPostcode = Postcode.getText().toString();
                                String postcode = dbcl.getPostcode();
                                dbcl.updatePostcode(postcode, strPostcode);
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
                                Date currentTime = Calendar.getInstance().getTime();
                                String date = dateFormat.format(calendar.getTime());
                                String Datetime = date + " " + currentTime;
                                dbcl.updateTimeUpdated(Datetime);
                            }
                        });
                    }
                    for(int i = 0; i<getAllProductsFromChosenCategory.size(); i++){
                        //DisplayProductTotal = getAllProductsFromChosenCategory.get(i);
                        //System.out.println(DisplayProductTotal);
                        if ((i % 3) == 0){
                            productName = getAllProductsFromChosenCategory.get(i);
                            productDesc = getAllProductsFromChosenCategory.get(i+1);
                            productPrice = getAllProductsFromChosenCategory.get(i+2);
                            layout.addView(createNewTextView(productName + " - " + productDesc + " - " + productPrice));
                        }
                    }
                    layout.addView(LogOutBtn);
                    //DisplayProducts.setText(DisplayProductTotal);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
        LogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(LogOutIntent);
                Login.isAdmin = false;
            }
        });

    }

    private TextView createNewTextView(String text) {
        final TextView newTextView = new TextView(this);
        newTextView.setTextColor(Color.parseColor("#FFFFFF"));
        newTextView.setBackgroundColor(Color.parseColor("#3498db"));
        newTextView.setTextSize(20);
        newTextView.setText(text);
        newTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickcount < 2){
                    clickcount ++;
                }
                String getText = newTextView.getText().toString();
                String[] split = getText.split("-");
                String ProductName = split[0] + "-";
                String ProductPrice = split[2];
                if(ProductsTotal == null){
                    ProductsTotal = "";
                }
                BasketTotal += Integer.parseInt(ProductPrice.replaceAll("[\\D]",""));
                //UserNameText = UserNameText.replaceAll("[\\D]", "");
                ProductsTotal += ProductName;
                System.out.println("username " + UserNameText + " productname " + ProductsTotal + " baskettotal " + BasketTotal);
               // if(clickcount == 1)
               // {
                   currentProducts = basketDB.getCurrentProducts();
                   int cBasketPrice = basketDB.getCurrentBasketPrice();
                   String cProducts = null;
                    /*String listString = "";

                    for (String s : currentProducts) {
                        listString += s + " ";
                    }*/
                    boolean doesUserExistInBasketTable = basketDB.CheckForUserExist();
                    if(!doesUserExistInBasketTable){
                        e1 = new Events(UserNameText, ProductsTotal, BasketTotal);
                        System.out.println("Create Table");
                        basketDB.addEvents(e1);
                    }
                   for(int j = 0; j<currentProducts.size(); j++){
                       cProducts = currentProducts.get(j);
                       String currentAndNewProducts = cProducts + ProductName;
                       System.out.println("ProductsTotal " + cProducts);
                       int currentAndNewBasketPrice = cBasketPrice + Integer.parseInt(ProductPrice.replaceAll("[\\D]",""));;
                       e1 = new Events(UserNameText, currentAndNewProducts, currentAndNewBasketPrice);
                       if(doesUserExistInBasketTable){
                           System.out.println("Update");
                           basketDB.updateEvents(e1);
                       }
                       else {
                           System.out.println("Create");
                           basketDB.addEvents(e1);
                       }
                   }
                //}
                /*else {
                    Events e1 = new Events(UserNameText, ProductsTotal, BasketTotal);
                    basketDB.addEvents(e1);
                }*/
            }
        });
        return newTextView;
    }

    private String printBasketContents(){
        String listString = "";
        ArrayList<String> ContentsInBasket = basketDB.getBasket();

        for (String s : ContentsInBasket) {
            listString += s + " ";
        }
        return listString;
    }

    @Override
    protected void onResume() { //called when activity goes into foreground
        super.onResume();
        //define layout
        LinearLayout ThisLayout = findViewById(R.id.MainScreenLayout);
        ThisLayout.setBackgroundColor(getResources().getColor(R.color.teal_200));
    }
    /*@Override
    public String toString() {

        return CategoriesDBClass.CategoryName;
    }*/
}