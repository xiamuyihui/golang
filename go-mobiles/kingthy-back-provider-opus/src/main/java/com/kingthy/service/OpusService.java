package com.kingthy.service;

import com.kingthy.dto.OpusDto;
import com.kingthy.entity.Opus;

import java.util.List;

/**
 * OpusService(作品的接口类)
 * <p>
 * @author 赵生辉 2017年05月04日 17:17
 *
 * @version 1.0.0
 */
public interface OpusService
{

  /**
   * 创建一个新的作品
   *
   * @param opus
   * @return
   */
  String createOpus(Opus opus);

  /**
   * 查询我的作品列表
   *
   * @param opus
   * @return
   */
  List<Opus> queryOpusList(Opus opus);

  /**
   * 查询作品详情
   *
   * @param uuid
   * @return
   */
  OpusDto queryOpusInfo(String uuid);

  /**
   * 查询指定作品
   *
   * @param uuid
   * @return
   */
  Opus queryOpus(String uuid, String memberUuid);

  /**
   * 删除指定的作品
   *
   * @param list
   * @return
   */
  int delete(List<String> list,String memberUuid);

  /**
   * 修改作品信息
   *
   * @param opus
   * @return
   */
  int updateOpus(Opus opus);

  /**
   * 获取会员的作品数量
   * @param userUuid
   * @return
   */
  int opusAmount(String userUuid);

}