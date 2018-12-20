import java.util.HashMap;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Player player;
    private HashMap<String, Item> allItems;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        allItems = new HashMap<>();
        player = new Player("Nihinlola", 100);
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");

        //create items
        Item bicycle, tonic, book, spoon, pen;

        bicycle = new Item ("Bicycle", "A two wheel bicycle", 520);
        tonic = new Item ("Tonic", "An elixir of life", 20);
        book = new Item ("Book","An old dusty book in gray leather", 40);
        spoon = new Item ("Spoon", "A shiny, metallic spoon",15);
        pen = new Item ("Pen", "A ball-point pen", 8);

        allItems.put("Bicycle", bicycle);
        allItems.put("Tonic", tonic);
        allItems.put("Book", book);
        allItems.put("Spoon", spoon);
        allItems.put("Pen", pen);

        // initialise room exits
        outside.setExit("west", theater);
        outside.setExit("north", lab);
        outside.setExit("east", pub);
        outside.setExit("down", office);
        outside.addItem("one", bicycle);
        outside.addItem("two", tonic);

        theater.setExit("west", outside);
        theater.setExit("up", pub);
        theater.setExit("down", lab);
        theater.addItem("three", spoon);
        theater.addItem("four", tonic);

        pub.setExit("west", outside);
        pub.setExit("down", lab);
        pub.addItem("five", book);
        pub.addItem("six", pen);

        lab.setExit("west",outside);
        lab.setExit("north", office);
        lab.setExit("up", theater);
        lab.addItem("seven", book);
        lab.addItem("eight", tonic);

        office.setExit("west", lab);
        office.setExit("up", pub);
        office.addItem("nine", bicycle);
        office.addItem("ten", pen);

        player.setCurrentRoom(outside);  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.       
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();

        //Call printLocationInfo method
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if(commandWord.equals("encourage")) {
            encourage();
        }
        else if(commandWord.equals("take")) {
            pickUpItem(command);
        }
        else if(commandWord.equals("drop")) {
            dropItem(command);
        }
        else if(commandWord.equals("items")) {
            DisplayAllCarriedItems(command);
        }

        return wantToQuit;
    }

    /**
     * Method that prints out encouragement for a user
     */
    private void encourage()
    {
        System.out.println("You can finish this! \nYou will win eventually!");
    }

    /**
     * Method that prints out the desctiption of a current room
     */
    private void look()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        String allCommands = parser.getAllCommands();
        System.out.println(allCommands);
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            player.setCurrentRoom(nextRoom);

            //Call printLocationInfo method
            printLocationInfo();
        }
    }

    /**
     * Method to pick up an item
     */
    private void pickUpItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to pick...
            System.out.println("Take what?");
            return;
        }

        String item = command.getSecondWord();
        Item selectedItem = selectItem(item);
        if(selectedItem == null) {
            System.out.println("Item entered was not found");
        }
        else {
            if((selectedItem.getWeight() + player.totalWeight()) > player.getMaximumWeight()) {
                System.out.println("You have exceeded the maximum weight");
            }
            else {
            player.pickItem(item, selectedItem);
            System.out.println(item + " has been picked up");
            }
        }
    }

    /**
     * Method to drop an item
     */
    private void dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to pick...
            System.out.println("Drop what?");
            return;
        }

        String item = command.getSecondWord();

        if(player.checkItem(item)) {
            player.dropItem(item);
            System.out.println("Player has dropped item");
        }
        else {
            System.out.println("Player does not have this item picked up");
        }
    }
    
     /**
     * Method to dislay all currently carried items
     */
    private void DisplayAllCarriedItems(Command command)
    {
        System.out.println(player.allCarriedItems());
    }
    

    /**
     * Method to selct an item
     */
    private Item selectItem(String itemName)
    {
        Item gotItem = null;
        for(String item: allItems.keySet()) {
            if(item.equals(itemName)) {
                gotItem = allItems.get(item);
            }
        }
        return gotItem;
    }

    /**
     * Prints location about the current or new location
     */
    private void printLocationInfo()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
        System.out.println();
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
