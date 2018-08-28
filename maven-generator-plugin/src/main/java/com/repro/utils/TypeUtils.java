package com.repro.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 类型转化工具
 * @author WangRan
 * @since 1.0
 * @see java.sql.Types
 */
public class TypeUtils {

    private static final Map<Integer, String> typeMap = new HashMap<>();
    //只提供建议使用的数据类型映射
    // TODO: 2018/8/17 丰富其余不常用类型映射
    static {
        typeMap.put(5, "Short");
        typeMap.put(4, "Integer");
        typeMap.put(-5, "Long");
        typeMap.put(6, "Float");
        typeMap.put(8, "Double");
        typeMap.put(3, "BigDecimal");
        typeMap.put(12, "String");
        typeMap.put(2004, "byte[]");
        typeMap.put(93, "Date");
        typeMap.put(92, "Date");
        typeMap.put(91, "Date");
    }

    /**
     * 根据数据类型返回对应java类型
     * @param dataType
     * @return
     */
    public static String dataTypeToJavaType(Integer dataType){
        return typeMap.get(dataType);
    }

}
