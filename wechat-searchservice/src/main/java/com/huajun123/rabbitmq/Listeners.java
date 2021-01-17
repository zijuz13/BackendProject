package com.huajun123.rabbitmq;

import com.huajun123.biz.ISearchBiz;
import com.huajun123.domain.Item;
import com.huajun123.domain.Project;
import com.huajun123.entity.Blog;
import com.huajun123.feignclients.BlogClients;
import com.huajun123.feignclients.ProjectsClient;
import com.huajun123.repository.BlogItemRepository;
import com.huajun123.repository.ItemRepository;
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

import java.util.concurrent.atomic.AtomicBoolean;

//Use RabbitMQ as a messaging queue to realize the data synchronization between RABBITMQ and MySQL
@Component
public class Listeners {
    @Autowired
    private BlogClients blogClients;
    @Autowired
    private LoadJsonUtils utils;
    @Autowired
    private BlogItemRepository repository;
    @Autowired
    private ItemRepository repository1;
    @Autowired
    private ProjectsClient projectsClient;
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
    @RabbitListener(bindings = @QueueBinding(value=@Queue(value="search.project.queue",durable = "true"),exchange = @Exchange(
            value="item.create.exchange",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),key={"item.create.then"}
    ))
    public void listenCreate1(int id){
            Project projectById = projectsClient.getProjectById(id);
            Item item = biz.buildItemForSearchFromProject(projectById);
            System.out.println(item);
            repository1.save(item);
    }
    @RabbitListener(bindings = @QueueBinding(value=@Queue(value="search.delete.queue",durable = "true"),exchange = @Exchange(
            value="item.create.exchange",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),key={"delete.es"}
    ))
    public void delete1(int id){
        LOGGER.info("received:{}",id);
        repository1.deleteById(Long.parseLong(id+""));
    }
}
