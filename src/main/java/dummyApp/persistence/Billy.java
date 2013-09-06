package dummyApp.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.portugal.BillyPortugal;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.services.entities.PTCustomer;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTProduct;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice;
import com.premiumminds.billy.portugal.services.export.exceptions.SAFTPTExportException;
import com.premiumminds.billy.portugal.services.export.pdf.creditnote.PTCreditNotePDFExportRequest;
import com.premiumminds.billy.portugal.services.export.pdf.creditnote.PTCreditNoteTemplateBundle;
import com.premiumminds.billy.portugal.services.export.pdf.invoice.PTInvoicePDFExportRequest;
import com.premiumminds.billy.portugal.services.export.pdf.invoice.PTInvoiceTemplateBundle;

public class Billy {

	public static final String INVOICE_XSL_PATH = "src/main/resources/templates/pt_invoice.xsl";
	public static final String CREDIT_NOTE_XSL_PATH = "src/main/resources/templates/pt_creditnote.xsl";
	public static final String SIMPLE_INVOICE_XSL_PATH = "src/main/resources/templates/pt_simpleinvoice.xsl";
	public static final String LOGO_PATH = "src/main/resources/logoBig.png";
	public Injector injector;
	public BillyPortugal billyPortugal;
	public PTIssuingParams parameters;

	public Billy(Injector injector, BillyPortugal billyPortugal) {
		this.injector = injector;
		this.billyPortugal = billyPortugal;
	}

	public void persistCustomer(PTCustomer.Builder customer) {
		billyPortugal.customers().persistence().create(customer);
	}

	public void persistBusiness(PTBusiness.Builder business) {
		billyPortugal.businesses().persistence().create(business);
	}

	public void persistProduct(PTProduct.Builder product) {
		billyPortugal.products().persistence().create(product);
	}

	public void issueInvoice(PTInvoice.Builder invoice, PTIssuingParams params)
			throws DocumentIssuingException {
		billyPortugal.invoices().issue(invoice, params);
	}

	public void issueSimpleInvoice(PTSimpleInvoice.Builder simpleInvoice,
			PTIssuingParams params) throws DocumentIssuingException {
		billyPortugal.simpleInvoices().issue(simpleInvoice, params);
	}

	public void issueCreditNote(PTCreditNote.Builder creditNote,
			PTIssuingParams params) throws DocumentIssuingException {
		billyPortugal.creditNotes().issue(creditNote, params);
	}

	public void exportSaft(UID appUID, UID businessUID, Date from, Date to)
			throws IOException, SAFTPTExportException {
		billyPortugal.saft().export(appUID, businessUID, "12-NrCertificado",
				from, to);
	}

	public void exportInvoicePDF(UID invoiceUID) throws IOException,
			SAFTPTExportException, ExportServiceException {
		InputStream xsl = new FileInputStream(Billy.INVOICE_XSL_PATH);
		PTInvoiceTemplateBundle bundle = new PTInvoiceTemplateBundle(
				Billy.LOGO_PATH, xsl, "12-NrCertificado");
		billyPortugal.invoices().pdfExport(
				new PTInvoicePDFExportRequest(invoiceUID, bundle));
	}
	
	public void exportCreditNotePDF(UID invoiceUID) throws IOException,
			SAFTPTExportException, ExportServiceException {
		InputStream xsl = new FileInputStream(Billy.CREDIT_NOTE_XSL_PATH);
		PTCreditNoteTemplateBundle bundle = new PTCreditNoteTemplateBundle(Billy.LOGO_PATH, xsl, "12-NrCertificado");
		billyPortugal.creditNotes().pdfExport(new PTCreditNotePDFExportRequest(invoiceUID, bundle));
	}
}