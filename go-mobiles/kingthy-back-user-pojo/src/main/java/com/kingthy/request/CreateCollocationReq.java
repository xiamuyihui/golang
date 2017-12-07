package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * CreateCollocationReq
 * @author 赵生辉
 * @date  2017/9/11.
 *
 */
@Data
@ToString
public class CreateCollocationReq implements Serializable
{
    private String temperatureUuid;

    private String occasionUuid;

    private String colourUuid;

    private String styleUuid;

    private String indexImage;

    private String goodslist;

    private String description;

    private static final long serialVersionUID = 1L;
}
