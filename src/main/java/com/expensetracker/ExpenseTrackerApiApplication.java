//package com.expensetracker;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication(scanBasePackages = "com.expensetracker")
//public class ExpenseTrackerApiApplication {
//	public static void main(String[] args) {
//		SpringApplication.run(ExpenseTrackerApiApplication.class, args);
//	}
//}
package com.expensetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class ExpenseTrackerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApiApplication.class, args);

		// Auto-open Swagger UI in the default browser
		openSwaggerUi();
	}

	private static void openSwaggerUi() {
		try {
			String swaggerUrl = "http://localhost:9095/swagger-ui/index.html";
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI(swaggerUrl));
			} else {
				System.out.println("Desktop is not supported. Open Swagger manually at: " + swaggerUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


