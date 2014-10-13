package org.perfcake.idea.module;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.NotNullFunction;
import com.intellij.util.xml.DomFileDescription;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.PerfCakeConst;
import org.perfcake.idea.Constants;
import org.perfcake.idea.model.Scenario;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by miron on 10. 10. 2014.
 */
public class ScenarioDomFileDescription extends DomFileDescription<Scenario> {

    private static final String[] PERFCAKE_NAMESPACES = {"", "urn:perfcake:scenario:3.0", "urn:perfcake:scenario:2.0"};

    public ScenarioDomFileDescription() {
        super(Scenario.class, Scenario.TAG_NAME);
    }

    @Override
    protected void initializeFileDescription() {
        registerNamespacePolicy("PERFCAKE_NAMESPACE", PERFCAKE_NAMESPACES);
    }

    @Override
    public boolean isMyFile(@NotNull XmlFile file, @Nullable Module module) {
        return file.getRootTag().getName().equals(Scenario.TAG_NAME);
    }

    @Nullable
    @Override
    public Icon getFileIcon(@Iconable.IconFlags int flags) {
        return Constants.NODE_ICON;
    }
}
