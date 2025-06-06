package bootcamp_2025_03_manthos.configurations;

import bootcamp_2025_03_manthos.controllers.UserController;
import bootcamp_2025_03_manthos.model.User;
import bootcamp_2025_03_manthos.exceptions.BootcampControllerAdvice;
import bootcamp_2025_03_manthos.repository.UserRepository;
import bootcamp_2025_03_manthos.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@ComponentScan(basePackageClasses = {
		UserController.class,
		UserService.class,
		BootcampControllerAdvice.class,
		Bootcamp202503Application.class,
})
@EnableJpaRepositories(basePackageClasses = {
		UserRepository.class
})
@EntityScan(basePackageClasses = {
		User.class
})
public class Bootcamp202503Application {

	public static void main(String[] args) {
		SpringApplication.run(Bootcamp202503Application.class, args);
	}

}
