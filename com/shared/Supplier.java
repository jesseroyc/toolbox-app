package com.shared;

import java.io.Serializable;
import java.net.InetAddress;

public class Supplier extends Model implements Serializable {

	private String supplierName;
	private String supplierAddress;
	private String supplierContact;

	public Supplier (InetAddress ip, String[] params) {
		super(ip,params[0],params[1],Integer.parseInt(params[2]));
		this.supplierName = params[4];
		this.supplierAddress = params[5];
		this.supplierContact = params[6];
	}

	public String getSupplierName() {
		return this.supplierName;
	}
	public String getSupplierAddress() {
		return this.supplierAddress;
	}
	public String getSupplierContact() {
		return this.supplierContact;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}
	public void setSupplierContact(String supplierAddress) {
		this.supplierContact = supplierAddress;
	}

	@Override
	public String toString () {
		return  this.ip + ";" +
				this.type + ";" +
				this.op + ";" +
				this._id + ";" +
				this.supplierName + ";" +
				this.supplierAddress + ";" +
				this.supplierContact;
	}
}
