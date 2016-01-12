package fr.ac_versailles.crdp.apiscol.tests.gp6;

import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.xml.XmlPage;

import fr.ac_versailles.crdp.apiscol.tests.ApiScolTests;

//@Ignore
public class CustomPreviewTest extends ApiScolTests {
	@Before
	public void initialize() {
		createClient();
	}

	@After
	public void close() {
		closeClient();
	}

	@Test
	public void testAssignThumbs() {
		URL url = getServiceUrl("/edit/meta", editionServiceBaseWanUrl);
		assertTrue("The url must be valid ", url != null);
		XmlPage page = postMetadataDocument("enqstat.xml", url);
		String metadataLinkLocation = getAtomLinkLocation(page, "self",
				"text/html");
		XmlPage newResourcePage = getNewResourcePage(metadataLinkLocation,
				"url");
		String urn = getAtomId(newResourcePage);
		String editUri = getEditMediaUri(newResourcePage);
		XmlPage page2 = postUrlContent(editUri, urn,
				"http://www.cheloniophilie.com",
				getAtomUpdatedField(newResourcePage));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url = getServiceUrl("/edit/preview", editionServiceBaseWanUrl);
		urn = getAtomId(page2);
		postCustomPreview("custom_preview.jpg", url, urn,
				getAtomUpdatedField(page2));
	}

}
