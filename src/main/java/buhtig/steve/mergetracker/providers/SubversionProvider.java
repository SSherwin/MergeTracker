package buhtig.steve.mergetracker.providers;

import buhtig.steve.mergetracker.model.Branch;
import buhtig.steve.mergetracker.model.BranchMergeTracker;
import buhtig.steve.mergetracker.model.Revision;
import org.apache.log4j.Logger;
import org.crsh.console.jline.internal.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Subversion info provider.
 *
 * Created by ssherwin on 01/12/2014.
 */
public class SubversionProvider implements IMergeTrackerDataProvider {

    private Logger log = Logger.getLogger(SubversionProvider.class);

    @Autowired
    IMessageParser parser;

    @Value("${revisiondata.url}")
    private String url;

    @Value("#{'${branches}'.split(',')}")
    private List<String> branches;

    @Value("#{'${merges}'.split(',')}")
    private List<String> merges;

    final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    private long lowestRevision = 999999999;

    private TreeMap<Long, BranchMergeTracker> mergeData;
    private TreeMap<String, Branch> branchData;


    @PostConstruct
    private void loadData() {
        branchData = new TreeMap<>();
        for(String branchName : branches) {
            final Branch branch = new Branch("branches/" + branchName);
            getSvnInfoForBranch(branch);
            branchData.put(branchName, branch);
        }
        // Trunk is always required.
        final Branch trunk= new Branch("trunk");
        branchData.put("trunk", trunk);
        getSvnInfoForBranch(trunk, lowestRevision);

        //Load the merge information This should be separated by ->
        mergeData = new TreeMap<>();
        for(String mergesName : merges) {
            String branchName = mergesName.split("->")[1];
            String mergeFromName = mergesName.split("->")[0];
            Branch branch = branchData.get(branchName);
            Branch mergeFrom = branchData.get(mergeFromName);
            BranchMergeTracker merge = new BranchMergeTracker(branch, mergeFrom);
            getSvnInfoOutstandingMerges(merge);
            mergeData.put(merge.getId(), merge);
        }

    }

    /**
     *
     * @param branchMergeTracker tracker to be refreshed
     */
    @Override
    public void refresh(BranchMergeTracker branchMergeTracker) {
        getSvnInfoForBranch(branchMergeTracker.getBranch());
        getSvnInfoOutstandingMerges(branchMergeTracker);
    }

    /**
     *
     * @return branch data.
     */
    @Override
    public TreeMap<Long, BranchMergeTracker> getMergeData() {
        return mergeData;
    }

    /**
     *
     * @return branch merge data.
     */
    @Override
    public TreeMap<String, Branch>  getBranchData() {
        return branchData;
    }

    /**
     *
     * @param branch to obtaind svn info for
     */
    private void getSvnInfoForBranch(final Branch branch) {
        getSvnInfoForBranch(branch, 1L);
    }

    /**
     *
     * @param branch to obtaind svn info for
     * @param startRevison revision to start at.
     */
    private void getSvnInfoForBranch(final Branch branch, final Long startRevison) {
        try {
            Long revStart = startRevison;
            if (null != branch.getLastRevision()) {
                revStart = branch.getLastRevision().getRevision()+1;
            }

            final String range = "-r" + revStart + ":HEAD";
            Process tr = Runtime.getRuntime().exec(new String[]{"svn", "log", "--xml", "--stop-on-copy", range, url + branch.getBranchName() });

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(tr.getInputStream());

            NodeList nList = doc.getElementsByTagName("logentry");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                long rev = Integer.valueOf(nNode.getAttributes().getNamedItem("revision").getNodeValue());
                String author = null;
                String msg = null;
                Date date = null;
                Long bugId = null;

                NodeList nList2 = nNode.getChildNodes();
                for (int temp2 = 0; temp2 < nList2.getLength(); temp2++) {
                    Node nNode2 = nList2.item(temp2);

                    if ("author".equals(nNode2.getNodeName()))
                        author =nNode2.getTextContent();
                    else if ("date".equals(nNode2.getNodeName())) {
                        date = convertDate(nNode2.getTextContent());
                    }
                    else if ("msg".equals(nNode2.getNodeName())) {
                        msg = nNode2.getTextContent();
                        bugId = parser.getBugIdFromMessage(msg);
                    }

                }
                Revision svnRev = new Revision(rev, author, msg, date);
                svnRev.setBugTrackId(bugId);
                branch.addRevision(svnRev);
                if (rev < lowestRevision) {
                    lowestRevision = rev;
                }
            }
        } catch(Exception exp) {
            log.error(exp.getMessage());
        }
    }

    private Date convertDate(String formattedDate) {
        Date date = null;;
        try {
            date = format.parse(formattedDate);
        } catch (ParseException exp) {
            log.error(exp.getMessage());
        }
        return date;



       // return new Date();
    }

    /**
     *
     * @param mergeTracker merge tracker info to get the list of outstanding merges for.
     */
    private void getSvnInfoOutstandingMerges(final BranchMergeTracker mergeTracker) {
        mergeTracker.clearRevisionsToMerge();
        try {
            // Run the SVN command....
            Process tr = Runtime.getRuntime().exec(new String[]{"svn", "mergeinfo", "--show-revs", "eligible",
                    url + mergeTracker.getMergeFrom().getBranchName(),
                    url + mergeTracker.getBranch().getBranchName()  });

            BufferedReader rd = new BufferedReader(new InputStreamReader(tr.getInputStream()));
            String line;
            while((line = rd.readLine()) != null) {
                long rev = Long.valueOf(line.substring(1));
                mergeTracker.addRevision(rev);
            }


        } catch(Exception exp) {
            log.error(exp.getMessage());
        }
    }
}
