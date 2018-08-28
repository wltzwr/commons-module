package com.repro;

import freemarker.template.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileCreator {

    private static Configuration config = new Configuration(Configuration.VERSION_2_3_23);
    static {
        config.setClassForTemplateLoading(FileCreator.class, "/templates");
    }


    public static void createFile(TableContext context){
        String basePath = System.getProperty("user.dir") + "/src/main/java/"+ context.getBasePackage().replace('.','/');

        createPackage(basePath);


        createJavaModelFile(context,basePath + "/po/" + context.getJavaModelName());
        createJavaMapperFile(context,basePath + "/mapper/" + context.getJavaMapperName());
        createXMLMapperFile(context,basePath + "/mapper/xml/" + context.getXMLMapperName());
        createServiceFile(context,basePath + "/service/" + context.getServiceName());
        createServiceImplFile(context,basePath + "/service/impl/" + context.getServiceImplName());
    }

    private static void createPackage(String basePath){
        String[] subPackages = {"/po","/mapper","/mapper/xml","/service","/service/impl"};
        for (String subPackage : subPackages){
            File f = new File(basePath + subPackage);
            if (!f.mkdirs()){
                System.out.println("新建失败" + f);
            }
        }

        /*File f = new File(basePath + "/po");
        f.mkdirs();
        f = new File(basePath + "/mapper");
        f.mkdirs();
        f = new File(basePath + "/mapper/xml");
        f.mkdirs();
        f = new File(basePath + "/service");
        f.mkdirs();
        f = new File(basePath + "/service/impl");
        f.mkdirs();*/
    }

    //C:\Users\WangRan\IdeaProjects\maven-generator-plugin\src\main\java\org\freemason\plugin\generator\po\AccountPO.java
    private static void createFileByTemplate(TableContext context, String filePath, String templateFile){
        try {
            Template template = config.getTemplate(templateFile);
            filePath = filePath.replace('/','\\');
            File file = new File(filePath);
            if (file.createNewFile()){
                template.process(context, new FileWriter(file));
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            //ignore exception.
        }
    }

    private static void createJavaModelFile(TableContext context,String filePath){
        createFileByTemplate(context,filePath,"javaModelTemplate.ftl");
    }


    private static void createServiceFile(TableContext context,String filePath){
        createFileByTemplate(context,filePath,"ServiceTemplate.ftl");
    }

    private static void createServiceImplFile(TableContext context,String filePath){
        createFileByTemplate(context,filePath,"ServiceImplTemplate.ftl");
    }


    private static void createJavaMapperFile(TableContext context,String filePath){
        createFileByTemplate(context,filePath,"javaMapperTemplate.ftl");
    }


    private static void createXMLMapperFile(TableContext context,String filePath){
        createFileByTemplate(context,filePath,"XMLMapperTemplate.ftl");
    }


}
