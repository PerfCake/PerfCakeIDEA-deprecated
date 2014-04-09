package org.perfcake.idea.util;

/**
 * Created by miron on 5.4.2014.
 */
public class PerfCakeIDEAException extends Exception {
    static final long serialVersionUID = -5337236963424329244L;

    public PerfCakeIDEAException(String message) {
        super(message);
    }

    public PerfCakeIDEAException(String message, Throwable cause) {
        super(message, cause);
    }

    public PerfCakeIDEAException(Throwable cause) {
        super(cause);
    }
}
