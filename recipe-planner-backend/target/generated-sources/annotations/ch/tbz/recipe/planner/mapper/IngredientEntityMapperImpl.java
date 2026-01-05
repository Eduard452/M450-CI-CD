package ch.tbz.recipe.planner.mapper;

import ch.tbz.recipe.planner.domain.Ingredient;
import ch.tbz.recipe.planner.entities.IngredientEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-05T16:28:57+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (JetBrains s.r.o.)"
)
@Component
public class IngredientEntityMapperImpl implements IngredientEntityMapper {

    @Override
    public Ingredient entityToDomain(IngredientEntity ingredientEntity) {
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

    @Override
    public IngredientEntity domainToEntity(Ingredient ingredient) {
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

    @Override
    public List<Ingredient> entitiesToDomains(List<IngredientEntity> ingredientEntities) {
        if ( ingredientEntities == null ) {
            return null;
        }

        List<Ingredient> list = new ArrayList<Ingredient>( ingredientEntities.size() );
        for ( IngredientEntity ingredientEntity : ingredientEntities ) {
            list.add( entityToDomain( ingredientEntity ) );
        }

        return list;
    }

    @Override
    public List<IngredientEntity> domainsToEntities(List<Ingredient> ingredients) {
        if ( ingredients == null ) {
            return null;
        }

        List<IngredientEntity> list = new ArrayList<IngredientEntity>( ingredients.size() );
        for ( Ingredient ingredient : ingredients ) {
            list.add( domainToEntity( ingredient ) );
        }

        return list;
    }
}
