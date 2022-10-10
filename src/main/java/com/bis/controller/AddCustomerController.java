package com.bis.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.bis.db.DbConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class AddCustomerController implements Initializable {
    @FXML
    private TextField cusid;
    @FXML
    private TextField cusname;
    @FXML
    private TextArea cusaddress;
    @FXML
    private TextField cusphone;
    @FXML
    private Button add;
    @FXML
    private Label addcusconfirm;
    @FXML
    private TextField cusemail;
    @FXML
    private TextField balance;
    @FXML
    private Button browse;
    private File file;
    @FXML
    private ImageView userimage;
    @FXML
    private TextField cuspass;
    private FileInputStream fis;
    int cf;
    @FXML
    public void ChooseFile(ActionEvent event) {
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            userimage.setImage(image);
            cf = 1;
        } else {
            cf = 0;
        }
    }
    @FXML
    public void AddCustomer(ActionEvent event) throws SQLException, FileNotFoundException {
    	String passwordRegex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
    	
    	try {
            if (cusid.getText().isEmpty() || cuspass.getText().isEmpty() || cusname.getText().isEmpty() || cusaddress.getText().isEmpty() || cusemail.getText().isEmpty() || cusphone.getText().isEmpty() || balance.getText().isEmpty()) {
                addcusconfirm.setText("Please Fill Up Everything");
            } else if (!Pattern.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", cusemail.getText())) {
                 addcusconfirm.setText("Email ID Invalid.");     
            } 
            else if (!Pattern.matches(passwordRegex, cuspass.getText())) {
            	 addcusconfirm.setText("Password Invalid.");
            }
            else if(!Pattern.matches("[0-9]{10}", cusphone.getText())) {
            	addcusconfirm.setText("Phone Number Invalid.");
            }
            else if (Integer.parseInt(balance.getText()) < 500) {
                addcusconfirm.setText("Minimum Balance Requirement Is 500.");
            } else {
                if (cf != 1) {
                    addcusconfirm.setText("Please Upload An Image To Add New Customer");
                } else if (cf == 1) {
                    try {
                        Connection con = DbConnection.Connection();
                        PreparedStatement ps = con.prepareStatement("INSERT INTO users VALUES (?,?,?,?,?,?,?,?)");
                        ps.setInt(1, Integer.parseInt(cusid.getText()));
                        ps.setString(2, cuspass.getText());
                        ps.setString(3, cusname.getText());
                        ps.setString(4, cusaddress.getText());
                        ps.setString(5, cusemail.getText());
                        ps.setString(6, cusphone.getText());
                        ps.setString(7, balance.getText());
                        fis = new FileInputStream(file);
                        ps.setBinaryStream(8, (InputStream) fis, (int) file.length());
                        int i = ps.executeUpdate();
                        if (i > 0) {
                            cusid.setText("");
                            cuspass.setText("");
                            cusname.setText("");
                            cusaddress.setText("");
                            cusemail.setText("");
                            cusphone.setText("");
                            balance.setText("");
                            addcusconfirm.setText("New Customer Added Successfully");
                        } else {
                            addcusconfirm.setText("Failed To Add New Customer");
                        }
                        ps.close();
                        con.close();
                    } catch (FileNotFoundException | NumberFormatException | SQLException e) {
                        addcusconfirm.setText("Invalid Customer ID or ID Is Not Available");
                    }
                }
            }
        } catch (NumberFormatException e) {
            addcusconfirm.setText("Please Enter Everything Correctly");
        }
        cf = 0;
        Image image = new Image("/icons/usericon1.png");
        userimage.setImage(image);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
