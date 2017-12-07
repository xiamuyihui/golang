package com.kingthy.request;

import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.util.List;

/**
 * DeleteOpusReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月30日 11:58
 *
 * @version 1.0.0
 */
@Data
@ToString
public class DeleteOpusReq implements Serializable
{

    private static final long serialVersionUID = 1L;

    private List<String> opusList;
}
