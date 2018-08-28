package com.repro;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Mojo(name = "generate")
public class MyMojo extends AbstractMojo {

    @Parameter(required = true)
    private String connectionUrl;
    @Parameter(required = true)
    private String userName;
    @Parameter(required = true)
    private String passWord;
    @Parameter(required = true)
    private String basePackage;

    private Log logger = getLog();
    //MetaDataAccessor accessor = new MetaDataAccessor(connectionUrl, username, password);

    public static ThreadLocal<String> local = new ThreadLocal<>();

    public void execute() throws MojoExecutionException {
        Set<TableContext> tableContexts = new HashSet<>();
        local.set(basePackage);
        try {
            MetaDataAccessor accessor = new MetaDataAccessor(connectionUrl, userName, passWord);
            Set<String> tableNames = accessor.getTableNames();
            for (String tableName : tableNames){
                tableContexts.add(accessor.getTable(tableName));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        for (TableContext context : tableContexts) {
            FileCreator.createFile(context);
        }



        System.out.println(tableContexts);
    }

}
