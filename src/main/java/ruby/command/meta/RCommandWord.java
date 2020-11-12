package ruby.command.meta;

import ruby.core.BotInformation;

import java.util.HashMap;

public class RCommandWord {
    //properties of the ruby.command
    private String name;
    private CommandCategory category;
    private String description;
    private String syntax;

    /**
     * Default constructor for objects of class RCommand. Creates
     * a new CommandWord object with the name.
     */
    public RCommandWord() {
        name = null;
        category = null;
        description = null;
        syntax = null;
    }

    /**
     * Constructor for objects of class RCommand. Creates
     * a new CommandWord object with the name.
     *
     * @param name The name of the command
     * @throws IllegalArgumentException If the name string is null or is ('')
     */
    public RCommandWord(String name) {
        setName(name);
        category = null;
        description = null;
        syntax = null;
    }

    public RCommandWord setName(String name) {
        if (name==null||name.equals("")) {
            throw new IllegalArgumentException("Name can not reference a null object or be an empty string");
        }
        this.name=name;
        return this;
    }

    public RCommandWord setCategory(CommandCategory category) {
        if (category==null) {
            throw new IllegalArgumentException("Category can not reference a null object");
        }
        this.category=category;
        return this;
    }

    public RCommandWord setDescription(String description) {
        if (description==null||description.equals("")) {
            throw new IllegalArgumentException("Description can not reference a null object or be an empty string");
        }
        this.description=description;
        return this;
    }

    public RCommandWord setSyntax(String syntax) {
        if (syntax==null||syntax.equals("")) {
            throw new IllegalArgumentException("Syntax can not reference a null object or be an empty string");
        }
        this.syntax=BotInformation.BOT_PREFIX+syntax;
        return this;
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
