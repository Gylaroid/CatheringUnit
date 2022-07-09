package ru.catheringunit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.catheringunit.application.PriceCalculator;
import ru.catheringunit.entity.Recipe;
import ru.catheringunit.entity.Ingredient;
import ru.catheringunit.entity.RecipeElement;
import ru.catheringunit.dao.CategoryDAO;
import ru.catheringunit.dao.RecipeDAO;
import ru.catheringunit.dao.IngredientDAO;
import ru.catheringunit.dao.RecipeElementDAO;

import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipesController {
    private final RecipeElementDAO recipeElementDAO;
    private final RecipeDAO recipeDAO;
    private final CategoryDAO categoryDAO;
    private final IngredientDAO ingredientDAO;
    private final PriceCalculator priceCalculator;

    @Autowired
    RecipesController (CategoryDAO categoryDAO,
                       RecipeElementDAO recipeElementDAO,
                       RecipeDAO recipeDAO,
                       IngredientDAO ingredientDAO,
                       PriceCalculator priceCalculator){
        this.categoryDAO = categoryDAO;
        this.recipeElementDAO = recipeElementDAO;
        this.recipeDAO = recipeDAO;
        this.ingredientDAO = ingredientDAO;
        this.priceCalculator = priceCalculator;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("categories", categoryDAO.getAll());
        model.addAttribute("recipes", recipeDAO.getAll());
        return "recipes/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        Recipe recipe = recipeDAO.getById(id);
        List<Ingredient> ingredients = recipeElementDAO.getIngredientsByRecipeId(recipe.getId());
        List<RecipeElement> elements = recipeElementDAO.getById(recipe.getId());
        List<Float> prices = priceCalculator.calculateIngredientsPrices(recipe);
        float sum = priceCalculator.calculateSum(prices);

        model.addAttribute("sumPrice", sum);
        model.addAttribute("prices", prices);
        model.addAttribute("recipe", recipe);
        model.addAttribute("recipeElements", elements);
        model.addAttribute("addedIngredients", ingredients);
        return "recipes/show";
    }

    @PostMapping()
    public String create(@ModelAttribute("recipe") Recipe recipe){
        recipeDAO.add(recipe);
        return "redirect:/recipes";
    }

//    @GetMapping("/{id}/edit_depricated")
//    public String edit(@PathVariable("id") long id, Model model){
//        model.addAttribute("recipe", recipeDAO.getById(id));
//        model.addAttribute("categories", categoryDAO.getAll());
//        return "recipes/edit";
//    }

    @PatchMapping("/{id}")
    public String update (@ModelAttribute("recipe") Recipe recipe){
        recipeDAO.update(recipe);
        return "redirect:/recipes/" + recipe.getId() + "/edit";
    }

    @GetMapping("/{id}/edit")
    public String addIngredient(@PathVariable("id") long id, Model model){
        Recipe recipe = recipeDAO.getById(id);
        List<Ingredient> ingredients = recipeElementDAO.getIngredientsByRecipeId(recipe.getId());
        List<RecipeElement> elements = recipeElementDAO.getById(recipe.getId());
        List<Float> prices = priceCalculator.calculateIngredientsPrices(recipe);
        float sum = priceCalculator.calculateSum(prices);

        model.addAttribute("prices", prices);
        model.addAttribute("recipeElements", elements);
        model.addAttribute("recipeElement", new RecipeElement());
        model.addAttribute("recipe", recipe);
        model.addAttribute("categories", categoryDAO.getAll());
        model.addAttribute("sumPrice", sum);
        model.addAttribute("addedIngredients", ingredients);
        model.addAttribute("ingredients", ingredientDAO.getAll());
        return "recipes/edit";
    }

    @PostMapping("/{id}/{ingredientId}")
    public String createIngredient(@ModelAttribute("recipeElement") RecipeElement recipeElement,
                                   @PathVariable("id") long id,
                                   @PathVariable("ingredientId") long ingredientId){
        recipeElement.setFoodOrDrinkId(id);
        recipeElement.setIngredientId(ingredientId);
        recipeElementDAO.add(recipeElement);
        return "redirect:/recipes/" + id +"/edit";
    }

    @DeleteMapping("/{id}/{ingredientId}")
    public String deleteIngredient(@PathVariable("id") long id, @PathVariable("ingredientId") long ingredientId){
        RecipeElement recipeElement = recipeElementDAO.getByIds(id, ingredientId);
        recipeElementDAO.remove(recipeElement);
        return "redirect:/recipes/" + id +"/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id){
        Recipe recipe = recipeDAO.getById(id);
        recipeDAO.remove(recipe);
        return "redirect:/recipes";
    }
}
