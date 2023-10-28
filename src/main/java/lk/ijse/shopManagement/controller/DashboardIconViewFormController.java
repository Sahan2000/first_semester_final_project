package lk.ijse.shopManagement.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.Duration;
import lk.ijse.shopManagement.model.CustomerModel;
import lk.ijse.shopManagement.model.ItemModel;
import lk.ijse.shopManagement.model.OrderModel;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class DashboardIconViewFormController implements Initializable {

    @FXML
    public Label lblTotalCustomerCount;
    @FXML
    public Label lblOrderCount;
    @FXML
    public Label lblTime;
    @FXML
    public Label lblItemCount;
    @FXML
    public DatePicker lblDate;
    @FXML
    public PieChart pieChart;
    @FXML
    private BarChart<String, Integer> barChart;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateCustomerTotalCount();
        generateOrderCount();
        generateTime();
        generateItemCount();
        generateDate();
        loadBarChart();
        loadPieChart();
    }

    private void loadBarChart() {
        Integer[] data = new Integer[0];
        try {
            data = OrderModel.getMonthlySalesValues();

            XYChart.Series<String, Integer> series = new XYChart.Series();
            series.setName("No. of Monthly Sales");
            series.getData().add(new XYChart.Data("JAN", data[0]));
            series.getData().add(new XYChart.Data("FEB", data[1]));
            series.getData().add(new XYChart.Data("MAR", data[2]));
            series.getData().add(new XYChart.Data("APR", data[3]));
            series.getData().add(new XYChart.Data("MAY", data[4]));
            series.getData().add(new XYChart.Data("JUN", data[5]));
            series.getData().add(new XYChart.Data("JUL", data[6]));
            series.getData().add(new XYChart.Data("AUG", data[7]));
            series.getData().add(new XYChart.Data("SEP", data[8]));
            series.getData().add(new XYChart.Data("OCT", data[9]));
            series.getData().add(new XYChart.Data("NOV", data[10]));
            series.getData().add(new XYChart.Data("DEC", data[11]));

            barChart.getData().addAll(series);
        } catch (SQLException e) {

        }
    }


    private void loadPieChart() {
        try {
            int homeValue = ItemModel.getHomeValue();
            int kitchenValue = ItemModel.getKitchenValue();
            int washingMachineValue = ItemModel.getWashingMachineValue();
            int refrigeratorVlaue = ItemModel.getRefrigeratorValue();
            int airConditionValue = ItemModel.getAirConditionValue();
            int televisionValue = ItemModel.getTelevisionValue();
        ObservableList<PieChart.Data> observableList = FXCollections.observableArrayList(
                    new PieChart.Data("Home Appliances",homeValue),
                    new PieChart.Data("Kitchen Appliances",kitchenValue),
                    new PieChart.Data("Washing Machine",washingMachineValue),
                    new PieChart.Data("Refrigerator",refrigeratorVlaue),
                    new PieChart.Data("Air Condition",airConditionValue),
                    new PieChart.Data("Television",televisionValue)
                );
        pieChart.getData().addAll(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void generateDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = String.valueOf(format.format(date));
        lblDate.setValue(LocalDate.parse(date1));
    }
    private void generateItemCount() {
        try {
            int itemCount = ItemModel.itemCount();
            if (itemCount<10){
                lblItemCount.setText("0"+itemCount);
            }
            lblItemCount.setText(String.valueOf(itemCount));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateTime() {

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e ->{
            LocalTime time = LocalTime.now();
            if (time.getMinute()< 10 && time.getSecond() < 10) {
                lblTime.setText(time.getHour() + ":" + "0" + time.getMinute() + ":" + "0" + time.getSecond());
            }else if (time.getMinute() < 10 && time.getSecond() > 10){
                lblTime.setText(time.getHour() + ":" + "0" + time.getMinute() + ":" + time.getSecond());
            }else {
                lblTime.setText(time.getHour() + ":" + time.getMinute() + ":" + time.getSecond());
            }
        }), new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void generateOrderCount() {
        try {
            int orderCount = OrderModel.orderCount();
            lblOrderCount.setText(String.valueOf(orderCount));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateCustomerTotalCount() {
        try {
            int customerCount = CustomerModel.totalCount();
            lblTotalCustomerCount.setText(String.valueOf(customerCount));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
