package com.ocado.basket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        BasketSplitter basketSplitter= new BasketSplitter("C:\\Users\\kamil\\IdeaProjects\\untitled\\src\\main\\resources\\Zadanie\\config.json");
        try (FileReader reader = new FileReader("C:\\Users\\kamil\\IdeaProjects\\untitled\\src\\main\\resources\\Zadanie\\basketTest.json")) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<String>>() {
            }.getType();
            ArrayList<String> basketProducts = gson.fromJson(reader, type);
            Map<String, List<String>> basket1 = basketSplitter.split(basketProducts);
            System.out.println(basket1);
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
