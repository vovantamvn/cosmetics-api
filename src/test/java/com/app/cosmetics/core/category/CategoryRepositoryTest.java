package com.app.cosmetics.core.category;

import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.item.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

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
        Item item = new Item();
        item.setName("Kem dưỡng da Pond");
        item.setDescription("Đây là kem dưỡng da số 1");
        item.setCount(100);
        item.setPrice(50_000);
        item.setPrice(80_000);
        item.setTypes(List.of("30ml", "50ml"));
        item.setExpiry(LocalDate.of(2023, 06, 06));
        item.setCategory(category);

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