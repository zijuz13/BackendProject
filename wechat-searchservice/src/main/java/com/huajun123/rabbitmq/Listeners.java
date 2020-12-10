package com.huajun123.rabbitmq;

import com.huajun123.biz.ISearchBiz;
import com.huajun123.entity.Blog;
import com.huajun123.feignclients.BlogClients;
import com.huajun123.repository.BlogItemRepository;
import com.huajun123.utils.LoadJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//Use RabbitMQ as a messaging queue to realize the data synchronization between RABBITMQ and MySQL
@Component
public class Listeners {
    @Autowired
    private BlogClients blogClients;
    @Autowired
    private LoadJsonUtils utils;
    @Autowired
    private BlogItemRepository repository;
    private static final Logger LOGGER= LoggerFactory.getLogger(Listeners.class);
    @Autowired
    private ISearchBiz biz;
    @RabbitListener(bindings = @QueueBinding(value=@Queue(value="es.blog1",durable = "true"),exchange = @Exchange(
            value="es.blog.exchange",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),key={"create","update"}
    ))
    public void listenUpdate(int id){
        LOGGER.info("rabbitmq received created/update request, id is {}",id);
        Blog blog = this.utils.loadJsonToBlog(blogClients.getBlogById(id));
        repository.save(biz.constructBlogItemFromBlog(blog));
    }
    @RabbitListener(bindings = @QueueBinding(value=@Queue(value="es.blog2",durable = "true"),exchange = @Exchange(
            value="es.blog.exchange",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),key={"delete"}
    ))
    public void listenDelete(int id){
        LOGGER.info("rabbitmq received delete request, id is {}",id);
        repository.deleteById(Long.parseLong(id+""));
    }
}
