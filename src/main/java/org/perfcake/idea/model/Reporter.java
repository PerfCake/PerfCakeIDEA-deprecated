// Generated on Fri Oct 10 19:44:44 CEST 2014
// DTD/Schema  :    urn:perfcake:scenario:3.0

package org.perfcake.idea.model;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * urn:perfcake:scenario:3.0:reporterElemType interface.
 * @author Miron
 */
public interface Reporter extends DomElement {

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


	/**
	 * Returns the list of destination children.
	 * @return the list of destination children.
	 */
	@NotNull
	List<Destination> getDestinations();
	/**
	 * Adds new child to the list of destination children.
	 * @return created child
	 */
	Destination addDestination();


}
