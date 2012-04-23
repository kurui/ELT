package com.chinarewards.elt.model.gift;

import java.util.List;

import com.chinarewards.elt.model.gift.search.GiftStatus;
import com.chinarewards.elt.model.user.UserRole;

public class GiftProcess {

	private String giftid;
	private String name;
	private String source;
	private String sourceText;
	private String price;
	private String integral;
	private String stock;
	// private String status;
	private String statusText;

	GiftStatus status;

	private List<UserRole> UserRoleVos;

	public String getGiftid() {
		return giftid;
	}

	public void setGiftid(String giftid) {
		this.giftid = giftid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceText() {
		return sourceText;
	}

	public void setSourceText(String sourceText) {
		this.sourceText = sourceText;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public GiftStatus getStatus() {
		return status;
	}

	public void setStatus(GiftStatus status) {
		this.status = status;
	}

	public List<UserRole> getUserRoleVos() {
		return UserRoleVos;
	}

	public void setUserRoleVos(List<UserRole> userRoleVos) {
		UserRoleVos = userRoleVos;
	}

}
