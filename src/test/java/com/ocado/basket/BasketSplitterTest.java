package com.ocado.basket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BasketSplitterTest {

    @Test
    void split() throws IOException {
        // Given

        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        Map<String, List<String>> deliveryOptionsTest = new HashMap<>();
        FileReader readerDelivery = new FileReader("C:\\Users\\kamil\\IdeaProjects\\untitled\\src\\main\\resources\\Zadanie\\expectedDeliveryTest.json");
        Type typeDelivery = new TypeToken<Map<String, List<String>>>() {}.getType();
        deliveryOptionsTest = gson.fromJson(readerDelivery, typeDelivery);
        readerDelivery.close();

        // When

        BasketSplitter basketSplitter = new BasketSplitter("C:\\Users\\kamil\\IdeaProjects\\untitled\\src\\main\\resources\\Zadanie\\configTest.json");
        FileReader readerBasket = new FileReader("C:\\Users\\kamil\\IdeaProjects\\untitled\\src\\main\\resources\\Zadanie\\basketTest.json");
        ArrayList<String> basketProducts= gson.fromJson(readerBasket, type);
        Map<String, List<String>> basket = basketSplitter.split(basketProducts);
        readerBasket.close();

        // Then

        Assertions.assertEquals(deliveryOptionsTest, basket);
    }
}
