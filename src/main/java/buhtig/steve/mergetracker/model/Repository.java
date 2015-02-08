package buhtig.steve.mergetracker.model;

import buhtig.steve.mergetracker.providers.IMergeTrackerDataProvider;

import java.util.List;

/**
 * Repository holds the model for a given aplpication.
 * Created by steve on 03/02/15.
 */
public class Repository {

    private List<Branch>branches;
    private List<BranchMergeTracker> merges;
    private List<Revision>revisions;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public List<BranchMergeTracker> getMerges() {
        return merges;
    }

    public void setMerges(List<BranchMergeTracker> merges) {
        this.merges = merges;
    }

    public List<Revision> getRevisions() {
        return revisions;
    }

    public void setRevisions(List<Revision> revisions) {
        this.revisions = revisions;
    }
}
