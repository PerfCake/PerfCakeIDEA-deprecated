package org.perfcake.idea.model;

import org.perfcake.model.Header;

/**
 * Created by miron on 30.4.2014.
 */
public class HeaderModel extends AbstractScenarioModel {
    public static final String NAME_PROPERTY = "name";
    public static final String VALUE_PROPERTY = "value";


    private Header header;

    public HeaderModel(Header header) {
        this.header = header;
    }

    public Header getHeader() {
        return header;
    }

    public void setName(String name) {
        String old = header.getName();
        header.setName(name);
        fireChangeEvent(NAME_PROPERTY, old, name);
    }

    public void setValue(String value) {
        String old = header.getValue();
        header.setValue(value);
        fireChangeEvent(VALUE_PROPERTY, old, value);
    }

    @Override
    public String toString() {
        return header.getName() + " : " + header.getValue();
    }
}
