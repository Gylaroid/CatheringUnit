package ru.catheringunit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.catheringunit.application.PriceCalculator;
import ru.catheringunit.dao.*;
import ru.catheringunit.entity.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/proposals")
public class ProposalsController {
    private final ProposalDAO proposalDAO;
    private final ProposalElementDAO proposalElementDAO;
    private final MenuDAO menuDAO;
    private final RecipeElementDAO recipeElementDAO;
    private final PriceCalculator priceCalculator;

    @Autowired
    public ProposalsController(ProposalDAO proposalDAO,
                               ProposalElementDAO proposalElementDAO,
                               MenuDAO menuDAO,
                               RecipeElementDAO recipeElementDAO,
                               PriceCalculator priceCalculator){
        this.proposalDAO = proposalDAO;
        this.proposalElementDAO = proposalElementDAO;
        this.menuDAO = menuDAO;
        this.recipeElementDAO = recipeElementDAO;
        this.priceCalculator = priceCalculator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("newProposal", new Proposal());
        model.addAttribute("proposals", proposalDAO.getAll());
        return "proposals/index";
    }

    @GetMapping("/{proposalId}")
    public String show(@PathVariable("proposalId") long proposalId, Model model){
        Proposal proposal = proposalDAO.getById(proposalId);
        model.addAttribute("proposal", proposal);

//        Верхняя таблица
        List<ProposalElement> proposalElements = proposalElementDAO.getById(proposalId);
        List<Float> addedPrices = priceCalculator.calculateMenusPrices(proposal);
        List<Menu> addedMenus = new ArrayList<>();
        float proposalSum = priceCalculator.calculateSum(addedPrices);

        for(ProposalElement proposalElement : proposalElements){
            addedMenus.add(menuDAO.getById(proposalElement.getMenuId()));
        }

        model.addAttribute("sum", proposalSum);
        model.addAttribute("addedMenus", addedMenus);
        model.addAttribute("addedPrices", addedPrices);
        model.addAttribute("proposalElements", proposalElements);

        return "proposals/show";
    }

    @PostMapping()
    public String create(@ModelAttribute("proposal") Proposal proposal){
        proposalDAO.add(proposal);
        return "redirect:/proposals";
    }

    @DeleteMapping("/{proposalId}")
    public String delete(@PathVariable("proposalId") long proposalId){
        Proposal proposal = proposalDAO.getById(proposalId);
        proposalDAO.remove(proposal);
        return "redirect:/proposals";
    }
    private float recipePrice(long recipeId){
        List<Ingredient> ingredients = recipeElementDAO.getIngredientsByRecipeId(recipeId);
        List<RecipeElement> elements = recipeElementDAO.getById(recipeId);
        float sum = 0;
        List<Float> prices = new ArrayList();
        for (int i = 0; i < ingredients.size(); i++){
            prices.add(ingredients.get(i).getPrice() * elements.get(i).getWeight());
            sum += prices.get(i);
        }
        return sum;
    }

    @GetMapping("/{proposalId}/edit")
    public String edit(@PathVariable("proposalId") long proposalId, Model model){
//        Форма изменения даты
        Proposal proposal = proposalDAO.getById(proposalId);
        model.addAttribute("proposal", proposal);

//        Верхняя таблица
        List<ProposalElement> proposalElements = proposalElementDAO.getById(proposalId);
        List<Float> addedPrices = priceCalculator.calculateMenusPrices(proposal);
        List<Menu> addedMenus = new ArrayList<>();
        ProposalElement delObject = new ProposalElement();
        float proposalSum = priceCalculator.calculateSum(addedPrices);

        for(ProposalElement proposalElement : proposalElements){
            addedMenus.add(menuDAO.getById(proposalElement.getMenuId()));
        }

        model.addAttribute("sum", proposalSum);
        model.addAttribute("delElement", delObject);
        model.addAttribute("addedMenus", addedMenus);
        model.addAttribute("addedPrices", addedPrices);
        model.addAttribute("proposalElements", proposalElements);

//        Нижняя таблица
        List<Menu> menus = menuDAO.getAll();
        List<Float> prices = priceCalculator.calculateMenusPrices(menus);

        model.addAttribute("menus", menus);
        model.addAttribute("prices", prices);
        model.addAttribute("proposalElement", new ProposalElement());
        return "proposals/edit";
    }

    @PatchMapping("/{proposalId}")
    public String update(@ModelAttribute("proposal") Proposal proposal){
        proposalDAO.update(proposal);
        return "redirect:/proposals/"+ proposal.getId() +"/edit";
    }

    @PostMapping("/{proposalId}/{menuId}")
    public String createMenu(@ModelAttribute("proposalElement") ProposalElement proposalElement,
                               @PathVariable("menuId") long menuId,
                               @PathVariable("proposalId") long proposalId){
        proposalElement.setMenuId(menuId);
        proposalElement.setProposalId(proposalId);
        System.out.println(proposalElement.getCount());
        proposalElementDAO.add(proposalElement);
        return "redirect:/proposals/{proposalId}/edit";
    }

    @DeleteMapping("/{proposalId}/{menuId}")
    public String deleteMenu(@ModelAttribute("delElement") ProposalElement proposalElement){
        proposalElementDAO.remove(proposalElement);
        return "redirect:/proposals/"+ proposalElement.getProposalId() +"/edit";
    }
}
