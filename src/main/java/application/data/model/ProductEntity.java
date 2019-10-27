package application.data.model;

import javax.persistence.*;

@Entity(name = "dbo_product_entity")
public class ProductEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_entity_id")
    @Id
    private int id;

    @Column(name = "amount")
    private long amount;

    @Column(name = "color_id" ,insertable = false,updatable = false)
    private int colorId;

    @Column(name = "product_id",insertable = false,updatable = false)
    private int productId;

    @ManyToOne(optional = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
