/*
 * Created on May 17, 2005
 *
 */
package com.dexels.navajo.adapter;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.adapter.filemap.*;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

/**
 * @author arjen
 *
 */
public class FileMap implements Mappable {

	public String fileName;
	public String separator;
	public String line;
	public FileLineMap [] lines;
	public boolean persist = true;
	public Binary content;
	
	private ArrayList lineArray = null;
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#load(com.dexels.navajo.server.Parameters, com.dexels.navajo.document.Navajo, com.dexels.navajo.server.Access, com.dexels.navajo.server.NavajoConfig)
	 */
	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {

	}

	private byte [] getBytes() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (int i = 0; i < lineArray.size(); i++) {
			FileLineMap flm = (FileLineMap) lineArray.get(i);
			if (flm.getLine() != null) {
				baos.write(flm.getLine().getBytes());
			} 
		}
		baos.close();
		return baos.toByteArray();
	}
	
	public Binary getContent() throws UserException {
		try {
			Binary b = new Binary(getBytes());
			b.setMimeType("application/text");
			return b;
		}
		catch (Exception e) {
			throw new UserException(-1, e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#store()
	 */
	public void store() throws MappableException, UserException {
		if (persist && fileName != null) {
			File f = new File(fileName);
			System.err.println("Writing to file: " + fileName);
			try {
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
				bos.write(getBytes());
				bos.close();
			} catch (Exception e) {
				throw new UserException(-1, e.getMessage());
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#kill()
	 */
	public void kill() {
		

	}

	public void setLines(FileLineMap [] l) {
		if (lineArray == null) {
			lineArray = new ArrayList();
		}
		for (int i = 0; i < l.length; i++) {
			lineArray.add(l[i]);
		}
	}
	
	public void setLine(FileLineMap l) {
		if (lineArray == null) {
			lineArray = new ArrayList();
		}
		lineArray.add(l);
	}
	
	public void setFileName(String f) {
		this.fileName = f;
	}
	
	public void setSeparator(String s) {
		this.separator = s;
	}
	
	public static void main(String[] args) throws Exception {
		FileMap fm = new FileMap();
		FileLineMap [] flm = new FileLineMap[2];
		flm[0] = new FileLineMap();
		flm[0].setLine("apenoot");
		flm[1] = new FileLineMap();
		flm[1].setLine("kibbeling");
		fm.setLines(flm);
		fm.setFileName("/home/arjen/aap.txt");
		fm.store();
	}
}
