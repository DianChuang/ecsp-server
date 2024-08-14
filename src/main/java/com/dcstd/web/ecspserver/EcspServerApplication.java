package com.dcstd.web.ecspserver;

//import com.dcstd.web.ecspserver.common.Result;
//import com.dcstd.web.ecspserver.config.GlobalConfiguration;
//import com.dcstd.web.ecspserver.exception.CustomException;
//import com.dcstd.web.ecspserver.exception.GlobalException;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/* 巅创网开 */
@EnableScheduling
@SpringBootApplication
public class EcspServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcspServerApplication.class, args);
    }

}
