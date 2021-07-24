package pl.lelenet.dekadapi.user.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository roleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        init();
    }

    @Override
    public void init() {
        if (roleRepository.count() > 0) return;

        for (UserRoleType roleType : UserRoleType.values()) {
            UserRole role = new UserRole();
            role.setRole(roleType.name());
            roleRepository.save(role);
        }
        roleRepository.flush();
    }

    @Override
    public UserRole find(UserRoleType role) {
        return roleRepository.findUserRoleByRole(role.name());
    }

    @Override
    public List<UserRole> findAll() {
        return roleRepository.findAll();
    }
}
