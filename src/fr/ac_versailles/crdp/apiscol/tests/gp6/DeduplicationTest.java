package fr.ac_versailles.crdp.apiscol.tests.gp6;

import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.xml.XmlPage;

import fr.ac_versailles.crdp.apiscol.tests.ApiScolTests;

//@Ignore
public class DeduplicationTest extends ApiScolTests {

	@Before
	public void initialize() {
		createClient();
	}

	@After
	public void close() {
		closeClient();
	}

	@Test
	public void testPostingDocuments() {
		URL url = getServiceUrl("/edit/meta", editionServiceBaseWanUrl);
		assertTrue("The Url must be valid", url != null);
		postDocumentTwiceWithModification(url);
	}

	private void postDocumentTwiceWithModification(URL url) {
		// create a first metadata entry
		XmlPage page = postMetadataDocument("renkan.xml", url, false, true);
		String titleString = "Madame Bovary de Flaubert: carte mentale";
		String summaryExtract = "Le processus de création de l'oeuvre Madame Bovary ainsi que son analyse littéraire sous forme de carte mentale.";

		testAtomDocumentTitleIs(titleString, page);
		testAtomDocumentSummaryContains(summaryExtract, page);
		String linkLocation = getAtomLinkLocation(page, "self", "text/html");
		page = getMetadata(linkLocation, true);
		testAtomRootElement(page);
		testAtomDocumentTitleIs(titleString, page);
		testAtomDocumentSummaryContains(summaryExtract, page);
		// send another one with the same content uri and deduplicate set to
		// true
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XmlPage modifiedPage = postMetadataDocument("renkan_modif.xml", url,
				false, false, true);
		titleString = "titre modifié";
		summaryExtract = "description modifiée.";
		testAtomDocumentTitleIs(titleString, modifiedPage);
		testAtomDocumentSummaryContains(summaryExtract, modifiedPage);
		String metadataLinkLocation = getAtomLinkLocation(page, "self",
				"text/html");
//		deleteMetadataEntry(metadataLinkLocation,
//				getAtomUpdatedField(modifiedPage));
	}
}
