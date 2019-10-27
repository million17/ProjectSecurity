package application.controller.api;


import application.data.model.Category;
import application.data.service.CategoryService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.CategoryDTO;
import application.model.viewmodel.common.CategoryVM;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryApiController {
    private static final Logger logger = LogManager.getLogger(CategoryApiController.class);


    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value="/detail", params = {"categoryId"}) //params có/không => tùy
    public @ResponseBody
    CategoryVM getSupplyInfor (@RequestParam(value = "categoryId") int categoryId){
        Category category = categoryService.findOne(categoryId);
        if(category==null)
            return null;
        CategoryVM CategoryVM= new CategoryVM();
        CategoryVM.setId(category.getId());
        CategoryVM.setName(category.getName());
        CategoryVM.setShortDesc(category.getShortDesc());
        CategoryVM.setCreatedDate(category.getCreatedDate());

        return CategoryVM;
    }

    @PostMapping(value="/delete/{categoryId}") //params có/không => tùy
    public @ResponseBody BaseApiResult delete (@PathVariable int categoryId){
        BaseApiResult result= new BaseApiResult();
        try {
            result.setSuccess(true);
            result.setMessage("Delete category successfully");
            categoryService.delCategory(categoryId);

        }catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @GetMapping("/fake")
    public BaseApiResult fakeCategory(){
        BaseApiResult result = new BaseApiResult();

        try {
            long totalCategories = categoryService.getTotalCategories();
            List<Category> categoryList = new ArrayList<>();
            for (long i = totalCategories +1; i<= totalCategories +5;i++){
                Category category = new Category();
                category.setName("Category " + i);
                category.setShortDesc("Category " + i + " short desc");
                category.setCreatedDate(new Date());
                categoryList.add(category);
            }
            categoryService.addNewListCategories(categoryList);
            result.setSuccess(true);
            result.setMessage("Fake list categories success !");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    @PostMapping(value = "/create")
    public BaseApiResult createProduct(@RequestBody CategoryDTO dto){
        DataApiResult result = new DataApiResult();

        try {
            Category category = new Category();
            category.setName(dto.getName());
            category.setShortDesc(dto.getShortDesc());
            category.setCreatedDate(new Date());
            categoryService.addNewCategory(category);
            result.setData(category.getId());
            result.setMessage("Save category successfully: " + category.getId());
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }



    @PostMapping("/update/{categoryId}")
    public BaseApiResult update (@PathVariable int categoryId,
                                 @RequestBody CategoryDTO dto){
        BaseApiResult result = new BaseApiResult();
        try{
            Category category = categoryService.findOne(categoryId);
            category.setId(dto.getId());
            category.setName(dto.getName());
            category.setShortDesc(dto.getShortDesc());
            category.setShortDesc(dto.getShortDesc());
            categoryService.addNewCategory(category);
            result.setSuccess(true);
            result.setMessage("Update category successfully");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
