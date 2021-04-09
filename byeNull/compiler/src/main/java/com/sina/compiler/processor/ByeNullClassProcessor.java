package com.sina.compiler.processor;

import com.sina.compiler.ClassParam;
import com.sina.compiler.Consts;
import com.sina.compiler.Log;
import com.sina.compiler.ParamTree;
import com.sina.compiler.ProcessParam;
import com.sina.compiler.annotation.ByeNull;
import com.sina.compiler.annotation.ByeNullField;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;


//编译版本
@SupportedSourceVersion(SourceVersion.RELEASE_7)
//指定处理的注解
@SupportedAnnotationTypes(Consts.ANNOTATION_BYE_NULL)
public class ByeNullClassProcessor extends AbstractProcessor {
    private Messager mMessager;
    private Filer mFiler;
    private Types mTypeUtils;
    private Log log;
    private HashMap<String, Boolean> mByNullMap;
    private HashMap<TypeMirror, ArrayList<ProcessParam>> typeElementArrayListHashMap;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
        mFiler = processingEnvironment.getFiler();
        mTypeUtils = processingEnvironment.getTypeUtils();
        log = Log.newLog(mMessager);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mByNullMap = processByNull(roundEnvironment);
        typeElementArrayListHashMap = processElement(roundEnvironment);
        ArrayList<ClassParam> classParams = processClassParam(roundEnvironment);
        if (classParams.size() > 0) {
            for (ClassParam classParam : classParams) {
                createClassFile(classParam);
            }
        }
        return true;
    }

    public ArrayList<ParamTree> processElementParam(ArrayList<ProcessParam> params) {
        ArrayList<ParamTree> mFieldList = new ArrayList<>();
        for (int i = 0; i < params.size(); i++) {
            ProcessParam processParam = params.get(i);
            ParamTree childParam = processChildParam(processParam.getElement());
            mFieldList.add(childParam);
        }
        return mFieldList;
    }

    private ParamTree processChildParam(Element element) {
        Boolean isByNull = mByNullMap.get(element.asType().toString());
        if(isByNull!=null&&isByNull){
            return null;
        }
        ArrayList<ProcessParam> params = typeElementArrayListHashMap.get(element.asType());
        if(params==null){
            ParamTree paramTree = new ParamTree();
            paramTree.setValue(element.getSimpleName().toString());
            return paramTree;
        }
        ParamTree paramTree = new ParamTree();
        paramTree.setValue(element.getSimpleName().toString());
        LinkedList<ParamTree> linked = paramTree.getLinked();
        for (int i = 0; i < params.size(); i++) {
            ProcessParam processParam = params.get(i);
            ParamTree childParam = processChildParam(processParam.getElement());
            if(childParam!=null){
                linked.add(childParam);
            }
        }
        return paramTree;
    }

    /**
     * 收集需要处理的类防止ABABAB的问题出现
     *
     *例如如果对象A->对象B->对象A的问题
     * 通过ByeNull注解保证在遍历属性时如果遇到ABA的问题时将A过滤。
     * @param roundEnvironment
     * @return
     */
    private HashMap<String, Boolean> processByNull(RoundEnvironment roundEnvironment) {
        HashMap<String, Boolean> mByNullMap = new HashMap<>();
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ByeNull.class);
        for (Element element : elements) {
            String mClassName = element.asType().toString();
            mByNullMap.put(mClassName, true);
        }
        return mByNullMap;
    }

    /**
     *遍历子Param
     */
    private ArrayList<ClassParam> processClassParam(RoundEnvironment roundEnvironment) {
        ArrayList<ClassParam> mClassParams = new ArrayList<>();
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ByeNull.class);
        for (Element element : elements) {
            String mClassName = element.getSimpleName().toString();
            ArrayList<ProcessParam> params = typeElementArrayListHashMap.get(element.asType());
            ClassParam classParam = new ClassParam();
            if (params != null) {
                ArrayList<ParamTree> mFieldValues = processElementParam(params);
                classParam.setFieldValue(mFieldValues);
            }
            classParam.setClassName(mClassName);
            mClassParams.add(classParam);
        }
        return mClassParams;
    }


    /**
     * 解析所有ByeNullField注解的类。存入HashMap
     * @param roundEnvironment
     * @return
     */
    private HashMap<TypeMirror, ArrayList<ProcessParam>> processElement(RoundEnvironment roundEnvironment) {
        HashMap<TypeMirror, ArrayList<ProcessParam>> mTypeElementHashMap = new HashMap<>();
        //获取所有被ByeNullField注解的参数Element
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ByeNullField.class);
        for (Element element : elements) {
            //获取Element的父容器ClassName
            TypeMirror enclosingElement = element.getEnclosingElement().asType();
            ArrayList<ProcessParam> processParams = mTypeElementHashMap.get(enclosingElement);
            if (processParams == null) {
                processParams = new ArrayList<>();
                ProcessParam processParam = new ProcessParam(element, element.getSimpleName().toString());
                processParams.add(processParam);
                mTypeElementHashMap.put(enclosingElement, processParams);
            } else {
                ProcessParam processParam = new ProcessParam(element, element.getSimpleName().toString());
                processParams.add(processParam);
            }
        }
        return mTypeElementHashMap;
    }
    /**
     * 生成Class类
     *
     * @param classParams
     */
    private void createClassFile(ClassParam classParams) {
        String className = classParams.getClassName();
        //创建类
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className + Consts.CALSS_CONSTS);
        classBuilder.addModifiers(Modifier.PUBLIC);
        //创建参数
        ArrayList<ParamTree> fieldValue = classParams.getFieldValue();
        if(fieldValue!=null&&fieldValue.size()>0){
            for (int i = 0; i < fieldValue.size(); i++) {
                ParamTree paramTree = fieldValue.get(i);
                createField(classBuilder,paramTree,null);
            }
            TypeSpec build = classBuilder.build();
            JavaFile javaFile = JavaFile.builder(Consts.PACKAGE_OF_GENERATE_FILE, build).build();
            try {
                javaFile.writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写入字段。
     * @param classBuilder
     * @param tree
     * @param sb
     */
    public void createField(TypeSpec.Builder classBuilder ,ParamTree tree,StringBuilder sb){
        if(tree.getLinked()==null||tree.getLinked().size()==0){
            StringBuilder mCurrent = new StringBuilder();
            //1.表示当前注解字段类中Field没有被ByeNullField注解
            if(sb!=null){
                mCurrent.append(sb).append("$");
            }
            mCurrent.append(tree.getValue());
            log.i(mCurrent.toString());
            FieldSpec mFieldBuilder = FieldSpec.builder(String.class, mCurrent.toString().toUpperCase())
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$S", mCurrent.toString()).build();
            classBuilder.addField(mFieldBuilder);
            return;
        }
        StringBuilder mCurrent = new StringBuilder();
        if(sb!=null){
            mCurrent.append(sb).append("$");
        }
        mCurrent.append(tree.getValue());
        FieldSpec mFieldBuilder = FieldSpec.builder(String.class, mCurrent.toString().toUpperCase())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", mCurrent.toString()).build();
        classBuilder.addField(mFieldBuilder);
        LinkedList<ParamTree> linkeds = tree.getLinked();
        for (ParamTree mTree : linkeds) {
            createField(classBuilder,mTree,mCurrent);
        }
    }

}
