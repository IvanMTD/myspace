package ru.myspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyspaceApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyspaceApplication.class, args);
		/*
		* Передаем в класс SpringApplication через метод run
		* Main класс и входной параметр метода main (args)
		* Для отслеживание POST и GET запросов необходимо
		* настроить класс Controller
		* */
	}
}
