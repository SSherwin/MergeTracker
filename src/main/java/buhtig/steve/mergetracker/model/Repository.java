package buhtig.steve.mergetracker.model;

import buhtig.steve.mergetracker.providers.IMergeTrackerDataProvider;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

/**
 * Repository holds the model for a given aplpication.
 * Created by steve on 03/02/15.
 */
public class Repository {

    private TreeMap<String, Branch> branches;
    private TreeMap<Long, BranchMergeTracker> merges;
    private List<Revision>revisions;
    private String name;

    public Repository() {
        this.merges = new TreeMap<>();
        this.branches = new TreeMap<>();
    }

    public IMergeTrackerDataProvider getProvider() {
        return provider;
    }

    public void setProvider(IMergeTrackerDataProvider provider) {
        this.provider = provider;
    }

    private IMergeTrackerDataProvider provider;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Branch> getBranches() {
        return branches.values();
    }

    public void addBranch(Branch branch) {
        this.branches.put(branch.getBranchName(), branch);
    }

    public void removeBranch(Branch branch) {
        this.branches.remove(branch.getBranchName());
    }


    public Collection<BranchMergeTracker> getMerges() {
        return merges.values();
    }


    public void addMerge(BranchMergeTracker merge) {
        for(BranchMergeTracker tracker : this.getMerges()) {
            if (tracker.getMergeFrom() == merge.getMergeFrom() &&
                    tracker.getBranch() == merge.getBranch()) {
                return;
            }
        }
        this.merges.put(merge.getId(), merge);
    }

    public void removeMerge(BranchMergeTracker merge) {
        this.merges.remove(merge.getId());
    }
    public List<Revision> getRevisions() {
        return revisions;
    }

    public void setRevisions(List<Revision> revisions) {
        this.revisions = revisions;
    }


    public Branch getBranch(String branch) {
        return this.branches.get(branch);
    }
}
