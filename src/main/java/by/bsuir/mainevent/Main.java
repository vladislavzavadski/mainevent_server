package by.bsuir.mainevent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by ulza1116 on 6/19/2017.
 */
@SpringBootApplication
@EnableTransactionManagement
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
