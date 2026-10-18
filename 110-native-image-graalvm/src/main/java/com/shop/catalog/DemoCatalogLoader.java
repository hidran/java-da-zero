package com.shop.catalog;

import java.io.IOException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@ImportRuntimeHints(CatalogRuntimeHints.class)
public class DemoCatalogLoader implements ApplicationRunner {
    static final String RESOURCE = "catalog/demo-products.csv";
    private final ProductRepository repository;

    public DemoCatalogLoader(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) throws IOException {
        String csv = new ClassPathResource(RESOURCE).getContentAsString(UTF_8);
        for (String line : csv.lines().toList()) {
            String[] fields = line.split(",");
            repository.save(new Product(0, fields[0], Integer.parseInt(fields[1])));
        }
    }
}
