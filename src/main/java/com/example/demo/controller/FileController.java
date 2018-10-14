package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/file")
public class FileController {

    @RequestMapping("/avatar/{name}")
    public void getAvatar(@PathVariable("name")String name,
                          HttpServletResponse response){
        response.setContentType("image/png");
        try {
            FileInputStream fis = new FileInputStream(
                    new File("/data/avatar/" + name)
            );
            OutputStream out = response.getOutputStream();
            byte[] avatar = new byte[fis.available()];
            fis.read(avatar);
            out.write(avatar);
            out.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
