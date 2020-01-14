package cn.gdcp.graduation.utils.page;


/**
 * 分页工具
 * @date 2018年4月24日 下午9:39:24
 */
public final class PageUtils {
	/**
	 * 	索引
	 */
	Long index;
	/**
	 * 	总页数
	 */
	Long total;

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public PageUtils() {
	}

	public PageUtils(Long index, Long total) {
		this.index = index;
		this.total = total;
	}

	public void init(Long count, Long start){
		if (count % 5 == 0){
			this.total = count / 5;
		}else{
			this.total = (count / 5) + 1;
		}
		if (start == 0){
			this.index = 0l;
		}else{
			this.index = (start - 1) * 5;
		}
	}
}
