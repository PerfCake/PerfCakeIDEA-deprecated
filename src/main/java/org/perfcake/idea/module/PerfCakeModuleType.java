package org.perfcake.idea.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.Constants;

import javax.swing.*;

/**
 * Created by miron on 8.1.2014.
 */
public class PerfCakeModuleType extends ModuleType<PerfCakeModuleBuilder> {
    @NonNls
    private static final String ID = "PERFCAKE_MODULE";

    public PerfCakeModuleType() {
        super(ID);
    }

    public static PerfCakeModuleType getInstance() {
        return (PerfCakeModuleType) ModuleTypeManager.getInstance().findByID(ID);
    }

    public static boolean isOfType(Module module) {
        return get(module) instanceof PerfCakeModuleType;
    }

    @NotNull
    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull PerfCakeModuleBuilder moduleBuilder, @NotNull ModulesProvider modulesProvider) {
        /*ArrayList<ModuleWizardStep> steps = new ArrayList<ModuleWizardStep>();
        steps.add(new PerfCakeWizardStep(moduleBuilder));
        final ModuleWizardStep[] wizardSteps = steps.toArray(new ModuleWizardStep[steps.size()]);
        return ArrayUtil.mergeArrays(super.createWizardSteps(wizardContext, moduleBuilder, modulesProvider), wizardSteps);*/
        return super.createWizardSteps(wizardContext, moduleBuilder, modulesProvider);
    }

    @NotNull
    @Override
    public PerfCakeModuleBuilder createModuleBuilder() {
        return new PerfCakeModuleBuilder();
    }


    @NotNull
    @Override
    public String getName() {
        return "PerfCake Module";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "PerfCake scenario editor";
    }


    @Override
    public Icon getBigIcon() {
        return Constants.BIG_ICON;
    }

    @Override
    public Icon getNodeIcon(boolean isOpened) {
        return Constants.NODE_ICON;
    }
}
