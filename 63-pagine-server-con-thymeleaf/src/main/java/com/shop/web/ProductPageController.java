package com.shop.web;

import com.shop.catalog.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductPageController {
    private final ProductService service;

    public ProductPageController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products",
                service.list().stream().map(ProductResponse::from).toList());
        return "products/list";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("product", ProductForm.empty());
        return "products/form";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("product") ProductForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "products/form";
        }
        service.create(form.name(), form.priceInCents());
        return "redirect:/products";
    }
}
