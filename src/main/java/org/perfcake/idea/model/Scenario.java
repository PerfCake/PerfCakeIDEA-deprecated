// Generated on Fri Oct 10 19:44:44 CEST 2014
// DTD/Schema  :    urn:perfcake:scenario:3.0

package org.perfcake.idea.model;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

/**
 * urn:perfcake:scenario:3.0:scenarioElemType interface.
 * @author Miron
 */
public interface Scenario extends DomElement {

    public static final String TAG_NAME = "scenario";
	/**
	 * Returns the value of the properties child.
	 * @return the value of the properties child.
	 */
	@NotNull
	Properties getProperties();


	/**
	 * Returns the value of the generator child.
	 * @return the value of the generator child.
	 */
	@NotNull
	@Required
	Generator getGenerator();


	/**
	 * Returns the value of the sender child.
	 * @return the value of the sender child.
	 */
	@NotNull
	@Required
	Sender getSender();


	/**
	 * Returns the value of the reporting child.
	 * @return the value of the reporting child.
	 */
	@NotNull
	Reporting getReporting();


	/**
	 * Returns the value of the messages child.
	 * @return the value of the messages child.
	 */
	@NotNull
	Messages getMessages();


	/**
	 * Returns the value of the validation child.
	 * @return the value of the validation child.
	 */
	@NotNull
	Validation getValidation();


}
