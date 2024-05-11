package com.example.backend.bootstrap;

import com.example.backend.entities.Role;
import com.example.backend.entities.RoleEnum;
import com.example.backend.entities.User;
import com.example.backend.repository.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles(){
        RoleEnum[] roleNames = new RoleEnum[] {RoleEnum.ADMIN,RoleEnum.USER, RoleEnum.SUPER_ADMIN};
        Map<RoleEnum,String> roleDescriptionMap = Map.of(
                RoleEnum.USER, "Default user role",
                RoleEnum.ADMIN, "Administrator role",
                RoleEnum.SUPER_ADMIN, "Super Administrator role"
        );
        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out:: println,() -> {
                Role roleToCreate = new Role();

                roleToCreate.setName(roleName)
                        .setDescription(roleDescriptionMap.get(roleName));

                        roleRepository.save(roleToCreate);
            });
        });
    }

}
