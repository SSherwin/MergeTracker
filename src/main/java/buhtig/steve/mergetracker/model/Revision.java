package buhtig.steve.mergetracker.model;

import java.util.Date;

/**
 * Revision models a scm revision
 * Created by Steve on 29/11/2014.
 */
public class Revision {

    private final long revision;
    private final String author;
    private final String message;
    private final Date commitDate;

    /**
     *
     * @param revision revision number
     * @param author author
     * @param message commit message
     * @param commitDate date committed
     */
    public Revision(long revision, String author, String message, Date commitDate) {
        this.revision = revision;
        this.author = author;
        this.message = message;
        this.commitDate = commitDate;
    }


    public long getRevision() {
        return revision;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public Date getCommitDate() {
        return commitDate;
    }

}
