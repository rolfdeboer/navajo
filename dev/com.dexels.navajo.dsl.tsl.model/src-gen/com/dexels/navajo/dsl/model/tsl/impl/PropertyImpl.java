/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.tsl.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.dexels.navajo.dsl.model.tsl.ExpressionTag;
import com.dexels.navajo.dsl.model.tsl.PossibleExpression;
import com.dexels.navajo.dsl.model.tsl.Property;
import com.dexels.navajo.dsl.model.tsl.TslPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.PropertyImpl#getExpressionValue <em>Expression Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyImpl extends ElementImpl implements Property {
	/**
	 * The cached value of the '{@link #getExpressionValue() <em>Expression Value</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpressionValue()
	 * @generated
	 * @ordered
	 */
	protected EList<ExpressionTag> expressionValue;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TslPackage.Literals.PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ExpressionTag> getExpressionValue() {
		if (expressionValue == null) {
			expressionValue = new EObjectContainmentEList<ExpressionTag>(ExpressionTag.class, this, TslPackage.PROPERTY__EXPRESSION_VALUE);
		}
		return expressionValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getType() {
		for (PossibleExpression possibleExpression : getAttributes()) {
			if("type".equals(possibleExpression.getKey())) {
				if(possibleExpression.getValue()!=null) {
					return possibleExpression.getValue();
				} else {
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getName() {
		for (PossibleExpression possibleExpression : getAttributes()) {
			if("name".equals(possibleExpression.getKey())) {
				if(possibleExpression.getValue()!=null) {
					return possibleExpression.getValue();
				} else {
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TslPackage.PROPERTY__EXPRESSION_VALUE:
				return ((InternalEList<?>)getExpressionValue()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TslPackage.PROPERTY__EXPRESSION_VALUE:
				return getExpressionValue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TslPackage.PROPERTY__EXPRESSION_VALUE:
				getExpressionValue().clear();
				getExpressionValue().addAll((Collection<? extends ExpressionTag>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TslPackage.PROPERTY__EXPRESSION_VALUE:
				getExpressionValue().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TslPackage.PROPERTY__EXPRESSION_VALUE:
				return expressionValue != null && !expressionValue.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PropertyImpl