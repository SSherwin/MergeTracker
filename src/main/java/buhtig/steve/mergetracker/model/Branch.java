package buhtig.steve.mergetracker.model;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Branch models a branch that revisions are on.
 * Created by Steve on 29/11/2014.
 */
public class Branch {

    private final String branchName;
    private TreeMap<Long, Revision> revisions;

    /**
     * constructor
     *
     * @param branchName branch name e.g. branches/RELEASE-1.0.0
     */
    public Branch(String branchName) {
        this.branchName = branchName;
        revisions = new TreeMap<Long, Revision>();
    }

    /**
     * @return list of revisions on this branch.
     */
    public List<Revision> getRevisions() {
        return new ArrayList<Revision>(this.revisions.values());
    }

    /**
     * @return earliest revision
     */
    public long getEarlistRevision() {
        return revisions.firstEntry().getValue().getRevision();
    }

    /**
     * @return last revision
     */
    public long getLastRevision() {
        return revisions.lastEntry().getValue().getRevision();
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
}
