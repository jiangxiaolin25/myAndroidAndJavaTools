package macc.paxsz.com.myapplication.Javatool;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/***
 * 主要是反射相关的一些操作
 * @author jiangxiaolin
 *除了这些方法外，还有可以获取包名，父类的名字，修饰符等操作
 */
public class ReflectApi {
	/***
	 * 通过对象获取class对象
	 * @param o
	 * @return
	 */
	public Class getclass1(Object o) {
		Class class1 = o.getClass();
		return class1;
	}



	/***
	 * 通过类路径获取类对象
	 * @param classpath  格式是.格式不是/格式  如Javamethedtool（包名）.reflectassert（类名）  而不是Javamethedtool/reflectassert
	 * @return
	 */
	public Class getclass2(String classpath) {
		Class class1 = null;
		try {
			class1 = Class.forName(classpath);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("没有找到类，老哥");
			e.printStackTrace();
		}
		return class1;


	}

	/**通过反射调用无参数的方法
	 * @param o  需要通过对象获取class1方式去获取，
	 * @param MethName  调用方法的名字
	 */
	public  static  void InvokeNoPara (Object o, String MethName) {
		Class class1 = o.getClass();
		Method declaredMethod = null;
		try {
			declaredMethod = class1.getDeclaredMethod(MethName);
			declaredMethod.invoke(o);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**通过反射调用有参数的方法
	 * @param o   需要通过对象获取class1方式去获取，
	 * @param MethName  调用方法的名字
	 * @return   方法返回的值，依据具体的方法确定
	 */
	public  static  Object InvokeHavePara (Object o, String MethName) {
		Class class1 = o.getClass();
		Method declaredMethod = null;
		Object invoke = null;
		try {
			//后面的参数根据方法具体的参数定义
			declaredMethod = class1.getDeclaredMethod(MethName,new Class[]{int.class,int.class});
			//后面是方法的具体的值
			invoke = declaredMethod.invoke(o, 2, 3);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return invoke ;
	}

	//还有一种是Class clazz = Car.class;直接通过类名获取

	/***
	 * 通过类的对象获取新建实例，只能获取空参数的构造函数
	 *
	 * @param c   类的对象
	 * @return
	 */
	public Object getobject(Class c) {
		Object newInstance = null;
		try {
			newInstance = c.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newInstance;
	}

	/***
	 *  通过getConstructor获取构造函数，可以是有参数的构造函数
	 * getDeclaredConstructor获取的是public的和非public的
	 * getConstructor获取的只能是public
	 * @param c  类的对象
	 * @return 类的对象
	 */
	public Object getobjectconstruc(Class c, int constructorarg) {
		Constructor constructor = null;
		Object newInstance = null;
		try {
			constructor = c.getConstructor(int.class);//int 类型在后续使用时更改即可
			try {
				newInstance = constructor.newInstance(constructorarg);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newInstance;


	}

	/***
	 * 获取fieldname指定的类属性  要想获取到该属性的具体值还需要field.get(mrReflectassert);mrReflectassert是通过NEW 出来的包含属性类的对象，
	 * 表示的是在这个类中的该field的值
	 * @param c   类的类型，
	 * @param fieldname  类变量的名称
	 * @return
	 */
	public Field getfield1(Class c, String fieldname) {
		Field field = null;
		try {
			field = c.getField(fieldname);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return field;

	}

	/***
	 *通过反射修改类的属性的值
	 * @param c  类的类对象
	 * @param o  new 的类对象
	 * @param fieldname  属性的名字
	 * @param value     需要修改属性的值
	 */
	public void setfieldvalue(Class c, Object o, String fieldname, String value) {
		Field field = null;
		Object object = null;
		try {
			field = c.getField(fieldname);
			field.setAccessible(true); //去除私有权限 就算是私有的也会获取到
			field.set(o, value);
			object = field.get(o);//获取修改后的值
			System.out.println("设置后的值为" + object);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/***
	 * 根据方法名调用公共方法，并获取到返回值
	 * @param c    类的类对象
	 * @param methonname   想要调用方法的名字
	 * @param methodarg    调用方法需要传入的参数，后续使用过程如有变化，随机改变即可
	 * @return 调用方法后返回的值
	 */
	public Object methodinvoKe(Class c, String methonname, String methodarg) {
		Method method = null;
		Object invoke = null;
		try {
			method = c.getMethod(methonname, String.class);
			method.setAccessible(true);
			invoke = method.invoke(c.newInstance(), methodarg);
			System.out.println("调用方法返回的值" + invoke);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return invoke;


	}

	/***
	 * 根据方法名调用私有方法，并获取到返回值
	 * @param c    类的类对象
	 * @param methonname   想要调用方法的名字
	 * @param methodarg    调用方法需要传入的参数，后续使用过程如有变化，随机改变即可
	 * @return 调用方法后返回的值
	 */
	public Object demethodinvoKe(Class c, String methonname, String methodarg) {
		Method method = null;
		Object invoke = null;
		try {
			method = c.getDeclaredMethod(methonname, String.class);
			method.setAccessible(true);//这句话必须要有
			invoke = method.invoke(c.newInstance(), methodarg);
			System.out.println("调用方法返回的值" + invoke);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return invoke;


	}


}
