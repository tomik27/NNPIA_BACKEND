package cz.upce.nnpia_semestralka;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;

//disabling spring security
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
public class NnpiaSemestralkaApplication {

    //Mapování objektů usnadňuje převod jednoho modelu na druhý
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

/*    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();*/


    public static void main(String[] args) {
        SpringApplication.run(NnpiaSemestralkaApplication.class, args);
    }

}
