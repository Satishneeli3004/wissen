package mavenproject.common.constant;

/**
 * PAGE 常量
 *
 * @author jinghong
 */
public interface PageCons {

    /**
     * 页数
     */
    String PAGE_PAGE = "page";
    /**
     * 分页大小
     */
    String PAGE_ROWS = "limit";
    /**
     * 排序字段
     */
    String SORT_FIELD = "sort";
    /**
     * 排序字段
     */
    String SORT_ORDER = "order";

    /**
     * 倒序
     */
    String DESC = "desc";

    /**
     * 升序
     */
    String ASC = "asc";
    /**
     * 查询总数
     */
    String SEARCH_COUNT = "searchCount";
    /**
     * 默认每页条目20,最大条目数100
     */
    int DEFAULT_LIMIT = 20;
    int MAX_LIMIT = 100;

}
