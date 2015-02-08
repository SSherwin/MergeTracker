package buhtig.steve.mergetracker.manager;

import buhtig.steve.mergetracker.model.Branch;
import buhtig.steve.mergetracker.model.BranchMergeTracker;
import buhtig.steve.mergetracker.model.MergeTracker;
import buhtig.steve.mergetracker.model.Repository;
import buhtig.steve.mergetracker.providers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by steve on 03/02/15.
 */
@Component
public class MergeTrackerManager {

    private ApplicationConfigurationProvider configurationProvider;
    private TreeMap<String, Repository> repositories;
    private SubversionProvider svnProvider;


    @Autowired
    private IMessageParser parser;

    @Autowired
    MergeTrackerManager(final ApplicationConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
        this.repositories = new TreeMap<>();
    }

    @PostConstruct
    public void initialise() {
        refresh();
    }

    public Repository getRepository(final String name) {
        return this.repositories.get(name);
    }

    public void refresh() {
        List<String> apps = configurationProvider.getApplicationList();
        for (String  app : apps) {
            if (getRepository(app) == null) {
                createRepository(app);
            }
            refreshRepository(getRepository(app));

        }
    }

    private void refreshRepository(Repository repository) {
       List<String>merges = configurationProvider.getMergeList(repository.getName());
       //Split

        List<Branch> branchesToRemove = new ArrayList(repository.getBranches());
        List<BranchMergeTracker> mergesToRemove = new ArrayList(repository.getMerges());

        for (String merge : merges) {
            String[] array = merge.split("\\|");
            Branch branchFrom = getBranch(repository, array[0]);
            Branch branchTo = getBranch(repository, array[1]);

            branchesToRemove.remove(branchFrom);
            branchesToRemove.remove(branchTo);

            BranchMergeTracker tracker = new BranchMergeTracker(branchTo,branchFrom);
            repository.addMerge(tracker);
            mergesToRemove.remove(tracker);
        }
         getBranch(repository, "trunk");

        for(Branch remove : branchesToRemove) {
            if (!remove.isTrunk()) {
                repository.removeBranch(remove);
            }
        }
        for(BranchMergeTracker remove : mergesToRemove) {
            repository.removeMerge(remove);
        }

        repository.getProvider().refresh(repository);

    }

    private Branch getBranch(Repository repository, String s) {
        Branch branch = repository.getBranch(s);
        if (null == branch) {
            branch = new Branch(s);
            repository.addBranch(branch);
        }
        return branch;
    }


    private void createRepository(final String app) {
        final Repository repository = new Repository();
        repository.setName(app);
        repository.setProvider(getProvider(app));
        this.repositories.put(repository.getName(), repository);
    }

    private IMergeTrackerDataProvider getProvider(final String app) {
        final IMergeTrackerDataProvider provider;
        switch (configurationProvider.getType(app)) {
            case "subversion":
                provider = getSubversionProvider();
                break;
            default:
                provider = getTestDataProvider();
        }
        return provider;
    }

    @Bean
    @Scope("prototype")
    private IMergeTrackerDataProvider getTestDataProvider() {
        return new TestDataProvider(parser);
    }

    @Bean
    @Scope("prototype")
    private IMergeTrackerDataProvider getSubversionProvider() {
        return new SubversionProvider();
    }

    public Collection<Repository> getRepositories() {
        return this.repositories.values();
    }
}
