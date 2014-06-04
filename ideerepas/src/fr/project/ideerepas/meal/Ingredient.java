package fr.project.ideerepas.meal;

/**
 * Created by elodie on 28/02/14.
 */
public class Ingredient {

    private String name;

    public Ingredient()
    {
        this.name = "Unknown";
    }

    public Ingredient(String name) {
        this.name = name;
    }

    /**
     * Get the ingredient name.
     * @return ingredient name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the ingredient name.
     * @param newName : New name of the ingredient.
     */
    public void setName(String newName) {
        this.name = newName;
    }

}
