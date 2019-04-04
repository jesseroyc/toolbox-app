package com.shared;

public class Item {
	
	private int itemId;
	private String itemName;
	private int itemQuantity;
	private double itemPrice;
	private int itemSupplierId;
	private boolean alreadyOrdered;
	private static final int ORDERQUANTITY = 40;
	private static final int MINIMUMUMBER = 20;

	public Item () {
		this.itemId = -1;
		this.itemName = "";
		this.itemQuantity = -1;
		this.itemPrice = -1;
		this.itemSupplierId = -1;
	}

	public Item (int id, String name, int quantity, double price, int supplier_id) {
		this.itemId = id;
		this.itemName = name;
		this.itemQuantity = quantity;
		this.itemPrice = price;
		this.itemSupplierId = supplier_id;

		setAlreadyOrdered(false);
	}
	
	public boolean decreaseItemQuantity () {
		if (itemQuantity > 0) {
			itemQuantity--;
		    return true;	
		}
		else
			return false;
			
	}
	public OrderLine placeOrder (){
		OrderLine ol;
		if (getItemQuantity() < MINIMUMUMBER && alreadyOrdered == false){
			ol = new OrderLine (this, ORDERQUANTITY);
			alreadyOrdered = true;
			return ol;
		}
	    return null;
	}

	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public void setItemSupplierId (int supplier_id) {
		this.itemSupplierId = supplier_id;
	}
	public int getItemSupplierId () {
		return itemSupplierId;
	}
	
	public String toString () {
		return "Item ID: " + itemId + ", Item Name: " + itemName + ", Item Quantity: " +
	           itemQuantity + ", Supplier ID: " + itemSupplierId + "\n";
	}

	public boolean isAlreadyOrdered() {
		return alreadyOrdered;
	}

	public void setAlreadyOrdered(boolean alreadyOrdered) {
		this.alreadyOrdered = alreadyOrdered;
	}

}
