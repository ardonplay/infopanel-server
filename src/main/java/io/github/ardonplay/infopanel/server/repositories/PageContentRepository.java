package io.github.ardonplay.infopanel.server.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.ardonplay.infopanel.server.common.Pair;
import io.github.ardonplay.infopanel.server.models.entities.PageContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PageContentRepository extends JpaRepository<PageContent, Integer> {
    @Query("SELECT e FROM PageContent e WHERE e.pageElementType.name IN :names AND e.body IN :bodies")
    List<PageContent> findPageContentsByPageElementTypeAndBody(
            @Param("names") List<String> names,
            @Param("bodies") List<JsonNode> bodies
    );

    default boolean containsPair(List<PageContent> entities, Pair<String, JsonNode> pair) {
        for (PageContent entity : entities) {
            if (entity.getPageElementType().getName().equals(pair.first()) && entity.getBody().equals(pair.second())) {
                return true;
            }
        }
        return false;
    }
}
