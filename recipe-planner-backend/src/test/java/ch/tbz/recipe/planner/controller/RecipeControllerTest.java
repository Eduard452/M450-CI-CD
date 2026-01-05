package ch.tbz.recipe.planner.controller;

import ch.tbz.recipe.planner.domain.Ingredient;
import ch.tbz.recipe.planner.domain.Recipe;
import ch.tbz.recipe.planner.domain.Unit;
import ch.tbz.recipe.planner.entities.IngredientEntity;
import ch.tbz.recipe.planner.entities.RecipeEntity;
import ch.tbz.recipe.planner.repository.RecipeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest {

    @MockBean
    RecipeRepository recipeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Ingredient tomato;
    private Ingredient cheese;

    @BeforeEach
    void setUp() {
        tomato = new Ingredient();
        tomato.setId(UUID.randomUUID());
        tomato.setName("Tomato");
        tomato.setComment("Fresh and ripe");
        tomato.setUnit(Unit.GRAMM);
        tomato.setAmount(200);

        cheese = new Ingredient();
        cheese.setId(UUID.randomUUID());
        cheese.setName("Cheese");
        cheese.setComment("Mozzarella");
        cheese.setUnit(Unit.GRAMM);
        cheese.setAmount(150);
    }

    private String toJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    void getRecipes_shouldGetRecipes() throws Exception {
        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk());
    }

    @Test
    void getRecipe_withValidId() throws Exception {
        UUID recipeId = UUID.randomUUID();

        RecipeEntity recipe = new RecipeEntity(
                recipeId,
                "Pizza",
                "Classic Italian",
                "pizza.jpg",
                List.of(new IngredientEntity(UUID.randomUUID(), "Tomato", "Fresh", Unit.PIECE, 3))
        );

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        mockMvc.perform(get("/api/recipes/{recipeId}", recipeId))
                .andExpect(status().isOk());
    }

    @Test
    void addRecipe_withValidBody() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(UUID.randomUUID());
        recipe.setName("Pizza Margherita");
        recipe.setDescription("Classic Italian pizza");
        recipe.setImageUrl("http://example.com/pizza.jpg");
        recipe.setIngredients(List.of(tomato, cheese));

        mockMvc.perform(post("/api/recipes")
                        .contentType("application/json")
                        .content(toJson(recipe)))
                .andExpect(status().isCreated());
    }

    @Test
    void addRecipe_withInvalidBody() throws Exception {
        mockMvc.perform(post("/api/recipes")
                        .contentType("application/json")
                        .content(toJson(null)))
                .andExpect(status().isBadRequest());
    }
}
