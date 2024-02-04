package io.github.ardonplay.infopanel.server.operations.pageOperations.services.mappers;


public interface EntityMapper<Entity, Domain> {

    Domain  toDto(Entity entity);

    Entity toEntity(Domain domain);
}
