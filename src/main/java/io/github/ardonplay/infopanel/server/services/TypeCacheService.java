package io.github.ardonplay.infopanel.server.services;

import io.github.ardonplay.infopanel.server.models.entities.PageElementType;
import io.github.ardonplay.infopanel.server.models.entities.PageType;
import io.github.ardonplay.infopanel.server.models.entities.UserRole;
import io.github.ardonplay.infopanel.server.repositories.PageElementTypeRepository;
import io.github.ardonplay.infopanel.server.repositories.PageTypeRepository;
import io.github.ardonplay.infopanel.server.repositories.UserRoleRepository;
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
    public Map<String, PageElementType> getPageElementTypes() {
        Map<String, PageElementType> pageElementTypeMap = new HashMap<>();

        pageElementTypeRepository.findAll().forEach(pageElementType -> pageElementTypeMap.put(pageElementType.getName(), pageElementType));

        return pageElementTypeMap;
    }

    @Cacheable("page_types")
    public Map<String, PageType> getPageTypes() {
        Map<String, PageType> pageTypeMap = new HashMap<>();

        pageTypeRepository.findAll().forEach(pageType -> pageTypeMap.put(pageType.getName(), pageType));

        return pageTypeMap;
    }

    @Cacheable("user_roles")
    public Map<String, UserRole> getUserRoles() {
        Map<String, UserRole> userRoleMap = new HashMap<>();

        userRoleRepository.findAll().forEach(userRole -> userRoleMap.put(userRole.getName(), userRole));

        return userRoleMap;
    }

}