package org.perfcake.idea.oldmodel;

import org.perfcake.model.Header;

/**
 * Created by miron on 30.4.2014.
 */
public class HeaderModel extends AbstractScenarioModel {
    public static final String NAME_PROPERTY = "name";
    public static final String VALUE_PROPERTY = "value";
    public static final String HEADER_PROPERTY = "header";


    private Header header;

    public HeaderModel(Header header) {
        this.header = header;
    }

    /**
     * @return PerfCake Header oldmodel intended for read only.
     */
    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        Header old = this.header;
        this.header = header;
        fireChangeEvent(HEADER_PROPERTY, old, header);
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
