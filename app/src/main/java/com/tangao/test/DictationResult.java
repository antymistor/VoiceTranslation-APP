package com.tangao.test;

import java.util.List;

/**
 * 解析 语音听写返回结果Json格式字符串 的模板类（多重嵌套Json）
 * <p/>
 * 语音识别结果Json数据格式（单条数据）：
 * {"sn":1,"ls":true,"bg":0,"ed":0,"ws":[
 * {"bg":0,"cw":[{"w":"今天","sc":0}]},
 * {"bg":0,"cw":[{"w":"的","sc":0}]},
 * {"bg":0,"cw":[{"w":"天气","sc":0}]},
 * {"bg":0,"cw":[{"w":"怎么样","sc":0}]},
 * {"bg":0,"cw":[{"w":"。","sc":0}]}
 * ]}
 * <p/>
 * sn  number :第几句
 * ls   boolean: 是否最后一句
 * bg  number :开始
 * ed  number :结束
 * ws  array :词
 * cw   array :中文分词
 * w  string :单字
 * sc  number :分数
 */
public class DictationResult {
    private String sn;
    private String ls;
    private String bg;
    private String ed;

    private List<Words> ws;

    public static class Words {
        private String bg;
        private List<Cw> cw;

        public static class Cw {
            private String w;
            private String sc;

            public String getW() {
                return w;
            }

            public void setW(String w) {
                this.w = w;
            }

            public String getSc() {
                return sc;
            }

            public void setSc(String sc) {
                this.sc = sc;
            }

            @Override
            public String toString() {
                return w;
            }
        }

        public String getBg() {
            return bg;
        }

        public void setBg(String bg) {
            this.bg = bg;
        }

        public List<Cw> getCw() {
            return cw;
        }

        public void setCw(List<Cw> cw) {
            this.cw = cw;
        }

        @Override
        public String toString() {
            String result = "";
            for (Cw cwTmp : cw) {
                result += cwTmp.toString();
            }
            return result;
        }
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getLs() {
        return ls;
    }

    public void setLs(String ls) {
        this.ls = ls;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getEd() {
        return ed;
    }

    public void setEd(String ed) {
        this.ed = ed;
    }

    public List<Words> getWs() {
        return ws;
    }

    public void setWs(List<Words> ws) {
        this.ws = ws;
    }

    @Override
    public String toString() {
        String result = "";
        for (Words wsTmp : ws) {
            result += wsTmp.toString();
        }
        return result;
    }
}