package lk.ijse.shopManagement.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardPageFormController implements Initializable {

    @FXML
    private JFXButton employeeBtn;

    @FXML
    private AnchorPane root1;

    @FXML
    private AnchorPane root;

    public void signBtnOnAtion(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane= FXMLLoader.load (getClass().getResource("/view/loginPageForm.fxml"));
        Scene scene= new Scene(anchorPane);
        Stage stage= (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Page");
        stage.centerOnScreen();
    }

    public void customerBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane=FXMLLoader.load(getClass().getResource("/view/customerManageForm.fxml"));
        root1.getChildren().clear();
        root1.getChildren().add(anchorPane);
    }

    public void dashboardBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane=FXMLLoader.load(getClass().getResource("/view/dashboard_icon_view_form.fxml"));
        root1.getChildren().clear();
        root1.getChildren().add(anchorPane);
    }

    public void supplierBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/supplierManageForm.fxml"));
        root1.getChildren().clear();
        root1.getChildren().add(anchorPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadDashboard();
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void loadDashboard() throws IOException {
        AnchorPane anchorPane=FXMLLoader.load(getClass().getResource("/view/dashboard_icon_view_form.fxml"));
        root1.getChildren().clear();
        root1.getChildren().add(anchorPane);
    }

    public void employeeBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/employeeManageForm.fxml"));
        root1.getChildren().clear();
        root1.getChildren().add(anchorPane);
        employeeBtn.setStyle("-fx-background-color: #E11299");
    }
    public void itemBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/itemManageForm.fxml"));
        root1.getChildren().clear();
        root1.getChildren().add(anchorPane);
        employeeBtn.setStyle("");
    }

    public void orderBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/orderManageForm.fxml"));
        root1.getChildren().clear();
        root1.getChildren().add(anchorPane);
    }
}
