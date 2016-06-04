package fr.ac_versailles.crdp.apiscol.tests.gp8;

import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.xml.XmlPage;

import fr.ac_versailles.crdp.apiscol.tests.ApiScolTests;

public class Scolomfr2GeneralFieldsTest extends ApiScolTests {
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
		XmlPage page = postMetadataDocument("scolomfr2/comptine.xml", url,
				false, true);
		testAtomDocumentTitleIs("Zwicke Zwacke", page);
		testResourceContentSrcIs(
				"https://www.neteduc-cloud.fr/contenus/activites/allemand_c3/zwicke_zwacke.mp3",
				page);
		testEducationalResourceTypeIs(
				"http://data.education.fr/voc/scolomfr/concept/scolomfr-voc-010-num-001",
				page);
		testEducationalResourceTypeLabelIs("animation", page);
		// deleteResource(page);
	}

}
