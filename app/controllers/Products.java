package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import models.Product;
import java.util.List;
import views.html.*;






public class Products extends Controller {

    private static final Form<Product> productForm = Form.form(Product.class);

    public static Result list()                        /*List all products*/
    {
        List<Product> products = Product.findAll();
        return ok(list.render(products));
    }

    public static Result newProduct()                   /*Show a blanck product form*/
    {
        return ok(views.html.product.details.render(productForm));
    }

    public static Result details(String ean)           /*Show a product edit form*/
    {
        final Product product = Product.findByEan(ean);
        if (product == null){
            return notFound(String.format("Product %s does not existe"), ean);
        }

        Form<Product> filledForm = productForm.fill(product);
        return ok(views.html.product.details.render(filledForm));
    }

    public static Result save()                         /*Save a product*/
    {
        Form<Product> boundForm = productForm.bindFromRequest();
        if(boundForm.hasErrors()){
            flash("error", "Please correct the form below.");
            return badRequest(views.html.product.details.render(boundForm));
        }

        Product product = boundForm.get();
        product.save();
        flash("success",
                String.format("Successfully added product %s", product));

        return redirect(routes.Products.list());
    }

    public static Result delete(String ean){
        final Product product = Product.findByEan(ean);
        if(product == null){
            return notFound(String.format("Product %s does not exist.", ean));
        }

        Product.remove(product);
        return redirect(routes.Products.list());
    }

}
