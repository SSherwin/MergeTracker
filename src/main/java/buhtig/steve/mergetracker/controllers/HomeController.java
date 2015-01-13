package buhtig.steve.mergetracker.controllers;

import buhtig.steve.mergetracker.model.BranchMergeTracker;
import buhtig.steve.mergetracker.services.MergerTrackerReportServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.TreeMap;

@Controller
@Configurable
class HomeController {

    @Autowired
    MergerTrackerReportServices mergeService;

    @SuppressWarnings("SameReturnValue")
    @RequestMapping("/")
    String index() {
        return "index";
    }

    @RequestMapping("properties")
    @ResponseBody
    java.util.Properties properties() {
        return System.getProperties();
    }

    @RequestMapping(method = RequestMethod.GET, value ="/listmerge")
    public String list(Model model) {
        model.addAttribute("mergelist", mergeService.list());


        return "listmerge";
    }
}
