import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by miron on 8.1.2014.
 */
public class PerfCakeModuleType extends ModuleType<PerfCakeModuleBuilder>{
    //private static final Icon ADD_PLUGIN_MODULE_ICON = IconLoader.getIcon("/add_plugin_modulewizard.png");
    @NonNls private static final String ID = "PERFCAKE_MODULE";

    public PerfCakeModuleType() {
        super(ID);
    }

    //TODO: Why?
    public static PerfCakeModuleType getInstance() {
        return (PerfCakeModuleType) ModuleTypeManager.getInstance().findByID(ID);
    }

    //TODO: Why?
    public static boolean isOfType(Module module) {
        return get(module) instanceof PerfCakeModuleType;
    }

    @NotNull
    @Override
    public PerfCakeModuleBuilder createModuleBuilder() {
        return new PerfCakeModuleBuilder();
    }

    @Override
    public Icon getNodeIcon(boolean isOpened) {
        return null;
    }

    @NotNull
    @Override
    public String getDescription() {
        return "PerfCake scenario editor";
    }


    //TODO: get name from constants
    @NotNull
    @Override
    public String getName() {
        return "PerfCake Module";
    }

    @Override
    public Icon getBigIcon() {
        return null;
    }
}
