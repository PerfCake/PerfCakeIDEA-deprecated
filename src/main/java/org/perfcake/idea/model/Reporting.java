// Generated on Fri Oct 10 19:44:44 CEST 2014
// DTD/Schema  :    urn:perfcake:scenario:3.0

package org.perfcake.idea.model;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.Required;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * urn:perfcake:scenario:3.0:reportingElemType interface.
 * @author Miron
 */
public interface Reporting extends DomElement, IProperties {

	/**
	 * Returns the list of reporter children.
	 * @return the list of reporter children.
	 */
	@NotNull
	@Required
	List<Reporter> getReporters();
	/**
	 * Adds new child to the list of reporter children.
	 * @return created child
	 */
	Reporter addReporter();


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
