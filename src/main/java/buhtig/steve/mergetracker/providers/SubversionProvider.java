package buhtig.steve.mergetracker.providers;

import buhtig.steve.mergetracker.model.BranchMergeTracker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.TreeMap;

/**
 * Created by ssherwin on 01/12/2014.
 */
public class SubversionProvider implements IMergeTrackerDataProvider {


    @Override
    public TreeMap<Long, BranchMergeTracker> loadData() {
        return null;
    }

    @Override
    public void refresh(BranchMergeTracker branchMergeTracker) {

    }
}
