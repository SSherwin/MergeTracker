package buhtig.steve.mergetracker.providers;

/**
 * Implement a message parser to provide a way of parsing messages
 * to extract the bug id.
 *
 * Created by Steve on 02/12/2014.
 */
public interface IMessageParser {

    /**
     * get bug if from a message
     * @return extracted id - null if non found.
     */
    public Long getBugIdFromMessage(final String message);
}
