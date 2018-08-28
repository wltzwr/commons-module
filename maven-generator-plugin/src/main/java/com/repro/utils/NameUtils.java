package com.repro.utils;

/**
 * 名称转换工具
 * @author WangRan
 * @since 1.0
 */
public class NameUtils {
    private static final char UNDER_LINE = '_';

    /**
     * 驼峰命名转化为下划线命名
     * @param camelName 驼峰名
     * @return 下划线名
     * ex：CamelCaseName  -> camel_case_name
     * ex：camelCaseName  -> camel_case_name
     * 请保证参数格式符合驼峰命名规范
     */
    public static String camelToUnderline(String camelName) {
        if (camelName == null){
            throw new NullPointerException("underlineName must not be null");
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < camelName.length(); i++) {
            char c = camelName.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0){
                    result.append(UNDER_LINE);
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
    /**
     * 下划线命名转化为驼峰命名
     * @param underlineName 下划线名
     * @param upperStart 是否首字母大写
     * @return 驼峰名
     * ex：camel_case_name , true  -> CamelCaseName
     * ex：camel_case_name , false -> camelCaseName
     * 请保证参数符合下划线命名规范
     */
    public static String underlineToCamel(String underlineName, boolean upperStart){
        if (underlineName == null){
            throw new NullPointerException("underlineName must not be null");
        }
        StringBuilder result = new StringBuilder();
        boolean f = false;
        char c = underlineName.charAt(0);
        result.append(upperStart ? Character.toUpperCase(c) : Character.toLowerCase(c));
        for (int i = 1; i < underlineName.length(); i++) {
            c = underlineName.charAt(i);
            if (c == UNDER_LINE){
                f = true;
            }else {
                result.append(f ? Character.toUpperCase(c) : Character.toLowerCase(c));
                f = false;
            }
        }
        return result.toString();
    }

    public static String upperStart(String resource){
        StringBuilder sb = new StringBuilder(resource);
        sb.replace(0,1,resource.substring(0,1).toUpperCase());
        return sb.toString();
    }


    public static void main(String[] args) {
        System.out.println(upperStart("userInfo"));
    }

}
