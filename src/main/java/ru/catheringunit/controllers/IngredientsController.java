package ru.catheringunit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.catheringunit.entity.Ingredient;
import ru.catheringunit.dao.IngredientDAO;

@Controller
@RequestMapping("/ingredients")
public class IngredientsController {
    private final IngredientDAO ingredientDAO;

    @Autowired
    public IngredientsController(IngredientDAO ingredientDAO){
        this.ingredientDAO = ingredientDAO;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("ingredients", ingredientDAO.getAll());
        return "ingredients/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long ingredientId, Model model){
        model.addAttribute("ingredient", ingredientDAO.getById(ingredientId));
        return "ingredients/show";
    }

    @GetMapping("/new")
    public String newIngredient(@ModelAttribute("ingredient") Ingredient ingredient){

        return "ingredients/new";
    }

    @PostMapping(path="", produces = {"application/x-www-form-urlencoded; charset=UTF-8"})
    public String createIngredient(@ModelAttribute("ingredient") Ingredient ingredient){
//        System.out.println(ingredien.getName());
        ingredientDAO.add(ingredient);
        return "redirect:/ingredients";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("ingredient", ingredientDAO.getById(id));
        return "ingredients/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("ingredient") Ingredient ingredient){
        ingredientDAO.update(ingredient);
        return "redirect: /ingredients/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        ingredientDAO.remove(id);
        return "redirect:/ingredients";
    }

}
