package com.app.cosmetics.core.category;

import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.item.ItemRepository;
import com.app.cosmetics.core.stock.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    void it_should_delete_category_and_items() {
        // Arrange
        Category category = new Category("Category");
        Item item = new Item(
                "Name",
                "Description",
                "Image",
                15,
                50_000,
                new ArrayList<Stock>(),
                null,
                category
        );

        // Act
        categoryRepository.save(category);
        itemRepository.save(item);

        final Long categoryId = category.getId();
        final Long itemId = item.getId();

        categoryRepository.deleteById(categoryId);

        // Assert
        assertFalse(categoryRepository.existsById(categoryId));
        assertFalse(itemRepository.existsById(itemId));
    }
}