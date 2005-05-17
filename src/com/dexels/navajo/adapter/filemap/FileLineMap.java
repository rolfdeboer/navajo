/*
 * Created on May 17, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dexels.navajo.adapter.filemap;

import java.util.ArrayList;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

/**
 * @author arjen
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FileLineMap implements Mappable {

	public String line;
	public FileRecordMap [] columns;
	public String column;
	public String separator = ";";
	public String columnSeparator;
	
	private ArrayList recordList;
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#load(com.dexels.navajo.server.Parameters, com.dexels.navajo.document.Navajo, com.dexels.navajo.server.Access, com.dexels.navajo.server.NavajoConfig)
	 */
	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#store()
	 */
	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#kill()
	 */
	public void kill() {
	}

	private String generateRecords() {
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < columns.length; i++) {
			if (columnSeparator != null) {
				bf.append(columnSeparator);
			}
			bf.append(columns[i].record);
			if (columnSeparator != null) {
				bf.append(columnSeparator);
			}
			if (i < columns.length - 1) {
				bf.append(this.separator);
			}
		}
		bf.append('\n');
		return bf.toString();
	}
	
	public String getLine() {
		if (columns != null) {
			line = generateRecords();
		} else if ( recordList != null ) {
			columns = new FileRecordMap[recordList.size()];
			columns = (FileRecordMap []) recordList.toArray(columns);
			line = generateRecords();
		}
		return line;
	}
	
	public void setLine(String l) {
		this.line = l + "\n";
	}
	
	public void setColumns(FileRecordMap [] r) {
		this.columns = r;
	}
	
	public void setColumn(String r) {
		FileRecordMap frm = new FileRecordMap();
		frm.setRecord(r);
		if ( recordList == null ) {
			recordList = new ArrayList();
		}
		recordList.add(frm);
	}
	
	public void setSeparator(String s) {
		this.separator = s;
	}
	
	public void setColumnSeparator(String s) {
		this.columnSeparator = s;
	}
}
