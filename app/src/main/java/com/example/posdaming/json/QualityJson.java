package com.example.posdaming.json;

import java.util.List;

/**
 * Created by 99213 on 2018/3/22.
 */

public class QualityJson {

    /**
     * is_success : true
     * msg : null
     * data : {"quality_id":22,"item_name":"后轮芯","item_no":"10101001","item_specs":"日普","measure_unit":"10","item_num":0,"is_quality":0,"qualityitems":[{"qualityitem_id":45,"quality_name":"y圈槽宽","quality_size":"6（02/-0）","result1":"","result2":"","result3":"","result4":"","result5":"","decide":0},{"qualityitem_id":46,"quality_name":"衬套孔径","quality_size":"-1（0.03/0）","result1":"","result2":"","result3":"","result4":"","result5":"","decide":0},{"qualityitem_id":47,"quality_name":"电镀","quality_size":"表面","result1":"","result2":"","result3":"","result4":"","result5":"","decide":0}]}
     */

    private boolean is_success;
    private Object msg;
    private DataBean data;

    public boolean isIs_success() {
        return is_success;
    }

    public void setIs_success(boolean is_success) {
        this.is_success = is_success;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * quality_id : 22
         * item_name : 后轮芯
         * item_no : 10101001
         * item_specs : 日普
         * measure_unit : 10
         * item_num : 0
         * is_quality : 0
         * qualityitems : [{"qualityitem_id":45,"quality_name":"y圈槽宽","quality_size":"6（02/-0）","result1":"","result2":"","result3":"","result4":"","result5":"","decide":0},{"qualityitem_id":46,"quality_name":"衬套孔径","quality_size":"-1（0.03/0）","result1":"","result2":"","result3":"","result4":"","result5":"","decide":0},{"qualityitem_id":47,"quality_name":"电镀","quality_size":"表面","result1":"","result2":"","result3":"","result4":"","result5":"","decide":0}]
         */

        private int quality_id;
        private String item_name;
        private String item_no;
        private String item_specs;
        private String measure_unit;
        private int item_num;
        private int is_quality;
        private List<QualityitemsBean> qualityitems;

        public int getQuality_id() {
            return quality_id;
        }

        public void setQuality_id(int quality_id) {
            this.quality_id = quality_id;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getItem_no() {
            return item_no;
        }

        public void setItem_no(String item_no) {
            this.item_no = item_no;
        }

        public String getItem_specs() {
            return item_specs;
        }

        public void setItem_specs(String item_specs) {
            this.item_specs = item_specs;
        }

        public String getMeasure_unit() {
            return measure_unit;
        }

        public void setMeasure_unit(String measure_unit) {
            this.measure_unit = measure_unit;
        }

        public int getItem_num() {
            return item_num;
        }

        public void setItem_num(int item_num) {
            this.item_num = item_num;
        }

        public int getIs_quality() {
            return is_quality;
        }

        public void setIs_quality(int is_quality) {
            this.is_quality = is_quality;
        }

        public List<QualityitemsBean> getQualityitems() {
            return qualityitems;
        }

        public void setQualityitems(List<QualityitemsBean> qualityitems) {
            this.qualityitems = qualityitems;
        }

        public static class QualityitemsBean {
            /**
             * qualityitem_id : 45
             * quality_name : y圈槽宽
             * quality_size : 6（02/-0）
             * result1 :
             * result2 :
             * result3 :
             * result4 :
             * result5 :
             * decide : 0
             */

            private int qualityitem_id;
            private String quality_name;
            private String quality_size;
            private String result1;
            private String result2;
            private String result3;
            private String result4;
            private String result5;
            private int decide;

            public int getQualityitem_id() {
                return qualityitem_id;
            }

            public void setQualityitem_id(int qualityitem_id) {
                this.qualityitem_id = qualityitem_id;
            }

            public String getQuality_name() {
                return quality_name;
            }

            public void setQuality_name(String quality_name) {
                this.quality_name = quality_name;
            }

            public String getQuality_size() {
                return quality_size;
            }

            public void setQuality_size(String quality_size) {
                this.quality_size = quality_size;
            }

            public String getResult1() {
                return result1;
            }

            public void setResult1(String result1) {
                this.result1 = result1;
            }

            public String getResult2() {
                return result2;
            }

            public void setResult2(String result2) {
                this.result2 = result2;
            }

            public String getResult3() {
                return result3;
            }

            public void setResult3(String result3) {
                this.result3 = result3;
            }

            public String getResult4() {
                return result4;
            }

            public void setResult4(String result4) {
                this.result4 = result4;
            }

            public String getResult5() {
                return result5;
            }

            public void setResult5(String result5) {
                this.result5 = result5;
            }

            public int getDecide() {
                return decide;
            }

            public void setDecide(int decide) {
                this.decide = decide;
            }
        }
    }
}
