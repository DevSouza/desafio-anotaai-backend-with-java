package br.com.devsouza.desafioanotaai.publishers.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.devsouza.desafioanotaai.domain.category.Category;
import br.com.devsouza.desafioanotaai.domain.product.Product;
import lombok.Getter;

@Getter
@JsonInclude(value = Include.NON_NULL)
public class CatalogEventBody {
    
    private CatalogEvent event;
    private Product product;
    private Category category;

    private CatalogEventBody() {}
    private CatalogEventBody(CatalogEvent event, Product product) {
        this.event = event;
        this.product = product;
    }
    private CatalogEventBody(CatalogEvent event, Category category) {
        this.event = event;
        this.category = category;
    }

    public static CatalogEventBody newProduct(Product product) {
        return new CatalogEventBody(CatalogEvent.CREATE_PRODUCT, product);
    }
    public static CatalogEventBody updateProduct(Product product) {
        return new CatalogEventBody(CatalogEvent.UPDATE_PRODUCT, product);
    }
    public static CatalogEventBody deleteProduct(Product product) {
        return new CatalogEventBody(CatalogEvent.DELETE_PRODUCT, product);
    }
    public static CatalogEventBody newCategory(Category category) {
        return new CatalogEventBody(CatalogEvent.CREATE_CATEGORY, category);
    }
    public static CatalogEventBody updateCategory(Category category) {
        return new CatalogEventBody(CatalogEvent.UPDATE_CATEGORY, category);
    }
    public static CatalogEventBody deleteCategory(Category category) {
        return new CatalogEventBody(CatalogEvent.DELETE_CATEGORY, category);
    }
}