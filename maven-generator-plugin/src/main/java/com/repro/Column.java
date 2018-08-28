package com.repro;


import com.repro.utils.NameUtils;
import java.sql.JDBCType;

public class Column{
    private final String columnName;
    private final String upperAttrName;
    private final String lowerAttrName;
    private final String javaDataType;
    private final JDBCType jdbcType;
    private final boolean isPrimaryKey;
    public JDBCType getJdbcType() {
        return jdbcType;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getJavaDataType() {
        return javaDataType;
    }

    public String getLowerAttrName() {
        return lowerAttrName;
    }

    public String getUpperAttrName() {
        return upperAttrName;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public  Column(String columnName, String javaDataType, JDBCType jdbcType, boolean isPrimaryKey) {
        this.columnName = columnName;
        this.upperAttrName = NameUtils.underlineToCamel(columnName,true);
        this.lowerAttrName = NameUtils.underlineToCamel(columnName,false);
        this.javaDataType = javaDataType;
        this.jdbcType = jdbcType;
        this.isPrimaryKey = isPrimaryKey;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Column){
            Column other = (Column)obj;
            return  this.columnName.equals(other.columnName) &&
                    this.javaDataType.equals(other.javaDataType) &&
                    this.jdbcType.equals(other.jdbcType) &&
                    this.isPrimaryKey == other.isPrimaryKey;
        }else {
            return false;
        }
    }


    @Override
    public int hashCode() {
        int hashCode = 325*(columnName.hashCode() + javaDataType.hashCode() + jdbcType.hashCode());
        return isPrimaryKey ? Math.abs(hashCode) : -Math.abs(hashCode);
    }
}

