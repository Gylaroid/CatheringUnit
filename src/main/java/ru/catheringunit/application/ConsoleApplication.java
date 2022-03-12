package ru.catheringunit.application;

import ru.catheringunit.entity.MenuElement;
import ru.catheringunit.entity.TableMeta;
import ru.catheringunit.service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleApplication {
    ConsoleApplication(){
        List<TableMeta> tablesMeta;
        Scanner in = new Scanner(System.in);

        System.out.println("Выберите таблицу: ");
        tablesMeta = new DBWorker().getTablesMeta();

        for(int i = 0; i < tablesMeta.size(); i++){
            StringBuilder tablePrint = new StringBuilder();
            tablePrint.append(i);
            tablePrint.append(") ");
            tablePrint.append(tablesMeta.get(i).getTableName());
            System.out.println(tablePrint);
        }

        int tableIndex = in.nextInt();

        System.out.println("Выберите поле");
        for(int i = 0; i < tablesMeta.get(tableIndex).getTableFields().size(); i++){
            StringBuilder fieldPrint = new StringBuilder();
            fieldPrint.append(i);
            fieldPrint.append(") ");
            fieldPrint.append(tablesMeta.get(tableIndex).getTableFields().get(i).getFieldName());
            fieldPrint.append(" : [");
            fieldPrint.append(tablesMeta.get(tableIndex).getTableFields().get(i).getFieldType());
            fieldPrint.append("]");
            System.out.println(fieldPrint);
        }

        int fieldIndex = in.nextInt();

        System.out.println("0) Добавить данные");
        System.out.println("1) Обновить данные");
        System.out.println("2) Удалить данные");

        int actionIndex = in.nextInt();
//        Object obj = selectService(tableIndex);

//        switch (actionIndex){
//            case 0:
//                obj.getClass();
//        }
    }

//    private Object selectService(int tableIndex){
//        Map tables = new HashMap<>();
//        tables.put(0, new MenuService());
//        tables.put(1, new ProposalService());
//        tables.put(2, new MenuElementService());
//        tables.put(3, new ProposalElementService());
//        tables.put(4, new IngredientService());
//        tables.put(5, new CategoryService());
//        tables.put(6, new FoodOrDrinkService());
//        tables.put(7, new RecipeElementService());
//
//        return tables.get(tableIndex);
//    }
}
