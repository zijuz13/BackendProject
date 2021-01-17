package com.huajun123.listeners;
import com.huajun123.biz.IEditBiz;
import com.huajun123.domain.Project;
import com.huajun123.feignclients.ProjectClient;
import com.huajun123.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.UnaryOperator;

@Component
public class ImageListener {
    @Autowired
    private ProjectClient client;
    private static final Logger LOGGER= LoggerFactory.getLogger(ImageListener.class);
    @Autowired
    private AmqpTemplate template;
    @Autowired
    private IEditBiz biz;
    @RabbitListener(bindings = @QueueBinding(value=@Queue(value="image.converter.queue",durable = "true"),
    exchange = @Exchange(value="item.create.exchange",ignoreDeclarationExceptions ="true",type= ExchangeTypes.TOPIC),key={"item.create"}))
    public void listen(int id){
        //This is like a pipeline, first convert mp4 into images, and then call es to create items
        LOGGER.info("received id:{}",id);
        ThreadUtils.execute(()->{
            this.processImage(id);
            this.sendMessageToES(id);
        });
    }
    private int processImage(int id){
        Project projectById = client.getProjectById(id);
        LOGGER.info("received project {}",projectById);
        if(null==projectById){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            projectById=client.getProjectById(id);
        }
        biz.addThumbPictureAddressToDataBase(projectById);
        return id;
    }
    private int sendMessageToES(int id){
        template.convertAndSend("item.create.then",id);
        return id;
    }
}
