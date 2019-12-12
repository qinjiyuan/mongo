package top.mixedinfos.mongo.demo.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

@Service
public class MongoGridFSService {
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private MongoDbFactory mongoDbFactory;

    public GridFSFile findFileById(Object id) throws Exception{
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return  gridFsTemplate.findOne(query);
    }

    public String saveFile(MultipartFile file) throws IOException{
        String filename = file.getName();
        String type = filename.substring(filename.lastIndexOf(".")-1,filename.length());
        Object id  = gridFsTemplate.store(
                                        file.getInputStream(),
                                        UUID.randomUUID().toString().replace("_","")+type,
                                        file.getContentType());
        return id.toString();
    }

    /**
     * 根据id删除文件
     */
    public void deleteFileById(Object id){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        gridFsTemplate.delete(query);
    }

    public byte[] downloadFile(String objectId) throws Exception {
        GridFSFile gridFSFile = findFileById(objectId);
        GridFSBucket bucket = GridFSBuckets.create(mongoDbFactory.getDb());
        GridFSDownloadStream in = bucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource resource = new GridFsResource(gridFSFile,in);
        InputStream inputStream = resource.getInputStream();
        byte[] f = getBytes(inputStream);
        return  f;
    }

    public byte[] getBytes(InputStream inputStream) throws  Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int  i = 0;
        while (-1!=(i=inputStream.read(b))){
            bos.write(b,0,i);
        }
        return bos.toByteArray();
    }

}
