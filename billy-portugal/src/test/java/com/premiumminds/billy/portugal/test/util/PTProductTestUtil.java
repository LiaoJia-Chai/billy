/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Product.ProductType;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.services.entities.PTProduct;
import com.premiumminds.billy.portugal.util.Taxes;

public class PTProductTestUtil {

	private static final String NUMBER_CODE = "123";
	private static final String UNIT_OF_MEASURE = "Kg";
	private static final String PRODUCT_CODE = "12345";
	private static final String DESCRIPTION = "DESCRIPTION";
	private static final String UID = "POTATOES";
	private static final String GROUP = "FOOD";
	private static final ProductType TYPE = ProductType.GOODS;

	private Injector injector;
	private Taxes taxes;
	private PTTaxEntity tax;

	public PTProductTestUtil(Injector injector) {
		this.injector = injector;
		this.taxes = new Taxes(injector);
		this.tax = (PTTaxEntity) this.taxes.continent().normal();
	}

	public PTProductEntity getProductEntity(String uid, String numberCode,
			String unitOfMeasure, String productCode, String description,
			ProductType type) {
		PTProduct.Builder productBuilder = this.injector
				.getInstance(PTProduct.Builder.class);

		productBuilder.clear();
		productBuilder.addTaxUID(this.tax.getUID()).setNumberCode(numberCode)
				.setUnitOfMeasure(unitOfMeasure).setProductCode(productCode)
				.setDescription(description).setType(type)
				.setProductGroup(PTProductTestUtil.GROUP);

		PTProductEntity product = (PTProductEntity) productBuilder.build();

		product.setUID(new UID(uid));

		return product;
	}

	public PTProductEntity getProductEntity(String uid) {
		return this.getProductEntity(uid, PTProductTestUtil.NUMBER_CODE,
				PTProductTestUtil.UNIT_OF_MEASURE,
				PTProductTestUtil.PRODUCT_CODE, PTProductTestUtil.DESCRIPTION,
				PTProductTestUtil.TYPE);
	}

	public PTProductEntity getProductEntity() {
		return this.getProductEntity(PTProductTestUtil.UID);
	}
}