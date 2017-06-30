本项目的目的是配合业务开发生成数据库实体对应的jar包；
生成后的target里有两个帮助bat：install.bat用于帮助把生成的jar包插入本地仓库，deploy.bat用于帮助把生成的jar包上传私服；


1、修改pom的配置文件：properties部分，以project.obj开头的；
project.obj.ids	对象列表
project.obj.groupId 目标jar包的groupId
project.obj.artifactId 目标jar包的artifactId
project.obj.version 目标jar包的version
project.obj.driverClass 业务数据库的驱动类
project.obj.connectionURL 业务数据库的连接地址
project.obj.userId 业务数据库的用户名
project.obj.password 业务数据库的密码
project.obj.domainPackage domain类的package
project.obj.mapperPackage mapper类的package
2、执行mvn clean verify命令打包

