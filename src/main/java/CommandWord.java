/**
 * This class is used to make objects that contain a command's name,
 * category, type,syntax, and description.
 *
 * @author Tony Abou-Zeidan
 * @version Mar 4, 2020
 */
public class CommandWord {

    //properties of the command
    private String name;
    private String category;
    private String description;
    private String syntax;

    /**
     * Default constructor for objects of class CommandWord. Creates
     * a new CommandWord object with the name, category, description, and syntax.
     *
     * @param name The name of the command
     * @param category The category of the command
     * @param description The description of the command
     * @param syntax The syntax for the command
     * @throws IllegalArgumentException If any of the given strings are null or are ('')
     */
    public CommandWord(String name,String category,String description,String syntax) {

        if (name==null||name.equals("")) {
            throw new IllegalArgumentException("Name can not reference a null object or be an empty string");

        } else if (category==null||category.equals("")) {
            throw new IllegalArgumentException("Category can not reference a null object or be an empty string");

        } else if (description==null||description.equals("")) {
            throw new IllegalArgumentException("Description can not reference a null object or be an empty string");

        } else if (syntax==null||syntax.equals("")) {
            throw new IllegalArgumentException("Syntax can not reference a null object or be an empty string");
        }

        this.name = name;
        this.category = category;
        this.description = description;
        this.syntax = syntax;
    }

    /**
     * Mutator for name of command.
     *
     * @return The name of the command
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator for category of command.
     *
     * @return The category of the command
     */
    public String getCategory() {
        return category;
    }

    /**
     * Mutator for description of command.
     *
     * @return The description of the command
     */
    public String getDescription() {
        return description;
    }

    /**
     * Mutator for syntax of command.
     *
     * @return The syntax of the command
     */
    public String getSyntax() {
        return syntax;
    }

    /**
     * Overwritten toString method. Returns a String of the command
     * with its properties for the purpose of being printed in a
     * discord chat.
     *
     * @return The discord chat friendly String representation of the command
     */
    @Override
    public String toString() {
        return "**%s**\n%*s*\n```%s```".format(name,description,syntax);
    }
}
