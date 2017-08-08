        **Skin集成文档**


此框架无需启动应用即可一键换肤，使用简单，暂时无其它副作用。有BUG欢迎issue
支持Android  原生view和自定义View，窗体（dialog ， popouWindow,windowManager）等换肤。



**第一步：**
你需要打包一个APK的skin，把生成的XXX.Apk修改成XXX.skin
很简单就是需要新建一个Android工程，里面存放你需要换肤的资源（drawable ，color (attr),color文件）等。
注意：命名一定要与你的项目中的命名要一样，否则会找不到该资源。
**第二步：**
```
在application的onCreate()中去初始化框架：
SkinManager.getInstance().init(this);
SkinManager.getInstance().load();
```
当然如果你想打印skin log 可以	SkinManage.getInstance().isOpenLog(true);;
默认log是关闭的。

假如：你想你的应用换肤的时候发送广播或者共享换肤标识(一般用不到)
需要再application的onCreate中去初始化:
```
SkinManager.getInstance().initSDK(this);
则可以不用初始化：SkinManager.getInstance().init(this);
```
**第三步：**

有三个base继承，降低了继承的难度（当然只作为参考）
分别是：
``
BaseSkinActivity
BaseSkinFragmentActivity
BaseSkinFragment
``
如果你的base已经继承了第三方Base可以把框架的Base作为参考，复制到你的Base中即可.

**第四步：**
``File skin = new File(“skin文件路径”);``

如果想恢复默认资源
调用SkinManager.getInstance().restoreDefaultTheme()

其中有三个load的方法区别：
```
SkinManager.getInstance().load(skinPackagePath,callback)：第一次加载外部资源。得到一个skin后  （三个过程的回调都发生在主线程）

SkinManager.getInstance().loadCallBack(callback);
```
如果你之前加载这个资源后，以后想再去加载直接调用这个即可

SkinManager.getInstance().loadPath(skinPath)：
加载资源。不想得到一个应用的skin callback。传入skin路径即可

你也可以在代码中动态的添加SkinView
如：
```
 代码中常有给View动态的去设置BackgroundimageView.setBackground(R);
 
 使用SkinManager.getInstance().getDrwable(R.drwable.XXX)获取Drawable
```
如：
```
imageView.setBackground(SkinManager.getInstance().getDrawable(R.drawable.XXX));
```
Color,dimen,string 同理
按照以上都是基本上可以完成大部分换肤需求


**自定义View换肤**
特别系列：
假如现在我们需要支持自定义View换肤该怎么办?
如：
```
<MyView
app:mytextColor = "@color/XXXX"
/>
```
在MyView类中需提供一个setAttr（...） 方法 供 SkinAttrHolder中去setskin

**第一步：**
写一个attrHolder 继承自 SkinAttrHolder
重写 apply() 方法：
如：

参照框架的SkinAttrHolder即可：

接下来就是把改属性注入到框架内（注意一定要在setContentView之前注册）：

```
SkinManager.getInstance().registAttrHolder("mytextColor",newMyViewColorHolder());
```
attrName （mytextColor）建议不要重复命名，不要跟Android自带attribute一样
如果一样可以继承之前的attrHolder Override  apply方法   super（）不可注释掉

以上就是自定义View attr change skin 方式
假如你说怎么每次都需要得到一个skinManager的实例很麻烦？
没关系框架支持链试调用如

```
SkinManager.getInstance()
.registAttrHolder("mytextColor",new MyViewColorHolder()
.registAttrHolder("otherTextColor",new   OtherViewColorHolder());
```

恩？还觉得不够优雅，也可以使用map的方式注入自定义属性
SkinManager.getInstance().registAttrHolderMap(attrMap)

突然需求更改了。某某个自定义属性不想支持attr了
没关系你可以选择任何时机去调用
SkinManager.getInstance().removeAttrHolder(attrName, skin)即可移除该属性。

**特殊windowManager的支持**


你现在又有新的疑惑那各种窗体如：（dialog ， popouWindow,windowManager）
怎么办?

其实该框架直接是可以支持各种窗体的（activity中创建的window）
但是你又说，恩？在activity中创建Window是可以的，但是我现在需要在服务，广播中，甚至是另外一个应用以一个广播的形式启动服务（service）再创建 各种窗体呢？（简单的说Window不是在activity中创建的）
现在就需要用到另外一个类：

```
SkinSuffixWindowManager：
SkinSuffixWindowManager.getInstace().addWindowView(mView).applySkinForViews(true);
传入你的View,设置为true 就是ViewChild也支持换肤
或者你只想传入view resID
mView=SkinSuffixWindowManager.getInstace().addWindowViewRef(viewId, root).applySkinForViews(true);
Addviewm(View)
```
规范如：
tag = (view_color = 资源文件中的id，background = 设置background属性 ，color =  color资源)
```
    <RelativeLayout
        android:id="@+id/popup_window"
        android:layout_width="160dp"
        android:layout_height="100dp"
        android:orientation="vertical"
         android:background="@color/view_color"
        android:tag="skin:view_color:background:color">
        </RelativeLayout>
```
如果你想支持更多属性：
 配置规范  skin:xx|xx   

```
  <RelativeLayout
        android:id="@+id/popup_window"
        android:layout_width="160dp"
        android:layout_height="100dp"
        android:orientation="vertical"
         android:background="@color/view_color"
 		<!--android:background="@color/other_view_color"-->

android:tag="skin:view_color:background:color|other_view_color:background:color">
</RelativeLayout>
```

**如果想要更灵活的切换N种theme**
**只需要两步：**

**SkinSwitchManager**

第一步：
``` 
SkinSwitchManager.getSkinSwitchManager().saveSkinTheme(1, "第一个skin路径")
                .saveSkinTheme(2, "第二个skin路径");
```
第二步：
 想要切换到第一个主题直接调用：
 ``` SkinSwitchManager.getSkinSwitchManager().switchSkinTheme(1);```


如果恢复到默认主题：
 ```SkinSwitchManager.getSkinSwitchManager().restoreDefaultTheme();```


----------------------欢迎start-----------------------

