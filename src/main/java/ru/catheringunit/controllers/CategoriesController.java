package ru.catheringunit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.catheringunit.entity.Category;
import ru.catheringunit.dao.CategoryDAO;

@Controller
@RequestMapping("/categories")
public class CategoriesController {
    private final CategoryDAO categoryDAO;

    @Autowired
    public CategoriesController(CategoryDAO categoryDAO){
        this.categoryDAO = categoryDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryDAO.getAll());
        return "categories/index";
    }

    @PostMapping()
    public String create(@ModelAttribute("category") Category category){
        categoryDAO.add(category);
        return "redirect:/categories";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id){
        Category category = categoryDAO.getById(id);
        categoryDAO.remove(category);
        return "redirect:/categories";
    }
}
