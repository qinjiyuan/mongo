package top.mixedinfos.mongo.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document(collection = "user")
@Data
public class User implements Serializable {
    @Id
    private String id;
    @Field("name")
    private String name;
    private String password;
    private String phone;
    private String sex;
    private String age;


}
