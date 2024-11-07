package com.zacharyvds.quillmc.TestFrameWork;

import com.zacharyvds.quillmc.domain.plugin.annotations.QuillComponent;

import java.util.ArrayList;
import java.util.List;

@QuillComponent
public class PizzaRepository {
    List<String> pizzaList;

    private final DependencyTester dependencyTester;

    public PizzaRepository(DependencyTester dependencyTester) {
        this.dependencyTester = dependencyTester;
        pizzaList = new ArrayList<>();
        pizzaList.add("Margherita");
        pizzaList.add("Pepperoni");
        pizzaList.add("Hawaiian");
        pizzaList.add("BBQ Chicken");
        pizzaList.add("Veggie");
    }

    public List<String> getPizzaList() {
        return pizzaList;
    }
}
