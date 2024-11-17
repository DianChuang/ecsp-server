package com.dcstd.web.ecspserver.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import com.alibaba.fastjson.util.IOUtils;
import com.dcstd.web.ecspserver.exception.CustomException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;

public class FileIOUtils {

    /**
     * 读取文件内容
     * @param filename 文件名
     * @return 文件内容(Bytes[])
     */
    public static byte[] readBytesFromFile(String filename) {
        try {
            // 之后再写这部分
            //if(filename.matches("^https://|^http://")){
            //    HttpResponse response = HttpUtil.createGet(filename).execute();
            //    return response.bodyBytes();
            //}
            ClassPathResource resource = new ClassPathResource(filename);
            InputStream inputStream = FileIOUtils.class.getClassLoader().getResourceAsStream(filename);
            return Objects.requireNonNull(inputStream).readAllBytes();
        } catch (IOException e) {
            throw new CustomException(500, "获取文件内容失败");
        }
    }
}
