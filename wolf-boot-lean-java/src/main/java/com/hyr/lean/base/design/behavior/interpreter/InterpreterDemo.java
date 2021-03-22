package com.hyr.lean.base.design.behavior.interpreter;

import java.util.Arrays;

/**
 * <b><code>InterpreterDemo</code></b>
 * <p/>
 * Description: 解释器模�?
 *
 * ## 个人小解�?
 * 解释器，类似编译器，就是将一些上下文 解释�? 我们能定义好的意义并执行对应操作
 *
 * ## 角色
 * 1. 抽象处理者（Handler）角色：定义�?个处理请求的接口，包含抽象处理方法和�?个后继连接�??
 * 2. 具体处理者（Concrete Handler）角色：实现抽象处理者的处理方法，判断能否处理本次请求，如果可以处理请求则处理，否则将该请求转给它的后继者�??
 * 3. 客户类（Client）角色：创建处理链，并向链头的具体处理�?�对象提交请求，它不关心处理细节和请求的传�?�过程�??
 *
 * ## 优点
 * 1. 扩展性好。由于在解释器模式中使用类来表示语言的文法规则，因此可以通过继承等机制来改变或扩展文法�??
 * 2. 容易实现。在语法树中的每个表达式节点类都是相似的，所以实现其文法较为容易�?
 *
 * ## 缺点
 * 1. 执行效率较低。解释器模式中�?�常使用大量的循环和递归调用，当要解释的句子较复杂时，其运行速度很慢，且代码的调试过程也比较麻烦�?
 * 2. 会引起类膨胀。解释器模式中的每条规则至少�?要定义一个类，当包含的文法规则很多时，类的个数将急剧增加，导致系统难以管理与维护�?
 * 3. 可应用的场景比较少�?�在软件�?发中，需要定义语�?文法的应用实例非常少，所以这种模式很少被使用到�??
 *
 * ## 应用场景�?
 * 1. 当语�?的文法较为简单，且执行效率不是关键问题时�?
 * 2. 当问题重复出现，且可以用�?种简单的语言来进行表达时�?
 * 3. 当一个语�?�?要解释执行，并且语言中的句子可以表示为一个抽象语法树的时候，�? XML 文档解释�?
 *
 * <p/>
 * <b>Creation Time:</b> 2019/7/25 21:21.
 *
 * @author HuWeihui
 */
public class InterpreterDemo {

    //客户�?
    public static void main(String[] args) {
        String[] infos = new String[]{"1","+","1","="};
        Context context = new Context(infos);
        context.operation();
    }

    /**
     * 抽象表达�?
     */
    interface AbstractExpression{

        boolean interpreter(String[] infos);

        String result();
    }

    /**
     * 终结表达�?
     */
    static class TerminalExpression implements AbstractExpression{

        @Override
        public boolean interpreter(String[] infos) {
            for (String info : infos) {
                if (info.equals("=")){
                    return true;
                }
            }
            return false;
        }

        @Override
        public String result() {
            return "";
        }
    }

    /**
     * 非终结表达式
     */
    static class NonterminalExpression implements AbstractExpression{
        private int val;

        private AbstractExpression expression;

        public NonterminalExpression(AbstractExpression expression) {
            this.expression = expression;
        }

        @Override
        public boolean interpreter(String[] infos) {
            if (expression.interpreter(infos)){
                Integer val1 = Integer.valueOf(infos[0]);
                Integer val2 = Integer.valueOf(infos[2]);

                if (infos[1].equals("+")){
                    val = val1 + val2;
                }
                return true;
            }
            return false;
        }

        @Override
        public String result() {
            return String.valueOf(val);
        }
    }

    /**
     * 上下文环�?
     */
    static class Context{
        private AbstractExpression expression;

        private String[] infos;

        public Context(String[] infos) {
            this.infos = infos;

            AbstractExpression abstractExpression = new TerminalExpression();

            expression = new NonterminalExpression(abstractExpression);
        }


        public void operation(){
            boolean interpreterSuccess = expression.interpreter(infos);

            if (interpreterSuccess){
                System.out.println(Arrays.toString(infos) + " " + expression.result());
            }else {
                System.out.println();
            }
        }

    }
}
