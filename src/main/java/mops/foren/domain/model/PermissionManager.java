package mops.foren.domain.model;

import java.util.List;

public class PermissionManager {

    private List<ForumId> student;
    private List<ForumId> moderator;
    private List<ForumId> admin;

    public boolean checkRight(ForumId forumId, Permission permission){
        if (StudentRights.hasPermission(permission)){
            return student.contains(forumId);
        }
        if (ModeratorRights.hasPermission(permission)){
            return moderator.contains(forumId);
        }
        if (AdminRights.hasPermission(permission)){
            return admin.contains(forumId);
        }
        return false;

    }



}
