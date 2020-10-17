package com.huajun123.biz;

import com.huajun123.counter.StopCounter;
import com.huajun123.domain.ElectionNews;

import java.util.List;
import java.util.Map;

public interface IPullBiz {
    String getInformation();
    String getInformationByDom();
    String getInformationByHtmlUnit();
    Map<String,String> getPredictitOrgInformation(StopCounter counter);
    List<ElectionNews> getAllElectionNews();
    void saveToRedis();
}
