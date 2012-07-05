package com.dexels.navajo.server;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.mapping.CompiledScript;

public class CompiledScriptFactory {
	
	private final static Logger logger = LoggerFactory
			.getLogger(CompiledScriptFactory.class);
	
	private String serviceName;
	
	@SuppressWarnings("unchecked")
	public CompiledScript getCompiledScript() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<? extends CompiledScript> c;
		c = (Class<? extends CompiledScript>) Class.forName(serviceName);
		CompiledScript instance = c.newInstance();
		return instance;
	}
	
	public void activate(Map<String,String> properties) {
		logger.info("Activating compiledscriptfactory");
		serviceName = properties.get("serviceName");
	}
}
