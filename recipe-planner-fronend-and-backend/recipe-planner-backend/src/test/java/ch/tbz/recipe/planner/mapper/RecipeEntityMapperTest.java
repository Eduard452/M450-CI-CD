package ch.tbz.recipe.planner.mapper;

import ch.tbz.recipe.planner.domain.Ingredient;
import ch.tbz.recipe.planner.domain.Recipe;
import ch.tbz.recipe.planner.domain.Unit;
import ch.tbz.recipe.planner.entities.IngredientEntity;
import ch.tbz.recipe.planner.entities.RecipeEntity;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

class RecipeEntityMapperTest {
    private SoftAssertions softAssertions;
    private RecipeEntityMapper recipeEntityMapper;

    private UUID id;
    private IngredientEntity tomatoEntity;
    private IngredientEntity cheeseEntity;

    private Ingredient tomatoIngredient;
    private Ingredient cheeseIngredient;

    @BeforeEach
    void setUp() {
        recipeEntityMapper = Mappers.getMapper(RecipeEntityMapper.class);
        id = UUID.randomUUID();
        softAssertions = new SoftAssertions();

        tomatoEntity = createIngredientEntity("Tomato entity", "Fresh and ripe", Unit.KILOGRAMM, 200);

        cheeseEntity = createIngredientEntity("Cheese entity", "Mozzarella", Unit.GRAMM, 150);

        tomatoIngredient = createIngredient("Tomato ingredient", "Mozzarella", Unit.GRAMM, 200);

        cheeseIngredient = createIngredient("Cheese ingredient", "Mozzarella for ingredient", Unit.DECILITRE, 150);

    }

    @Test
    void entityToDomain_withValidRecipeEntity() {
        RecipeEntity recipeEntity = createRecipeEntity("name", "description", "imageUrl", List.of(tomatoEntity, cheeseEntity));

        Recipe recipe = recipeEntityMapper.entityToDomain(recipeEntity);

        assertRecipeMatchesEntity(recipe, recipeEntity);
    }

    @Test
    void entityToDomain_withNullValues() {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(id);
        recipeEntity.setIngredients(List.of(tomatoEntity));

        Recipe recipe = recipeEntityMapper.entityToDomain(recipeEntity);

        assertRecipeMatchesEntity(recipe, recipeEntity);
    }

    @Test
    void domainToEntity() {
        Recipe recipe = createRecipe("domainToEntity", "This is a great description", "my imageUrl", List.of(tomatoIngredient, cheeseIngredient));

        RecipeEntity recipeEntity = recipeEntityMapper.domainToEntity(recipe);

        softAssertions.assertThat(recipeEntity.getId()).isNotNull();
        softAssertions.assertThat(recipeEntity.getName()).isEqualTo("domainToEntity");
        softAssertions.assertThat(recipeEntity.getDescription()).isEqualTo("This is a great description");
        softAssertions.assertThat(recipeEntity.getImageUrl()).isEqualTo("my imageUrl");

        softAssertions.assertThat(recipeEntity.getIngredients())
                .extracting("name")
                .containsExactly("Tomato ingredient", "Cheese ingredient");

        softAssertions.assertAll();
    }

    @Test
    void domainToEntity_withNullValues() {
        Recipe recipe = new Recipe();
        recipe.setId(id);

        RecipeEntity recipeEntity = recipeEntityMapper.domainToEntity(recipe);

        Assertions.assertThat(recipeEntity.getId()).isNotNull();
    }

    private IngredientEntity createIngredientEntity(String name, String desc, Unit unit, int amount) {
        return new IngredientEntity(UUID.randomUUID(), name, desc, unit, amount);
    }

    private Ingredient createIngredient(String name, String description, Unit unit, int amount) {
        return new Ingredient(UUID.randomUUID(), name, description, unit, amount);
    }

    private RecipeEntity createRecipeEntity(String name, String description, String imageUrl, List<IngredientEntity> ingredients) {
        return new RecipeEntity(UUID.randomUUID(), name, description, imageUrl, ingredients);
    }

    private Recipe createRecipe (String name, String description, String imageUrl, List<Ingredient> ingredients) {
        return new Recipe(UUID.randomUUID(), name, description, imageUrl, ingredients);
    }

    private void assertRecipeMatchesEntity(Recipe recipe, RecipeEntity entity) {
        softAssertions.assertThat(recipe.getId()).isEqualTo(entity.getId());
        softAssertions.assertThat(recipe.getName()).isEqualTo(entity.getName());
        softAssertions.assertThat(recipe.getDescription()).isEqualTo(entity.getDescription());
        softAssertions.assertThat(recipe.getImageUrl()).isEqualTo(entity.getImageUrl());

        softAssertions.assertThat(recipe.getIngredients())
                .extracting("name")
                .containsExactlyInAnyOrderElementsOf(
                        entity.getIngredients().stream().map(IngredientEntity::getName).toList()
                );

        softAssertions.assertAll();
    }
}