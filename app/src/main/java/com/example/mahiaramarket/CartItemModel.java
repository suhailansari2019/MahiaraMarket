package com.example.mahiaramarket;

import java.util.ArrayList;
import java.util.List;

public class CartItemModel {
    public static final int CART_ITEM = 0 ;
    public static  final  int TOTAL_AMOUNT = 1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    ///////// cart items//////////
    private String productID;
  private String  productImage;
  private String productTitle;
  private Long freeCoupens;
  private String productPrice;
  private String ProductCuttedPrice;
  private Long productQuantity;
  private Long productMaxQuantity;
    private Long stockQuantity;
  private Long offersApplied;
  private Long coupensApplied;
  private boolean inStock;
  private List<String> qtyIDs;
  private boolean qtyError;
  private String selectedCoupenId;
  private String discountedPrice;
  private boolean COD;

    public boolean isCOD() {
        return COD;
    }

    public void setCOD(boolean COD) {
        this.COD = COD;
    }

    public CartItemModel(boolean COD, int type, String productID, String productImage, String productTitle, Long freeCoupens, String productPrice, String productCuttedPrice, Long productQuantity, Long offersApplied, Long coupensApplied, boolean inStock, Long productMaxQuantity, Long stockQuantity) {
        this.type = type;
        this.productID = productID;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freeCoupens = freeCoupens;
        this.productPrice = productPrice;
        ProductCuttedPrice = productCuttedPrice;
        this.productQuantity = productQuantity;
        this.offersApplied = offersApplied;
        this.coupensApplied = coupensApplied;
        this.inStock = inStock;
        this.productMaxQuantity = productMaxQuantity;
        this.stockQuantity = stockQuantity;
        qtyIDs = new ArrayList<>();
        qtyError = false;
        this.COD = COD;
    }

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getSelectedCoupenId() {
        return selectedCoupenId;
    }

    public void setSelectedCoupenId(String selectedCoupenId) {
        this.selectedCoupenId = selectedCoupenId;
    }

    public boolean isQtyError() {
        return qtyError;
    }

    public void setQtyError(boolean qtyError) {
        this.qtyError = qtyError;
    }

    public Long getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<String> getQtyIDs() {
        return qtyIDs;
    }

    public void setQtyIDs(List<String> qtyIDs) {
        this.qtyIDs = qtyIDs;
    }

    public Long getProductMaxQuantity() {
        return productMaxQuantity;
    }

    public void setProductMaxQuantity(Long productMaxQuantity) {
        this.productMaxQuantity = productMaxQuantity;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String  getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Long getFreeCoupens() {
        return freeCoupens;
    }

    public void setFreeCoupens(Long freeCoupens) {
        this.freeCoupens = freeCoupens;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCuttedPrice() {
        return ProductCuttedPrice;
    }

    public void setProductCuttedPrice(String productCuttedPrice) {
        ProductCuttedPrice = productCuttedPrice;
    }

    public Long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Long getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(Long offersApplied) {
        this.offersApplied = offersApplied;
    }

    public Long getCoupensApplied() {
        return coupensApplied;
    }

    public void setCoupensApplied(Long coupensApplied) {
        this.coupensApplied = coupensApplied;
    }
    ///////// cart items//////////


    ////////// cart total //////////

    private int totalItems,totalItemPrice,totalAmount,savedAmount;
    private String deliveryPrice;
    public CartItemModel(int type) {
        this.type = type;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(int totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(int savedAmount) {
        this.savedAmount = savedAmount;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }
    ////////// cart total //////////

}
