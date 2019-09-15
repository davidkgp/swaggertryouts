package com.swagger.tryouts;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@SpringBootApplication
public class SwaggerTryOuts {

    public static void main(String[] args) {

        SpringApplication.run(SwaggerTryOuts.class, args);

    }
}

@RestController
class MyController {

    @GetMapping(path = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get a single student", notes = "This is going to return a single pre configured student")
    public ResponseEntity<Student> getStudent() {
        return new ResponseEntity<>(new Student("Tom", "Hardy", 67, new Address("1156FT", "67", "Amstelveen")), HttpStatus.OK);
    }

    @PostMapping(path = "/student", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a single student", notes = "This is going to return 201 always")
    public ResponseEntity<HttpStatus> createStudent(@RequestBody Student studentInput) {
        System.out.println(studentInput);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

@ApiModel(description = "Represents a student object in the api")
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
class Student {
    private final String firstName;
    private final String lastName;
    private final int age;
    private final Address residence;

}


@ApiModel(description = "Represents a student's address object in the api")
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
class Address {
    private final String postCode;
    private final String houseNumber;
    private final String city;

}

@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                .groupName("V2")
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.swagger.tryouts"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo("REST Api Documentation",
                "REST Api Documentation",
                "1.0",
                "http://example.org",
                new Contact("Kaushik Bagchi", "http://example.org", "davidkgp@gmail.com"),
                "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<VendorExtension>());
    }
}


