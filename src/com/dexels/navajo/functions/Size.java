package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import java.util.ArrayList;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class Size extends FunctionInterface {

  public Size() {
  }
  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    Object arg = this.getOperands().get(0);
    if (arg == null)
      throw new TMLExpressionException("Argument expected for Size() function.");
    if (!(arg instanceof ArrayList))
      throw new TMLExpressionException("Expected list argument for size() function.");
    ArrayList list = (ArrayList) arg;
    return new Integer(list.size());
  }

  public String usage() {
    return "Size(list)";
  }
  public String remarks() {
    return "This function return the size of a list argument.";
  }
}