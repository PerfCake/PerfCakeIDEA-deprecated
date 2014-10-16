// Generated on Fri Oct 10 19:44:44 CEST 2014
// DTD/Schema  :    urn:perfcake:scenario:3.0

package org.perfcake.idea.model;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * urn:perfcake:scenario:3.0:validationElemType interface.
 * @author Miron
 */
public interface Validation extends DomElement {

	/**
	 * Returns the value of the enabled child.
	 * @return the value of the enabled child.
	 */
	@NotNull
	GenericAttributeValue<Boolean> getEnabled();


	/**
	 * Returns the value of the fastForward child.
	 * @return the value of the fastForward child.
	 */
	@NotNull
	GenericAttributeValue<Boolean> getFastForward();


	/**
	 * Returns the list of validator children.
	 * @return the list of validator children.
	 */
	@NotNull
	List<Validator> getValidators();
	/**
	 * Adds new child to the list of validator children.
	 * @return created child
	 */
	Validator addValidator();


}
