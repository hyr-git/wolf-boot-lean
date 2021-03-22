package com.hyr.lean.base.design.behavior.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>ObserverDemo</code></b>
 * <p/>
 * Description 观察者模式DEMO
 *
 * ## 个人小解�?
 * 观察者�?�观察某个目标，针对这个目标的行为，相应的我做某些东�?
 *
 * ## 角色
 * 1. 抽象主题（Subject）角色：也叫抽象目标类，它提供了�?个用于保存观察�?�对象的聚集类和增加、删除观察�?�对象的方法，以及�?�知�?有观察�?�的抽象方法�?
 * 2. 具体主题（Concrete    Subject）角色：也叫具体目标类，它实现抽象目标中的�?�知方法，当具体主题的内部状态发生改变时，�?�知�?有注册过的观察�?�对象�??
 * 3. 抽象观察者（Observer）角色：它是�?个抽象类或接口，它包含了�?个更新自己的抽象方法，当接到具体主题的更改�?�知时被调用�?
 * 4. 具体观察者（Concrete Observer）角色：实现抽象观察者中定义的抽象方法，以便在得到目标的更改通知时更新自身的状�?��??
 *
 * ## 优点
 * 1. 降低了目标与观察者之间的耦合关系，两者之间是抽象耦合关系�?
 * 2. 目标与观察�?�之间建立了�?套触发机制�??
 *
 * ## 缺点
 * 1. 目标与观察�?�之间的依赖关系并没有完全解除，而且有可能出现循环引用�??
 * 2. 当观察�?�对象很多时，�?�知的发布会花费很多时间，影响程序的效率�?
 *
 * ## 应用场景�?
 * 1. 对象间存在一对多关系，一个对象的状�?�发生改变会影响其他对象�?
 * 2. 当一个抽象模型有两个方面，其中一个方面依赖于另一方面时，可将这二者封装在独立的对象中以使它们可以各自独立地改变和复用�?
 *
 *
 * <p/>
 * <b>Creation Time:</b> 2019/7/24 16:33.
 *
 * @author Hu-Weihui
 * @since hui-base-java ${PROJECT_VERSION}
 */
public class ObserverDemo {

    //客户�?
    public static void main(String[] args) {
        Subject subject=new ConcreteSubject();
        Observer obs1=new ConcreteObserver();
        Observer obs2=new ConcreteObserver();
        subject.add(obs1);
        subject.add(obs2);
        subject.notifyObservers();
    }


    /**
     * 抽象观察�?
     */
    interface Observer{
        void response();
    }


    /**
     * 具体观察�?
     */
    static class ConcreteObserver implements Observer{
        @Override
        public void response() {
            System.out.println("公众号发送文章啦!!! ");
        }
    }

    /**
     * 抽象被观察目�?
     */
    static abstract class Subject {

        protected List<Observer> observers = new ArrayList<>();

        void add(Observer observer){
            System.out.println("�?个订阅�?�新�?");
            observers.add(observer);
        }

        void remove(Observer observer){
            System.out.println("�?个订阅�?�取�?");
            observers.remove(observer);
        }

        public abstract void notifyObservers();
    }


    /**
     * 具体被观察目�?
     */
    static class ConcreteSubject extends Subject{
        @Override
        public void notifyObservers() {
            for (Observer observer : super.observers) {
                observer.response();
            }
        }
    }
}
