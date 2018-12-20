import java.util.HashMap;
/**
 * Represents a player that plays the Zuul Game.
 *
 * @author (Nihinlolamiwa Fajemilehin)
 * @version (13th of November 2018)
 */
public class Player
{
    private String name;
    private Room currentRoom;
    private HashMap<String, Item> playerItems;
    private int maximumWeight;

    /**
     * Constructor for objects of class Player
     */
    public Player(String name, int maxWeight)
    {
        this.name = name;
        playerItems = new HashMap<>();
        maximumWeight = maxWeight;
    }

    /**
     * Method to set the current room of a player
     */
    public void setCurrentRoom(Room currRoom)
    {
        currentRoom = currRoom;
    }

    /**
     * Method to return player's items maximum weight
     * @return the maximum weight
     */
    public int getMaximumWeight()
    {
        return maximumWeight;
    }

    /**
     * Method to get the current room of a player
     * @return the current room a player is in
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * Method that returns the decription of all currently carried items
     */
    public String allCarriedItems()
    {
        String allDesc = "";
        for(String key : playerItems.keySet()) {
            Item currItem = playerItems.get(key);
            allDesc += "Item: " + currItem.getName() + "\n";
        }
        return allDesc + "\n" + "Total weight: " + totalWeight();
    }

    /**
     * Method to calculate the total weight of items carried
     */
    public int totalWeight()
    {
        int total = 0;
        if (playerItems.size() > 0) {
            for(String key : playerItems.keySet()) {
                Item currItem = playerItems.get(key);
                total += currItem.getWeight();
            }
        }
        return total;
    }

    /**
     * Method to drop item
     */
    public void dropItem(String key)
    {
        playerItems.remove(key);
    }

    /**
     * Method to pick item
     */
    public void pickItem(String key, Item item)
    {
        playerItems.put(key, item);
    }

    /**
     * Method to check if a player currently has an item picked up
     */
    public boolean checkItem(String inputedKey)
    { 
        for(String key : playerItems .keySet()) {
            if(key.equals(inputedKey)) {
                return  true;
            }
        }
        return false;
    }
}
