package io.github.ardonplay.infopanel.server.common.repositories;

import io.github.ardonplay.infopanel.server.common.entities.PageContentOrder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


@Transactional
public interface PageContentOrderRepository extends JpaRepository<PageContentOrder, Integer> {
    List<PageContentOrder> findAllByPageId(Integer pageId);
    @Transactional
    @Modifying
    @Query("DELETE FROM PageContentOrder e WHERE e.page.id = :pageId AND e.id NOT IN :orders")
    void deleteAllByPageAndPageContentNotInList(Integer pageId, List<Integer> orders);

}