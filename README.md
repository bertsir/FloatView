# FloatView
###老规矩，先上图

![floatView.gif](http://upload-images.jianshu.io/upload_images/3029020-d42fb5483266d90c.gif?imageMogr2/auto-orient/strip)

演示中用的是CardView嵌套的ListView,背景是摄像头，接下来说实现。理论上所有的View都能这么玩

讲一下大体逻辑，很简单，监听水平仪来动态改变来动态设置View的位置，接下来帖代码。

相机处理用的是自己封装的工具，喜欢的可以拿去用
