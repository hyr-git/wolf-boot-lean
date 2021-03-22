package com.hyr.lean.base.design.behavior.state;

import lombok.Data;

/**
 * <b><code>StateDemo</code></b>
 * <p/>
 * Description 状�?�模�?
 *
 * ## 个人小解�?
 * 状�?�模式，�?个行为有多个状�?��?�状态模式就是根据行为切换对应状�?
 *
 * ## 角色
 * 1. 环境（Context）角色：也称为上下文，它定义了客户感兴趣的接口，维护�?个当前状态，并将与状态相关的操作委托给当前状态对象来处理�?
 * 2. 抽象状�?�（State）角色：定义�?个接口，用以封装环境对象中的特定状�?�所对应的行为�??
 * 3. 具体状�?�（Concrete    State）角色：实现抽象状�?�所对应的行为�??
 *
 * ## 优点
 * 1. 状�?�模式将与特定状态相关的行为�?部化到一个状态中，并且将不同状�?�的行为分割�?来，满足“单�?职责原则”�??
 * 2. 减少对象间的相互依赖。将不同的状态引入独立的对象中会使得状�?�转换变得更加明确，且减少对象间的相互依赖�??
 * 3. 有利于程序的扩展。�?�过定义新的子类很容易地增加新的状�?�和转换�?
 *
 * ## 缺点
 * 1. 状�?�模式的使用必然会增加系统的类与对象的个数�??
 * 2. 状�?�模式的结构与实现都较为复杂，如果使用不当会导致程序结构和代码的混乱�?
 *
 * ## 应用场景�?
 * 1. 当一个对象的行为取决于它的状态，并且它必须在运行时根据状态改变它的行为时，就可以考虑使用状�?�模式�??
 * 2. �?个操作中含有庞大的分支结构，并且这些分支决定于对象的状�?�时�?
 *
 * <p/>
 * <b>Creation Time:</b> 2019/7/24 17:51.
 *
 * @author Hu-Weihui
 * @since hui-base-java ${PROJECT_VERSION}
 */
public class StateDemo {

    //客户�?
    public static void main(String[] args) {

        Context context = new Context();

        context.handle();
        context.handle();


    }

    /**
     * 环境上下�?
     */
    @Data
    static class Context{
        private State state;

        public Context() {
            this.state = new ConcreteStateA();
        }

        public void handle(){
            state.handle(this);
        }
    }

    /**
     * 状�?�类
     */
    interface State{
        void handle(Context context);
    }


    /**
     * 具体状�?�A
     */
    static class ConcreteStateA implements State{
        @Override
        public void handle(Context context) {
            System.out.println("当前状�?�：未登�?");
            System.out.println("执行登录......");
            context.setState(new ConcreteStateB());
        }
    }

    /**
     * 具体状�?�B
     */
    static class ConcreteStateB implements State{

        @Override
        public void handle(Context context) {
            System.out.println("当前状�?�：登录");
            System.out.println("�?出登�?......");
            context.setState(new ConcreteStateA());
        }
    }
}
