package com.shared;

import java.io.Serializable;
import java.net.InetAddress;

public class Item extends Model implements Serializable {

	private String itemName;
	private int itemQuantity;
	private double itemPrice;
	private int itemSupplierId;

	public Item (InetAddress ip, String[] params) {
		super(ip, params[0],params[1],Integer.parseInt(params[2]));
		try{
			this.itemName = params[3];
			this.itemQuantity = Integer.parseInt(params[4]);
			this.itemPrice = Double.parseDouble(params[5]);
			this.itemSupplierId = Integer.parseInt(params[6]);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	public String getItemName() {
		return itemName;
	}
	public int getItemQuantity() {
		return itemQuantity;
	}
	public double getItemPrice() {
		return itemPrice;
	}
	public int getItemSupplierId () {
		return itemSupplierId;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public void setItemSupplierId (int supplier_id) {
		this.itemSupplierId = supplier_id;
	}

	@Override
	public String toString () {
		return  this.ip + ";" +
				this.type + ";" +
				this.op + ";" +
				this._id + ";" +
				this.itemName + ";" +
				this.itemQuantity + ";" +
				this.itemPrice + ";" +
				this.itemSupplierId;
	}
}
