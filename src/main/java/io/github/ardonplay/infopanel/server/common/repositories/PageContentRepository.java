package io.github.ardonplay.infopanel.server.common.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.ardonplay.infopanel.server.common.models.Pair;
import io.github.ardonplay.infopanel.server.common.entities.PageContent;
import io.github.ardonplay.infopanel.server.operations.pageOperations.models.enums.PageElementType;
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

    default boolean containsPair(List<PageContent> entities, Pair<PageElementType, JsonNode> pair) {
        for (PageContent entity : entities) {
            if (entity.getPageElementType().getName().equals(pair.first().name()) && entity.getBody().equals(pair.second())) {
                return true;
            }
        }
        return false;
    }
}
