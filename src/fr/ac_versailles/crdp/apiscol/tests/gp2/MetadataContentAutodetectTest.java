package fr.ac_versailles.crdp.apiscol.tests.gp2;

import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.xml.XmlPage;

import fr.ac_versailles.crdp.apiscol.tests.ApiScolTests;

//@Ignore
public class MetadataContentAutodetectTest extends ApiScolTests {

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
		URL url = getServiceUrl("/edit/meta", editionServiceBaseUrl);
		assertTrue("The Url must be valid", url != null);
		XmlPage page = postMetadataDocument("notice_mardi.xml", url, false,
				true);
		String metadataLinkLocation = getAtomLinkLocation(page, "self",
				"text/html");
		System.out.println(metadataLinkLocation);
		System.out.println(page.asXml());
	}

}
