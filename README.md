# Jdb2C

##  将数据库中的表快速生成实体类引入Java项目中

####  使用方法:
1) **导入jar包,在项目根目录创建名为"datasource.yaml"文件**
2) ***配置文件如下:***
---------------------------------
##### driver : com.mysql.jdbc.Driver
##### url : jdbc:mysql://ip地址:端口号/数据库名(数据库名必须与mysql数据库中表名一致，且数据库名必须要以db结尾)
##### username : 用户名
##### password : 密码
---------------------------------
3) *在src目录下新建一个名为**pojo**的包*
4) **在main函数调用start.Jdb2C()的静态方法生成**
5) **Date类型jar需自行手动导入，建议使用sql.date**
