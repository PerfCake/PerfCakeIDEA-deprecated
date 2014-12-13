package org.perfcake.idea.model;

import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by miron on 15. 11. 2014.
 */
public interface IProperties extends DomElement {
    /**
     * Returns the list of property children.
     *
     * @return the list of property children.
     */
    @NotNull
    List<Property> getProperties();

    /**
     * Adds new child to the list of property children.
     *
     * @return created child
     */
    Property addProperty();

}
