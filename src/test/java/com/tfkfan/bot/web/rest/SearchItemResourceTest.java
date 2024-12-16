package com.tfkfan.bot.web.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.*;
import static org.hamcrest.Matchers.*;

import com.tfkfan.bot.TestUtil;
import com.tfkfan.bot.service.dto.SearchItemDTO;
import io.quarkus.liquibase.LiquibaseFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import jakarta.inject.Inject;
import java.util.List;
import liquibase.Liquibase;
import org.junit.jupiter.api.*;

@QuarkusTest
public class SearchItemResourceTest {

    private static final TypeRef<SearchItemDTO> ENTITY_TYPE = new TypeRef<>() {};

    private static final TypeRef<List<SearchItemDTO>> LIST_OF_ENTITY_TYPE = new TypeRef<>() {};

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_IMG = "AAAAAAAAAA";
    private static final String UPDATED_IMG = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    String adminToken;

    SearchItemDTO searchItemDTO;

    @Inject
    LiquibaseFactory liquibaseFactory;

    @BeforeAll
    static void jsonMapper() {
        RestAssured.config = RestAssured.config()
            .objectMapperConfig(objectMapperConfig().defaultObjectMapper(TestUtil.jsonbObjectMapper()));
    }

    @BeforeEach
    public void authenticateAdmin() {
        this.adminToken = TestUtil.getAdminToken();
    }

    @BeforeEach
    public void databaseFixture() {
        try (Liquibase liquibase = liquibaseFactory.createLiquibase()) {
            liquibase.dropAll();
            liquibase.validate();
            liquibase.update(liquibaseFactory.createContexts(), liquibaseFactory.createLabels());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SearchItemDTO createEntity() {
        var searchItemDTO = new SearchItemDTO();
        searchItemDTO.title = DEFAULT_TITLE;
        searchItemDTO.img = DEFAULT_IMG;
        searchItemDTO.price = DEFAULT_PRICE;
        searchItemDTO.href = DEFAULT_HREF;
        return searchItemDTO;
    }

    @BeforeEach
    public void initTest() {
        searchItemDTO = createEntity();
    }

    @Test
    public void getAllSearchItems() {
        // Initialize the database
        searchItemDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(searchItemDTO)
            .when()
            .post("/api/search-items")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract()
            .as(ENTITY_TYPE);

        // Get all the searchItemList
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-items?sort=id,desc")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("title", hasItem(DEFAULT_TITLE))
            .body("img", hasItem(DEFAULT_IMG))
            .body("price", hasItem(DEFAULT_PRICE.intValue()))
            .body("href", hasItem(DEFAULT_HREF));
    }

    @Test
    public void getSearchItem() {
        // Initialize the database
        searchItemDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(searchItemDTO)
            .when()
            .post("/api/search-items")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract()
            .as(ENTITY_TYPE);

        var response = // Get the searchItem
            given()
                .auth()
                .preemptive()
                .oauth2(adminToken)
                .accept(APPLICATION_JSON)
                .when()
                .get("/api/search-items/{href}", searchItemDTO.href)
                .then()
                .statusCode(OK.getStatusCode())
                .contentType(APPLICATION_JSON)
                .extract()
                .as(ENTITY_TYPE);

        // Get the searchItem
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-items/{href}", searchItemDTO.href)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("title", is(DEFAULT_TITLE))
            .body("img", is(DEFAULT_IMG))
            .body("price", is(DEFAULT_PRICE.intValue()))
            .body("href", is(DEFAULT_HREF));
    }

    @Test
    public void getNonExistingSearchItem() {
        // Get the searchItem
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-items/{href}", "")
            .then()
            .statusCode(NOT_FOUND.getStatusCode());
    }
}
