package com.shop.catalog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTests {
    private static final Category PERIPHERALS = new Category(1, "Peripherals");
    private static final Category AUDIO = new Category(3, "Audio");
    private static final List<Product> CATALOG = List.of(
            new Product(1, "Keyboard", "Compact keyboard.", 4900, PERIPHERALS),
            new Product(2, "Mouse", "Wireless mouse.", 2500, PERIPHERALS),
            new Product(3, "Headset", "Over-ear headset.", 7900, AUDIO),
            new Product(4, "Mouse pad", "Cloth pad.", 1500, PERIPHERALS));

    @Mock
    ProductRepository products;

    @Mock
    CategoryRepository categories;

    ProductService service;

    @BeforeEach
    void createService() {
        service = new ProductService(products, categories);
    }

    @Test
    void searchFiltersByNameAndCategory() {
        given(products.findAll()).willReturn(CATALOG);
        ProductPage page = service.search("MOUSE", 1L, ProductSort.ID, 0, 12);
        assertThat(page.content()).extracting(Product::name).containsExactly("Mouse", "Mouse pad");
        assertThat(page.totalElements()).isEqualTo(2);
    }

    @Test
    void searchSortsByPriceAndCutsThePage() {
        given(products.findAll()).willReturn(CATALOG);
        ProductPage page = service.search("", null, ProductSort.PRICE, 1, 3);
        assertThat(page.content()).extracting(Product::name).containsExactly("Headset");
        assertThat(page.totalPages()).isEqualTo(2);
    }

    @Test
    void createStripsTheNameAndSavesWithTheCategory() {
        given(products.existsByName("Webcam")).willReturn(false);
        given(categories.findById(1)).willReturn(Optional.of(PERIPHERALS));
        given(products.save(any())).willAnswer(call -> call.getArgument(0));
        Product created = service.create("  Webcam ", " Full HD. ", 5900, 1);
        assertThat(created.name()).isEqualTo("Webcam");
        assertThat(created.description()).isEqualTo("Full HD.");
        assertThat(created.category()).isEqualTo(PERIPHERALS);
    }

    @Test
    void createRejectsATakenName() {
        given(products.existsByName("Keyboard")).willReturn(true);
        assertThatThrownBy(() -> service.create("Keyboard", "Again.", 4900, 1))
                .isInstanceOf(DuplicateProductNameException.class);
        verify(products, never()).save(any());
    }

    @Test
    void createRejectsAnUnknownCategory() {
        given(products.existsByName("Webcam")).willReturn(false);
        given(categories.findById(9)).willReturn(Optional.empty());
        assertThatThrownBy(() -> service.create("Webcam", "Full HD.", 5900, 9))
                .isInstanceOf(CategoryNotFoundException.class);
    }
}
