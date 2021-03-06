package com.hyr.lean.base.design.create.factory;

/**
 * <b><code>FactoryDemo</code></b>
 * <p/>
 * Description 抽象工厂模式DEMO
 * <p/>
 * <b>Creation Time:</b> 2019/7/24 10:29.
 *
 * @author Hu-Weihui
 * @since hui-base-java ${PROJECT_VERSION}
 */
public class FactoryDemo {


    /**
     * 抽象产品
     */
    interface Product{
        void hellow();
    }

    /**
     * 具体产品A
     */
    static class ConcreteProductA implements Product{

        @Override
        public void hellow() {
            System.out.println("hellow concreteProductA");
        }
    }

    /**
     * 具体产品B
     */
    static class ConcreteProductB implements Product{

        @Override
        public void hellow() {
            System.out.println("hellow concreteProductB");
        }
    }

    /**
     * 抽象工厂
     */
    interface AbstractFactory{
        Product createProduct();
    }

    /**
     * 具体工厂A
     */
    static class ConcreteFacoryA implements AbstractFactory{

        @Override
        public Product createProduct() {
            System.out.println("concreteFacoryA  --> concreteProductA");
            return new ConcreteProductA();
        }
    }


    /**
     * 具体工厂B
     */
    static class ConcreteFactoryB implements AbstractFactory{

        @Override
        public Product createProduct() {
            System.out.println("concreteFacoryB  --> concreteProductB");
            return new ConcreteProductB();
        }
    }

    //客户端
    public static void main(String[] args) {
        AbstractFactory abstractFactory = null;

        abstractFactory = new ConcreteFacoryA();
        abstractFactory.createProduct().hellow();

        abstractFactory = new ConcreteFactoryB();
        abstractFactory.createProduct().hellow();
    }
}
