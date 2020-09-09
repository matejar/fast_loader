package org.playground;

import java.util.*;

public class FastLoadComparator implements Comparator<FastLoaderRecord> {

    @Override
    public int compare(FastLoaderRecord o1, FastLoaderRecord o2) {
        for (int partIndex = 0;
             partIndex < o1.getMatchId().getPartsOfMatchId().length && partIndex < o2.getMatchId().getPartsOfMatchId().length;
             partIndex++) {
            int comparingResult = o1.getMatchId().getPartsOfMatchId()[partIndex].compareTo(o2.getMatchId().getPartsOfMatchId()[partIndex]);
            if (comparingResult != 0) {
                return comparingResult;
            }
        }
        return Integer.compare(o1.getMatchId().getPartsOfMatchId().length, o2.getMatchId().getPartsOfMatchId().length);
    }
}
