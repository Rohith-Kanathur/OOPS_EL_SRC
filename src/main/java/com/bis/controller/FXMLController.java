package com.bis.controller;

import com.bis.db.DbConnection;
import com.cis.exception.InvalidPasswordException;
import com.cis.exception.InvalidUserIDException;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FXMLController implements Initializable {
    @FXML
    private TextField useridtf;
    @FXML
    private Label mssg;
    @FXML
    private Button loginb;
    @FXML
    private PasswordField passwordtf;
    @FXML
    private RadioButton userrb;
    @FXML
    private ToggleGroup UserOrAdmin;
    @FXML
    private RadioButton adminrb;
    @FXML
    private void Login(ActionEvent event) throws SQLException, IOException {
        Connection con = DbConnection.Connection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        if (userrb.isSelected()) {
            ps1 = con.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps1.setString(1, useridtf.getText());
            
            ps2 = con.prepareStatement("SELECT * FROM users WHERE id = ? and password = ?");
            ps2.setString(1, useridtf.getText());
            ps2.setString(2, passwordtf.getText());
            
            rs1 = ps1.executeQuery();
            rs2 = ps2.executeQuery();
            
            try {
            	if (!rs1.next())
            		throw new InvalidUserIDException();
            	
            	if (!rs2.next())
            		throw new InvalidPasswordException();
            	
            	Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/UserPage.fxml"));
                loader.load();
                Parent root = loader.getRoot();
                UserPageController upc = loader.getController();
                upc.GetUserID(useridtf.getText(), rs2.getString("name"));
                stage.setTitle("User Page");
                Image icon = new Image("/icons/UserPage.png");
                stage.getIcons().add(icon);
                stage.setMinHeight(710);
                stage.setMinWidth(1345);
                stage.setMaximized(true);
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/styles/UserPage.css");
                stage.setScene(scene);
                stage.show();
                mssg.setText("");
            }
            catch (InvalidUserIDException e) {
            	String mesg = e.toString();
            	mssg.setText(mesg);
            }
            catch (InvalidPasswordException e) {
            	String mesg = e.toString();
            	mssg.setText(mesg);
            }
            
            ps1.close();
            ps2.close();
            rs1.close();
            rs2.close();
        } else if (adminrb.isSelected()) {
        	ps1 = con.prepareStatement("SELECT * FROM admins WHERE id = ?");
            ps1.setString(1, useridtf.getText());
            
            ps2 = con.prepareStatement("SELECT * FROM admins WHERE id = ? and password = ?");
            ps2.setString(1, useridtf.getText());
            ps2.setString(2, passwordtf.getText());
            
            rs1 = ps1.executeQuery();
            rs2 = ps2.executeQuery();
            
            try {
            	if (!rs1.next())
            		throw new InvalidUserIDException();
            	
            	if (!rs2.next())
            		throw new InvalidPasswordException();
            	
            	Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/AdminPage.fxml"));
                loader.load();
                Parent root = loader.getRoot();
                AdminPageController apc = loader.getController();
                apc.GetAdminID( useridtf.getText());
                stage.setTitle("Admin Page");
                Image icon = new Image("/icons/UserPage.png");
                stage.getIcons().add(icon);
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/styles/AdminPage.css");
                stage.setScene(scene);
                stage.show();
                mssg.setText("");
            }
            catch (InvalidUserIDException e) {
            	String mesg = e.toString();
            	mssg.setText(mesg);
            }
            catch (InvalidPasswordException e) {
            	String mesg = e.toString();
            	mssg.setText(mesg);
            }
            
            ps1.close();
            ps2.close();
            rs1.close();
            rs2.close();
        }
        con.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
