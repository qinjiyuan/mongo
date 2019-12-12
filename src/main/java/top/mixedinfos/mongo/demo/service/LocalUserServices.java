package top.mixedinfos.mongo.demo.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import top.mixedinfos.mongo.demo.entity.User;


@Service("localUserServices")
public interface LocalUserServices extends MongoRepository<User,String> {
    User findByName(String name);

}
