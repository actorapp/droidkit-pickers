package com.droidkit.file.selectors.items;

import java.io.File;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class HistoryItem extends ExplorerItem {
    public HistoryItem() {
        super(null, false);
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
