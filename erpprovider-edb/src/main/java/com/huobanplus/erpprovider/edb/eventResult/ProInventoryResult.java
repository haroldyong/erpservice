package com.huobanplus.erpprovider.edb.eventResult;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.huobanplus.erpprovider.edb.bean.EDBProduct;

import java.util.List;

/**
 * Created by allan on 2015/7/28.
 */
@JacksonXmlRootElement(localName = "items")
public class ProInventoryResult {
    private int totalResults;
    private int totalResultsAll;
    @JacksonXmlProperty(localName = "Rows")
    private List<EDBProduct> rows;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalResultsAll() {
        return totalResultsAll;
    }

    public void setTotalResultsAll(int totalResultsAll) {
        this.totalResultsAll = totalResultsAll;
    }

    public List<EDBProduct> getRows() {
        return rows;
    }

    public void setRows(List<EDBProduct> rows) {
        this.rows = rows;
    }
}
