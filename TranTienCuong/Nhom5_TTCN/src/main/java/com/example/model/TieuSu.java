package com.example.model;

import java.util.Date;

public class TieuSu {
	private int ID;
	private int idNhanKhau;
	private Date tuNgay;
	private Date denNgay;
	private String ngheNghiep;
	private String noiLamViec;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getIdNhanKhau() {
		return idNhanKhau;
	}
	public void setIdNhanKhau(int idNhanKhau) {
		this.idNhanKhau = idNhanKhau;
	}
	public Date getTuNgay() {
		return tuNgay;
	}
	public void setTuNgay(Date tuNgay) {
		this.tuNgay = tuNgay;
	}
	public Date getDenNgay() {
		return denNgay;
	}
	public void setDenNgay(Date denNgay) {
		this.denNgay = denNgay;
	}
	public String getNgheNghiep() {
		return ngheNghiep;
	}
	public void setNgheNghiep(String ngheNghiep) {
		this.ngheNghiep = ngheNghiep;
	}
	public String getNoiLamViec() {
		return noiLamViec;
	}
	public void setNoiLamViec(String noiLamViec) {
		this.noiLamViec = noiLamViec;
	}

}
