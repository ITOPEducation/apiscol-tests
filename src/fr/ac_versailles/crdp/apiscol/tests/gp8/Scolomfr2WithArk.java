package fr.ac_versailles.crdp.apiscol.tests.gp8;

import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.xml.XmlPage;

import fr.ac_versailles.crdp.apiscol.tests.ApiScolTests;

public class Scolomfr2WithArk extends ApiScolTests {
	@Before
	public void initialize() {
		createClient();
	}

	@After
	public void close() {
		closeClient();
	}

	@Test
	public void testPostingScolomfr2Document() {
		URL url = getServiceUrl("/edit/meta", editionServiceBaseWanUrl);
		assertTrue("The Url must be valid", url != null);
		XmlPage page = postMetadataDocument("scolomfr2/itop_ark.xml", url,
				false, true, false);
		String scolomfrLinkLocation = getAtomLinkLocation(page, "describedby",
				"application/lom+xml");
		XmlPage scolomfr = getXMLPage(scolomfrLinkLocation);
		DomElement arkIdentifier = getGeneralIdentifiers("ARK", scolomfr);
		DomElement uriIdentifier = getGeneralIdentifiers("URI", scolomfr);
		assertTrue("The ARK identifier should have been protected",
				arkIdentifier != null);
		assertTrue("There should be an URI identifier now",
				uriIdentifier != null);
	}

}
