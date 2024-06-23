package com.yun.demo.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author: zhangxiaoyun
 * @description:
 * @date: 2024-05-20
 */
@Data
public class PageQuery {
    private Integer pageNo = 1;
    private Integer pageSize = 10;
    private String sortBy;
    private Boolean asc = true;

    public Page<T> toMpPage() {
        Page<T> page = Page.of(pageNo, pageSize);
        if(StrUtil.isBlank(sortBy)){
            page.addOrder(new OrderItem("id",asc));
        }else {
            page.addOrder(new OrderItem(sortBy,asc));
        }
        return page;
    }
}
