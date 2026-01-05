package ch.tbz.recipe.planner.controller;

import ch.tbz.recipe.planner.domain.Ingredient;
import ch.tbz.recipe.planner.domain.Recipe;
import ch.tbz.recipe.planner.domain.Unit;
import ch.tbz.recipe.planner.mapper.RecipeEntityMapper;
import ch.tbz.recipe.planner.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @MockBean
    private RecipeEntityMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Recipe createRecipe() {
        return new Recipe(
                UUID.randomUUID(),
                "Lasagne",
                "Nice food",
                "image.jpg",
                List.of(new Ingredient(
                        UUID.randomUUID(),
                        "Tomato",
                        "Fresh",
                        Unit.PIECE,
                        2
                ))
        );
    }

    @Test
    void getRecipes_shouldReturnListOfRecipes() throws Exception {
        Mockito.when(recipeService.getRecipes())
                .thenReturn(List.of(createRecipe()));

        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Lasagne"));
    }

    @Test
    void getRecipeById_shouldReturnRecipe() throws Exception {
        UUID id = UUID.randomUUID();
        Recipe recipe = createRecipe();

        Mockito.when(recipeService.getRecipeById(id))
                .thenReturn(recipe);

        mockMvc.perform(get("/api/recipes/recipe/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Lasagne"));
    }

    @Test
    void addRecipe_shouldReturnCreatedRecipe() throws Exception {
        Recipe recipe = createRecipe();

        Mockito.when(recipeService.addRecipe(Mockito.any()))
                .thenReturn(recipe);

        mockMvc.perform(post("/api/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Lasagne"));
    }
}
