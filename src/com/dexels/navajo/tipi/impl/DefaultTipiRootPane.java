package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.tipixml.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class DefaultTipiRootPane
    extends DefaultTipi {
  protected RootPaneContainer myRootPaneContainer;
  protected abstract void setBounds(Rectangle r);
  private String myTitle;
  private String myIcon;
  private String myMenuBar="";
  protected abstract Rectangle getBounds();

  protected abstract void setIcon(ImageIcon ic);

  protected abstract void setTitle(String s);

  protected abstract void setJMenuBar(JMenuBar s);

//  public DefaultTipiRootPane() {
//  }
//  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
//    super.load(definition,instance,context);
//
//
//    String elmName = definition.getName();
//
//    String menubar = (String)definition.getAttribute("menubar");
//    if (menubar!=null) {
//      XMLElement xe = context.getTipiMenubarDefinition(menubar);
//      TipiMenubar tm = context.createTipiMenubar();
//      tm.load(xe,context);
//      context.getTopLevel().setTipiMenubar(tm);
//    }
//  }



  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    Rectangle r = getBounds();
    if (name.equals("menubar")) {
      //System.err.println("MENUBAR!!!!");
      try {
        myMenuBar = (String)object;
        XMLElement instance = new CaseSensitiveXMLElement();
        instance.setName("component-instance");
        instance.setAttribute("name",(String)object);
        TipiComponent tm = myContext.instantiateComponent(instance);
//        XMLElement xe = myContext.getTipiMenubarDefinition( (String) object);
//        TipiMenubar tm = myContext.createTipiMenubar();
//        tm.load(xe, myContext);
        //System.err.println("I am: "+getClass()+" menu");
        setJMenuBar( (JMenuBar) tm.getContainer());
      }
      catch (TipiException ex) {
        ex.printStackTrace();
        setJMenuBar(null);
        myMenuBar = "";
      }
    }
    if (name.equals("x")) {
      r.x = ((Integer) object).intValue();
    }
    if (name.equals("y")) {
      r.y = ((Integer) object).intValue();
    }
    if (name.equals("w")) {
        r.width = ( (Integer) object).intValue();
    }
    if (name.equals("h")) {
      r.height = ((Integer) object).intValue();
    }
    if (name.equals("title")) {
      myTitle = object.toString();
      setTitle(myTitle);
    }
    if (name.equals("icon")) {
      myIcon = (String) object;
      ImageIcon ic = myContext.getIcon(myIcon);
      setIcon(ic);
    }
      setBounds(r);
  }

  public Object getComponentValue(String name) {
    if (name.equals("x")) {
      return new Integer(this.getContainer().getX());
    }
    if (name.equals("y")) {
      return new Integer(this.getContainer().getY());
    }
    if (name.equals("w")) {
      return new Integer(this.getContainer().getWidth());
    }
    if (name.equals("h")) {
      return new Integer(this.getContainer().getHeight());
    }
    if (name.equals("title")) {
      return myTitle;
    }
    if (name.equals("icon")) {
      return myIcon;
    }
    if (name.equals("menubar")) {
      return myMenuBar;
    }

    return super.getComponentValue(name);
  }

  public Container createContainer() {
    /**@todo Implement this com.dexels.navajo.tipi.TipiComponent abstract method*/
    throw new java.lang.UnsupportedOperationException("Method createContainer() not yet implemented.");
  }
}