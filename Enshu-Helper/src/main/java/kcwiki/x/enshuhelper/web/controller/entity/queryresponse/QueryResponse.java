/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.web.controller.entity.queryresponse;

import java.util.List;
import kcwiki.x.enshuhelper.web.controller.entity.BaseResponse;

/**
 *
 * @author iHaru
 */
public class QueryResponse extends BaseResponse {
    private List<MatchInfo> matchlist;

    /**
     * @return the matchlist
     */
    public List<MatchInfo> getMatchlist() {
        return matchlist;
    }

    /**
     * @param matchlist the matchlist to set
     */
    public void setMatchlist(List<MatchInfo> matchlist) {
        this.matchlist = matchlist;
    }
    
}
