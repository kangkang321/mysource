package com.neusoft.hp.runtime.handler;

import lombok.Data;

/**
 * 类BaseListener.java的实现描述：表单设计器埋点 FIXME
 * 后续考虑整个package放入底层jar或者与ISimpleEntityOperatorService、ISimpleEntityQueryService一起放入jar包
 * 
 * @author Administrator 2017年3月16日 上午10:47:16
 */
@Data
public class BaseHandler implements Comparable<BaseHandler> {

    /**
     * 顺序越小，越先执行;FIXME 顺序通过注解来
     */
    @Deprecated
    private int     sorted;

    /**
     * 是否可用
     */
    @Deprecated
    private boolean used;

    @Override
    public int compareTo(BaseHandler o) {
        if (o == null) {
            return 1;
        } else {
            return this.sorted - o.sorted;
        }
    }

}
