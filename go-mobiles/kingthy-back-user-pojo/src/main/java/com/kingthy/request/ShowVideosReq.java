package com.kingthy.request;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * ShowVideosReq
 * @author likejie
 * @date  2017/9/11.
 *
 */
@Data
@ToString
public class ShowVideosReq implements Serializable {

    private String token;

    @JsonIgnore
    private String memberUuid;
    private String name;
    private String videoUrl;
    private Integer pageSize;
    private Integer pageNum;

}
