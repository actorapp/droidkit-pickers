package com.droidkit.picker.util;

import com.droidkit.picker.items.ExplorerItem;

import java.util.ArrayList;

/**
 * Created by kiolt_000 on 07/10/2014.
 */
public class FileSearchOrderComparator extends FileOrderComparator {

    private final String strongRegex;
    private final String mostStrongRegex;

    public FileSearchOrderComparator(String searchQuery){
        String tempRegex = searchQuery.toLowerCase();
        String[] splitedTempRegex = tempRegex.split("\\s+");
        ArrayList<String> filtered = new ArrayList<String>();
        for (String s : splitedTempRegex) {
            if (s != null && !s.equals("")) {
                filtered.add(s);
            }
        }
        tempRegex = filtered.toString()
                .replaceAll("\\[", "(").replaceAll("]", ")")
                .replaceAll(",", "|")
                .replaceAll("\\s+", "")
                .replaceAll("\\.","\\\\.");

        this.strongRegex = "(((.*)(\\s+))|(^))"+tempRegex + ".*"; // strong regex
        this.mostStrongRegex = "^"+tempRegex + ".*"; // THE MOST STRONG REGEX CAREFUL!!1
    }

    @Override
    protected int compareFiles(ExplorerItem explorerItem, ExplorerItem explorerItem2) {
        boolean firstStronk = false;
        boolean secondStronk = false;
        if (explorerItem.getTitle().toLowerCase().matches(strongRegex)) {
            firstStronk = true;
        }
        if (explorerItem2.getTitle().toLowerCase().matches(strongRegex)) {
            secondStronk = true;
        }
        if(firstStronk && secondStronk){
            boolean firstVeryStronk = false;
            boolean secondVeryStronk = false;

            if (explorerItem.getTitle().toLowerCase().matches(mostStrongRegex)) {
                firstVeryStronk = true;
            }
            if (explorerItem2.getTitle().toLowerCase().matches(mostStrongRegex)) {
                secondVeryStronk = true;
            }

            if(firstVeryStronk && secondVeryStronk) {
                return (explorerItem.getTitle().compareToIgnoreCase(explorerItem2.getTitle()));
            }

            if(!firstVeryStronk && !secondVeryStronk){
                return (explorerItem.getTitle().compareToIgnoreCase(explorerItem2.getTitle()));
            }
            return firstVeryStronk ? -1 : 1;

        }
        if(!firstStronk && !secondStronk){
            return (explorerItem.getTitle().compareToIgnoreCase(explorerItem2.getTitle()));
        }
        return firstStronk ? -1 : 1;
    }
}
