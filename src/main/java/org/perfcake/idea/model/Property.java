// Generated on Fri Oct 10 19:44:44 CEST 2014
// DTD/Schema  :    urn:perfcake:scenario:3.0

package org.perfcake.idea.model;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

/**
 * urn:perfcake:scenario:3.0:propertyElemType interface.
 * @author Miron
 */
public interface Property extends DomElement {

	/**
	 * Returns the value of the name child.
	 * @return the value of the name child.
	 */
	@NotNull
    @Attribute("name")
    @SubTag
	GenericAttributeValue<String> getName();


	/**
	 * Returns the value of the value child.
	 * @return the value of the value child.
	 */
	@NotNull
    @Attribute("value")
    @SubTag
	GenericAttributeValue<String> getValue();


}
