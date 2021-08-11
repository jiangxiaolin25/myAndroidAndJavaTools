package macc.paxsz.com.myapplication.Javatool;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * 作者：jiangxiaolin on 2020/3/12
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class GetAnnotationApi {

    /***
     * 获取一个方法上的
     * @param oArraydemo   添加了自定义注解定义的方法所在的类里面的对象，具体情况具体分析
     * 备注：尽量把自定义定义在同一个类里面
     */
    private static void annotation(Object oArraydemo ) {
        try {
//			arraydemo reflectassert=new arraydemo();
            //获取Student的Class对象
            Class stuClass = oArraydemo.getClass();

            //说明一下，这里形参不能写成Integer.class，应写为int.class
            Method stuMethod = stuClass.getMethod("study",int.class);

            if(stuMethod.isAnnotationPresent((Class<? extends Annotation>) CherryAnnotation.class)){
                System.out.println("Student类上配置了CherryAnnotation注解！");
                //获取该元素上指定类型的注解
                CherryAnnotation cherryAnnotation = stuMethod.getAnnotation(CherryAnnotation.class);

                System.out.println("name: " + cherryAnnotation.name() + ", age: " + cherryAnnotation.age()
                        + ", score: " + cherryAnnotation.score()[0]);
            }else{
                System.out.println("Student类上没有配置CherryAnnotation注解！");
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @CherryAnnotation(name = "cherry-peng",age = 23,score = {99,66,77})
//		@CherryAnnotation(name = "", score = { 0 })
//把注解添加到方法上
    public void study(int times){
        for(int i = 0; i < times; i++){
            System.out.println("Good Good Study, Day Day Up!");
        }
    }

//自定义注解
    @Retention(RetentionPolicy.RUNTIME)
    @Target(value = {ElementType.METHOD})
    public @interface CherryAnnotation {
        String name();
        int age() default 18;
        int[] score();
    }

}
