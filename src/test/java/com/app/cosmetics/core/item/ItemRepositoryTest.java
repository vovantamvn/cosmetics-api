package com.app.cosmetics.core.item;

import com.app.cosmetics.core.branch.Branch;
import com.app.cosmetics.core.branch.BranchRepository;
import com.app.cosmetics.core.category.Category;
import com.app.cosmetics.core.category.CategoryRepository;
import com.app.cosmetics.core.stock.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BranchRepository branchRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void it_should_delete_item_and_do_not_delete_category() {
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

        itemRepository.deleteById(itemId);

        // Assert
        assertFalse(itemRepository.existsById(itemId));
        assertTrue(categoryRepository.existsById(categoryId));
    }

    @Test
    void it_should_delete_item_and_do_not_delete_branch() {
        // Arrange
        Branch branch = new Branch("Branch");
        Item item = new Item(
                "Name",
                "Description",
                "Image",
                15,
                50_000,
                new ArrayList<Stock>(),
                branch,
                null
        );

        // Act
        branchRepository.save(branch);
        itemRepository.save(item);

        final Long branchId = branch.getId();
        final Long itemId = item.getId();

        itemRepository.deleteById(itemId);

        // Assert
        assertFalse(itemRepository.existsById(itemId));
        assertTrue(branchRepository.existsById(branchId));
    }
}