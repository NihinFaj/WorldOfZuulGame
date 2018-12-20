
/**
 * Write a description of class Item here.
 *
 * @author (Nihinlolamiwa Fajemilehin)
 * @version (13th of November 2018)
 */
public class Item
{
    private String name;
    private String description;
    private int weight;

    /**
     * Constructor for objects of class Item
     */
    public Item(String name, String description, int weight)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }
    
     /**
     * Method to return the name of the item
     */
    public String getName()
    {
        return name;
    }

    /**
     * Method to return the description of the item
     */
    public String getDescription()
    {
        return description;
    }
    
     /**
     * Method to return the weight of the item
     */
    public int getWeight()
    {
        return weight;
    }
}
