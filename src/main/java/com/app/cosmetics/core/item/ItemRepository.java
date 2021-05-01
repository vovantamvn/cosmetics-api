package com.app.cosmetics.core.item;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ItemRepository extends CrudRepository<Item, Long> {
}
