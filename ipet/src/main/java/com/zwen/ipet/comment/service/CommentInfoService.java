package com.zwen.ipet.comment.service;

import java.util.List;

import com.zwen.ipet.comment.domain.CommentInfoDTO;
import com.zwen.ipet.comment.domain.CommentInfoQuery;
import com.zwen.ipet.order.domain.OrderInfoDTO;
import com.zwen.ipet.order.domain.OrderItemDTO;

/**
 * 评论信息管理模块的service组件接口
 * @author zwen
 *
 */
public interface CommentInfoService {

	/**
	 * 新增手动发表的评论信息
	 * @param commentInfoDTO 评论信息DTO对象
	 * @throws Exception
	 */
	void saveManualPublishedCommentInfo(CommentInfoDTO commentInfoDTO) throws Exception;
	
	/**
	 * 新增自动发表的评论信息
	 * @param orderInfoDTO 订单信息DTO对象
	 * @param orderItemDTO 订单条目DTO对象
	 * @return 处理结果
	 * @throws Exception
	 */
	CommentInfoDTO saveAutoPublishedCommentInfo(
			OrderInfoDTO orderInfoDTO, OrderItemDTO orderItemDTO) throws Exception;
	
	/**
	 * 分页查询评论信息
	 * @param query 评论查询条件
	 * @return 评论信息
	 * @throws Exception
	 */
	List<CommentInfoDTO> listByPage(CommentInfoQuery query) throws Exception;
	
	/**
	 * 根据id查询评论信息
	 * @param id 评论信息id
	 * @return 评论信息
	 * @throws Exception
	 */
	CommentInfoDTO getById(Long id) throws Exception;
	
	/**
	 * 更新评论
	 * @param comment 评论信息
	 * @throws Exception
	 */
	void update(CommentInfoDTO comment) throws Exception;
	
	/**
	 * 删除评论
	 * @param id 删除评论
	 * @return 处理结果
	 * @throws Exception
	 */
	void remove(Long id) throws Exception;
	
}
