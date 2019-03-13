# Service Alarm Platform 使用教程
> 通过该平台接入 Service 、GateWay 等服务或平台暴露的监控接口，通过配置监控规则和通知方式，可近实时地监控服务状态。

## 首页
> Github:  <a href="https://github.com/ngbdf/service-alarm-platform" target="_blank">Service Alarm Platform</a>

![](https://user-gold-cdn.xitu.io/2019/3/13/1697596a32916e42?w=1920&h=937&f=png&s=391442)

## 微信报警&RocketChat报警
<html>
  <table style="margin-left: auto; margin-right: auto;">
    <tr>
      <td>
        <img src="./docs/images/wechat-monitor.png"/>
      </td>
      <td>
        <img src="./docs/images/rocketchat-monitor.png"/>
      </td>
    </tr>
  </table>
</html>



## 项目架构

![](https://user-gold-cdn.xitu.io/2019/3/13/1697595a72eb5a71?w=952&h=509&f=png&s=19960)

## 项目数据库关系

![](https://user-gold-cdn.xitu.io/2019/3/13/1697595fb6433be9?w=958&h=569&f=png&s=10989)

## 添加组
> **GroupName**: 组名  
> **Description**: 组介绍

![](https://user-gold-cdn.xitu.io/2019/3/13/16975984f32ec3b6?w=1920&h=937&f=png&s=256888)


## 添加 Service
> **GroupName**: 组名（不可更改）  
> **Service Name**: 服务名称  
> **Description**: 服务描述  
> **Alarm Way**: 报警方式，默认提供微信和 RocketChat 报警方式

![](https://user-gold-cdn.xitu.io/2019/3/13/169759aa4a526018?w=1920&h=937&f=png&s=41967)

![](https://user-gold-cdn.xitu.io/2019/3/13/16975a02c524b597?w=1920&h=937&f=png&s=28974)

## 添加 URL
> 填写完Service信息后，点击跳转到URL管理页面，根据暴露的接口填入相应的数据，点击 ``TEST`` 按钮对填入的URL及参数进行校验，校验通过后点击 ``SAVE`` 按钮。

![](https://user-gold-cdn.xitu.io/2019/3/13/1697599cf3d25d61?w=1920&h=937&f=png&s=44361)

![](https://user-gold-cdn.xitu.io/2019/3/13/16975a058ee38197?w=1920&h=937&f=png&s=31345)

## 添加 Rule
> 填写完URL信息后，点击跳转到Rule管理页面，点击 ``REQUEST`` 按钮，显示监控数据
> Alias: 规则别名
> Formula: 监控规则，点击监控数据中所要监控的数据的 ``Key``，则会自动生成规则，如果需要调整，可自行编辑
> Description: 规则描述

![](https://user-gold-cdn.xitu.io/2019/3/13/16975a46849f1272?w=1920&h=937&f=png&s=51050)

![](https://user-gold-cdn.xitu.io/2019/3/13/16975a89c9b783c8?w=1920&h=937&f=png&s=32697)

