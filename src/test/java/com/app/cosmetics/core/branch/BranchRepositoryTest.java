package com.app.cosmetics.core.branch;

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
class BranchRepositoryTest {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
        branchRepository.deleteAll();
    }

    @Test
    void it_should_delete_branch_and_items() {
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

        branchRepository.deleteById(branchId);

        // Assert
        assertFalse(branchRepository.existsById(branchId));
        assertFalse(itemRepository.existsById(itemId));
    }
}