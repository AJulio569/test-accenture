package co.com.bancolombia.model.franchise.model;


import java.util.ArrayList;
import java.util.List;

public class Branch {
   private String name;
   private List<Product> products;

    public Branch(String name, List<Product> products) {
        this.name = name;
        this.products = products != null ? products : new ArrayList<>();
    }

    public Branch() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
