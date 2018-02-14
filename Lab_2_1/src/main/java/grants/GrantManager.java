package grants;

import java.util.List;

public class GrantManager {
    public static boolean canReadTheField(Role role, String objId, String attrName){
        return false;
    }

    public static boolean canWriteTheField(Role role, String objId, String attrName){
        return false;
    }

    public static boolean canReadTheObject(Role role, String objId){
        return false;
    }

    public static boolean canWriteTheObject(Role role, String objId){
        return false;
    }
    
}
