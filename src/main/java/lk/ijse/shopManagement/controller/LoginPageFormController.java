package lk.ijse.shopManagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.shopManagement.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginPageFormController implements Initializable {
    @FXML
    public JFXTextField txtUserName;
    @FXML
    public JFXPasswordField txtUserPassword;

    @FXML
    public ImageView icon1;
    @FXML
    public ImageView icon;
    @FXML
    public JFXTextField txtPasswordFeild;
    @FXML
    private AnchorPane root1;
    @FXML
    public JFXButton loginBtn;
    @FXML
    private AnchorPane root;
    private String password;
    @FXML
    public void loginBtnOnAction(ActionEvent actionEvent) throws IOException {
        String userName = txtUserName.getText();
        String userPassword = txtUserPassword.getText();
        String userPassword1 = txtPasswordFeild.getText();

        try {
            boolean isvalid = UserModel.check(userName,userPassword,userPassword1);
            if (isvalid){

                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardPageForm.fxml"));
                Scene scene= new Scene(anchorPane);
                Stage stage= (Stage) root.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Dashboard Page");
                stage.centerOnScreen();
            }else {
                new Alert(Alert.AlertType.ERROR,"Wrong User Name And Password").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"SQL ERROR").show();
        }
        txtUserName.setText("");
        txtUserPassword.setText("");

    }

    public void eyeIconOnMouseClicked(MouseEvent mouseEvent) {
        password= txtUserPassword.getText();
        txtPasswordFeild.setVisible(true);
        icon1.setVisible(true);
        txtPasswordFeild.setText(password);
        txtUserPassword.setVisible(false);
        icon.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtPasswordFeild.setVisible(false);
        icon1.setVisible(false);
        txtUserName.requestFocus();
    }

    public void closeEyeOnMouseClicked(MouseEvent mouseEvent) {
        password= txtPasswordFeild.getText();
        txtUserPassword.setText(password);
        txtUserPassword.setVisible(true);
        icon.setVisible(true);
        txtPasswordFeild.setVisible(false);
        icon1.setVisible(false);
    }

    public void txtUserNameOnAction(ActionEvent actionEvent) {
        txtUserPassword.requestFocus();
    }

    public void txtPasswordFeildOnAction(ActionEvent actionEvent) {
        loginBtn.fire();
    }

    public void txtUserPasswordOnAction(ActionEvent actionEvent) {
        loginBtn.fire();
    }
}
