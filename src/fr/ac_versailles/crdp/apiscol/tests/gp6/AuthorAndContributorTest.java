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
public class AuthorAndContributorTest extends ApiScolTests {
	@Before
	public void initialize() {
		createClient();
	}

	@After
	public void close() {
		closeClient();
	}

	@Test
	public void testPostingMetadataWithAuthorAndPublisher() {
		URL url = getServiceUrl("/edit/meta", editionServiceBaseWanUrl);
		assertTrue("The Url must be valid", url != null);
		XmlPage page = postMetadataDocument("enqstat.xml", url);
		testAtomDocumentAuthorsContains("Dornbusch, Joachim", page);
		testAtomDocumentContributorsContainsOrganization("publisher", "GEP SES Versailles", page);
		deleteResource(page);
	}

}
