package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
/**
 * @author:xumin
 * @Description:
 * @Date:18:14 2017/11/2
 */
@Data
@ToString
public class SparePart extends BaseTableFileds implements Serializable {

    private String className;

    private String sn;

    private static final long serialVersionUID = 1L;

}