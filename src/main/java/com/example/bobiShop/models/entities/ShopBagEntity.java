package com.example.bobiShop.models.entities;

import com.example.bobiShop.models.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="shop_bag")
public class ShopBagEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Length(min=3, max=15)
    private String itemName;

    @Column
    private String imgUrl;
    @Positive
    @Column()
    private BigDecimal price;
@ManyToOne
    private UserEntity buyer;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UserEntity getBuyer() {
        return buyer;
    }

    public void setBuyer(UserEntity buyer) {
        this.buyer = buyer;
    }
}
