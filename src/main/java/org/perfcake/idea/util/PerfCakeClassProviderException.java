package org.perfcake.idea.util;

/**
 * Created by miron on 21.3.2014.
 */
public class PerfCakeClassProviderException extends Exception {

    static final long serialVersionUID = -7034893199713760935L;

    public PerfCakeClassProviderException(String message) {
        super(message);
    }

    public PerfCakeClassProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public PerfCakeClassProviderException(Throwable cause) {
        super(cause);
    }
}
