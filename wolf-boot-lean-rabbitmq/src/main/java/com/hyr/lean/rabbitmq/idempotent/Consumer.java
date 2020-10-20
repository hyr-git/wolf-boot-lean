package com.hyr.lean.rabbitmq.idempotent;



import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;


/****
 * 幂等操作---死信队列
 * @author m1832
 *
 */
@Component
@Slf4j
public class Consumer {


    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receiveMessage(Message message) throws Exception {

    	//TODO 找时间抽取工具类
        Jedis jedis = new Jedis("dev.carsir.xin", 6379);
        jedis.auth("devTaG8ie0)fE");

        String messageId = message.getMessageProperties().getMessageId();
        String msg = new String(message.getBody(),"UTF-8");
       
        log.info("接收导的消息为："+msg+"==消息id为："+messageId);
        String messageIdRedis = jedis.get("messageId");

        if(messageId == messageIdRedis){
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String email = jsonObject.getString("email");
        String content = jsonObject.getString("timestamp");

        String httpUrl = "http://127.0.0.1:8080/email?email"+email+"&content="+content;
        // 如果发生异常则返回null
        //String body = HttpUtils.httpGet(httpUrl, "utf-8");
        //
        /*if(body == null){
            throw new Exception();
        }*/
        jedis.set("messageId",messageId);
    }
}