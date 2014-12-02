package buhtig.steve.mergetracker.providers;

import org.springframework.stereotype.Component;

/**
 * Parser to extract a issue number from the revision message.
 * This implementation is to use the last line that starts "issue #" to be the
 * applicable issue number.
 *
 * Created by Steve on 02/12/2014.
 */
@Component
public class MessageParser implements IMessageParser{

    @Override
    public Long getBugIdFromMessage(String message) {
        final String lines[] = message.split("\\r?\\n");
        Long issue = null;
        for (int idx = lines.length-1; idx >= 0; idx--) {
            if (lines[idx].toLowerCase().startsWith("issue #")) {
                final String issueString = lines[idx].substring(7).split(" ")[0];
                issue = Long.parseLong(issueString);
                break;
            }
        }
        return issue;
    }
}
