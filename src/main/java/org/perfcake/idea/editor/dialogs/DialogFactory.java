package org.perfcake.idea.editor.dialogs;

import com.intellij.util.xml.DomElement;
import org.perfcake.idea.model.*;

import java.awt.*;

/**
 * Created by miron on 1. 12. 2014.
 */
public class DialogFactory {

    public static MyDialogWrapper createDialogForElement(DomElement domElement, Component parent, Mode mode) {
        if (domElement instanceof Generator) {
            return new GeneratorDialog(parent, (Generator) domElement);
        }
        if (domElement instanceof Property) {
            return new PropertyDialog(parent, (Property) domElement, mode);
        }
        if (domElement instanceof Properties) {
            return new PropertiesDialog(parent, (Properties) domElement);
        }
        if (domElement instanceof Validation) {
            return new ValidationDialog(parent, (Validation) domElement);
        }
        if (domElement instanceof Validator) {
            return new ValidatorDialog(parent, (Validator) domElement, mode);
        }
        if (domElement instanceof Header) {
            return new HeaderDialog(parent, (Header) domElement, mode);
        }
        if (domElement instanceof Sender) {
            return new SenderDialog(parent, (Sender) domElement);
        }
        if (domElement instanceof Reporting) {
            return new ReportingDialog(parent, (Reporting) domElement);
        }
        if (domElement instanceof Reporter) {
            return new ReporterDialog(parent, (Reporter) domElement, mode);
        }
        if (domElement instanceof Destination) {
            return new DestinationDialog(parent, (Destination) domElement, mode);
        }
        if (domElement instanceof Period) {
            return new PeriodDialog(parent, (Period) domElement, mode);
        }
        return null;
    }
}
