package application.controller.api;


import application.data.model.ProductImage;
import application.data.service.ProductImageService;
import application.data.service.ProductService;
import application.model.api.DataApiResult;
import application.model.dto.ProductImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping(path = "/api/productImage")
public class ProductImageApiController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductImageService productImageService;


    @PostMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody ProductImageDTO productImageDTO){
        DataApiResult result = new DataApiResult();

        ProductImage productImage = productImageService.findOne(productImageDTO.getId());
        if (productImage== null){
            productImage = new ProductImage();
            productImage.setCreatedDate(new Date());
        }
        productImage.setLink(productImageDTO.getLink());
        productImage.setTitle(productImageDTO.getTitle());
        productImage.setProductId(productImageDTO.getProductId());
        productImage.setProduct(productService.findOne(productImageDTO.getProductId()));
        try {
            productImageService.update(productImage);
            result.setData(productImage);
            result.setMessage("Update success!");
            result.setSuccess(true);

        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/delete/{productImageId}")
    public ResponseEntity<?> delete(@Valid @RequestBody int productImageId){
        DataApiResult result= new DataApiResult();

        ProductImage productImage= productImageService.findOne(productImageId);
        if(productImage==null){
            result.setMessage("Not found product image!");
            result.setSuccess(false);
            return ResponseEntity.badRequest().body(result);
        }

        try{
            productImageService.delete(productImageId);
            result.setMessage("Update success!");
            result.setSuccess(true);
        } catch (Exception e){
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }


}
