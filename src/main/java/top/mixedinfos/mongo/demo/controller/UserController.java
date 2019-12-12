package top.mixedinfos.mongo.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.mixedinfos.mongo.demo.entity.User;
import top.mixedinfos.mongo.demo.service.LocalUserServices;

import javax.annotation.Resource;


@RestController
@RequestMapping("/userController")
public class UserController {
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private LocalUserServices localUserServices;

    @RequestMapping("/getUser")
    public User getUser(){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("qinjiyuan"));
        User user = mongoTemplate.findOne(query,User.class);
        return user;
    }

    @RequestMapping("/getName")
    public User getName(@RequestParam("name") String name){
        User user = localUserServices.findByName(name);
        return user;
    }
}
