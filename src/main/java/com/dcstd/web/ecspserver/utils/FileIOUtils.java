package com.dcstd.web.ecspserver.utils;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.dcstd.web.ecspserver.exception.CustomException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class FileIOUtils {

    /**
     * 读取文件内容
     * @param filename 文件名
     * @return 文件内容(Bytes[])
     */
    public static byte[] readBytesFromFile(String filename) {
        try {
            if(filename.matches("^https://|^http://")){
                HttpResponse response = HttpUtil.createGet(filename).execute();
                return response.bodyBytes();
            }
            ClassPathResource resource = new ClassPathResource(filename);
            //System.out.println("resource"+resource.getAbsolutePath());
            File file = new File(resource.getAbsolutePath());
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new CustomException(500, "获取文件内容失败");
        }
    }
}
