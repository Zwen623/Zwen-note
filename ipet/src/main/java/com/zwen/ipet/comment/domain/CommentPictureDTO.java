package com.zwen.ipet.comment.domain;

import java.util.Date;

import com.zwen.ipet.common.util.AbstractObject;

/**
 * 评论晒图
 * @author zwen
 *
 */
public class CommentPictureDTO extends AbstractObject {

	/**
	 * id
	 */
	private Long id;
	/**
	 * 评论信息id
	 */
	private Long commentInfoId;
	/**
	 * 评论图片路径
	 */
	private String commentPicturePath;
	/**
	 * 评论晒图的创建时间
	 */
	private Date gmtCreate;
	/**
	 * 评论晒图的修改时间
	 */
	private Date gmtModified;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCommentInfoId() {
		return commentInfoId;
	}
	public void setCommentInfoId(Long commentInfoId) {
		this.commentInfoId = commentInfoId;
	}
	public String getCommentPicturePath() {
		return commentPicturePath;
	}
	public void setCommentPicturePath(String commentPicturePath) {
		this.commentPicturePath = commentPicturePath;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
	@Override
	public String toString() {
		return "CommentPictureDTO [id=" + id + ", commentInfoId=" + commentInfoId + ", commentPicturePath="
				+ commentPicturePath + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
	
}
