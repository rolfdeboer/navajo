package com.dexels.navajo.tipi;

import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface TipiExecutable {
  public void performAction() throws TipiBreakException,TipiException;
  public XMLElement store();
}