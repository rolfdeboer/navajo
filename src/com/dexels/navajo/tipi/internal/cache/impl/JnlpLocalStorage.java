package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.*;
import java.net.*;

import com.dexels.navajo.tipi.internal.cache.*;

public class JnlpLocalStorage implements LocalStorage {

	public void flush(String location) throws IOException {

	}

	public void flushAll(String location) throws IOException {

	}

	public InputStream getLocalData(String location) throws IOException {
		return null;
	}

	public long getLocalModificationDate(String location) throws IOException {
		return 0;
	}

	public URL getURL(String location) throws IOException {
		return null;
	}

	public boolean hasLocal(String location) throws IOException {
		return false;
	}

	public void storeData(String location, InputStream data) throws IOException {

	}

}
