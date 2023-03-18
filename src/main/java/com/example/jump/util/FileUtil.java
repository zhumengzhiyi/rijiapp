package com.example.jump.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    //把字符集保存到指定路径的文本文件
    public static void saveText(String path,String txt){
        BufferedWriter os=null;
        try{
            os=new BufferedWriter(new FileWriter(path));
            os.write(txt);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    //从指定路径的文本文件中读取内容字符集
    public static String openText(String path){
        BufferedReader is=null;
        StringBuilder sb=new StringBuilder();
        try{
            is=new BufferedReader(new FileReader(path));
            String line=null;
            while((line=is.readLine())!=null){
                sb.append(line);
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return sb.toString();
    }
}
