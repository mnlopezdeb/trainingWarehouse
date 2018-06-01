package models;

import java.util.*;
import play.data.validation.Constraints;


public class Product
{
    @Constraints.Required
    public String ean;
    @Constraints.Required
    public String name;
    public String description;

    public Product(){}

    public Product(String ean, String name, String description)
    {
        this.ean            =  ean;
        this. name          =  name;
        this.description    =  description;
    }

    public String toString()
    {
        return String.format("%s - %s", ean, name);
    }

    private static List<Product> products;

    static
    {
        products = new ArrayList<Product>();
        products.add(new Product("1111111111111", "paperclips 1", "paperclips desc 1"));
        products.add(new Product("2222222222222", "paperclips 2", "paperclips desc 2"));
        products.add(new Product("3333333333333", "paperclips 3", "paperclips desc 3"));
        products.add(new Product("4444444444444", "paperclips 4", "paperclips desc 4"));
        products.add(new Product("5555555555555", "paperclips 5", "paperclips desc 5"));
    }

    public static List<Product> findAll()
    {
        return new ArrayList<Product>(products);
    }

    public static Product findByEan(String ean)
    {
        for(Product candidate : products)
        {
            if(candidate.ean.equals(ean))  {return candidate;}
        }
        return null;
    }

    public static List<Product> findByName(String term)
    {
        final List<Product> results = new ArrayList<Product>();
        for (Product candidate : products)
        {
            if(candidate.name.toLowerCase().contains(term.toLowerCase())) {results.add(candidate);}

        }

        return results;
    }


    public static boolean remove(Product product) { return products.remove(product); }

    public  void save()
    {
        products.remove(findByEan((this.ean)));
        products.add(this);
    }



}