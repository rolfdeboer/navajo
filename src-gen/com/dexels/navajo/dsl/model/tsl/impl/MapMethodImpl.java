/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.tsl.impl;

import com.dexels.navajo.dsl.model.tsl.MapMethod;
import com.dexels.navajo.dsl.model.tsl.TslPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Map Method</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.MapMethodImpl#getMapName <em>Map Name</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.MapMethodImpl#getMethodClosingName <em>Method Closing Name</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.MapMethodImpl#getMethodName <em>Method Name</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.MapMethodImpl#getMethodClosingMethod <em>Method Closing Method</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MapMethodImpl extends ElementImpl implements MapMethod {
	/**
	 * The default value of the '{@link #getMapName() <em>Map Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapName()
	 * @generated
	 * @ordered
	 */
	protected static final String MAP_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMapName() <em>Map Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapName()
	 * @generated
	 * @ordered
	 */
	protected String mapName = MAP_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getMethodClosingName() <em>Method Closing Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethodClosingName()
	 * @generated
	 * @ordered
	 */
	protected static final String METHOD_CLOSING_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMethodClosingName() <em>Method Closing Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethodClosingName()
	 * @generated
	 * @ordered
	 */
	protected String methodClosingName = METHOD_CLOSING_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getMethodName() <em>Method Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethodName()
	 * @generated
	 * @ordered
	 */
	protected static final String METHOD_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMethodName() <em>Method Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethodName()
	 * @generated
	 * @ordered
	 */
	protected String methodName = METHOD_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getMethodClosingMethod() <em>Method Closing Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethodClosingMethod()
	 * @generated
	 * @ordered
	 */
	protected static final String METHOD_CLOSING_METHOD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMethodClosingMethod() <em>Method Closing Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethodClosingMethod()
	 * @generated
	 * @ordered
	 */
	protected String methodClosingMethod = METHOD_CLOSING_METHOD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MapMethodImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TslPackage.Literals.MAP_METHOD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMapName() {
		return mapName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapName(String newMapName) {
		String oldMapName = mapName;
		mapName = newMapName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TslPackage.MAP_METHOD__MAP_NAME, oldMapName, mapName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMethodClosingName() {
		return methodClosingName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMethodClosingName(String newMethodClosingName) {
		String oldMethodClosingName = methodClosingName;
		methodClosingName = newMethodClosingName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TslPackage.MAP_METHOD__METHOD_CLOSING_NAME, oldMethodClosingName, methodClosingName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMethodName(String newMethodName) {
		String oldMethodName = methodName;
		methodName = newMethodName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TslPackage.MAP_METHOD__METHOD_NAME, oldMethodName, methodName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMethodClosingMethod() {
		return methodClosingMethod;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMethodClosingMethod(String newMethodClosingMethod) {
		String oldMethodClosingMethod = methodClosingMethod;
		methodClosingMethod = newMethodClosingMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TslPackage.MAP_METHOD__METHOD_CLOSING_METHOD, oldMethodClosingMethod, methodClosingMethod));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TslPackage.MAP_METHOD__MAP_NAME:
				return getMapName();
			case TslPackage.MAP_METHOD__METHOD_CLOSING_NAME:
				return getMethodClosingName();
			case TslPackage.MAP_METHOD__METHOD_NAME:
				return getMethodName();
			case TslPackage.MAP_METHOD__METHOD_CLOSING_METHOD:
				return getMethodClosingMethod();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TslPackage.MAP_METHOD__MAP_NAME:
				setMapName((String)newValue);
				return;
			case TslPackage.MAP_METHOD__METHOD_CLOSING_NAME:
				setMethodClosingName((String)newValue);
				return;
			case TslPackage.MAP_METHOD__METHOD_NAME:
				setMethodName((String)newValue);
				return;
			case TslPackage.MAP_METHOD__METHOD_CLOSING_METHOD:
				setMethodClosingMethod((String)newValue);
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
			case TslPackage.MAP_METHOD__MAP_NAME:
				setMapName(MAP_NAME_EDEFAULT);
				return;
			case TslPackage.MAP_METHOD__METHOD_CLOSING_NAME:
				setMethodClosingName(METHOD_CLOSING_NAME_EDEFAULT);
				return;
			case TslPackage.MAP_METHOD__METHOD_NAME:
				setMethodName(METHOD_NAME_EDEFAULT);
				return;
			case TslPackage.MAP_METHOD__METHOD_CLOSING_METHOD:
				setMethodClosingMethod(METHOD_CLOSING_METHOD_EDEFAULT);
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
			case TslPackage.MAP_METHOD__MAP_NAME:
				return MAP_NAME_EDEFAULT == null ? mapName != null : !MAP_NAME_EDEFAULT.equals(mapName);
			case TslPackage.MAP_METHOD__METHOD_CLOSING_NAME:
				return METHOD_CLOSING_NAME_EDEFAULT == null ? methodClosingName != null : !METHOD_CLOSING_NAME_EDEFAULT.equals(methodClosingName);
			case TslPackage.MAP_METHOD__METHOD_NAME:
				return METHOD_NAME_EDEFAULT == null ? methodName != null : !METHOD_NAME_EDEFAULT.equals(methodName);
			case TslPackage.MAP_METHOD__METHOD_CLOSING_METHOD:
				return METHOD_CLOSING_METHOD_EDEFAULT == null ? methodClosingMethod != null : !METHOD_CLOSING_METHOD_EDEFAULT.equals(methodClosingMethod);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (mapName: ");
		result.append(mapName);
		result.append(", methodClosingName: ");
		result.append(methodClosingName);
		result.append(", methodName: ");
		result.append(methodName);
		result.append(", methodClosingMethod: ");
		result.append(methodClosingMethod);
		result.append(')');
		return result.toString();
	}

} //MapMethodImpl
