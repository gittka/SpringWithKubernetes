package com.alx_tek.order;

import com.alx_tek.order.stub.InventoryClientStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

import static org.hamcrest.MatcherAssert.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}
	static {
		mySQLContainer.start();
		System.setProperty("spring.datasource.url", mySQLContainer.getJdbcUrl());
		System.setProperty("spring.datasource.username", mySQLContainer.getUsername());
		System.setProperty("spring.datasource.password", mySQLContainer.getPassword());
	}

	@Test
	void shouldSubmitOrder() {
		String requestBody = """
				{
				    "skuCode": "IPhone_16",
				    "price": 1000,
				    "quantity": 1
				}""";
		InventoryClientStub.checkStock("IPhone_16", 1);
		var responseBody = RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/orders")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().asString();
		assertThat(responseBody, Matchers.is("Order placed successfully: "));
	}
}
