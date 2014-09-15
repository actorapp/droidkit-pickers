package com.droidkit.file.selectors.util;

import com.droidkit.file.selectors.items.ExplorerItem;

import java.util.Comparator;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class FileOrderComparator implements Comparator<com.droidkit.file.selectors.items.ExplorerItem> {
        @Override
        public int compare(ExplorerItem explorerItem, ExplorerItem explorerItem2) {
            if (explorerItem.isDirectory()) {
                if (explorerItem2.isDirectory()) {
                    return compareNames(explorerItem, explorerItem2);
                } else {
                    return -1;
                }
            }

            if (!explorerItem2.isDirectory()) {
                return compareNames(explorerItem, explorerItem2);
            }
            return 1;
        }

    public int compareNames(ExplorerItem explorerItem, ExplorerItem explorerItem2) {
        return (explorerItem.getTitle().compareToIgnoreCase(explorerItem2.getTitle()));
    }


}
