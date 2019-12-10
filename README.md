### quartz-demo
![](https://img.shields.io/badge/language-java-orange.svg) ![](https://img.shields.io/badge/build-%20passing-brightgreen.svg)
#### 概述
springboot集成quartz，实现集群环境下可控的动态定时任务。

#### 说明

1.初始化数据库

运行tables_mysql_innodb.sql（quartz表）、job_mysql_innodb.sql（自定义job表）

2.修改配置文件数据库配置

3.运行项目、测试

- 添加任务

  ```
  POST /job/save HTTP/1.1
  Host: localhost:8080
  Content-Type: application/json
  
  {
  	"className":"com.bsi.bdc.quartzdemo.job.HelloJob",
  	"cronExpression":"*/6 * * * * ?",
  	"jobName":"HelloJob",
  	"jobGroup":"TEST_GROUP",
  	"triggerName":"HELLO_TRIGGER",
  	"triggerGroup":"TEST_GROUP",
  	"pause":"false",
  	"enable":"true",
  	"description":"Test HelloJob For SpringBoot"
  }
  ```

  | response | status | msg         |
  | -------- | ------ | ----------- |
  | 成功     | 200    | 新增job成功 |
  | 失败     | 500    | 新增job失败 |

- 暂停任务

  ```
  GET /job/pause/3 HTTP/1.1
  Host: localhost:8080
  ```

  | response | status | msg         |
  | -------- | ------ | ----------- |
  | 成功     | 200    | 暂停job成功 |
  | 失败     | 500    | 暂停job失败 |

- 恢复任务

  ```
  GET /job/resume/1 HTTP/1.1
  Host: localhost:8080
  ```

  | response | status | msg         |
  | -------- | ------ | ----------- |
  | 成功     | 200    | 恢复job成功 |
  | 失败     | 500    | 恢复job失败 |

- 执行任务

  ```
  GET /job/run/3 HTTP/1.1
  Host: localhost:8080
  ```

  | response | status | msg             |
  | -------- | ------ | --------------- |
  | 成功     | 200    | 开启job执行成功 |
  | 失败     | 500    | 开启job执行失败 |

- 更新任务

  ```
  PUT /job/update/3 HTTP/1.1
  Host: localhost:8080
  Content-Type: application/json
  
  {
      "id":3,
      "cronExpression":"*/5 * * * * ?",
      "pause": "false",
  }
  ```

  | response | status | msg         |
  | -------- | ------ | ----------- |
  | 成功     | 200    | 更新job成功 |
  | 失败     | 500    | 更新job失败 |

- 获取单个任务

  ```
  GET /job/get/3 HTTP/1.1
  Host: localhost:8080
  ```

  | response | status | msg          |
  | -------- | ------ | ------------ |
  | 成功     | 200    | 见下方代码块 |
  | 失败     | 500    | 获取job失败  |

  ```
  {   
      "id": 4,
      "className": "com.bsi.bdc.quartzdemo.job.HelloJob",
      "cronExpression": "*/5 * * * * ?",
      "jobName": "HelloJob",
      "jobGroup": "TEST_GROUP",
      "triggerName": "HELLO_TRIGGER",
      "triggerGroup": "TEST_GROUP",
      "pause": false,
      "enable": true,
      "description": "Test HelloJob For SpringBoot",
      "createTime": "2019-11-13 07:26:25",
      "updateTime": "2019-11-14 01:34:54"
  }
  ```

  

- 获取所有任务

  ```
  GET /job/all HTTP/1.1
  Host: localhost:8080
  ```

  | response | status | msg          |
  | -------- | ------ | ------------ |
  | 成功     | 200    | 见下方代码块 |

  ```
  [
  		{
          "id": 4,
          "className": "com.bsi.bdc.quartzdemo.job.HelloJob",
          "cronExpression": "*/5 * * * * ?",
          "jobName": "HelloJob",
          "jobGroup": "TEST_GROUP",
          "triggerName": "HELLO_TRIGGER",
          "triggerGroup": "TEST_GROUP",
          "pause": false,
          "enable": true,
          "description": "Test HelloJob For SpringBoot",
          "createTime": "2019-11-13 07:26:25",
          "updateTime": "2019-11-14 01:34:54"
      }
  ]
  ```

- 删除单个任务

  ```
  DELETE /job/delete/3 HTTP/1.1
  Host: localhost:8080
  ```

  | response | status | msg         |
  | -------- | ------ | ----------- |
  | 成功     | 200    | 删除job成功 |
  | 失败     | 500    | 删除job失败 |

#### 备注
如果你有好的意见或建议，欢迎给我们提issue或pull request。
