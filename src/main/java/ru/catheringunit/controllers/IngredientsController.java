package ru.catheringunit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.catheringunit.application.PriceCalculator;
import ru.catheringunit.entity.Ingredient;
import ru.catheringunit.dao.IngredientDAO;

@Controller
@RequestMapping("/ingredients")
public class IngredientsController {
    private final IngredientDAO ingredientDAO;
    private final PriceCalculator priceCalculator;

    @Autowired
    public IngredientsController(IngredientDAO ingredientDAO, PriceCalculator priceCalculator){
        this.ingredientDAO = ingredientDAO;
        this.priceCalculator = priceCalculator;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("ingredients", ingredientDAO.getAll());
        return "ingredients/index";
    }

    @PostMapping()
    public String createIngredient(@ModelAttribute("ingredient") Ingredient ingredient){
        ingredient.setPrice(ingredient.getPrice());
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
        ingredient.setPrice(ingredient.getPrice());
        ingredientDAO.update(ingredient);
        return "redirect: /ingredients/" + ingredient.getId() + "/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        ingredientDAO.remove(id);
        return "redirect:/ingredients";
    }

}
