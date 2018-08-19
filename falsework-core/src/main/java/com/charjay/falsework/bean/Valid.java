package com.charjay.falsework.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 表单验证
 */
public class Valid {

    public Valid(boolean required) {
        super();
        setRequired(required);
    }

    public Valid(boolean required, String datatype) {
        super();
        setRequired(required);
        setDatatype(datatype);
    }

    public Valid(boolean required, int minLength, int maxLength) {
        super();
        setRequired(required);
        setLengthRange(minLength,maxLength);
    }

    public Valid(boolean required, int minLength, int maxLength, String datatype) {
        this(required, datatype);
        setLengthRange(minLength,maxLength);
    }

    public Valid(String datatype) {
        setDatatype(datatype);
    }

    /**
     * 是否必填
     */
    private boolean required  = false;
    /**
     * 最小长度
     */
    private int     minLength = 0;
    /**
     * 最大长度
     */
    private int     maxLength = Integer.MAX_VALUE;

    public enum DataType {
        MOBILE("m"), URL("url"), EMAIL("e"), NUMBER("n"), IDCARD("idCard"), REQUIRE("*"), DOUBLE("match");

        private String nCode;

        private DataType(String _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return this.nCode;
        }
    }

    /**
     * 内置正则表达式 {
     *      "match":/^(.+?)(\d+)-(\d+)$/,
     *      "*":/[\w\W]+/,
     *      "*6-16":/^[\w\W]{6,16}$/,
     *      "n":/^\d+$/,
     *      "n6-16":/^\d{6,16}$/,
     *      "s":/^[\u4E00-\u9FA5\uf900-\ufa2d\w\.\s]+$/,
     *      "s6-18":/^[\u4E00-\u9FA5\uf900-\ufa2d\w\.\s]{6,18}$/,
     *      "p":/^[0-9]{6}$/,
     *      "m":/^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$/,
     *      "e":/^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
     *      "url":/^(\w+:\/\/)?\w+(\.\w+)+.*$/ }
     *  默认正则表达式
     * idCard,date 用","分隔表示规则累加；
     */
    private String datatype;

    public Valid appendDataType(DataType... param) {
        List<String> tmp = new ArrayList<>();
        for (DataType dt : param) {
            tmp.add(dt.toString());
        }
        String ret = StringUtils.join(tmp, ",");
        if (this.datatype == null || "".equals(this.datatype)) {
            this.datatype = ret;
        } else {
            this.datatype += ("," + ret);
        }
        return this;
    }

    public boolean isRequired() {
        if (datatype != null && datatype.contains("*")) {
            return true;
        }
        return this.required;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
    
    public void setLengthRange(int minLength, int maxLength) {
//        String value = String.format("*%d-%d", minLength, maxLength);
//        Pattern pattern = Pattern.compile(".*\\*[\\d]+-[\\d]+.*");
//        if(datatype == null || "".equals(datatype)) {
//            datatype = value;
//        } else {
//            Matcher matcher = pattern.matcher(datatype);
//            if(!matcher.matches()) {
//                datatype += "," + value;
//            }
//        }
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public void setRequired(boolean required) {
        this.required = required;
        if(required) {
            if(datatype == null || "".equals(datatype)) {
                datatype = "*";
            } else if(!datatype.contains("*")) {
                datatype += ",*";
            }
        }
    }

}
