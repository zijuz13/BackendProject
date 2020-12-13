package com.huajun123.listeners;

import com.huajun123.biz.IEditBiz;
import com.huajun123.domain.Project;
import com.huajun123.feignclients.ProjectClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageListener {
    @Autowired
    private ProjectClient client;
    private static final Logger LOGGER= LoggerFactory.getLogger(ImageListener.class);
    @Autowired
    private IEditBiz biz;
    @RabbitListener(bindings = @QueueBinding(value=@Queue(value="image.converter.queue",durable = "true"),
    exchange = @Exchange(value="item.create.exchange",ignoreDeclarationExceptions ="true",type= ExchangeTypes.TOPIC),key={"item.create"}))
    public void listen(int id){
        Project projectById = client.getProjectById(id);
        LOGGER.info("received project {}",projectById);
       biz.addThumbPictureAddressToDataBase(projectById);
    }
}
