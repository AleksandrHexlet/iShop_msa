package com.sprng.users.repository;


import com.sprng.library.entity.Category;
import com.sprng.library.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> , JpaSpecificationExecutor<Product> {
    List<Product> findByCategoryProductUrl(String url);

    @Query(nativeQuery = true, value = "SELECT * FROM product WHERE name_product = :name and (url_image = :image or " +
            "url_image = null)")
    List<Product> getProductsByNameAndImage(String name, String image);


//    @Query(nativeQuery = true, value = "SELECT  * FROM product WHERE name_product in (:nameArray)")
//    List<Product> getProductsByName(@Param(value= "nameArray") String[] array);


    @Query("SELECT prod FROM Product prod WHERE prod.quantityStock > :quantity AND prod.price < :price ")
    List<Product> getProductsByPriceAndQuantity(int quantity, BigDecimal price);

    @Query("SELECT product FROM Product product LEFT JOIN product.productTrader manufacture  WHERE " +
            "product.rating > :rating AND manufacture.cityStorage = :country")
    List<Product> getProductsByRatingAndManufactureCountry(short rating, String country);

 @Query("SELECT prod FROM Product prod WHERE prod.nameProduct in (:namesOfProduct)")
    List<Product> getProductsByName(@Param(value= "namesOfProduct") String[] array);

 @Query("SELECT prod FROM Product prod LEFT JOIN prod.categoryProduct category WHERE " +
         "category.name = :nameCategory AND prod.price > :minPrice AND prod.price < :maxPrice")
 List<Product> getProductsByCategoryAndPrice(String nameCategory, BigDecimal minPrice, BigDecimal maxPrice);


    @Query("SELECT prod FROM Product prod WHERE prod.categoryProduct =:category  AND prod.rating >= :rating AND prod.dateAdded >= :weekAgo")
    List<Product> findProductsbyRatingAndAddedLastWeek(Category category, double rating, LocalDate weekAgo);
//    получение товаров XXX категории с рейтингом равным или выше ХХХ, добавленных за последние XXX дней


// получение товаров из XXX категории с выборкой по производителю (равно указанному),
// по доступному количеству (больше или равно указанному), по рейтингу (больше или равно указанному)

//    @Query("SELECT prod FROM Product prod LEFT JOIN prod.productTrader manufactory " +
//            "WHERE manufactory.name =:#{#id.name} AND manufactory.cityStorage =:#{#id.cityStorage} " +
//            "AND prod.quantityStock >= :quantity AND prod.rating >=:rating")
//    List<Product>findProductByProductManufacturerAndQuantityStockAndRating(ProductManufactureId id,int quantity,double rating);

    // вместо имени и страны хотел передать ProductManufactureId и у него взять имя и страну.Idea писала ошибку.
        // Как можно использовать ProductManufactureId в аргументах ?



}

//SELECT * FROM product WHERE name_product in (:nameArray)
