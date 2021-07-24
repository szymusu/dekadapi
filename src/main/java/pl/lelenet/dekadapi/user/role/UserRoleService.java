package pl.lelenet.dekadapi.user.role;

import java.util.List;

public interface UserRoleService {
    void init();
    UserRole find(UserRoleType role);
    List<UserRole> findAll();
}
