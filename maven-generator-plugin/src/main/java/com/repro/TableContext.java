package com.repro;


import com.repro.utils.NameUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TableContext {

    private String parentName;

    private String basePackage;
    //不需要import包的类型
    private static final Map<String, String> REQUIRED_IMPORT_TYPES_MAP = new HashMap<>();
    static {
        REQUIRED_IMPORT_TYPES_MAP.put("Date", "java.util.Date");
    }

    private final String baseJavaName;

    private final String tableName;

    private final String javaModelName;

    private final String XMLMapperName;

    private final String javaMapperName;

    private final String ServiceName;

    private final String ServiceImplName;

    private Set<Column> columns = new HashSet<>();

    private Set<String> requiredPackages = new HashSet<>();

    private static final Set<String> excludeColumnName = new HashSet<>();

    {
        excludeColumnName.add("id");
        excludeColumnName.add("is_delete");
        excludeColumnName.add("create_time");
        excludeColumnName.add("modify_time");
    }

    public TableContext(String parentName, String tableName, String basePackage) {
        this.basePackage = basePackage;
        this.parentName = parentName;
        this.tableName = tableName;
        this.baseJavaName = NameUtils.underlineToCamel(tableName,true);
        this.javaModelName = baseJavaName + "PO.java";
        this.XMLMapperName = baseJavaName + "Mapper.xml";
        this.javaMapperName = baseJavaName + "Mapper.java";
        this.ServiceName = baseJavaName + "Service.java";
        this.ServiceImplName = baseJavaName + "ServiceImpl.java";
    }

    public String getBasePackage() {
        return basePackage;
    }

    public TableContext(String tableName, String basePackage) {
        this("adasda", tableName, basePackage);
    }

    public String getParentName() {
        return parentName;
    }

    public String getBaseJavaName() {
        return baseJavaName;
    }

    public void addColumn(Column c){
        if (!excludeColumnName.contains(c.getColumnName())){
            if (columns.add(c)){
                String requiredPackage = REQUIRED_IMPORT_TYPES_MAP.get(c.getJavaDataType());
                if (requiredPackage != null){
                    requiredPackages.add(requiredPackage);
                }
            }
        }
    }

    public Set<String> getRequiredPackages() {
        return requiredPackages;
    }

    public String getTableName() {
        return tableName;
    }

    public String getJavaModelName() {
        return javaModelName;
    }

    public String getXMLMapperName() {
        return XMLMapperName;
    }

    public String getJavaMapperName() {
        return javaMapperName;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public String getServiceImplName() {
        return ServiceImplName;
    }

    public Set<Column> getColumns() {
        return columns;
    }

}
