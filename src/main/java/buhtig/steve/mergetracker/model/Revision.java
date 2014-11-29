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
    private boolean eligibleForMerge = false; // Assume it is merged.


    /**
     *
     * @param revision revision number
     * @param author author
     * @param message commit message
     * @param commitDate date commited
     */
    public Revision(long revision, String author, String message, Date commitDate) {
        this.revision = revision;
        this.author = author;
        this.message = message;
        this.commitDate = commitDate;
    }

    /**
     *
     * @return true if revision has been merged
     */
    public boolean isEligibleForMerge() {
        return eligibleForMerge;
    }

    /**
     *
     * @param eligibleForMerge merge status.
     */
    public void setMerge(boolean eligibleForMerge) {
        this.eligibleForMerge = eligibleForMerge;
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
