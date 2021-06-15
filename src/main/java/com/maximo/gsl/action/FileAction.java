package com.maximo.gsl.action;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : guosl
 * @version : 1.0
 * @date : 2021/6/14 19:00
 * @description : com.maximo.gsl.action
 */
public class FileAction {

    public FileAction() {
    }

    private File file;
    private String filePath;
    private FileOutputStream outStream;
    private BufferedWriter writer;

    public FileAction(String fileName) {
        this.filePath = file(fileName);
        this.file = new File(filePath);
        try {
            this.outStream = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.writer = new BufferedWriter(new OutputStreamWriter(outStream, StandardCharsets.UTF_8));
    }

    public void writeInFile(String str) {
        try {
            writer.write(str.concat("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFileAction() {
        try {
            if (null != writer) {
                writer.close();
            }
            if (null != outStream) {
                outStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String file(String fileName) {
        File testFile = new File(System.getProperty("user.dir") + File.separator + "maximo_translate" + File.separator +
                "src" + File.separator + "main" + File.separator + "resources" + File.separator + fileName);
        // 返回的是File类型,可以调用exsit()等方法
        File fileParent = testFile.getParentFile();
        if (!fileParent.exists()) {
            // 能创建多级目录
            fileParent.mkdirs();
        }
        if (!testFile.exists()) {
            try {
                //有路径才能创建文件
                testFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return testFile.getAbsolutePath();
    }

    /** 清空文件内容 */
    public void clearInfoForFile() {
        try {
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> readToMap() {
        Map<String, String> map = new HashMap<>();
        try {
            File file = new File(filePath);
            // 判断文件是否存在
            if (file.isFile() && file.exists()) {
                // 考虑到编码格式
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                //按行读取
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (!"".equals(lineTxt)) {
                        //对行的内容进行分析处理后再放入map里。
                        String[] reds = lineTxt.split(";target:");
                        //放入map
                        map.put(reds[0].replace("source:", ""), reds[1]);
                    }
                }
                //关闭InputStreamReader
                read.close();
                //关闭BufferedReader
                bufferedReader.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return map;
    }
}
