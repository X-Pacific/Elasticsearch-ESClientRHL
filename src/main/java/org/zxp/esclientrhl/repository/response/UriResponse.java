package org.zxp.esclientrhl.repository.response;

import java.util.List;

/**
 * @program: esclientrhl
 * @description: uri返回json串反序列化对象
 * @author: X-Pacific zhang
 * @create: 2019-10-10 10:45
 **/
public class UriResponse {


    /**
     * took : 9
     * timed_out : false
     * _shards : {"total":5,"successful":5,"skipped":0,"failed":0}
     * hits : {"total":{"value":1,"relation":"eq"},"max_score":0.9808292,"hits":[{"_index":"index","_type":"main4","_id":"aaa","_score":0.9808292,"_source":{"end_date":null,"proposal_no":"aaa","sum_amount":0,"business_nature_name":"aaaaaa2","operate_date":null,"sum_premium":0,"appli_name":null,"business_nature":null,"insured_code":null,"appli_code":null,"serialVersionUID":1,"operate_date_format":null,"insured_name":null,"com_code":null,"risk_code":null,"risk_name":null,"start_date":null}}]}
     */

    private int took;
    private boolean timed_out;
    private ShardsBean _shards;
    private HitsBeanX hits;

    public int getTook() {
        return took;
    }

    public void setTook(int took) {
        this.took = took;
    }

    public boolean isTimed_out() {
        return timed_out;
    }

    public void setTimed_out(boolean timed_out) {
        this.timed_out = timed_out;
    }

    public ShardsBean get_shards() {
        return _shards;
    }

    public void set_shards(ShardsBean _shards) {
        this._shards = _shards;
    }

    public HitsBeanX getHits() {
        return hits;
    }

    public void setHits(HitsBeanX hits) {
        this.hits = hits;
    }

    public static class ShardsBean {
        /**
         * total : 5
         * successful : 5
         * skipped : 0
         * failed : 0
         */

        private int total;
        private int successful;
        private int skipped;
        private int failed;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSuccessful() {
            return successful;
        }

        public void setSuccessful(int successful) {
            this.successful = successful;
        }

        public int getSkipped() {
            return skipped;
        }

        public void setSkipped(int skipped) {
            this.skipped = skipped;
        }

        public int getFailed() {
            return failed;
        }

        public void setFailed(int failed) {
            this.failed = failed;
        }
    }

    public static class HitsBeanX {
        /**
         * total : {"value":1,"relation":"eq"}
         * max_score : 0.9808292
         * hits : [{"_index":"index","_type":"main4","_id":"aaa","_score":0.9808292,"_source":{"end_date":null,"proposal_no":"aaa","sum_amount":0,"business_nature_name":"aaaaaa2","operate_date":null,"sum_premium":0,"appli_name":null,"business_nature":null,"insured_code":null,"appli_code":null,"serialVersionUID":1,"operate_date_format":null,"insured_name":null,"com_code":null,"risk_code":null,"risk_name":null,"start_date":null}}]
         */

        private TotalBean total;
        private double max_score;
        private List<HitsBean> hits;

        public TotalBean getTotal() {
            return total;
        }

        public void setTotal(TotalBean total) {
            this.total = total;
        }

        public double getMax_score() {
            return max_score;
        }

        public void setMax_score(double max_score) {
            this.max_score = max_score;
        }

        public List<HitsBean> getHits() {
            return hits;
        }

        public void setHits(List<HitsBean> hits) {
            this.hits = hits;
        }

        public static class TotalBean {
            /**
             * value : 1
             * relation : eq
             */

            private int value;
            private String relation;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public String getRelation() {
                return relation;
            }

            public void setRelation(String relation) {
                this.relation = relation;
            }
        }

        public static class HitsBean {
            /**
             * _index : index
             * _type : main4
             * _id : aaa
             * _score : 0.9808292
             * _source : {"end_date":null,"proposal_no":"aaa","sum_amount":0,"business_nature_name":"aaaaaa2","operate_date":null,"sum_premium":0,"appli_name":null,"business_nature":null,"insured_code":null,"appli_code":null,"serialVersionUID":1,"operate_date_format":null,"insured_name":null,"com_code":null,"risk_code":null,"risk_name":null,"start_date":null}
             */

            private String _index;
            private String _type;
            private String _id;
            private double _score;
            private SourceBean _source;

            public String get_index() {
                return _index;
            }

            public void set_index(String _index) {
                this._index = _index;
            }

            public String get_type() {
                return _type;
            }

            public void set_type(String _type) {
                this._type = _type;
            }

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public double get_score() {
                return _score;
            }

            public void set_score(double _score) {
                this._score = _score;
            }

            public SourceBean get_source() {
                return _source;
            }

            public void set_source(SourceBean _source) {
                this._source = _source;
            }

            public static class SourceBean {
                /**
                 * end_date : null
                 * proposal_no : aaa
                 * sum_amount : 0.0
                 * business_nature_name : aaaaaa2
                 * operate_date : null
                 * sum_premium : 0.0
                 * appli_name : null
                 * business_nature : null
                 * insured_code : null
                 * appli_code : null
                 * serialVersionUID : 1
                 * operate_date_format : null
                 * insured_name : null
                 * com_code : null
                 * risk_code : null
                 * risk_name : null
                 * start_date : null
                 */

                private Object end_date;
                private String proposal_no;
                private double sum_amount;
                private String business_nature_name;
                private Object operate_date;
                private double sum_premium;
                private Object appli_name;
                private Object business_nature;
                private Object insured_code;
                private Object appli_code;
                private int serialVersionUID;
                private Object operate_date_format;
                private Object insured_name;
                private Object com_code;
                private Object risk_code;
                private Object risk_name;
                private Object start_date;

                public Object getEnd_date() {
                    return end_date;
                }

                public void setEnd_date(Object end_date) {
                    this.end_date = end_date;
                }

                public String getProposal_no() {
                    return proposal_no;
                }

                public void setProposal_no(String proposal_no) {
                    this.proposal_no = proposal_no;
                }

                public double getSum_amount() {
                    return sum_amount;
                }

                public void setSum_amount(double sum_amount) {
                    this.sum_amount = sum_amount;
                }

                public String getBusiness_nature_name() {
                    return business_nature_name;
                }

                public void setBusiness_nature_name(String business_nature_name) {
                    this.business_nature_name = business_nature_name;
                }

                public Object getOperate_date() {
                    return operate_date;
                }

                public void setOperate_date(Object operate_date) {
                    this.operate_date = operate_date;
                }

                public double getSum_premium() {
                    return sum_premium;
                }

                public void setSum_premium(double sum_premium) {
                    this.sum_premium = sum_premium;
                }

                public Object getAppli_name() {
                    return appli_name;
                }

                public void setAppli_name(Object appli_name) {
                    this.appli_name = appli_name;
                }

                public Object getBusiness_nature() {
                    return business_nature;
                }

                public void setBusiness_nature(Object business_nature) {
                    this.business_nature = business_nature;
                }

                public Object getInsured_code() {
                    return insured_code;
                }

                public void setInsured_code(Object insured_code) {
                    this.insured_code = insured_code;
                }

                public Object getAppli_code() {
                    return appli_code;
                }

                public void setAppli_code(Object appli_code) {
                    this.appli_code = appli_code;
                }

                public int getSerialVersionUID() {
                    return serialVersionUID;
                }

                public void setSerialVersionUID(int serialVersionUID) {
                    this.serialVersionUID = serialVersionUID;
                }

                public Object getOperate_date_format() {
                    return operate_date_format;
                }

                public void setOperate_date_format(Object operate_date_format) {
                    this.operate_date_format = operate_date_format;
                }

                public Object getInsured_name() {
                    return insured_name;
                }

                public void setInsured_name(Object insured_name) {
                    this.insured_name = insured_name;
                }

                public Object getCom_code() {
                    return com_code;
                }

                public void setCom_code(Object com_code) {
                    this.com_code = com_code;
                }

                public Object getRisk_code() {
                    return risk_code;
                }

                public void setRisk_code(Object risk_code) {
                    this.risk_code = risk_code;
                }

                public Object getRisk_name() {
                    return risk_name;
                }

                public void setRisk_name(Object risk_name) {
                    this.risk_name = risk_name;
                }

                public Object getStart_date() {
                    return start_date;
                }

                public void setStart_date(Object start_date) {
                    this.start_date = start_date;
                }
            }
        }
    }
}
