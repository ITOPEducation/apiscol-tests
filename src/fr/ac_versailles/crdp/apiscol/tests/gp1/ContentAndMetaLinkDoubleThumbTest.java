package fr.ac_versailles.crdp.apiscol.tests.gp1;

import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.xml.XmlPage;

import fr.ac_versailles.crdp.apiscol.tests.ApiScolTests;

public class ContentAndMetaLinkDoubleThumbTest extends ApiScolTests {
	@Before
	public void initialize() {
		createClient();
	}

	@After
	public void close() {
		closeClient();
	}

	@Test
	public void testPostingLinkWithMetadata() {
		URL url = getServiceUrl("/edit/meta", editionServiceBaseWanUrl);
		assertTrue("The Url must be valid", url != null);
		XmlPage page = postMetadataDocument("enqstat.xml", url);
		String metadataLinkLocation = getAtomLinkLocation(page, "self",
				"text/html");
		XmlPage newResourcePage = getNewResourcePage(metadataLinkLocation,
				"url");
		String urn = getAtomId(newResourcePage);
		String editUri = getEditMediaUri(newResourcePage);
		String etag = getAtomUpdatedField(newResourcePage);
		newResourcePage = postUrlContent(editUri, urn,
				"http://www.poisson-or.com/", etag);
		waitDuring(3000);
		// test preview
		XmlPage page4 = getThumbsSuggestionForMetaId(metadataLinkLocation);
		String thumbSuggestionUri = getAnyThumbSuggestionUri(page4);
		XmlPage page5 = chooseThumbForMetadataId(metadataLinkLocation,
				thumbSuggestionUri, getThumbEtag(page4));
		page4 = getThumbsSuggestionForMetaId(metadataLinkLocation);
		thumbSuggestionUri = getAnyThumbSuggestionUri(page4);
		chooseThumbForMetadataId(metadataLinkLocation, thumbSuggestionUri,
				getThumbEtag(page4));
		testDefaultThumbHasuri(page5);
		waitDuring(5000);
		XmlPage metaPage = getMetadata(metadataLinkLocation, true);
		String scolomfrLinkLocation = getAtomLinkLocation(metaPage,
				"describedby", "application/lom+xml");
		XmlPage scolomfr = getXMLPage(scolomfrLinkLocation);
		int nbRelations = getNbRelations(
				"http://data.education.fr/voc/scolomfr/concept/scolomfr-voc-009-num-021",
				scolomfr);

		deleteResource(newResourcePage);
		XmlPage newMetadataPage = getMetadata(metadataLinkLocation, false);
		deleteMetadataEntry(metadataLinkLocation,
				getAtomUpdatedField(newMetadataPage));
		assertTrue("There should be one 'a pour vignette' relation and not "
				+ nbRelations, nbRelations == 1);
	}
}
