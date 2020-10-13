package ruby.command.meta;

import ruby.core.BotInformation;

/**
 * This class is used to make objects that contain a ruby.command's name,
 * category, type,syntax, and description.
 *
 * @author Tony Abou-Zeidan
 * @version Mar 4, 2020
 */
public class CommandWord {

    //properties of the ruby.command
    private String name;
    private CommandCategory category;
    private String description;
    private String syntax;

    /**
     * Default constructor for objects of class ruby.command.meta.CommandWord. Creates
     * a new ruby.command.meta.CommandWord object with the name, category, description, and syntax.
     *
     * @param name The name of the ruby.command
     * @param category The category of the ruby.command
     * @param description The description of the ruby.command
     * @param syntax The syntax for the ruby.command
     * @throws IllegalArgumentException If any of the given strings are null or are ('')
     */
    public CommandWord(String name,CommandCategory category,String description,String syntax) {

        if (name==null||name.equals("")) {
            throw new IllegalArgumentException("Name can not reference a null object or be an empty string");

        } else if (category==null) {
            throw new IllegalArgumentException("Category can not reference a null object or be an empty string");

        } else if (description==null||description.equals("")) {
            throw new IllegalArgumentException("Description can not reference a null object or be an empty string");

        } else if (syntax==null||syntax.equals("")) {
            throw new IllegalArgumentException("Syntax can not reference a null object or be an empty string");
        }

        this.name = name;
        this.category = category;
        this.description = description;
        this.syntax = BotInformation.BOT_PREFIX + syntax;
    }

    /**
     * Mutator for name of ruby.command.
     *
     * @return The name of the ruby.command
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator for category of ruby.command.
     *
     * @return The category of the ruby.command
     */
    public CommandCategory getCategory() {
        return category;
    }

    /**
     * Mutator for description of ruby.command.
     *
     * @return The description of the ruby.command
     */
    public String getDescription() {
        return description;
    }

    /**
     * Mutator for syntax of ruby.command.
     *
     * @return The syntax of the ruby.command
     */
    public String getSyntax() {
        return syntax;
    }

    /**
     * Overwritten toString method. Returns a String of the ruby.command
     * with its properties for the purpose of being printed in a
     * discord chat.
     *
     * @return The discord chat friendly String representation of the ruby.command
     */
    @Override
    public String toString() {
        return "**%s**\n%*s*\n```%s```".format(name,description,syntax);
    }
}
