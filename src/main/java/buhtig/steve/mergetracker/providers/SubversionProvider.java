package buhtig.steve.mergetracker.providers;

import buhtig.steve.mergetracker.model.Branch;
import buhtig.steve.mergetracker.model.BranchMergeTracker;
import buhtig.steve.mergetracker.model.Repository;
import buhtig.steve.mergetracker.model.Revision;
import org.apache.log4j.Logger;
import org.crsh.console.jline.internal.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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
@Component
public class SubversionProvider implements IMergeTrackerDataProvider {
    private Logger log = Logger.getLogger(SubversionProvider.class);

    @Autowired
    private IMessageParser parser;

    final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public void refresh(Repository repository) {
        for (Branch branch : repository.getBranches()) {
            if (!"trunk".equals(branch.getBranchName())) {
                getSvnInfoForBranch(branch, repository, false);
            }
        }
        getSvnInfoForBranch(repository.getBranch("trunk"), repository, true);

        for (BranchMergeTracker mergeTracker : repository.getMerges()) {
            getSvnInfoOutstandingMerges(mergeTracker, repository.getUrl());
        }
    }

    /**
     *
     * @param branch to obtaind svn info for
     * @param repository repository processing
     * @param trunkBranch true if this is the trunk branch
     */
    private void getSvnInfoForBranch(final Branch branch, final Repository repository, final boolean trunkBranch) {
        final String url = repository.getUrl();
        try {
            Long revStart = (trunkBranch ? 1L : repository.getLowestRevision() );
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
                if (rev < repository.getLowestRevision()) {
                    repository.setLowestRevision(rev);
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
    private void getSvnInfoOutstandingMerges(final BranchMergeTracker mergeTracker, final String url) {
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
