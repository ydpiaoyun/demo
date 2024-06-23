package com.yun.demo.enums;

public enum CacheEnum {

    GET_USER(CacheConstants.GET_USER, CacheConstants.EXPIRES_5_MIN),
    /**
     * 获取菜单列表
     */
    GET_MENU(CacheConstants.GET_MENU, CacheConstants.EXPIRES_10_MIN),
    ;
    /**
     * 缓存名称
     */
    private final String name;
    /**
     * 过期时间
     */
    private final int expires;

    /**
     * 构造
     */
    CacheEnum(String name, int expires) {
        this.name = name;
        this.expires = expires;
    }

    public String getName() {
        return name;
    }

    public int getExpires() {
        return expires;
    }
}
