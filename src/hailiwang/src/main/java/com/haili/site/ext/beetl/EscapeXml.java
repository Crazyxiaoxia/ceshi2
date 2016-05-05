package com.haili.site.ext.beetl;

import org.apache.commons.lang3.StringEscapeUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * 过滤xml文档函数
 * @author 王晓东
 */
public class EscapeXml implements Function {

    /**
     * 过滤xml文档函数实现
     */
    @Override
    public Object call(Object[] arg, Context context) {
        if (arg.length != 1 || null == arg[0] || !(arg[0] instanceof String)) {
            return "";
        }
        String content = null;// 

        content = (String) arg[0];

        return StringEscapeUtils.escapeXml11(content);
    }

}
