package top.mixedinfos.mongo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/mongoGridFSController")
public class MongoGridFSController {
    @Autowired
    private top.mixedinfos.mongo.demo.service.MongoGridFSService mongoGridFSService;

    @RequestMapping(value = "/getMonImg")
    public void getMonImg(HttpServletResponse response, @RequestParam String id) throws Exception {
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(response.getOutputStream());
            byte[] f = mongoGridFSService.downloadFile(id);
            out.write(f);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/saveFile")
    public void saveFile() throws IOException{
        File file  = new File("D:\\download\\help.png");
        InputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
        mongoGridFSService.saveFile(multipartFile);
    }
}
