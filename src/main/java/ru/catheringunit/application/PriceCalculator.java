package ru.catheringunit.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.catheringunit.dao.MenuElementDAO;
import ru.catheringunit.dao.ProposalElementDAO;
import ru.catheringunit.dao.RecipeDAO;
import ru.catheringunit.dao.RecipeElementDAO;
import ru.catheringunit.entity.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class PriceCalculator {
//    private RecipeDAO recipeDAO;
    private RecipeElementDAO recipeElementDAO;
    private MenuElementDAO menuElementDAO;
    private ProposalElementDAO proposalElementDAO;

    @Autowired
    public PriceCalculator(RecipeElementDAO recipeElementDAO,
//                           RecipeDAO recipeDAO,
                           MenuElementDAO menuElementDAO,
                           ProposalElementDAO proposalElementDAO){
//        this.recipeDAO = recipeDAO;
        this.recipeElementDAO = recipeElementDAO;
        this.menuElementDAO = menuElementDAO;
        this.proposalElementDAO = proposalElementDAO;
    }

    public PriceCalculator(){

    }

    public float calculateSum(List<Float> prices){
        float sum = 0;
        for(Float price : prices){
            sum += price;
        }
        return sum;
    }

    public float calculateIngredientPrice(Ingredient ingredient){
       return ingredient.getPrice();
    }

    public List<Float> calculateIngredientsPrices(Recipe recipe){
        List<Ingredient> ingredients = recipeElementDAO.getIngredientsByRecipeId(recipe.getId());
        List<RecipeElement> elements = recipeElementDAO.getById(recipe.getId());
        List<Float> prices = new ArrayList();

        for (int i = 0; i < ingredients.size(); i++){
            prices.add(ingredients.get(i).getPrice() * elements.get(i).getWeight());
        }
        return prices;
    }

    public List<Float> calculateIngredientsPrices(List<Ingredient> ingredients){
        List<Float> prices = new ArrayList();

        for(Ingredient ingredient : ingredients){
            prices.add(calculateIngredientPrice(ingredient));
        }
        return prices;
    }

    public float calculateRecipePrice(Recipe recipe){
        return calculateSum(calculateIngredientsPrices(recipe));
    }

    public List<Float> calculateRecipesPrices(Menu menu){
        List<MenuElement> elements = menuElementDAO.getById(menu.getId());
        List<Float> prices = new ArrayList();

        for(MenuElement element : elements){
            Recipe recipe = new Recipe();
            recipe.setId(element.getRecipeId());
            prices.add(calculateRecipePrice(recipe) * element.getCount());
        }
        return prices;
    }

    public List<Float> calculateRecipesPrices(List<Recipe> recipes){
        List<Float> prices = new ArrayList();

        for(Recipe recipe : recipes){
            prices.add(calculateRecipePrice(recipe));
        }
        return prices;
    }

    public float calculateMenuPrice(Menu menu){
        return calculateSum(calculateRecipesPrices(menu));
    }

    public List<Float> calculateMenusPrices(Proposal proposal){
        List<ProposalElement> proposalElements = proposalElementDAO.getById(proposal.getId());
        List<Float> prices = new ArrayList();

        for(ProposalElement element : proposalElements){
            Menu menu = new Menu();
            menu.setId(element.getMenuId());
            prices.add(calculateMenuPrice(menu) * element.getCount());
        }
        return prices;
    }

    public List<Float> calculateMenusPrices(List<Menu> menus) {
        List<Float> prices = new ArrayList();

        for (Menu menu : menus) {
            prices.add(calculateMenuPrice(menu));
        }
        return prices;
    }

    public float calculateProposalPrice(Proposal proposal){
        return calculateSum(calculateMenusPrices(proposal));
    }
}
