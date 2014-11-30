package buhtig.steve.mergetracker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * BranchMergeTrack defines the relationship between the branches
 * for merging.
 * Created by Steve on 29/11/2014.
 */
public class BranchMergeTracker {

    private final Branch branch;
    private final Branch mergeFrom;
    private TreeMap<Long, Revision> revisionsToMerge;


    /**
     *
     * @param branch reporting on branch
     * @param mergeFrom location where getting revisions from
     */
    public BranchMergeTracker(Branch branch, Branch mergeFrom) {
        this.branch = branch;
        this.mergeFrom = mergeFrom;
        revisionsToMerge = new TreeMap<>();
    }

    public Branch getBranch() {
        return branch;
    }

    public Branch getMergeFrom() {
        return mergeFrom;
    }

    public List<Revision> getRevisionsToMerge() {
        return new ArrayList<>(revisionsToMerge.values());
    }

    public void addRevision(long revision) {
        addRevision(mergeFrom.getRevision(revision));
    }

    public void addRevision(Revision revision) {
        revisionsToMerge.put(revision.getRevision(), revision);
    }



}
