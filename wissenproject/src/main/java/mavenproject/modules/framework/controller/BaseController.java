package mavenproject.modules.framework.controller;

import cn.hutool.core.convert.Convert;
import mavenproject.common.constant.PageCons;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cn.hutool.core.util.StrUtil.*;
import static com.baomidou.mybatisplus.core.metadata.OrderItem.asc;
import static com.baomidou.mybatisplus.core.metadata.OrderItem.desc;

/**
 * controller父类
 *
 * @author jinghong 2019/10/17
 */
@Slf4j
public class BaseController {

    /**
     * request
     */
    @Autowired
    protected HttpServletRequest request;

    /**
     * response
     */
    @Autowired
    protected HttpServletResponse response;


    /**
     * 获取分页对象
     *
     * @param camelToUnderscore 是否驼峰转下划线
     * @param <T>
     * @return
     */
    protected <T> Page<T> getPage(Boolean order, Boolean camelToUnderscore) {
        int index = 0;
        // 页数
        Integer cursor = Convert.toInt(request.getParameter(PageCons.PAGE_PAGE), index);
        // 分页大小
        Integer limit = Convert.toInt(request.getParameter(PageCons.PAGE_ROWS), PageCons.DEFAULT_LIMIT);
        // 是否查询分页
        Boolean searchCount = Convert.toBool(request.getParameter(PageCons.SEARCH_COUNT), true);
        limit = limit > PageCons.MAX_LIMIT ? PageCons.MAX_LIMIT : limit;
        Page<T> page = new Page<>(cursor, limit, searchCount);
        // 排序
        String sortField = request.getParameter(PageCons.SORT_FIELD);
        if (order && isNotBlank(sortField)) {
            // 排序的类型 desc asc
            String sortOrder = request.getParameter(PageCons.SORT_ORDER);
            // 排序的字段 camelToUnderscore 为true时 处理驼峰转下划线
            String field = camelToUnderscore ? toUnderlineCase(sortField) : sortField;
            // 倒序
            if (equalsIgnoreCase(sortOrder, PageCons.DESC)) {
                page.addOrder(desc(field));
            } else {
                // 升序
                page.addOrder(asc(field));
            }
        }
        return page;
    }

    protected <T> Page<T> getPage(Boolean order) {
        return getPage(order, true);
    }

    protected <T> Page<T> getPage() {
        return getPage(true, true);
    }

}
