# WeatherForecast
Android 大作业，本乡下人倾心设计的ui，数据来自和风天气免费接口（真好），信息完整、界面简洁的天气预报。

以下摘自《Android 大作业实验报告》：
#### 应用安卓设计中的Activity组件开发技术，设计了一个WeatherForecast.apk 软件，经过认真的规划，编程，测试，成功实现主要的天气预报功能，
主要功能如下：
1. 支持显示实时天气状况，包括实时温度、天气情况、风力风向等信息；
2. 界面显示八种生活指数，包括穿衣指数、旅游指数等等；
3. 支持未来24小时内逐3个小时的短时预报；
4. 支持一个七天内的天气预报；
5. 支持搜索并收藏城市、切换城市，共支持国内2000多个城市


#### 该程序
1. 界面采用 MaterialDesign 的设计风格，主界面由一个Activity搭载单个Fragment构成，
Fragment 中又包含了四个RecyclerView，分别用于显示天气实况、逐小时预报、七天天气预报以及生活指数；
2. 应用的数据来自免费天气数据接口和风天气；
3. 应用的网络请求部分使用了开源框架okHttp，处理天气数据接口的Json数据用了开源框架Gson；
4. 应用搜索、选择并切换城市时，使用SharedPreference将应用数据保存到本地，实现数据持久化。

![](http://owx8bh6es.bkt.clouddn.com/2017-12-18%2022-17-43%20%E7%9A%84%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE.png)
![](http://owx8bh6es.bkt.clouddn.com/2017-12-18%2022-14-11%20%E7%9A%84%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE.png)
![](http://owx8bh6es.bkt.clouddn.com/2017-12-18%2022-14-48%20%E7%9A%84%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE.png)
![](http://owx8bh6es.bkt.clouddn.com/2017-12-18%2022-15-47%20%E7%9A%84%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE.png)
![](http://owx8bh6es.bkt.clouddn.com/2017-12-18%2022-17-03%20%E7%9A%84%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE.png)
