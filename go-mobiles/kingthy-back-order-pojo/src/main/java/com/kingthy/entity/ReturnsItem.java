package com.kingthy.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * @author:xumin
 * @Description:
 * @Date:18:14 2017/11/2
 */
public class ReturnsItem implements Serializable {
    private Long id;

    private Date createDate;

    private Date modifyDate;

    private Long version;

    private String name;

    private Integer quantity;

    private String sn;

    private Long returnsId;

    private String specifications;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public Long getReturnsId() {
        return returnsId;
    }

    public void setReturnsId(Long returnsId) {
        this.returnsId = returnsId;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications == null ? null : specifications.trim();
    }
}