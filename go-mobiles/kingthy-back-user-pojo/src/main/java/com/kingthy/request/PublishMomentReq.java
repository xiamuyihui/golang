package com.kingthy.request;

import com.esotericsoftware.kryo.NotNull;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * PublishMomentReq(描述其作用)
 * @author 赵生辉
 * @date  2017年05月19日 14:59
 *
 */
@Data
@ToString
public class PublishMomentReq implements Serializable
{

    private String image;

    private String context;

    private String token;

    private String memberNick;

    private String memberHead;

    private Integer type;

    private String video;

    private String thumbnail;

    private static final long serialVersionUID = 1L;
}
