// Generated on Fri Oct 10 19:44:44 CEST 2014
// DTD/Schema  :    urn:perfcake:scenario:3.0

package org.perfcake.idea.model;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * urn:perfcake:scenario:3.0:destinationElemType interface.
 * @author Miron
 */
public interface Destination extends DomElement, IProperties {

	/**
	 * Returns the value of the class child.
	 * @return the value of the class child.
	 */
	@NotNull
	@Attribute ("class")
	GenericAttributeValue<String> getClazz();


	/**
	 * Returns the value of the enabled child.
	 * @return the value of the enabled child.
	 */
	@NotNull
	GenericAttributeValue<Boolean> getEnabled();


	/**
	 * Returns the list of period children.
	 * @return the list of period children.
	 */
	@NotNull
	List<Period> getPeriods();
	/**
	 * Adds new child to the list of period children.
	 * @return created child
	 */
	Period addPeriod();


	/**
	 * Returns the list of property children.
	 * @return the list of property children.
	 */
	@NotNull
	List<Property> getProperties();
	/**
	 * Adds new child to the list of property children.
	 * @return created child
	 */
	Property addProperty();


}
