package com.hexa.guessandshoot.Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DafterCart {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("store_id")
@Expose
private String storeId;
@SerializedName("activity_id")
@Expose
private String activityId;
@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("maximum")
@Expose
private String maximum;
@SerializedName("notes")
@Expose
private Object notes;
@SerializedName("status")
@Expose
private String status;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("wallet_amount")
@Expose
private String walletAmount;
@SerializedName("orders_on_dafter")
@Expose
private String ordersOnDafter;
@SerializedName("receipts_on_dafter")
@Expose
private String receiptsOnDafter;
@SerializedName("payments_by_store")
@Expose
private String paymentsByStore;
@SerializedName("payments_by_wallet")
@Expose
private String paymentsByWallet;
@SerializedName("dafter_credit")
@Expose
private String dafterCredit;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getStoreId() {
return storeId;
}

public void setStoreId(String storeId) {
this.storeId = storeId;
}

public String getActivityId() {
return activityId;
}

public void setActivityId(String activityId) {
this.activityId = activityId;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getMaximum() {
return maximum;
}

public void setMaximum(String maximum) {
this.maximum = maximum;
}

public Object getNotes() {
return notes;
}

public void setNotes(Object notes) {
this.notes = notes;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getCreatedAt() {
return createdAt;
}

public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

public String getWalletAmount() {
return walletAmount;
}

public void setWalletAmount(String walletAmount) {
this.walletAmount = walletAmount;
}

public String getOrdersOnDafter() {
return ordersOnDafter;
}

public void setOrdersOnDafter(String ordersOnDafter) {
this.ordersOnDafter = ordersOnDafter;
}

public String getReceiptsOnDafter() {
return receiptsOnDafter;
}

public void setReceiptsOnDafter(String receiptsOnDafter) {
this.receiptsOnDafter = receiptsOnDafter;
}

public String getPaymentsByStore() {
return paymentsByStore;
}

public void setPaymentsByStore(String paymentsByStore) {
this.paymentsByStore = paymentsByStore;
}

public String getPaymentsByWallet() {
return paymentsByWallet;
}

public void setPaymentsByWallet(String paymentsByWallet) {
this.paymentsByWallet = paymentsByWallet;
}

public String getDafterCredit() {
return dafterCredit;
}

public void setDafterCredit(String dafterCredit) {
this.dafterCredit = dafterCredit;
}

}