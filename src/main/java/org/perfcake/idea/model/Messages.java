// Generated on Fri Oct 10 19:44:44 CEST 2014
// DTD/Schema  :    urn:perfcake:scenario:3.0

package org.perfcake.idea.model;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * urn:perfcake:scenario:3.0:messagesElemType interface.
 * @author Miron
 */
public interface Messages extends DomElement {

	/**
	 * Returns the list of message children.
	 * @return the list of message children.
	 */
	@NotNull
	@Required
	List<Message> getMessages();
	/**
	 * Adds new child to the list of message children.
	 * @return created child
	 */
	Message addMessage();


}
