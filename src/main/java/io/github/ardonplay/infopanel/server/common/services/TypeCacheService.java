package io.github.ardonplay.infopanel.server.common.services;

import io.github.ardonplay.infopanel.server.common.entities.PageElementTypeEntity;
import io.github.ardonplay.infopanel.server.common.entities.PageTypeEntity;
import io.github.ardonplay.infopanel.server.common.entities.UserRoleEntity;
import io.github.ardonplay.infopanel.server.common.repositories.PageElementTypeRepository;
import io.github.ardonplay.infopanel.server.common.repositories.PageTypeRepository;
import io.github.ardonplay.infopanel.server.common.repositories.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class TypeCacheService {

    private final PageTypeRepository pageTypeRepository;

    private final UserRoleRepository userRoleRepository;

    private final PageElementTypeRepository pageElementTypeRepository;

    @Cacheable("page_element_types")
    public Map<String, PageElementTypeEntity> getPageElementTypes() {
        Map<String, PageElementTypeEntity> pageElementTypeMap = new HashMap<>();

        pageElementTypeRepository.findAll().forEach(pageElementType -> pageElementTypeMap.put(pageElementType.getName(), pageElementType));

        return pageElementTypeMap;
    }

    @Cacheable("page_types")
    public Map<String, PageTypeEntity> getPageTypes() {
        Map<String, PageTypeEntity> pageTypeMap = new HashMap<>();

        pageTypeRepository.findAll().forEach(pageType -> pageTypeMap.put(pageType.getName(), pageType));

        return pageTypeMap;
    }

    @Cacheable("user_roles")
    public Map<String, UserRoleEntity> getUserRoles() {
        Map<String, UserRoleEntity> userRoleMap = new HashMap<>();

        userRoleRepository.findAll().forEach(userRole -> userRoleMap.put(userRole.getName(), userRole));

        return userRoleMap;
    }

}
