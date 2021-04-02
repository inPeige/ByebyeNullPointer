package com.sina.compiler.processor;

import com.sina.compiler.Consts;
import com.sina.compiler.Log;
import com.sina.compiler.annotation.ByeNull;
import com.sina.compiler.annotation.ByeNullField;

import java.util.List;
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
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


//编译版本
@SupportedSourceVersion(SourceVersion.RELEASE_7)
//指定处理的注解
@SupportedAnnotationTypes(Consts.ANNOTATION_BYE_NULL)
public class ByeNullClassProcessor extends AbstractProcessor {
    private Messager mMessager;
    private Filer mFiler;
    private Types mTypeUtils;
    private Log log;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
        mFiler = processingEnvironment.getFiler();
        mTypeUtils = processingEnvironment.getTypeUtils();
        log= Log.newLog(mMessager);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        processElement(roundEnvironment);
        return true;
    }

    private void processElement(RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ByeNullField.class);
        for (Element element : elements) {
            log.i(element.getSimpleName().toString());
            log.i(element.getEnclosingElement().getSimpleName().toString());
            log.i(element.asType().toString());
            log.i(element.getModifiers().toString());
            log.i(element.getKind().name().toString());
        }
    }
}
