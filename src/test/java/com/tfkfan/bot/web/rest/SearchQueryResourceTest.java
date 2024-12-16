package com.tfkfan.bot.web.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import com.tfkfan.bot.TestUtil;
import com.tfkfan.bot.service.dto.SearchQueryDTO;
import io.quarkus.liquibase.LiquibaseFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import jakarta.inject.Inject;
import java.util.List;
import liquibase.Liquibase;
import org.junit.jupiter.api.*;

@QuarkusTest
public class SearchQueryResourceTest {

    private static final TypeRef<SearchQueryDTO> ENTITY_TYPE = new TypeRef<>() {};

    private static final TypeRef<List<SearchQueryDTO>> LIST_OF_ENTITY_TYPE = new TypeRef<>() {};

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Long DEFAULT_MIN_PRICE = 1L;
    private static final Long UPDATED_MIN_PRICE = 2L;

    private static final Long DEFAULT_MAX_PRICE = 1L;
    private static final Long UPDATED_MAX_PRICE = 2L;

    String adminToken;

    SearchQueryDTO searchQueryDTO;

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
    public static SearchQueryDTO createEntity() {
        var searchQueryDTO = new SearchQueryDTO();
        searchQueryDTO.value = DEFAULT_VALUE;
        searchQueryDTO.active = DEFAULT_ACTIVE;
        searchQueryDTO.minPrice = DEFAULT_MIN_PRICE;
        searchQueryDTO.maxPrice = DEFAULT_MAX_PRICE;
        return searchQueryDTO;
    }

    @BeforeEach
    public void initTest() {
        searchQueryDTO = createEntity();
    }

    @Test
    public void createSearchQuery() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the SearchQuery
        searchQueryDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(searchQueryDTO)
            .when()
            .post("/api/search-queries")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract()
            .as(ENTITY_TYPE);

        // Validate the SearchQuery in the database
        var searchQueryDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat(searchQueryDTOList).hasSize(databaseSizeBeforeCreate + 1);
        var testSearchQueryDTO = searchQueryDTOList.stream().filter(it -> searchQueryDTO.id.equals(it.id)).findFirst().get();
        assertThat(testSearchQueryDTO.value).isEqualTo(DEFAULT_VALUE);
        assertThat(testSearchQueryDTO.active).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testSearchQueryDTO.minPrice).isEqualTo(DEFAULT_MIN_PRICE);
        assertThat(testSearchQueryDTO.maxPrice).isEqualTo(DEFAULT_MAX_PRICE);
    }

    @Test
    public void createSearchQueryWithExistingId() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the SearchQuery with an existing ID
        searchQueryDTO.id = 1L;

        // An entity with an existing ID cannot be created, so this API call must fail
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(searchQueryDTO)
            .when()
            .post("/api/search-queries")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the SearchQuery in the database
        var searchQueryDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat(searchQueryDTOList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkValueIsRequired() throws Exception {
        var databaseSizeBeforeTest = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // set the field null
        searchQueryDTO.value = null;

        // Create the SearchQuery, which fails.
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(searchQueryDTO)
            .when()
            .post("/api/search-queries")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the SearchQuery in the database
        var searchQueryDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat(searchQueryDTOList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkActiveIsRequired() throws Exception {
        var databaseSizeBeforeTest = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // set the field null
        searchQueryDTO.active = null;

        // Create the SearchQuery, which fails.
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(searchQueryDTO)
            .when()
            .post("/api/search-queries")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the SearchQuery in the database
        var searchQueryDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat(searchQueryDTOList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void updateSearchQuery() {
        // Initialize the database
        searchQueryDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(searchQueryDTO)
            .when()
            .post("/api/search-queries")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract()
            .as(ENTITY_TYPE);

        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // Get the searchQuery
        var updatedSearchQueryDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries/{id}", searchQueryDTO.id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .body()
            .as(ENTITY_TYPE);

        // Update the searchQuery
        updatedSearchQueryDTO.value = UPDATED_VALUE;
        updatedSearchQueryDTO.active = UPDATED_ACTIVE;
        updatedSearchQueryDTO.minPrice = UPDATED_MIN_PRICE;
        updatedSearchQueryDTO.maxPrice = UPDATED_MAX_PRICE;

        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(updatedSearchQueryDTO)
            .when()
            .put("/api/search-queries/" + searchQueryDTO.id)
            .then()
            .statusCode(OK.getStatusCode());

        // Validate the SearchQuery in the database
        var searchQueryDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat(searchQueryDTOList).hasSize(databaseSizeBeforeUpdate);
        var testSearchQueryDTO = searchQueryDTOList.stream().filter(it -> updatedSearchQueryDTO.id.equals(it.id)).findFirst().get();
        assertThat(testSearchQueryDTO.value).isEqualTo(UPDATED_VALUE);
        assertThat(testSearchQueryDTO.active).isEqualTo(UPDATED_ACTIVE);
        assertThat(testSearchQueryDTO.minPrice).isEqualTo(UPDATED_MIN_PRICE);
        assertThat(testSearchQueryDTO.maxPrice).isEqualTo(UPDATED_MAX_PRICE);
    }

    @Test
    public void updateNonExistingSearchQuery() {
        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(searchQueryDTO)
            .when()
            .put("/api/search-queries/" + Long.MAX_VALUE)
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the SearchQuery in the database
        var searchQueryDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat(searchQueryDTOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSearchQuery() {
        // Initialize the database
        searchQueryDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(searchQueryDTO)
            .when()
            .post("/api/search-queries")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract()
            .as(ENTITY_TYPE);

        var databaseSizeBeforeDelete = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // Delete the searchQuery
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .delete("/api/search-queries/{id}", searchQueryDTO.id)
            .then()
            .statusCode(NO_CONTENT.getStatusCode());

        // Validate the database contains one less item
        var searchQueryDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat(searchQueryDTOList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void getAllSearchQueries() {
        // Initialize the database
        searchQueryDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(searchQueryDTO)
            .when()
            .post("/api/search-queries")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract()
            .as(ENTITY_TYPE);

        // Get all the searchQueryList
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries?sort=id,desc")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("id", hasItem(searchQueryDTO.id.intValue()))
            .body("value", hasItem(DEFAULT_VALUE))
            .body("active", hasItem(DEFAULT_ACTIVE.booleanValue()))
            .body("minPrice", hasItem(DEFAULT_MIN_PRICE.intValue()))
            .body("maxPrice", hasItem(DEFAULT_MAX_PRICE.intValue()));
    }

    @Test
    public void getSearchQuery() {
        // Initialize the database
        searchQueryDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(searchQueryDTO)
            .when()
            .post("/api/search-queries")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract()
            .as(ENTITY_TYPE);

        var response = // Get the searchQuery
            given()
                .auth()
                .preemptive()
                .oauth2(adminToken)
                .accept(APPLICATION_JSON)
                .when()
                .get("/api/search-queries/{id}", searchQueryDTO.id)
                .then()
                .statusCode(OK.getStatusCode())
                .contentType(APPLICATION_JSON)
                .extract()
                .as(ENTITY_TYPE);

        // Get the searchQuery
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries/{id}", searchQueryDTO.id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("id", is(searchQueryDTO.id.intValue()))
            .body("value", is(DEFAULT_VALUE))
            .body("active", is(DEFAULT_ACTIVE.booleanValue()))
            .body("minPrice", is(DEFAULT_MIN_PRICE.intValue()))
            .body("maxPrice", is(DEFAULT_MAX_PRICE.intValue()));
    }

    @Test
    public void getNonExistingSearchQuery() {
        // Get the searchQuery
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/search-queries/{id}", Long.MAX_VALUE)
            .then()
            .statusCode(NOT_FOUND.getStatusCode());
    }
}
