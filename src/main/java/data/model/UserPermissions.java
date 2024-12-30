package data.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UserPermissions {
    // Define permissions as constants
    public static final String VIEW_JOBS = "VIEW_JOBS";
    public static final String APPLY_JOBS = "APPLY_JOBS";
    public static final String MANAGE_JOBS = "MANAGE_JOBS";
    public static final String VIEW_STATISTICS = "VIEW_STATISTICS";
    public static final String MANAGE_USERS = "MANAGE_USERS";
    public static final String MANAGE_ADMINS = "MANAGE_ADMINS";
    public static final String MANAGE_SYSTEM = "MANAGE_SYSTEM";

    // Define permission sets for each user type
    private static final Set<String> CLIENT_PERMISSIONS = Collections.unmodifiableSet(
        new HashSet<>(Arrays.asList(
            VIEW_JOBS,
            APPLY_JOBS
        ))
    );

    private static final Set<String> ADMIN_PERMISSIONS = Collections.unmodifiableSet(
        new HashSet<>(Arrays.asList(
            VIEW_JOBS,
            APPLY_JOBS,
            MANAGE_JOBS,
            VIEW_STATISTICS,
            MANAGE_USERS
        ))
    );

    private static final Set<String> SUPERADMIN_PERMISSIONS = Collections.unmodifiableSet(
        new HashSet<>(Arrays.asList(
            VIEW_JOBS,
            APPLY_JOBS,
            MANAGE_JOBS,
            VIEW_STATISTICS,
            MANAGE_USERS,
            MANAGE_ADMINS,
            MANAGE_SYSTEM
        ))
    );

    // Get permissions for a specific user type
    public static Set<String> getPermissionsForUserType(UserTypes userType) {
        switch (userType) {
            case CLIENT:
                return CLIENT_PERMISSIONS;
            case ADMIN:
                return ADMIN_PERMISSIONS;
            default:
                return Collections.emptySet();
        }
    }

    // Check if a user type has a specific permission
    public static boolean hasPermission(UserTypes userType, String permission) {
        return getPermissionsForUserType(userType).contains(permission);
    }
}
