package com.amberllo.butterknife_compiler;

import com.amberllo.butterknife_annotation.BindView;
import com.amberllo.butterknife_annotation.ViewBinder;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes("com.amberllo.butterknife_annotation.BindView")
@AutoService(Processor.class)
public class BindProcessor extends AbstractProcessor {

    Filer filer;
    Messager messager;
    Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
        this.elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Map<String/** qualifiedName */, List<Element>> bindViews = new HashMap<>();


        Set<? extends Element> bindViewElements = roundEnv.getElementsAnnotatedWith(BindView.class);

        for (Element element : bindViewElements) {

            TypeElement classElement = (TypeElement) element.getEnclosingElement();
            //com.amberllo.butterknife.app.MainActivity
            String qualifiedName = classElement.getQualifiedName().toString();

            List<Element> list = bindViews.get(qualifiedName);
            if (list == null) {
                list = new ArrayList<>();
                bindViews.put(qualifiedName, list);
            }
            list.add(element);
        }


        for (Map.Entry<String, List<Element>> entry : bindViews.entrySet()) {
            String className = entry.getKey(); //Activity类名
            List<Element> elements = entry.getValue(); //Activity 里面的注解元素
            makeFile(className, elements);
        }

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(BindView.class.getName());
        return set;
    }

    public void makeFile(String tag, List<Element> elements) {
        try {

            TypeElement typeElement = elementUtils.getTypeElement(tag);

            ClassName className = ClassName.get(typeElement);


            String clazzName = className.simpleName();
            String packageName = className.packageName();

            String clazzFullName = className.reflectionName();

            MethodSpec.Builder bindMethodBuilder = MethodSpec.methodBuilder("bind")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class)
                    .addParameter(Object.class, "target")
                    .addAnnotation(Override.class)
                    .addCode("  "+clazzFullName + " activity = (" + clazzFullName + ")target; \n");

            for (Element element : elements) {
                String varName = element.getSimpleName().toString();
                BindView annotation = element.getAnnotation(BindView.class);
                int viewId = annotation.value();
                bindMethodBuilder.addCode("  activity."+varName+" = activity.findViewById("+viewId+");\n");
            }


            TypeSpec activityClass = TypeSpec.classBuilder(clazzName + "$$ViewBinder")
                    .addSuperinterface(ViewBinder.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(bindMethodBuilder.build())
                    .build();

            JavaFile javaFile = JavaFile.builder(packageName, activityClass).build();
            javaFile.writeTo(filer);
        } catch (Exception e) {

            e.printStackTrace();
        }


    }
}
