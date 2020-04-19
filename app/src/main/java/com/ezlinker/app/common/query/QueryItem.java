package com.ezlinker.app.common.query;

import lombok.Data;

/**
 * @program: ezlinker
 * @description:
 * @author: wangwenhai
 * @create: 2019-12-20 17:37
 **/
@Data
public class QueryItem {


    public static class LIKE  {
        private String l;

        private String k;
        private String v;

        public String getL() {
            return l;
        }

        public void setL(String l) {
            this.l = l;
        }

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }
    }

    public static class EQ  {
        private String l;

        public String getL() {
            return l;
        }

        public void setL(String l) {
            this.l = l;
        }

        private String k;
        private String v;

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }
    }

    public static class NEQ  {
        private String l;

        private String k;
        private String v;

        public String getL() {
            return l;
        }

        public void setL(String l) {
            this.l = l;
        }

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }
    }

    public static class LTE  {
        private String k;
        private String v;
        private String l;

        public String getL() {
            return l;
        }

        public void setL(String l) {
            this.l = l;
        }

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }
    }

    public static class GTE  {
        private String k;
        private String v;
        private String l;

        public String getL() {
            return l;
        }

        public void setL(String l) {
            this.l = l;
        }

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }
    }

    public static class BETWEEN  {
        private String lg;
        private String k;
        private String l;
        private String r;

        public String getLg() {
            return lg;
        }

        public void setLg(String lg) {
            this.lg = lg;
        }

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        public String getL() {
            return l;
        }

        public void setL(String l) {
            this.l = l;
        }

        public String getR() {
            return r;
        }

        public void setR(String r) {
            this.r = r;
        }
    }

    public static class ORDER  {
        private String[] k;
        private String v;

        public String[] getK() {
            return k;
        }

        public void setK(String[] k) {
            this.k = k;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }
    }
}
