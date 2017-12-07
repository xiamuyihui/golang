package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * 修改手机号DTO
 * @author likejie
 * @date  2017/6/13.
 */
@Data
@ToString
public class ModifyPhoneDTO implements Serializable {


    @JsonIgnore
    private String memberUuid;
    /**
     *
     * 手机号
     */
    private String phone;

    /**
     *
     * 验证码缓存key
     */
    private String code;


}
