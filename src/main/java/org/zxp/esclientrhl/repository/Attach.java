package org.zxp.esclientrhl.repository;

/**
 * @program: esclientrhl
 * @description: 提供更强的查询功能可选条件
 * @author: X-Pacific zhang
 * @create: 2019-10-14 10:42
 **/
public class Attach{
    private PageSortHighLight pageSortHighLight = null;
    private String[] includes;
    private String[] excludes;
    private String routing;

    public String[] getIncludes() {
        return includes;
    }

    public void setIncludes(String[] includes) {
        this.includes = includes;
    }

    public String[] getExcludes() {
        return excludes;
    }

    public void setExcludes(String[] excludes) {
        this.excludes = excludes;
    }

    public PageSortHighLight getPageSortHighLight() {
        return pageSortHighLight;
    }

    public void setPageSortHighLight(PageSortHighLight pageSortHighLight) {
        this.pageSortHighLight = pageSortHighLight;
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }
}
