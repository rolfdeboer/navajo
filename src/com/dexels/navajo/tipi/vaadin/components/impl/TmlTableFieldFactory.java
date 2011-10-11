package com.dexels.navajo.tipi.vaadin.components.impl;

import com.dexels.navajo.tipi.vaadin.document.CompositeArrayContainer;
import com.dexels.navajo.tipi.vaadin.document.SelectedItemValuePropertyBridge;
import com.dexels.navajo.tipi.vaadin.document.SelectionBridge;
import com.dexels.navajo.tipi.vaadin.document.SelectionListBridge;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Select;

public class TmlTableFieldFactory extends DefaultFieldFactory {
	private static final long serialVersionUID = -7394569632662794450L;

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field createdField = super.createField(item, propertyId, uiContext);
		createdField.setCaption("");
		return createdField;
	}

	@Override
	public Field createField(Container container, Object itemId,
			Object propertyId, Component uiContext) {
		Item message = container.getItem(itemId);
		MessageTable mt = (MessageTable)uiContext;
		CompositeArrayContainer cac = (CompositeArrayContainer)container;
		Integer size = cac.getSizeForColumn((String) propertyId);
		Property property = message.getItemProperty(propertyId);
		if(property instanceof SelectedItemValuePropertyBridge) {
			SelectedItemValuePropertyBridge cmb = (SelectedItemValuePropertyBridge)property;
			return createDropdownBox(cmb);
		}
//		ShirtNumber
		Field createdField = super.createField(container, itemId, propertyId, uiContext);
		createdField.setCaption("");
		if(size!=null) {
			System.err.println("Setting size: "+size);
			createdField.setWidth(mt.getColumnWidth(propertyId),Sizeable.UNITS_PIXELS);
			;
			
	}
		return createdField;
	}

	private Field createDropdownBox(SelectedItemValuePropertyBridge cmb) {
		SelectionListBridge slb = cmb.createListBridge();
		SelectionBridge found = null;
		for (Object ii : slb.getItemIds()) {
			SelectionBridge iii = (SelectionBridge) slb.getItem(ii);
			if(iii.isSelectedBool()) {
				found = iii;
			}
		}
		final AbstractSelect select = new NativeSelect("Omm", slb);
		select.setImmediate(true);
		select.setPropertyDataSource(cmb);
//		cmb.setValue(slb.getSource().getSelected());
		if(found!=null) {
			System.err.println("SELECTED!");
			select.select(found);

		}
		select.addListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = -4325015230483421707L;

			@Override
			public void valueChange(ValueChangeEvent event) {
//				Selection property = (Selection) select.getValue();
//				System.err.println("class: "+property.getClass()+" val: "+property);
//				System.err.println("Selection changed: "+property);
//				SelectedItemValuePropertyBridge svpb = (SelectedItemValuePropertyBridge) property;
//				Selection ss = (Selection) svpb.getValue();
//				System.err.println("Sel: "+ss);
			}
			

		});
		return select;
	}

	
}
