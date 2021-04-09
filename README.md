# ByebyeNullPointer

### 注解和反射
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
| CLASS | 字节码增强 | 在编译出class后，通过修改class数据实现修改代码逻辑的目的。|
| RUNTIME  | 反射 | 在程序运行期间，通过反射技术动态获取注解及其元素，从而完成不同的逻辑判断 |

