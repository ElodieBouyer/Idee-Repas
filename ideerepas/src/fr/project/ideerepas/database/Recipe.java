package fr.project.ideerepas.database;

/**
 * Created by elodie on 28/02/14.
 */
public class Recipe {

    private String description;

    public Recipe() {
        this.description = "Unknown";
    }

    /**
     * Get the recipe description.
     * @return The recipe description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the recipe description.
     * @param newDescription New recipe description.
     */
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }
}
