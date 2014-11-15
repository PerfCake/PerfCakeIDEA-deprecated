// Generated on Fri Oct 10 19:44:44 CEST 2014
// DTD/Schema  :    urn:perfcake:scenario:3.0

package org.perfcake.idea.model;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.SubTagList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * urn:perfcake:scenario:3.0:messageElemType interface.
 * @author Miron
 */
public interface Message extends DomElement, IProperties {

	/**
	 * Returns the value of the uri child.
	 * @return the value of the uri child.
	 */
	@NotNull
	GenericAttributeValue<String> getUri();


	/**
	 * Returns the value of the content child.
	 * @return the value of the content child.
	 */
	@NotNull
	GenericAttributeValue<String> getContent();


	/**
	 * Returns the value of the multiplicity child.
	 * @return the value of the multiplicity child.
	 */
	@NotNull
	GenericAttributeValue<String> getMultiplicity();


	/**
	 * Returns the list of header children.
	 * @return the list of header children.
	 */
	@NotNull
	List<Header> getHeaders();
	/**
	 * Adds new child to the list of header children.
	 * @return created child
	 */
	Header addHeader();


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
	 * Returns the list of validatorRef children.
	 * @return the list of validatorRef children.
	 */
	@NotNull
	@SubTagList ("validatorRef")
	List<ValidatorRef> getValidatorRefs();
	/**
	 * Adds new child to the list of validatorRef children.
	 * @return created child
	 */
	@SubTagList ("validatorRef")
	ValidatorRef addValidatorRef();


}
