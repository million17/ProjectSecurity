package application.extension;

import application.data.model.Product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MyFunction {
    public static String getGuid(HttpServletRequest request) {
        Cookie cookie[] = request.getCookies();

        if(cookie!=null) {
            for(Cookie c :cookie) {
                if(c.getName().equals("guid"))  return c.getValue();
            }
        }
        return null;
    }
    public static boolean isContainer(List<Product> productList, int id){
        for(Product product: productList){
            if (product.getId()==id)
                return true;
        }
        return false;
    }
}
