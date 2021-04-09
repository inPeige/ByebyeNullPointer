# ByebyeNullPointer

### 注解和反射
```java
  @Target(ElementType.type)//元注解是指注解上的注解
  @Retention(RetentionPolicy.SOURCE)
  public @interface Anm {
  }
```
##### Target 指定注解的作用域

1.TYPE ： 指定当前注解可以作用在class 、interface 、enum；
2.FIELD ：指定当前注解可以作用在参数上；
3.METHOD ：指定当前注解可以作用在方法上；
4.PARAMETER ：指定当前注解可以作用在参数上；
5.CONSTRUCTOR：指定当前注解可以作用在构造函数上；
##### Retention 保留级别

1.SOURCE： 注解会被保留在源码级别，会被编译器忽略
2.CLASS ：注解会被保留在字节码级别，会被编译器保留，但会被JVM忽略
3.RUNTIME： 注解会在运行时保留，由JVM保留，因此可以在运行时环境使用它
即： RUNTIME>CLASS>SOURCE
