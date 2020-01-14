package cn.gdcp.graduation.utils.page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页相应封装
 * @date 2018年5月9日 下午4:29:49
 */
public class PageResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1935533107666764534L;
	
	
	
	public PageResponse() {
		super();
	}
	public PageResponse(Long pageStart,Long pageTotal,Long pageCount,List<?> data) {
		super();
		this.pageStart=pageStart;
		this.pageTotal=pageTotal;
		this.pageCount=pageCount;
		this.data=data;
	}
	/**
	 * 每页显示数量
	 */
	private Long pageTotal;
	/**
	 * 总也数
	 */
	private Long pageCount;
	/**
	 * 当前页码
	 */
	private Long pageStart;
	/**
	 * 数据
	 */
	private List<?> data;
	public Long getPageTotal() {
		return pageTotal;
	}
	public void setPageTotal(Long pageTotal) {
		if(pageTotal==null||pageTotal<=0) {
			pageTotal=10L;
		}
		this.pageTotal = pageTotal;
	}
	public Long getPageStart() {
		return pageStart;
	}
	public void setPageStart(Long pageStart) {
		if(pageStart==null||pageStart<=0) {
			pageStart=1L;
		}
		this.pageStart = pageStart;
	}
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	public Long getPageCount() {
		return pageCount;
	}
	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}
	
	
}
