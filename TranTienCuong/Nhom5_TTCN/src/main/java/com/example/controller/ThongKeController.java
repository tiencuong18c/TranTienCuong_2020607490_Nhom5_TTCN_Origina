package com.example.controller;

import com.example.bean.NhanKhauBean;


import com.example.controller.NhanKhauManageController.ThongTinNhanKhauController;
import com.example.model.NhanKhau;
import com.example.services.NhanKhauService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;


public class ThongKeController extends Controller implements Initializable {
    @FXML
    MenuButton menuGioiTinh;
    @FXML
    MenuItem gtToanBo;
    @FXML
    MenuItem gtNam;
    @FXML
    MenuItem gtNu;
    @FXML
    MenuButton menuTrangThai;
    @FXML
    MenuItem ttToanBo;
    @FXML
    MenuItem ttThuongTru;
    @FXML
    MenuItem ttTamTru;
    @FXML
    MenuItem ttTamVang;
    @FXML
    TextField minNam;
    @FXML
    TextField maxNam;
    @FXML
    TextField minDoTuoi;
    @FXML
    TextField maxDoTuoi;
    @FXML
    Button showButton;
    @FXML
    Button exportButton;
    @FXML
    TableView<NhanKhau> nhanKhauTable;
    @FXML
    TableColumn<NhanKhau, Integer> idColumn;
    @FXML
    TableColumn<NhanKhau, String> hoTenColumn;
    @FXML
    TableColumn<NhanKhau, Date> ngaySinhColumn;
    @FXML
    TableColumn<NhanKhau, String> gioiTinhColumn;
    @FXML
    TableColumn<NhanKhau, String> diaChiColumn;
    private ObservableList<NhanKhau> nhanKhauList;
    private String typeOfGender = "Toan bo";
    private String typeOfStatus = "Toan bo";
    private NhanKhauService conn = new NhanKhauService();

    public ThongKeController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        nhanKhauList = FXCollections.observableArrayList(

        );
        idColumn.setCellValueFactory(new PropertyValueFactory<NhanKhau, Integer>("ID"));
        hoTenColumn.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("hoTen"));
        ngaySinhColumn.setCellValueFactory(new PropertyValueFactory<NhanKhau, Date>("namSinh"));
        gioiTinhColumn.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("gioiTinh"));
        diaChiColumn.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("diaChiHienNay"));
        nhanKhauTable.setItems(nhanKhauList);
        showInfor();

        nhanKhauTable.setOnMousePressed(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                // TODO Auto-generated method stub
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/example/nhankhaumanage/thong-tin-nhan-khau.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();

                    ThongTinNhanKhauController thongTinNhanKhau = loader.getController();
                    thongTinNhanKhau.showInforByID(nhanKhauTable.getSelectionModel().getSelectedItem().getID());

                    Stage modal_dialog = new Stage(StageStyle.DECORATED);
                    modal_dialog.initModality(Modality.WINDOW_MODAL);
                    modal_dialog.initOwner(stage);
                    modal_dialog.setTitle("Thông tin nhân khẩu");
                    Scene scene = new Scene(root);
                    modal_dialog.setScene(scene);
                    modal_dialog.show();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    public void showInfor() {
        ResultSet rs = conn.getNhanKhau();
        try {
            if (rs != null) {
                while (rs.next()) {
                    NhanKhau nhanKhau = new NhanKhau();
                    nhanKhau.setID(rs.getInt("ID"));
                    nhanKhau.setHoTen(rs.getString("hoTen"));
                    nhanKhau.setNamSinh(rs.getDate("namSinh"));
                    nhanKhau.setGioiTinh(rs.getString("gioiTinh"));
                    nhanKhau.setDiaChiHienNay(rs.getString("diaChiHienNay"));
                    nhanKhauList.add(nhanKhau);
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void chonGtToanBo() {
        menuGioiTinh.setText("Toàn bộ");
        typeOfGender = "Toan bo";
    }

    public void chonGtNam() {
        menuGioiTinh.setText("Nam");
        typeOfGender = "Nam";
    }

    public void chonGtNu() {
        menuGioiTinh.setText("Nữ");
        typeOfGender = "Nu";
    }

    public void chonTtToanBo() {
        menuTrangThai.setText("Toàn bộ");
        typeOfStatus = "Toan bo";
    }

    public void chonTtThuongTru() {
        menuTrangThai.setText("Thường trú");
        typeOfStatus = "Thuong tru";
    }

    public void chonTtTamTru() {
        menuTrangThai.setText("Tạm trú");
        typeOfStatus = "Tam tru";
    }

    public void chonTtTamVang() {
        menuTrangThai.setText("Tạm vắng");
        typeOfStatus = "Tam vang";
    }

    public void showData() {
        int tuTuoi = -1, denTuoi = 200, tuNam = 0, denNam = 2100;
        try {
            if (!this.minDoTuoi.getText().trim().isEmpty()) {
                tuTuoi = Integer.parseInt(this.minDoTuoi.getText().trim());
            }
            if (!this.maxDoTuoi.getText().trim().isEmpty()) {
                denTuoi = Integer.parseInt(this.maxDoTuoi.getText().trim());
            }
            if (!this.minNam.getText().trim().isEmpty()) {
                tuNam = Integer.parseInt(this.minNam.getText().trim());
            }
            if (!this.maxNam.getText().trim().isEmpty()) {
                denNam = Integer.parseInt(this.maxNam.getText().trim());
            }
        } catch (Exception e) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Warning");
            dialog.setContentText("Vui lòng nhập đúng kiểu dữ liệu!!");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.show();
        }
        List<NhanKhauBean> list = conn.statisticNhanKhau(tuTuoi, denTuoi, typeOfGender, typeOfStatus, tuNam, denNam);
        nhanKhauList = FXCollections.observableArrayList(

        );

        for (NhanKhauBean n : list) {
            nhanKhauList.add(n.getNhanKhau());
        }

        nhanKhauTable.setItems(nhanKhauList);
    }
    
    
    public void exportCSVFile() {

    	DateFormat dfm =new  SimpleDateFormat("yyyyMMddHHmmss");
    	try {
	        File file = new File("fileExport/" + dfm.format(new java.util.Date()) + ".csv");
	        FileWriter fw = new FileWriter(file, true);
	        BufferedWriter writer = new BufferedWriter( fw );
	        for (NhanKhau i : nhanKhauList) {
	        	String text = i.toString();
	            writer.write(text);
	            writer.newLine();
	        }
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Thông báo");
            dialog.setContentText("Xuất file thành công!!");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.show();
	        writer.close();
	   
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }	
	}
	
	
}
