package org.perfcake.idea.module;

import com.intellij.openapi.module.Module;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomFileDescription;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.model.Scenario;

/**
 * Created by miron on 10. 10. 2014.
 */
public class ScenarioDomFileDescription extends DomFileDescription<Scenario> {

    public ScenarioDomFileDescription(Class<Scenario> rootElementClass, @NonNls String rootTagName, @NonNls String... allPossibleRootTagNamespaces) {
        super(Scenario.class, "scenario", "", "urn:perfcake:scenario:3.0", "urn:perfcake:scenario:2.0");
    }

    @Override
    public boolean isMyFile(@NotNull XmlFile file, @Nullable Module module) {
        System.out.println();
        return file.getRootTag().getName().equals("scenario");
    }
}
