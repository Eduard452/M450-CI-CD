package ch.tbz.recipe.planner.mapper;

import ch.tbz.recipe.planner.domain.Ingredient;
import ch.tbz.recipe.planner.domain.Recipe;
import ch.tbz.recipe.planner.entities.IngredientEntity;
import ch.tbz.recipe.planner.entities.RecipeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-05T16:28:58+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (JetBrains s.r.o.)"
)
@Component
public class RecipeEntityMapperImpl implements RecipeEntityMapper {

    @Override
    public Recipe entityToDomain(RecipeEntity recipeEntity) {
        if ( recipeEntity == null ) {
            return null;
        }

        Recipe recipe = new Recipe();

        recipe.setId( recipeEntity.getId() );
        recipe.setName( recipeEntity.getName() );
        recipe.setDescription( recipeEntity.getDescription() );
        recipe.setImageUrl( recipeEntity.getImageUrl() );
        recipe.setIngredients( ingredientEntityListToIngredientList( recipeEntity.getIngredients() ) );

        return recipe;
    }

    @Override
    public RecipeEntity domainToEntity(Recipe recipe) {
        if ( recipe == null ) {
            return null;
        }

        RecipeEntity recipeEntity = new RecipeEntity();

        recipeEntity.setId( recipe.getId() );
        recipeEntity.setName( recipe.getName() );
        recipeEntity.setDescription( recipe.getDescription() );
        recipeEntity.setImageUrl( recipe.getImageUrl() );
        recipeEntity.setIngredients( ingredientListToIngredientEntityList( recipe.getIngredients() ) );

        return recipeEntity;
    }

    protected Ingredient ingredientEntityToIngredient(IngredientEntity ingredientEntity) {
        if ( ingredientEntity == null ) {
            return null;
        }

        Ingredient ingredient = new Ingredient();

        ingredient.setId( ingredientEntity.getId() );
        ingredient.setName( ingredientEntity.getName() );
        ingredient.setComment( ingredientEntity.getComment() );
        ingredient.setUnit( ingredientEntity.getUnit() );
        ingredient.setAmount( ingredientEntity.getAmount() );

        return ingredient;
    }

    protected List<Ingredient> ingredientEntityListToIngredientList(List<IngredientEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<Ingredient> list1 = new ArrayList<Ingredient>( list.size() );
        for ( IngredientEntity ingredientEntity : list ) {
            list1.add( ingredientEntityToIngredient( ingredientEntity ) );
        }

        return list1;
    }

    protected IngredientEntity ingredientToIngredientEntity(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientEntity ingredientEntity = new IngredientEntity();

        ingredientEntity.setId( ingredient.getId() );
        ingredientEntity.setName( ingredient.getName() );
        ingredientEntity.setComment( ingredient.getComment() );
        ingredientEntity.setUnit( ingredient.getUnit() );
        ingredientEntity.setAmount( ingredient.getAmount() );

        return ingredientEntity;
    }

    protected List<IngredientEntity> ingredientListToIngredientEntityList(List<Ingredient> list) {
        if ( list == null ) {
            return null;
        }

        List<IngredientEntity> list1 = new ArrayList<IngredientEntity>( list.size() );
        for ( Ingredient ingredient : list ) {
            list1.add( ingredientToIngredientEntity( ingredient ) );
        }

        return list1;
    }
}
