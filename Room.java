import java.util.HashMap;
import java.util.*;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    public String description;
    private HashMap<String, Room> exits;
    private HashMap<String, Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new HashMap<>();
    }
    
    /**
     * Return a long description of this room, of the form:
     * You are in the Kitchen.
     * Exits: north west
     * @return A description of the room, including exits
     */
    public String getLongDescription()
    {
        return " You are " + description + ".\n" + getExitString() + ".\n" + getItemsDetails();
    }

    /**
     * Function to get details about items in a room
     */
    public String getItemsDetails()
    {
        String desc = "";
        for(String key : items.keySet()) {
            Item currItem = items.get(key);
            desc += "Name: " + currItem.getName() + ", Description: " + currItem.getDescription() + ", Weight: " + currItem.getWeight() + "\n";
        }
        return desc;
    }
    
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param nieghbour The room to which the exit leads
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * method to add items to a room
     */
    public void addItem(String name, Item item)
    {
        items.put(name, item);
    }

    /**
     * Determines the location of the next room 
     * based on the direction the user has entered
     * @param direction The direction of the room the user wants to enter next
     * @return an object of the next room to be entered
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }
    
    /**
     * Return a description of the room's exits,
     * for example, "Exists: north west".
     * @return A description of the available exits.
     */
    public String getExitString()
    {
        String exitString = "Exits: ";
        Set<String> exitKeys = exits.keySet();
        for(String key: exitKeys) {
            exitString += " " + key;
        }
        return exitString;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

}
