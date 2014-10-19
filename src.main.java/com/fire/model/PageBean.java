package com.fire.model;

import java.util.List;

/**
 * 分页对象.
 * 
 * @param <T>
 *            the generic type
 */
public class PageBean<T> {

	/** 每页记录数. */
	private int pageSize = 5;

	/** 当前页码. */
	private int currentPage = 1;

	/** 总记录数. */
	private int totalResults = 0;

	/** 总页数. */
	private int totalPages;

	/** 数据. */
	private List<T> data;

	/**
	 * 构造函数.
	 */
	public PageBean() {
	}

	/**
	 * 构造函数.
	 * 
	 * @param totalResults
	 *            总记录数
	 */
	public PageBean(int totalResults) {
		this.totalResults = (totalResults > 0) ? totalResults : 0;
		this.totalPages = (int) Math.ceil(this.totalResults / (double) this.pageSize);
	}

	/**
	 * 构造函数.
	 * 
	 * @param totalResults
	 *            总记录数
	 * @param pageSize
	 *            每页大小
	 */
	public PageBean(int totalResults, int pageSize) {
		this.totalResults = (totalResults > 0) ? totalResults : 0;
		if (pageSize > 0) {
			this.pageSize = pageSize;
		}
		this.totalPages = (int) Math.ceil(this.totalResults / (double) this.pageSize);
	}

	/**
	 * 获取 每页记录数.
	 * 
	 * @return the 每页记录数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置 每页记录数.
	 * 
	 * @param pageSize
	 *            the new 每页记录数
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取 当前页码.
	 * 
	 * @return the 当前页码
	 */
	public int getCurrentPage() {
		if (currentPage > getTotalPages()) {
			currentPage = getTotalPages();
		}
		return currentPage > 0 ? currentPage : 1;
	}

	/**
	 * 设置 当前页码.
	 * 
	 * @param currentPage
	 *            the new 当前页码
	 */
	public void setCurrentPage(int currentPage) {
		if (currentPage >= 1) {
			this.currentPage = currentPage;
		}
	}

	/**
	 * 获取 总记录数.
	 * 
	 * @return the 总记录数
	 */
	public int getTotalResults() {
		this.totalResults = (totalResults > 0) ? totalResults : 0;
		return totalResults;
	}

	/**
	 * 设置 总记录数.
	 * 
	 * @param totalResults
	 *            the new 总记录数
	 */
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	/**
	 * 获取 总页数.
	 * 
	 * @return the 总页数
	 */
	public int getTotalPages() {
		this.totalPages = (int) Math.ceil(this.totalResults / (double) this.pageSize);
		return totalPages = totalPages > 0 ? totalPages : 1;
	}

	/**
	 * 设置 总页数.
	 * 
	 * @param totalPages
	 *            the new 总页数
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * 是否有下一页.
	 * 
	 * @return true, if successful
	 */
	public boolean hasNextPage() {
		if (this.currentPage < this.getTotalPages()) {
			return true;
		}
		return false;
	}

	/**
	 * 是否有上一页.
	 * 
	 * @return true, if successful
	 * @return
	 */
	public boolean hasPreviousPage() {
		if (this.currentPage > 1) {
			return true;
		}
		return false;
	}

	/**
	 * 获取当前显示记录页.
	 * 
	 * @return the start rownum
	 * @return
	 */
	public int getStartRownum() {
		return (getCurrentPage() - 1) * getPageSize() + 1;
	}

	/**
	 * 获取 数据.
	 * 
	 * @return the 数据
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * 设置 数据.
	 * 
	 * @param data
	 *            the new 数据
	 */
	public void setData(List<T> data) {
		this.data = data;
	}
}
