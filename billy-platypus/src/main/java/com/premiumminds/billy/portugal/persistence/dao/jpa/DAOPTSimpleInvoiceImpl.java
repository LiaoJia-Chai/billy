/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.dao.jpa;

import java.util.List;

import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTSimpleInvoiceEntity;

public class DAOPTSimpleInvoiceImpl extends DAOPTInvoiceImpl {

	public DAOPTSimpleInvoiceImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	public PTSimpleInvoiceEntity getEntityInstance() {
		return new JPAPTSimpleInvoiceEntity();
	}

	@Override
	protected Class<JPAPTSimpleInvoiceEntity> getEntityClass() {
		return JPAPTSimpleInvoiceEntity.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PTSimpleInvoiceEntity getLatestInvoiceFromSeries(String series)
			throws BillyRuntimeException {

		List<Object[]> list = findLastestUID(this.getEntityClass(), series);

		if (list.size() != 0)
			return (PTSimpleInvoiceEntity) this.get(new UID((String) list
					.get(0)[0]));
		else
			throw new BillyRuntimeException();
	}

}
