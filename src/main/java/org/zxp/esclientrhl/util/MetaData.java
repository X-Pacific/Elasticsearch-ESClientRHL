package org.zxp.esclientrhl.util;

import org.elasticsearch.common.unit.ByteSizeUnit;

import java.util.concurrent.TimeUnit;

/**
 * 元数据载体类
 */
public class MetaData{
    public MetaData(String indexname, String indextype) {
        this.indexname = indexname;
        this.indextype = indextype;
    }
    String indexname = "";
    String indextype = "";

    String[] searchIndexNames;

    public String[] getSearchIndexNames() {
        return searchIndexNames;
    }

    public void setSearchIndexNames(String[] searchIndexNames) {
        this.searchIndexNames = searchIndexNames;
    }

    boolean printLog = false;

    public boolean isPrintLog() {
        return printLog;
    }

    public void setPrintLog(boolean printLog) {
        this.printLog = printLog;
    }

    public String getIndexname() {
        return indexname;
    }
    public void setIndexname(String indexname) {
        this.indexname = indexname;
    }
    public String getIndextype() {
        return indextype;
    }
    public void setIndextype(String indextype) {
        this.indextype = indextype;
    }

    int number_of_shards;
    int number_of_replicas;

    public int getNumber_of_shards() {
        return number_of_shards;
    }

    public void setNumber_of_shards(int number_of_shards) {
        this.number_of_shards = number_of_shards;
    }

    public int getNumber_of_replicas() {
        return number_of_replicas;
    }

    public void setNumber_of_replicas(int number_of_replicas) {
        this.number_of_replicas = number_of_replicas;
    }

    public MetaData(String indexname, String indextype, int number_of_shards, int number_of_replicas) {
        this.indexname = indexname;
        this.indextype = indextype;
        this.number_of_shards = number_of_shards;
        this.number_of_replicas = number_of_replicas;
    }

    public MetaData(int number_of_shards, int number_of_replicas) {
        this.number_of_shards = number_of_shards;
        this.number_of_replicas = number_of_replicas;
    }

    boolean alias;
    String[] aliasIndex;
    String writeIndex;
    boolean rollover;
    long rolloverMaxIndexAgeCondition;
    TimeUnit rolloverMaxIndexAgeTimeUnit;
    long rolloverMaxIndexDocsCondition;
    long rolloverMaxIndexSizeCondition;
    ByteSizeUnit rolloverMaxIndexSizeByteSizeUnit;


    public boolean isAlias() {
        return alias;
    }

    public void setAlias(boolean alias) {
        this.alias = alias;
    }

    public String[] getAliasIndex() {
        return aliasIndex;
    }

    public void setAliasIndex(String[] aliasIndex) {
        this.aliasIndex = aliasIndex;
    }

    public String getWriteIndex() {
        return writeIndex;
    }

    public void setWriteIndex(String writeIndex) {
        this.writeIndex = writeIndex;
    }

    public boolean isRollover() {
        return rollover;
    }

    public void setRollover(boolean rollover) {
        this.rollover = rollover;
    }

    public long getRolloverMaxIndexAgeCondition() {
        return rolloverMaxIndexAgeCondition;
    }

    public void setRolloverMaxIndexAgeCondition(long rolloverMaxIndexAgeCondition) {
        this.rolloverMaxIndexAgeCondition = rolloverMaxIndexAgeCondition;
    }

    public TimeUnit getRolloverMaxIndexAgeTimeUnit() {
        return rolloverMaxIndexAgeTimeUnit;
    }

    public void setRolloverMaxIndexAgeTimeUnit(TimeUnit rolloverMaxIndexAgeTimeUnit) {
        this.rolloverMaxIndexAgeTimeUnit = rolloverMaxIndexAgeTimeUnit;
    }

    public long getRolloverMaxIndexDocsCondition() {
        return rolloverMaxIndexDocsCondition;
    }

    public void setRolloverMaxIndexDocsCondition(long rolloverMaxIndexDocsCondition) {
        this.rolloverMaxIndexDocsCondition = rolloverMaxIndexDocsCondition;
    }

    public long getRolloverMaxIndexSizeCondition() {
        return rolloverMaxIndexSizeCondition;
    }

    public void setRolloverMaxIndexSizeCondition(long rolloverMaxIndexSizeCondition) {
        this.rolloverMaxIndexSizeCondition = rolloverMaxIndexSizeCondition;
    }

    public ByteSizeUnit getRolloverMaxIndexSizeByteSizeUnit() {
        return rolloverMaxIndexSizeByteSizeUnit;
    }

    public void setRolloverMaxIndexSizeByteSizeUnit(ByteSizeUnit rolloverMaxIndexSizeByteSizeUnit) {
        this.rolloverMaxIndexSizeByteSizeUnit = rolloverMaxIndexSizeByteSizeUnit;
    }
}