package com.dexels.navajo.tipi.ant.projectbuilder;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.projectbuilder.XsdBuilder;


public class TipiBuildXsd extends BaseTipiClientTask {
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiBuildXsd.class);

	public void execute() throws BuildException {
		try {
			XsdBuilder xsd = new XsdBuilder();
			xsd.build(repository,repository+"Extensions/", extensions,getProject().getBaseDir());
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}


	

}