### 一、注解和反射
```java
  @Target(ElementType.type)//元注解是指注解上的注解
  @Retention(RetentionPolicy.SOURCE)
  public @interface Anm {
  }
```
**元注解**是指注解上的注解

**Target** 指定注解的作用域

- **TYPE** ： 指定当前注解可以作用在class 、interface 、enum；
- **FIELD** ：指定当前注解可以作用在参数上；
- **METHOD** ：指定当前注解可以作用在方法上；
- **PARAMETER** ：指定当前注解可以作用在参数上；
- **CONSTRUCTOR**：指定当前注解可以作用在构造函数上；

**Retention** 保留级别
- **SOURCE**： 注解会被保留在源码级别，会被编译器忽略
- **CLASS** ：注解会被保留在字节码级别，会被编译器保留，但会被JVM忽略
- **RUNTIME**： 注解会在运行时保留，由JVM保留，因此可以在运行时环境使用它
即： RUNTIME>CLASS>SOURCE 

**根据注解不同的保留级别，自然有不同的使用场景**
|  级别   | 技术  | 说明  |
| --- | --- | --- |
| SOURCE  |  APT  | 在编译期间获取注解与注解声明的类包含的所有成员信息，一般用于生成辅助类相应的技术（JavaPoet）  |
| CLASS | 字节码增强（ASM） | 在编译出class后，通过修改class数据实现修改代码逻辑的目的。|
| RUNTIME  | 反射 | 在程序运行期间，通过反射技术动态获取注解及其元素，从而完成不同的逻辑判断 |



### ByeNull思想

# ByebyeNullPointer

实际开发过程中对象的层级特别深，当我们获取某个对象时。需要对前置对象做大量的判断，费时费力。如果不做有可能会空指针异常。
```java
status2.taginfo.tagname
if (status2.getTagInfo() != null && TextUtils.isEmpty(status2.getTagInfo().getTagName())) {
            //too do
}
```



### ByeNull思想

##### 思考 

 1.运行时通过反射可以获取当前对象的Field属性

- 如果我们能过拿到我们想要获取对象的调用链路,那么是否就可以通过反射来判断链路中是否有空对象？  

```java
public static final String MVIDEOINFO$MTAG$ACTIONLOG = "mVideoInfo$mTag$actionLog";

private static Object isFieldNull(Object mObj, String mField) throws NoSuchFieldException, 	IllegalAccessException {
        Class<?> mClass = mObj.getClass();
        //获取当前Obj的Field属性
        Field mClassField =  mClass.getDeclaredField(mField);
        mClassField.setAccessible(true);
        return mClassField.get(mObj);
    }
//传入路径和需要校验的对象 
public static boolean isNull(Object mObj,String path){
        if (TextUtils.isEmpty(path) || mObj == null) {
            return false;
        }
        String[] fieldArray = parsPath(path);
        if(fieldArray.length<=0){
            return false;
        }
        Object mFieldObj=mObj;
        try {
            for (String s : fieldArray) {
                mFieldObj = isFieldNull(mFieldObj, s);
                if(mFieldObj==null){
                    return false;
                }
            }
            return true;
        }catch ( NoSuchFieldException|IllegalAccessException e){
            return false;
        }
 }


  private static String[] parsPath(String path){
       return path.split("\\$");
  }

```

### 问题：如何找到链路？

```java
@ByeNull
public class Status {

    private String mid;
    @ByeNullField
    private VideoInfo mVideoInfo;
    @ByeNullField
    private TagInfo mTagInfo;
}
public class VideoInfo {
    @ByeNullField
    private TagInfo mTag;
    @ByeNullField
    private CommInfo mComm;
    @ByeNullField
    private TagList mTagList;
}
```

```java
public class Status$Consts {
  public static final String MVIDEOINFO = "mVideoInfo";

  public static final String MVIDEOINFO$MTAG = "mVideoInfo$mTag";

  public static final String MVIDEOINFO$MTAG$ACTIONLOG = "mVideoInfo$mTag$actionLog";

  public static final String MVIDEOINFO$MTAG$USERINFO = "mVideoInfo$mTag$userInfo";

  public static final String MVIDEOINFO$MCOMM = "mVideoInfo$mComm";

  public static final String MVIDEOINFO$MTAGLIST = "mVideoInfo$mTagList";

  public static final String MTAGINFO = "mTagInfo";

  public static final String MTAGINFO$ACTIONLOG = "mTagInfo$actionLog";

  public static final String MTAGINFO$USERINFO = "mTagInfo$userInfo";
}
```

```java
if (status2.getTagInfo() != null &&status2.getTagInfo().getActionLog()!=null&&xxxx&&xxxx) {
            //too do
}

boolean isNull1 = ByNullUtils.isNull(status1, Status$Consts.MVIDEOINFO$MTAG$ACTIONLOG);

```



##### APT 技术

APT（Annotation Processing Tool）技术通过编译期解析注解，并且生成Java代码的一种技术，一般会结合Javapoet技术生成Java代码。

##### SPI机制

**SPI（Service Provider Interface）**的作用是为接口寻找服务实现，在项目中通过配置文件为接口寻找服务实现。

- 首先在main目录下创建resources目录。

- 然后在resources目录下创建META-INF目录。

- 接下来在META-INF目录下创建services目录。

- 最后创建一个文件名为**javax.annotaion.processing.Processor**的文件，其中的内容就是为接口寻找服务实现的全类名。

- ![SPI机制](/Users/liupei1/Library/Application Support/typora-user-images/image-20210409143407350.png)





### 遗留问题

1. ABA的问题
2. 反射消耗性能     思考：通过ASM将Utils消除，转化为判空代码