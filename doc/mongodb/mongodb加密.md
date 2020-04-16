### 1.启动mongo  
找到mongodb安装目录，命令行运行命令:   
`./bin/mongo`  
### 2.查看当前已存在的数据库  
`show dbs`  
### 3.切换到你想要加密的数据库  
`use XXX`  
XXX为你要加密的数据库  
### 4.为当前的数据库添加用户  
`db.createUser({user:'root', pwd:'123456', roles:[{role:"readWrite",db:"XXX"}]})`  
注意：readWrite为你想让用户拥有的权限，可根据不同的需求给用户设置不同的权限。  
### 5.修改配置文件  
在mongodb.conf中加上:  
`auth=true`  
### 6.重启mongodb