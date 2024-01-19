package com.ecommerce.productservice;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.ecommerce.productservice.DTO.ProductRequest;
import com.ecommerce.productservice.DTO.ProductResponse;
import com.ecommerce.productservice.repository.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.assertions.Assertions;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:7.0.4")).withExposedPorts(27017);

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	Gson gson;

	@BeforeAll
	public static void beforeAll(){
		mongoDBContainer.start();
	}

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry){
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
		registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
		registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);
		
	}

	@Test
	@Order(1)
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestJSON = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
										.contentType(MediaType.APPLICATION_JSON)
										.content(productRequestJSON))
					 .andExpect(MockMvcResultMatchers.status().isCreated());
		
		Assertions.assertTrue(productRepository.findAll().size() == 1);
	}

	@Test
	@Order(2)
	void shouldGetProduct() throws Exception{
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
					 .andExpect(MockMvcResultMatchers.status().isOk())
					 .andReturn();
	
		System.out.println(result.getResponse().getContentAsString());
		List<ProductResponse> productsResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
		ProductRequest productRequest = getProductRequest();

		Assertions.assertTrue(productsResponse.size() == 1);
		ProductResponse productResponse = productsResponse.get(0);
		Assertions.assertNotNull(productResponse.getId());
		Assertions.assertTrue(productResponse.getName().equals(productRequest.getName()));
		Assertions.assertTrue(productResponse.getDescription().equals(productRequest.getDescription()));
		Assertions.assertTrue(productResponse.getPrice().equals(productRequest.getPrice()));
	}

	private ProductRequest getProductRequest(){
		return ProductRequest.builder()
							.name("Iphone 13")
							.description("Iphone 13")
							.price(BigDecimal.valueOf(1402.80))
							.build();
	}
}
