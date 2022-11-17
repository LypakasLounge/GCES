package com.lypaka.gces.Modules;

import java.util.Map;

public class CatchingModule {

    private final String finalStagePermission;
    private final String firstStagePermission;

    private final String middleStagePermission;
    private final String singleStagePermission;
    private final String legendaryPermission;
    private final String shinyPermission;
    private final Map<String, Integer> tierMap;

    public CatchingModule (String finalStagePermission, String firstStagePermission, String middleStagePermission, String singleStagePermission,
                    String legendaryPermission, String shinyPermission, Map<String, Integer> tierMap) {

        this.finalStagePermission = finalStagePermission;
        this.firstStagePermission = firstStagePermission;
        this.middleStagePermission = middleStagePermission;
        this.singleStagePermission = singleStagePermission;
        this.legendaryPermission = legendaryPermission;
        this.shinyPermission = shinyPermission;
        this.tierMap = tierMap;

    }

    public String getFinalStagePermission() {

        return this.finalStagePermission;

    }

    public String getFirstStagePermission() {

        return this.firstStagePermission;

    }

    public String getMiddleStagePermission() {

        return this.middleStagePermission;

    }

    public String getSingleStagePermission() {

        return this.singleStagePermission;

    }

    public String getLegendaryPermission() {

        return this.legendaryPermission;

    }

    public String getShinyPermission() {

        return this.shinyPermission;

    }

    public Map<String, Integer> getTierMap() {

        return this.tierMap;

    }

}
