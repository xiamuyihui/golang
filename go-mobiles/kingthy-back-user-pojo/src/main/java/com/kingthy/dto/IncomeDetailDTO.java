package com.kingthy.dto;


import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * 收益
 * @author xumin
 * @date  2017/6/12 15:22
 */
@Data
@ToString
public class IncomeDetailDTO implements Serializable {

    private  String goodsUuid;

    private String goodsName;

    private Integer quantity;

    private BigDecimal money;

    private Date createDate;

    @Data
    @ToString
    public static class OrderItem implements Serializable {
        private String goodsUuid;
        private Integer quantity;
        private BigDecimal amount;

    }
}
