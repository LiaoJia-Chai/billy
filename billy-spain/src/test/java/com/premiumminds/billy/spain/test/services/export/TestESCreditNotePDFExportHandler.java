/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.test.services.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNote;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntity;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParams;
import com.premiumminds.billy.spain.services.export.pdf.creditnote.ESCreditNotePDFExportHandler;
import com.premiumminds.billy.spain.services.export.pdf.creditnote.ESCreditNoteTemplateBundle;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.test.util.ESCreditNoteTestUtil;
import com.premiumminds.billy.spain.util.PaymentMechanism;
import com.premiumminds.billy.spain.util.Services;

public class TestESCreditNotePDFExportHandler extends ESPersistencyAbstractTest {

	public static final int		NUM_ENTRIES					= 10;
	public static final String	XSL_PATH					= "src/main/resources/templates/es_creditnote.xsl";
	public static final String	LOGO_PATH					= "src/main/resources/logoBig.png";

	@Test
	public void testPDFcreation() throws NoSuchAlgorithmException,
		ExportServiceException, URISyntaxException, DocumentIssuingException,
		IOException {

		File file = File.createTempFile("Result", ".pdf");

		InputStream xsl = new FileInputStream(
				TestESCreditNotePDFExportHandler.XSL_PATH);
		ESCreditNoteTemplateBundle bundle = new ESCreditNoteTemplateBundle(
				TestESCreditNotePDFExportHandler.LOGO_PATH, xsl);

		ESCreditNotePDFExportHandler handler = new ESCreditNotePDFExportHandler(
				ESAbstractTest.injector.getInstance(DAOESCreditNote.class));

		handler.toFile(
				file.toURI(),
				this.generateESCreditNote(PaymentMechanism.CASH,
						this.getNewIssuedInvoice()), bundle);
	}

	private ESCreditNoteEntity generateESCreditNote(
			PaymentMechanism paymentMechanism, ESInvoiceEntity reference)
		throws DocumentIssuingException {

		Services services = new Services(ESAbstractTest.injector);

		ESIssuingParams params = this.getParameters("AC", "3000");

		ESCreditNoteEntity creditNote = null;
		creditNote = (ESCreditNoteEntity) services.issueDocument(
				new ESCreditNoteTestUtil(ESAbstractTest.injector)
						.getCreditNoteBuilder(reference), params);

		creditNote.setCustomer((CustomerEntity) reference.getCustomer());
		creditNote.setBusiness((BusinessEntity) reference.getBusiness());
		creditNote.setCreditOrDebit(CreditOrDebit.CREDIT);

		return creditNote;
	}
}