package ru.catheringunit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.catheringunit.entity.Recipe;
import ru.catheringunit.entity.Ingredient;
import ru.catheringunit.entity.RecipeElement;
import ru.catheringunit.dao.CategoryDAO;
import ru.catheringunit.dao.RecipeDAO;
import ru.catheringunit.dao.IngredientDAO;
import ru.catheringunit.dao.RecipeElementDAO;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipesController {
    private final RecipeElementDAO recipeElementDAO;
    private final RecipeDAO recipeDAO;
    private final CategoryDAO categoryDAO;
    private final IngredientDAO ingredientDAO;

    @Autowired
    RecipesController (CategoryDAO categoryDAO, RecipeElementDAO recipeElementDAO,
                       RecipeDAO recipeDAO, IngredientDAO ingredientDAO){
        this.categoryDAO = categoryDAO;
        this.recipeElementDAO = recipeElementDAO;
        this.recipeDAO = recipeDAO;
        this.ingredientDAO = ingredientDAO;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("recipes", recipeDAO.getAll());
        return "recipes/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        Recipe recipe = recipeDAO.getById(id);
        List<Ingredient> ingredients = recipeElementDAO.getIngredientsByRecipeId(recipe.getId());
        List<RecipeElement> elements = recipeElementDAO.getById(recipe.getId());

        float sum = 0;
        List<Float> prices = new ArrayList();
        for (int i = 0; i < ingredients.size(); i++){
            prices.add(ingredients.get(i).getPrice() * elements.get(i).getWeight());
            sum += prices.get(i);
        }

        model.addAttribute("sumPrice", sum);
        model.addAttribute("prices", prices);
        model.addAttribute("recipe", recipe);
        model.addAttribute("recipeElements", elements);
        model.addAttribute("addedIngredients", ingredients);
        return "recipes/ingredients";
    }

    @GetMapping("/new")
    public String save(Model model){
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("categories", categoryDAO.getAll());
        return "recipes/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("recipe") Recipe recipe){
        recipeDAO.add(recipe);
        return "redirect:/recipes/" + recipe.getId() +"/add";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model){
        model.addAttribute("recipe", recipeDAO.getById(id));
        model.addAttribute("categories", categoryDAO.getAll());
        return "recipes/edit";
    }

    @PatchMapping("/{id}")
    public String update (@ModelAttribute("recipe") Recipe recipe){
        recipeDAO.update(recipe);
        return "redirect:/recipes/" + recipe.getId() + "/add";
    }

    @GetMapping("/{id}/add")
    public String addIngredient(@PathVariable("id") long id, Model model){
        Recipe recipe = recipeDAO.getById(id);
        List<Ingredient> ingredients = recipeElementDAO.getIngredientsByRecipeId(recipe.getId());
        List<RecipeElement> elements = recipeElementDAO.getById(recipe.getId());

        float sum = 0;
        List<Float> prices = new ArrayList();
        for (int i = 0; i < ingredients.size(); i++){
            prices.add(ingredients.get(i).getPrice() * elements.get(i).getWeight());
            sum += prices.get(i);
        }

//        for(int i = 0; i < ingredients.size(); i++){
//            int weight = 0;
//            for(int j = 0; j < elements.size(); j++){
//                if(elements.get(j).getIngredientId() == ingredients.get(i).getId()){
//                    weight = elements.get(j).getWeight();
//                }
//            }
//            if(weight != 0){
//                ingredients.get(i).setPrice(ingredients.get(i).getPrice() * weight);
//            }
//            sum += ingredients.get(i).getPrice();
//        }
        model.addAttribute("prices", prices);
        model.addAttribute("recipeElements", elements);
        model.addAttribute("recipeElement", new RecipeElement());
        model.addAttribute("recipe", recipe);
        model.addAttribute("sumPrice", sum);

        model.addAttribute("addedIngredients", ingredients);
        model.addAttribute("ingredients", ingredientDAO.getAll());
        return "recipes/add";
    }

    @PostMapping("/{id}/{ingredientId}")
    public String createIngredient(@ModelAttribute("recipeElement") RecipeElement recipeElement,
                                   @PathVariable("id") long id,
                                   @PathVariable("ingredientId") long ingredientId){
//        recipeElement.setPrice(recipeElement.getWeight() * ingredientDAO.getById(ingredientId).getPrice());
        recipeElement.setFoodOrDrinkId(id);
        recipeElement.setIngredientId(ingredientId);
        recipeElementDAO.add(recipeElement);
        return "redirect:/recipes/" + id +"/add";
    }

    @DeleteMapping("/{id}/{ingredientId}")
    public String deleteIngredient(@PathVariable("id") long id, @PathVariable("ingredientId") long ingredientId){
        RecipeElement recipeElement = recipeElementDAO.getByIds(id, ingredientId);
        recipeElementDAO.remove(recipeElement);
        return "redirect:/recipes/" + id +"/add";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id){
        Recipe recipe = recipeDAO.getById(id);
        recipeDAO.remove(recipe);
        return "redirect:/recipes";
    }
}
