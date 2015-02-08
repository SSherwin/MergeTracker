package buhtig.steve.mergetracker.model;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Branch models a branch that revisions are on.
 * Created by Steve on 29/11/2014.
 */
public class Branch {

    public static final String TRUNK = "trunk";

    private final String branchName;
    private TreeMap<Long, Revision> revisions;

    /**
     * constructor
     *
     * @param branchName branch name e.g. branches/RELEASE-1.0.0
     */
    public Branch(String branchName) {
        this.branchName = branchName;
        revisions = new TreeMap<>();
    }

    /**
     * @return list of revisions on this branch.
     */
    public List<Revision> getRevisions() {
        return new ArrayList<>(this.revisions.values());
    }

    /**
     * @return earliest revision
     */
    public Revision getEarliestRevision() {
        final Map.Entry<Long, Revision> firstEntry = revisions.firstEntry();
        if (null == firstEntry) {
            return null;
        }
        return firstEntry.getValue();
    }

    /**
     * @return last revision
     */
    public Revision getLastRevision() {

        final Map.Entry<Long, Revision> lastEntry = revisions.lastEntry();
        if (null == lastEntry) {
            return null;
        }
        return lastEntry.getValue();
    }

    /**
     * @param revision to add to branch.
     */
    public void addRevision(@NotNull final Revision revision) {
        revisions.put(revision.getRevision(), revision);
    }

    /**
     * @return branch name
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * @param revision to get
     * @return revision object for given id.
     */
    public Revision getRevision(long revision) {
        return revisions.get(revision);
    }

    public boolean isTrunk() {
        return TRUNK.equalsIgnoreCase(branchName);
    }
}
