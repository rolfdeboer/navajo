package tests.core;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import tipi.TipiCoreExtension;
import tipi.TipiExtension;

import com.dexels.navajo.tipi.testimpl.AbstractTipiTest;

public class CoreTipi extends AbstractTipiTest {
	
	@Before
	public void setUp() throws Exception {
		List<TipiExtension> elist = new ArrayList<TipiExtension>();
		TipiExtension ed = new TipiCoreExtension();
		ed.loadDescriptor();
		elist.add(ed);
		
		setContext("init", new File("testsrc/tests/core"),elist);
		System.err.println("Setup complete");
	}

	@Test
	public void testTipi() {
		// try {
		// Thread.sleep(100);
		// } catch (InterruptedException e) {
		// }
		getContext().shutdown();
		String xx = getContext().getInfoBuffer();
		assertEquals("event1\nevent2\n0.99\n", xx);
		System.err.println("Test ok: "+xx);
	}

}