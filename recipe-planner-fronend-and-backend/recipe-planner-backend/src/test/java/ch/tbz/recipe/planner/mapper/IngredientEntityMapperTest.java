package ch.tbz.recipe.planner.mapper;

import ch.tbz.recipe.planner.domain.Ingredient;
import ch.tbz.recipe.planner.domain.Unit;
import ch.tbz.recipe.planner.entities.IngredientEntity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

class IngredientEntityMapperTest {

    private IngredientEntityMapper ingredientEntityMapper;
    private SoftAssertions softAssertions;

    private IngredientEntity tomatoEntity;
    private IngredientEntity cheeseEntity;

    private Ingredient tomatoIngredient;
    private Ingredient cheeseIngredient;

    @BeforeEach
    void setUp() {
        ingredientEntityMapper = Mappers.getMapper(IngredientEntityMapper.class);
        softAssertions = new SoftAssertions();

        tomatoEntity = createIngredientEntity("Tomato entity", "Fresh and ripe", Unit.KILOGRAMM, 200);
        cheeseEntity = createIngredientEntity("Cheese entity", "Mozzarella", Unit.GRAMM, 150);

        tomatoIngredient = createIngredient("Tomato ingredient", "Fresh tomato", Unit.KILOGRAMM, 200);
        cheeseIngredient = createIngredient("Cheese ingredient", "Mozzarella for ingredient", Unit.GRAMM, 150);
    }

    @Test
    void entityToDomain() {
        Ingredient ingredient = ingredientEntityMapper.entityToDomain(tomatoEntity);

        softAssertions.assertThat(ingredient.getId()).isEqualTo(tomatoEntity.getId());
        softAssertions.assertThat(ingredient.getName()).isEqualTo(tomatoEntity.getName());
        softAssertions.assertThat(ingredient.getComment()).isEqualTo(tomatoEntity.getComment());
        softAssertions.assertThat(ingredient.getUnit()).isEqualTo(tomatoEntity.getUnit());
        softAssertions.assertThat(ingredient.getAmount()).isEqualTo(tomatoEntity.getAmount());

        softAssertions.assertAll();
    }

    @Test
    void domainToEntity() {
        IngredientEntity entity = ingredientEntityMapper.domainToEntity(cheeseIngredient);

        softAssertions.assertThat(entity.getId()).isEqualTo(cheeseIngredient.getId());
        softAssertions.assertThat(entity.getName()).isEqualTo(cheeseIngredient.getName());
        softAssertions.assertThat(entity.getComment()).isEqualTo(cheeseIngredient.getComment());
        softAssertions.assertThat(entity.getUnit()).isEqualTo(cheeseIngredient.getUnit());
        softAssertions.assertThat(entity.getAmount()).isEqualTo(cheeseIngredient.getAmount());

        softAssertions.assertAll();
    }

    @Test
    void entitiesToDomains() {
        List<Ingredient> ingredients = ingredientEntityMapper.entitiesToDomains(List.of(tomatoEntity, cheeseEntity));

        softAssertions.assertThat(ingredients).hasSize(2);
        softAssertions.assertThat(ingredients)
                .extracting("name")
                .containsExactlyInAnyOrder("Tomato entity", "Cheese entity");

        softAssertions.assertAll();
    }

    @Test
    void domainsToEntities() {
        List<IngredientEntity> entities = ingredientEntityMapper.domainsToEntities(List.of(tomatoIngredient, cheeseIngredient));

        softAssertions.assertThat(entities).hasSize(2);
        softAssertions.assertThat(entities)
                .extracting("name")
                .containsExactlyInAnyOrder("Tomato ingredient", "Cheese ingredient");

        softAssertions.assertAll();
    }

    private IngredientEntity createIngredientEntity(String name, String comment, Unit unit, int amount) {
        return new IngredientEntity(UUID.randomUUID(), name, comment, unit, amount);
    }

    private Ingredient createIngredient(String name, String comment, Unit unit, int amount) {
        return new Ingredient(UUID.randomUUID(), name, comment, unit, amount);
    }
}