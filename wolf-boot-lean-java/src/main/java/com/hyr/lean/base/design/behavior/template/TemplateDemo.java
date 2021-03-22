package com.hyr.lean.base.design.behavior.template;

/**
 * <b><code>TemplateDemo</code></b>
 * <p/>
 * Description 模板方法模式DEMO
 *
 * ## 个人小解�?
 * 模板模式，做事情要有方法，套路�?�模板方法就是套路，套进去之后，根据事情的不同也可以通过子类重写�?
 *
 * ## 角色
 * 1. 抽象类（Abstract Class）：负责给出�?个算法的轮廓和骨架�?�它由一个模板方法和若干个基本方法构成�?�这些方法的定义如下�?
 *
 * 模板方法：定义了算法的骨架，按某种顺序调用其包含的基本方法�??
 *
 * 基本方法：是整个算法中的�?个步骤，包含以下几种类型�?
 * 抽象方法：在抽象类中申明，由具体子类实现�?
 * 具体方法：在抽象类中已经实现，在具体子类中可以继承或重写它�??
 * 钩子方法：在抽象类中已经实现，包括用于判断的逻辑方法和需要子类重写的空方法两种�??
 *
 * 2. 具体子类（Concrete Class）：实现抽象类中�?定义的抽象方法和钩子方法，它们是�?个顶级�?�辑的一个组成步骤�??
 *
 * ## 优点
 * 1. 它封装了不变部分，扩展可变部分�?�它把认为是不变部分的算法封装到父类中实现，而把可变部分算法由子类继承实现，便于子类继续扩展�?
 * 2. 它在父类中提取了公共的部分代码，便于代码复用�?
 * 3. 部分方法是由子类实现的，因此子类可以通过扩展方式增加相应的功能，符合�?闭原则�??
 *
 * ## 缺点
 * 1. 对每个不同的实现都需要定义一个子类，这会导致类的个数增加，系统更加庞大，设计也更加抽象�??
 * 2. 父类中的抽象方法由子类实现，子类执行的结果会影响父类的结果，这导致一种反向的控制结构，它提高了代码阅读的难度�?
 *
 * ## 应用场景�?
 * 1. 算法的整体步骤很固定，但其中个别部分易变时，这时候可以使用模板方法模式，将容易变的部分抽象出来，供子类实现�??
 * 2. 当多个子类存在公共的行为时，可以将其提取出来并集中到�?个公共父类中以避免代码重复�?�首先，要识别现有代码中的不同之处，并且将不同之处分离为新的操作。最后，用一个调用这些新的操作的模板方法来替换这些不同的代码�?
 * 3. 当需要控制子类的扩展时，模板方法只在特定点调用钩子操作，这样就只允许在这些点进行扩展�?
 *
 * <p/>
 * <b>Creation Time:</b> 2019/7/24 17:06.
 *
 * @author Hu-Weihui
 * @since hui-base-java ${PROJECT_VERSION}
 */
public class TemplateDemo {
   // 客户�?
    public static void main(String[] args) {
        Template template = null;

        template = new ConcreteClassA();
        template.templateMeth();

        template = new ConcreteClassB();
        template.templateMeth();
    }

    /**
     * 模板类，提供�?个模板方�?
     */
     static abstract class Template{
        final void templateMeth(){
            method1();

            method2();

            specialmethod();

        }

        void method1(){
            System.out.println("电饭锅插�?");
        }

        void method2(){
            System.out.println("放入材料");
        }

        abstract void specialmethod();

    }


    /**
     * 具体模板类A
     */
    static class ConcreteClassA extends Template{

        @Override
        void specialmethod() {
            System.out.println("按煮饭按钮，�?30min");
        }
    }

    /**
     * 具体模板类B
     */
    static class ConcreteClassB extends Template{

        @Override
        void specialmethod() {
            System.out.println("按煲汤按钮，�?1h");
        }
    }
}
