package com.zacharyvds.quillmc.TestFrameWork;

import com.zacharyvds.quillmc.domain.plugin.annotations.QuillService;

import java.util.List;
import java.util.Random;

@QuillService
public class PizzaService {
    private final PizzaRepository pizzaRepo;
    public PizzaService(PizzaRepository pizzaRepo) {
        this.pizzaRepo = pizzaRepo;
    }

    public String getRandomPizza(){
        Random random = new Random();
        List<String> pizzalist =  pizzaRepo.getPizzaList();
        return pizzalist.get(random.nextInt(pizzalist.size()));
    }
}
