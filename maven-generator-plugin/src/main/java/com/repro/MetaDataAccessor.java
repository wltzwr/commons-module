package com.repro;



import com.repro.utils.TypeUtils;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import static com.repro.MyMojo.local;

public class MetaDataAccessor {
    private boolean initialized = false;
    private Connection connection;

    public MetaDataAccessor(String url, String username, String password) throws SQLException {
        url += "?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        init(url, username, password);
    }

    private void init(String url, String username, String password) throws SQLException {
        if (!initialized) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                // ignore   mysqlConnector always exists
            }
            connection = DriverManager.getConnection(url, username, password);
            initialized = true;
        }
    }

    public boolean isInitialized() {
        return initialized;
    }

    public Set<String> getTableNames() throws SQLException {
        ResultSet rs = connection.getMetaData().getTables(connection.getCatalog(), null, "%", new String[]{"TABLE"});
        Set<String> tableNames = new HashSet<>();
        while (rs.next()) {
            tableNames.add(rs.getString("TABLE_NAME"));
        }
        return tableNames;
    }

    public TableContext getTable(String tableName) throws SQLException {
        TableContext context = new TableContext(tableName, local.get());
        DatabaseMetaData meta;
        meta = connection.getMetaData();
        ResultSet rs = meta.getPrimaryKeys(null, null, "account");
        String pkColumnName = "";
        while (rs.next()) {
            pkColumnName = rs.getString("COLUMN_NAME");
        }
        rs = meta.getColumns(connection.getCatalog(), null, tableName, null);
        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME");
            int dataType = rs.getInt("DATA_TYPE");
            context.addColumn(new Column(
                    columnName,
                    TypeUtils.dataTypeToJavaType(dataType),
                    JDBCType.valueOf(dataType),
                    pkColumnName.equals(columnName))
            );
        }

        return context;
    }


    public static void main(String[] args) {


    }


}
