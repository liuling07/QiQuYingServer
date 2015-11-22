# QiQuYingServer
奇趣营app的接口服务端，[奇趣营客户端](http://www.liuling123.com)<br/>
服务端采用Spring MVC + Hibernate，数据库使用Mysql，上传的图片托管于七牛。

# Usage
将qiquying.sql文件的数据导入你的本地Mysql数据库，然后将项目导入eclipse，修改src目录下的init.properties配置文件：<br/>
```
qiniu.ak=你的七牛Access Key
qiniu.sk=你的七牛Secret Key
qiniu.bucketName=你的七牛空间名
qiniu.url=你的七牛URL

jdbc.driverClassName=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/qiquying?zeroDateTimeBehavior=convertToNull
jdbc.username=你的Mysql用户名
jdbc.password=你的Mysql密码
```

配置好之后，将工程部署到Tomcat并启动，然后访问http://localhost:8080/QiQuYingServer/admin/ 进入后台管理登录界面，<br/>
登录进入后台管理主界面，初始用户名：123456789，密码：123456。在后台管理界面可以添加和趣事、趣图和美图内容。

# About me
* Blog:[http://www.liuling123.com](http://www.liuling123.com)
* Email:[476777389@qq.com](mailto:476777389@qq.com)

# License
Copyright 2015 liuling

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.