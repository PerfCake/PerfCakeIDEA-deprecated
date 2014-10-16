// Generated on Fri Oct 10 19:44:44 CEST 2014
// DTD/Schema  :    urn:perfcake:scenario:3.0

package org.perfcake.idea.model;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

/**
 * urn:perfcake:scenario:3.0:periodElemType interface.
 * @author Miron
 */
public interface Period extends DomElement {

	/**
	 * Returns the value of the type child.
	 * @return the value of the type child.
	 */
	@NotNull
	GenericAttributeValue<String> getType();


	/**
	 * Returns the value of the value child.
	 * @return the value of the value child.
	 */
	@NotNull
	GenericAttributeValue<String> getValue();


}
