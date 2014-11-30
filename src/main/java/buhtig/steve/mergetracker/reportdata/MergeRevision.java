package buhtig.steve.mergetracker.reportdata;

import buhtig.steve.mergetracker.model.Revision;

import java.util.Date;

/**
 * Created by Steve on 30/11/2014.
 */
public class MergeRevision extends Revision {

    private boolean eligibleForMerge = true; // Assume it is to be merged

    /**
     * @param revision  revision to clone5
     */
    public MergeRevision(Revision revision, boolean eligibleForMerge) {
        super(revision.getRevision(),
                revision.getAuthor(),
                revision.getMessage(),
                revision.getCommitDate());
        this.eligibleForMerge = eligibleForMerge;
    }

    /**
     *
     * @param eligibleForMerge merge status.
     */
    public void setMerge(boolean eligibleForMerge) {
        this.eligibleForMerge = eligibleForMerge;
    }


    /**
     *
     * @return true if revision has been merged
     */
    public boolean isEligibleForMerge() {
        return eligibleForMerge;
    }

}
