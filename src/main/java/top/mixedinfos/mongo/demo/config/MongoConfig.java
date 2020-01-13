package top.mixedinfos.mongo.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
public class MongoConfig {
    @Value("${mongodb.bucket}")
    private String bucket;
    @Bean
    public GridFsTemplate gridFsTemplate(MongoDbFactory dbFactory, MongoConverter converter) {
        return new GridFsTemplate(dbFactory, converter, bucket);
    }
}
