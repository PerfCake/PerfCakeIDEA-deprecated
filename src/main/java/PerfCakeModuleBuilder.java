import com.intellij.ide.util.projectWizard.JavaModuleBuilder;
import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.ModuleType;

/**
 * Created by miron on 21.1.2014.
 */
public class PerfCakeModuleBuilder extends JavaModuleBuilder {

    public ModuleType getModuleType(){
        return PerfCakeModuleType.getInstance();
    }

    @Override
    public String getGroupName() {


        return JavaModuleType.JAVA_GROUP;
    }
}