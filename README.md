# maximo_translate
maximo 翻译到语言表

## 文件说明

+ map.txt 存储需翻译的文本、需翻译的语言及翻译后的文本；可自定义内容，匹配到则无需调用接口。
+ out.sql 生成插入语言表的SQL语句。

## 开发逻辑：

1. 获取所有语言表；
2. 获取业务表中需翻译内容；
3. 通过接口查询需翻译内容的结果；
4. 生成Insert语句。

## 目录结构

```
maximo_translate
├─out
│  └─artifacts
│      └─maximo_translate_jar   jar可执行文件
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─maximo
│  │  │          └─gsl
│  │  │              ├─action       操作（查询语言表，获取需翻译字段，调用接口，生成SQL）
│  │  │              ├─bean         数据对象
│  │  │              ├─jdbc         数据库连接
│  │  │              └─translate    翻译接口
│  │  └─resources
│  │      └─META-INF    构建清单
│  └─test
│      └─java    
└─target
```
## 版本介绍

**_V 1.0_**

1. 支持db2数据库连接；
2. 支持测试数据库连接；
3. 支持选择翻译接口；
4. 支持导出SQL文件；
5. 支持自定义导出文件及翻译文件目录；
6. 支持自定义翻译内容；

> 注意事项:只支持英文安装版；只支持翻译中文和英文
   
后续计划
1. 加入有道和微软翻译接口；
2. 支持自定义接口appid，key;




