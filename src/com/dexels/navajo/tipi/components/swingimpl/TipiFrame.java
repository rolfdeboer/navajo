package com.dexels.navajo.tipi.components.swingimpl;

import java.net.*;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiFrame
    extends TipiSwingDataComponentImpl {
  private JFrame myFrame = null;
  private boolean fullscreen = false;
  private String myMenuBar = "";
  public TipiFrame() {
  }

  public Object createContainer() {
    myFrame = new TipiSwingFrame(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
//        myContext.setToplevel(myFrame);
    return (Container) myFrame;
  }

  public void addToContainer(Object c, Object constraints) {
    if (constraints!=null) {
      System.err.println("ConstraintClass: "+constraints.getClass());
    }
    if (JMenuBar.class.isInstance(c)) {
      myFrame.setJMenuBar( (JMenuBar) c);
    }
    else {
      myFrame.getContentPane().add( (Component) c, constraints);
    }
  }

  public void removeFromContainer(Object c) {
    myFrame.getContentPane().remove( (Component) c);
  }

  protected void setBounds(Rectangle r) {
    myFrame.setBounds(r);
  }

  protected Rectangle getBounds() {
    return myFrame.getBounds();
  }

  protected void setIcon(ImageIcon ic) {
    if (ic == null) {
      return;
    }
    myFrame.setIconImage(ic.getImage());
  }

  protected void setTitle(String s) {
    myFrame.setTitle(s);
  }

  public void setContainerLayout(Object layout) {
    myFrame.getContentPane().setLayout( (LayoutManager) layout);
  }

  private ImageIcon getIcon(URL u) {
    return new ImageIcon(u);
  }

  public void setComponentValue(String name, Object object) {
    if (name.equals("fullscreen") && ( (Boolean) object).booleanValue()) {
      fullscreen = ( (Boolean) object).booleanValue();
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          myFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
      });
    }
    if (name.equals("visible")) {
      getSwingContainer().setVisible(object.equals("true"));
      if (fullscreen) {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            ( (JFrame) myFrame).setExtendedState(JFrame.MAXIMIZED_BOTH);
          }
        });
      }
    }
    if ("icon".equals(name)) {
      setIcon(getIcon( (URL) object));
    }
    if ("title".equals(name)) {
      this.setTitle( (String) object);
    }
    Rectangle r = getBounds();
    if (name.equals("x")) {
      r.x = ( (Integer) object).intValue();
    }
    if (name.equals("y")) {
      r.y = ( (Integer) object).intValue();
    }
    if (name.equals("w")) {
      r.width = ( (Integer) object).intValue();
    }
    if (name.equals("h")) {
      r.height = ( (Integer) object).intValue();
    }
    setBounds(r);
    if (name.equals("menubar")) {
      try {
        if (object==null || object.equals("")) {
          System.err.println("null menu bar. Not instantiating");
          return;
        }

        myMenuBar = (String)object;
        XMLElement instance = new CaseSensitiveXMLElement();
        instance.setName("component-instance");
        instance.setAttribute("name",(String)object);
        instance.setAttribute("id",(String)object);
        TipiComponent tm = this.addComponentInstance(myContext,instance,null);
        setJMenuBar( (JMenuBar) tm.getContainer());
      }
      catch (TipiException ex) {
        ex.printStackTrace();
        setJMenuBar(null);
        myMenuBar = "";
      }
    }
    super.setComponentValue(name, object);
//    if (name.equals("centered") && ((Boolean)object).booleanValue()) {
//      ((JFrame)myFrame).setLocationRelativeTo(null);
//    }
  }

  public Object getComponentValue(String name) {
    if ("visible".equals(name)) {
      return new Boolean(myFrame.isVisible());
    }
//    if("title".equals(name)){
//      return myWindow.getTitle();
//    }
    Rectangle r = myFrame.getBounds();
    if (name.equals("x")) {
      return new Integer(r.x);
    }
    if (name.equals("y")) {
      return new Integer(r.y);
    }
    if (name.equals("w")) {
      return new Integer(r.width);
    }
    if (name.equals("h")) {
      return new Integer(r.height);
    }
    if (name.equals("resizable")) {
      return new Boolean(myFrame.isResizable());
    }
    return super.getComponentValue(name);
  }

  protected void setJMenuBar(JMenuBar s) {
    ( (JFrame) myFrame).setJMenuBar(s);
  }
}