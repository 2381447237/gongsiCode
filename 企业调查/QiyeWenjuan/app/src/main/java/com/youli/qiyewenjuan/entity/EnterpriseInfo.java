package com.youli.qiyewenjuan.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 作者: zhengbin on 2017/12/21.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 企业信息
 */

public class EnterpriseInfo implements Serializable{

    private String  comName;//单位名称
    private String industry;//所属行业
    private String code;//组织机构代码
    private String staffNum;//企业员工总数
    private String seniorNum;//企业高管人数
    private String middleNum;//企业中层人数
    private List<AnswerInfo> Receiv;//企业首次提交答案

    public EnterpriseInfo(String comName, String industry, String code, String staffNum, String seniorNum, String middleNum) {
        this.comName = comName;
        this.industry = industry;
        this.code = code;
        this.staffNum = staffNum;
        this.seniorNum = seniorNum;
        this.middleNum = middleNum;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getMiddleNum() {
        return middleNum;
    }

    public void setMiddleNum(String middleNum) {
        this.middleNum = middleNum;
    }

    public String getSeniorNum() {
        return seniorNum;
    }

    public void setSeniorNum(String seniorNum) {
        this.seniorNum = seniorNum;
    }

    public String getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum;
    }



    @Override
    public String toString() {
        return "单位名称="+comName+"所属行业="+industry+"组织机构代码="+code+"企业员工总数="+staffNum+"企业高管人数="+seniorNum+"企业中层人数="
                +middleNum;
    }
}
