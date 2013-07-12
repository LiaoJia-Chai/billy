/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-core-jpa.
 * 
 * billy-core-jpa is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-core-jpa is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-core-jpa.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.core.persistence.entities.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.ShippingPointEntity;
import com.premiumminds.billy.core.persistence.entities.SupplierEntity;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.services.entities.Supplier;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;

@Entity
@Table(name = Config.TABLE_PREFIX + "GENERIC_INVOICE")
public class JPAGenericInvoiceEntity extends JPABaseEntity implements
GenericInvoiceEntity {
	private static final long serialVersionUID = 1L;

	@Column(name = "NUMBER")
	protected String number;

	@Column(name = "SERIES")
	protected String series;

	@ManyToOne(targetEntity = JPABusinessEntity.class)
	@JoinColumn(name = "ID_BUSINESS", referencedColumnName = "ID")
	protected Business business;

	@ManyToOne(targetEntity = JPACustomerEntity.class)
	@JoinColumn(name = "ID_CUSTOMER", referencedColumnName = "ID")
	protected Customer customer;

	@ManyToOne(targetEntity = JPASupplierEntity.class)
	@JoinColumn(name = "ID_SUPPLIER", referencedColumnName = "ID")
	protected Supplier supplier;

	@Column(name = "OFFICE_NUMBER")
	protected String officeNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE")
	protected Date date;

	@Column(name = "AMOUNT_WITH_TAX", scale = 7)
	protected BigDecimal amountWithTax;

	@Column(name = "TAX_AMOUNT", scale = 7)
	protected BigDecimal taxAmount;

	@Column(name = "AMOUNT_WITHOUT_TAX", scale = 7)
	protected BigDecimal amountWithoutTax;
	
	@Column(name = "DISCOUNTS_AMOUNT", scale = 7)
	protected BigDecimal discountsAmount;

	@OneToOne(targetEntity = JPAShippingPointEntity.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "ID_SHIPPING_POINT_ORIGIN", referencedColumnName = "ID")
	protected ShippingPoint shippingOrigin;

	@OneToOne(targetEntity = JPAShippingPointEntity.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "ID_SHIPPING_POINT_DESTINATION", referencedColumnName = "ID")
	protected ShippingPoint shippingDestination;

	@Column(name = "PAYMENT_TERMS")
	protected String paymentTerms;

	@Column(name = "SELF_BILLED")
	protected Boolean selfBilled;

	@Column(name = "SOURCE_ID")
	protected String sourceId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "GENERAL_LEDGER_DATE")
	protected Date generalLedgerDate;

	@Column(name = "BATCH_ID")
	protected String batchId;

	@Column(name = "TRANSACTION_ID")
	protected String transactionId;

	@Column(name = "CURRENCY")
	protected Currency currency;

	@Column(name = "SETTLEMENT_DESCRIPTION")
	protected String settlementDescription;

	@Column(name = "SETTLEMENT_DISCOUNT", scale = 7)
	protected BigDecimal settlementDiscount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SETTLEMENT_DATE")
	protected Date settlementDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "PAYMENT_MECHANISM")
	protected Enum<?> paymentMechanism;

	@Enumerated(EnumType.STRING)
	@Column(name = "CREDIT_OR_DEBIT")
	protected CreditOrDebit creditOrDebit;

	@ElementCollection
	@CollectionTable(
			name=Config.TABLE_PREFIX + "INVOICE_RECEIPT_NUMBER",
			joinColumns=@JoinColumn(name="ID_INVOICE"))
	@Column(name="RECEIPT_NUMBER")
	protected List<String> receiptNumbers;

	@OneToMany(targetEntity = JPAGenericInvoiceEntity.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name=Config.TABLE_PREFIX + "INVOICE_ENTRY",
			joinColumns={ @JoinColumn(name="ID_INVOICE", referencedColumnName="ID") },
			inverseJoinColumns={ @JoinColumn(name="ID_ENTRY", referencedColumnName="ID", unique=true) })
	protected List<GenericInvoiceEntry> entries;


	public JPAGenericInvoiceEntity() {
		this.entries = new ArrayList<GenericInvoiceEntry>();
		this.receiptNumbers = new ArrayList<String>();
	}
	
	@Override
	public String getNumber() {
		return number;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Business getBusiness() {
		return business;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Customer getCustomer() {
		return customer;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Supplier getSupplier() {
		return supplier;
	}

	@Override
	public String getOfficeNumber() {
		return officeNumber;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public BigDecimal getAmountWithTax() {
		return amountWithTax;
	}

	@Override
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	@Override
	public BigDecimal getAmountWithoutTax() {
		return amountWithoutTax;
	}
	
	@Override
	public BigDecimal getDiscountsAmount() {
		return discountsAmount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ShippingPoint getShippingOrigin() {
		return shippingOrigin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ShippingPoint getShippingDestination() {
		return shippingDestination;
	}

	@Override
	public String getPaymentTerms() {
		return paymentTerms;
	}

	@Override
	public boolean isSelfBilled() {
		return selfBilled;
	}

	@Override
	public String getSourceId() {
		return sourceId;
	}

	@Override
	public Date getGeneralLedgerDate() {
		return generalLedgerDate;
	}

	@Override
	public String getBatchId() {
		return batchId;
	}

	@Override
	public String getTransactionId() {
		return transactionId;
	}

	@Override
	public Currency getCurrency() {
		return currency;
	}

	@Override
	public String getSettlementDescription() {
		return settlementDescription;
	}

	@Override
	public BigDecimal getSettlementDiscount() {
		return settlementDiscount;
	}

	@Override
	public Date getSettlementDate() {
		return settlementDate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enum<?> getPaymentMechanism() {
		return paymentMechanism;
	}

	@Override
	public CreditOrDebit getCreditOrDebit() {
		return creditOrDebit;
	}

	@Override
	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public <T extends Business> void setBusiness(T business) {
		this.business = business;
	}

	@Override
	public <T extends CustomerEntity> void setCustomer(T customer) {
		this.customer = customer;
	}

	@Override
	public <T extends SupplierEntity> void setSupplier(T supplier) {
		this.supplier = supplier;
	}

	@Override
	public void setOfficeNumber(String number) {
		this.officeNumber = number;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public void setAmountWithTax(BigDecimal amount) {
		this.amountWithTax = amount;
	}

	@Override
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	@Override
	public void setAmountWithoutTax(BigDecimal amount) {
		this.amountWithoutTax = amount;
	}
	
	@Override
	public void setDiscountsAmount(BigDecimal amount) {
		this.discountsAmount = amount;
	}

	@Override
	public <T extends ShippingPointEntity> void setShippingOrigin(T origin) {
		this.shippingOrigin = origin;
	}

	@Override
	public <T extends ShippingPointEntity> void setShippingDestination(
			T destination) {
		this.shippingDestination = destination;
	}

	@Override
	public void setPaymentTerms(String terms) {
		this.paymentTerms = terms;
	}

	@Override
	public void setSelfBilled(boolean selfBilled) {
		this.selfBilled = selfBilled;
	}

	@Override
	public void setSourceId(String source) {
		this.sourceId = source;
	}

	@Override
	public void setGeneralLedgerDate(Date date) {
		this.generalLedgerDate = date;
	}

	@Override
	public void setBatchId(String id) {
		this.batchId = id;
	}

	@Override
	public void setTransactionId(String id) {
		this.transactionId = id;
	}

	@Override
	public List<String> getReceiptNumbers() {
		return this.receiptNumbers;
	}

	@Override
	public List<GenericInvoiceEntry> getEntries() {
		return this.entries;
	}

	@Override
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Override
	public void setSettlementDescription(String description) {
		this.settlementDescription = description;
	}

	@Override
	public void setSettlementDiscount(BigDecimal discount) {
		this.settlementDiscount = discount;
	}

	@Override
	public void setSettlementDate(Date date) {
		this.settlementDate = date;
	}

	@Override
	public <T extends Enum<T>> void setPaymentMechanism(T mechanism) {
		this.paymentMechanism = mechanism;
	}

	@Override
	public void setCreditOrDebit(CreditOrDebit creditOrDebit) {
		this.creditOrDebit = creditOrDebit;
	}

}