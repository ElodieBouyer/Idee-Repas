package fr.project.ideerepas.meal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elodie on 28/02/14.
 */
public class Meal {

    private String picture;
    private String name;
    private List<Ingredient> ingredientsList;
    private Recipe recipe;

    public Meal(String newName) {
        this.name = newName;
    }

    /**
     * Get the meal picture.
     * @return
     */
    public String getPicture() {
        return this.picture;
    }

    /**
     * Get the meal name.
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the list ingredients of the meal.
     * @return
     */
    public List<Ingredient> getIngredientsList() {
        return this.ingredientsList;
    }

    /**
     * Get the meal recipe.
     * @return
     */
    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Add a new picture of the meal.
     * @param newPicture
     */
    public void addPicture(String newPicture) {
        this.picture = newPicture;
    }

    /**
     * Add a new ingredient.
     * @param ingredientName Name of the new ingredient.
     */
    public void addIngredient(String ingredientName)
    {
        Ingredient igt = new Ingredient(ingredientName);

        if( this.ingredientsList == null ) {
            this.ingredientsList = new ArrayList<Ingredient>();
        }

        this.ingredientsList.add(igt);
    }
}
