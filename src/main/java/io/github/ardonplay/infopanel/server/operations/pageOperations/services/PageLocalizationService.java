package io.github.ardonplay.infopanel.server.operations.pageOperations.services;

import io.github.ardonplay.infopanel.server.common.entities.page.PageEntity;


public interface PageLocalizationService {

    PageEntity getLocalization(PageEntity page, String lang);
}
