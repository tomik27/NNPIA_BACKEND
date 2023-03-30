package cz.upce.nnpia_semestralka;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
@SecurityScheme(name = "NNPRO_API", scheme = "Bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class NnpiaSemestralkaApplication {

    //Mapování objektů usnadňuje převod jednoho modelu na druhý
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }



    public static void main(String[] args) {
        SpringApplication.run(NnpiaSemestralkaApplication.class, args);
    }

}
