package ru.catheringunit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.catheringunit.application.PriceCalculator;
import ru.catheringunit.dao.CategoryDAO;
import ru.catheringunit.dao.MenuDAO;
import ru.catheringunit.dao.MenuElementDAO;
import ru.catheringunit.dao.RecipeDAO;
import ru.catheringunit.entity.Category;
import ru.catheringunit.entity.Menu;
import ru.catheringunit.entity.MenuElement;
import ru.catheringunit.entity.Recipe;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/menus")
public class MenusController {
    private MenuElementDAO menuElementDAO;
    private MenuDAO menuDAO;
    private RecipeDAO recipeDAO;
    private CategoryDAO categoryDAO;
    private PriceCalculator priceCalculator;

    public MenusController(){    }

    @Autowired
    public MenusController(MenuElementDAO menuElementDAO,
                           MenuDAO menuDAO,
                           RecipeDAO recipeDAO,
                           CategoryDAO categoryDAO,
                           PriceCalculator priceCalculator){
        this.menuElementDAO = menuElementDAO;
        this.menuDAO = menuDAO;
        this.recipeDAO = recipeDAO;
        this.categoryDAO = categoryDAO;
        this.priceCalculator = priceCalculator;
    }

    private List<Recipe> recipeSorter(List<Recipe> recipes, Category category){
        List<Recipe> sortedRecipes = new ArrayList<>();

        if(category.getId() != 0){
            for(int i = 0; i < recipes.size(); i++){
                if(recipes.get(i).getCategoryId() == category.getId()){
                    sortedRecipes.add(recipes.get(i));
                }
            }
            recipes = sortedRecipes;
        }
        return recipes;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("menu", new Menu());
        model.addAttribute("menus", menuDAO.getAll());
        return "menus/index";
    }

    @GetMapping("/{menuId}")
    public String show(@PathVariable("menuId") long menuId, Model model){
        Menu menu = menuDAO.getById(menuId);
        model.addAttribute("menu", menu);

//        Верхняя таблица
        List<MenuElement> menuElements = menuElementDAO.getById(menuId);
        List<Float> addedPrices = priceCalculator.calculateRecipesPrices(menu);
        float sum = priceCalculator.calculateSum(addedPrices);
        List<Recipe> addedRecipes = new ArrayList<>();

        for(MenuElement menuElement : menuElements){
            addedRecipes.add(recipeDAO.getById(menuElement.getRecipeId()));
        }

        model.addAttribute("menuElements", menuElements);
        model.addAttribute("addedRecipes", addedRecipes);
        model.addAttribute("addedPrices", addedPrices);
        model.addAttribute("sum", sum);

        return "menus/show";
    }

    @PostMapping()
    public String create(@ModelAttribute("menu") Menu menu){
        menuDAO.add(menu);
        return "redirect:/menus";
    }

    @DeleteMapping("/{menuId}")
    public String delete(@PathVariable("menuId") long id){
        Menu menu = menuDAO.getById(id);
        menuDAO.remove(menu);
        return "redirect:/menus";
    }

    @GetMapping("/{menuId}/edit")
    public String edit(@ModelAttribute("category") Category category,
                       @PathVariable("menuId") long id,
                       Model model){
//        Форма изменения названия
        Menu menu = menuDAO.getById(id);
        model.addAttribute("menuE", menu);

//        Категории
        model.addAttribute("categoryE", new Category());
        model.addAttribute("categories", categoryDAO.getAll());

//        Верхняя таблица
        List<MenuElement> menuElements = menuElementDAO.getById(id);
        List<Recipe> addedRecipes = new ArrayList<>();
        List<Float> addedPrices = priceCalculator.calculateRecipesPrices(menu);
        float sum = priceCalculator.calculateSum(addedPrices);
        for(MenuElement element : menuElements){
            addedRecipes.add(recipeDAO.getById(element.getRecipeId()));
        }

        model.addAttribute("menuElements", menuElements);
        model.addAttribute("addedRecipes", addedRecipes);
        model.addAttribute("addedPrices", addedPrices);
        model.addAttribute("sum", sum);
        model.addAttribute("delElement", new MenuElement());

//        Нижняя таблица
        List<Recipe> recipes = recipeSorter(recipeDAO.getAll(), category);
        List<Float> prices = priceCalculator.calculateRecipesPrices(recipes);

        model.addAttribute("recipes", recipes);
        model.addAttribute("prices", prices);
        model.addAttribute("menuElement", new MenuElement());
        return "menus/edit";
    }

    @PatchMapping("/{menuId}")
    public String update(@ModelAttribute("menuE") Menu menu){
        menuDAO.update(menu);
        return "redirect:/menus/{menuId}/edit";
    }

    @PostMapping("/{menuId}/{recipeId}")
    public String createRecipe(@ModelAttribute("menuElemt") MenuElement menuElement,
                               @PathVariable("menuId") long menuId,
                               @PathVariable("recipeId") long recipeId){
        menuElement.setMenuId(menuId);
        menuElement.setRecipeId(recipeId);
        menuElementDAO.add(menuElement);
        return "redirect:/menus/{menuId}/edit";
    }

    @DeleteMapping("/{menuId}/{recipeId}")
    public String deleteRecipe(@ModelAttribute("menuElement") MenuElement menuElement){
        System.out.println(menuElement.toString());
        menuElementDAO.remove(menuElement);
        return "redirect:/menus/{menuId}/edit";
    }
}
