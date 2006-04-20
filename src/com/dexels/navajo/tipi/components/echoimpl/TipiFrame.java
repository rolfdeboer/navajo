package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.*;

import com.dexels.navajo.tipi.components.echoimpl.impl.TipiLayoutManager;

import echopointng.ContainerEx;
import echopointng.MenuBar;
import echopointng.able.Positionable;
import echopointng.able.Sizeable;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiFrame extends TipiEchoDataComponentImpl {

    private ContentPane myContentPane = new ContentPane();

    private Window myWindow;

    // private ContainerEx innerContainer;

    private Grid contentPane;

    public TipiFrame() {
    }

    public Window getWindow() {
        return myWindow;
    }

    public Object createContainer() {
        myWindow = new Window();
        // innerContainer = new ContainerEx();
        contentPane = new Grid(1);
        myWindow.getContent().add(contentPane);
        // innerContainer.add(contentPane);
        return myWindow;

    }

    public void setContainerLayout(Object layout) {
    }

    /**
     * setComponentValue
     * 
     * @param name
     *            String
     * @param object
     *            Object
     * @todo Implement this
     *       com.dexels.navajo.tipi.components.core.TipiComponentImpl method
     */
    protected void setComponentValue(String name, Object object) {
        if ("title".equals(name)) {
            myWindow.setTitle("" + object);
        }
        if ("background".equals(name)) {
            if (object instanceof Color) {
                contentPane.setBackground((Color) object);
                myWindow.setBackground((Color) object);
            }
        }
        super.setComponentValue(name, object);

    }

    public void addToContainer(Object c, Object constraints) {
        if (c instanceof MenuBar) {
            MenuBar m = (MenuBar) c;
            m.setPosition(Positionable.ABSOLUTE);
            m.setTop(new Extent(0, Extent.PX));
            m.setWidth(new Extent(100, Extent.PERCENT));
            m.setHeight(new Extent(20, Extent.PX));
            // MEnu geslooopt. kijk ik later naar
            // innerContainer.add(m);
            // contentPane.setTop(new Extent(20, Extent.PX));
        } else {
            Component child = (Component) c;
            contentPane.add(child);
            if (constraints != null && constraints instanceof LayoutData) {
                child.setLayoutData((LayoutData) constraints);
            }

        }
    }

    public void removeFromContainer(Object c) {
        Component cc = (Component) getContainer();
        Component child = (Component) c;
        if (c instanceof MenuBar) {
            // MEnu geslooopt. kijk ik later naar
            // innerContainer.remove((Component) c);

        } else {
            contentPane.remove((Component) c);

        }
    }

    protected Object getComponentValue(String name) {
        if ("title".equals(name)) {
            return myWindow.getTitle();
        }
        return super.getComponentValue(name);
    }

}
