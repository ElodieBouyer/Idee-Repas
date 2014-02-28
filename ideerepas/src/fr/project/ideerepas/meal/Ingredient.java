package fr.project.ideerepas.meal;

/**
 * Created by elodie on 28/02/14.
 */
public class Ingredient {

    private String name;
    private String picture;

    public Ingredient()
    {
        this.name = "Unknown";
        this.picture = null;
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
     * Get the ingredient picture path.
     * @return ingredient picture path.
     */
    public String getPicture() {
        return this.picture;
    }

    /**
     * Set the ingredient name.
     * @param newName : New name of the ingredient.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Set the ingredient picture path.
     * @param newPicture New picture path of the ingredient.
     */
    public void addPicture(String newPicture) {
        this.picture = newPicture;
    }
}
