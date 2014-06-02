package fr.project.ideerepas.meal;

public class Menu {
	private String name;

	public Menu() {
		this.name = "Unknown";
	}

	/**
	 * Get the recipe description.
	 * @return The recipe description.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set the recipe description.
	 * @param newDescription New recipe description.
	 */
	public void setName(String newName) {
		this.name = newName;
	}
}
