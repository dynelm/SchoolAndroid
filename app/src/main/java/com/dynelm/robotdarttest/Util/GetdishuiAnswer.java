package com.dynelm.robotdarttest.Util;

import com.dynelm.robotdarttest.InstanceExe.Answer_Instance;

/**
 * Created by Administrator on 2018/9/5 0005.
 */

public class GetdishuiAnswer {

    public volatile static GetdishuiAnswer getdishuiAnswer = null;
    public   String getAnswer(String s){
        if (s.contains("个人所得税")){
            if (s.contains("办理")||s.contains("资讯")){
                return "请去往一号窗口办理个人所得税的相关内容";
            }
            if (s.contains("税率")){
                return Answer_Instance.gerensuodeshuishuilv;
            }
            if (s.contains("范围")){
                return Answer_Instance.gerensuideshuizhengshuifanwei;
            }
            return "个人所得税是对个人取得我国税法规定征税的各项应税所得征收的一种税,如需详细了解，请去一号窗口，或咨询工作人员";

        }
        if (s.contains("企业所得税")){
            if (s.contains("办理")||s.contains("资讯")){
                return "请去往二号窗口办理企业所得税的相关内容";
            }
            if (s.contains("税率")){
                return Answer_Instance.Qiyesuideshuishuilv;
            }
            return "企业所得税是指对中华人民共和国境内的企业（居民企业及非居民企业）和其他取得收入的组织以其生产经营所得为课税对象所征收的一种所得税,如需详细了解，请去二号窗口，或咨询工作人员";
        }
        if (s.contains("印花税")){
            if (s.contains("办理")||s.contains("资讯")){
                return "请去往三号窗口办理企业所得税的相关内容";
            }
            return "印花税是对经济活动和经济交往中书立、领受具有法律效力的凭证的行为所征收的一种税。因采用在应税凭证上粘贴印花税票作为完税的标志而得名,如需详细了解，请去三号号窗口，或咨询工作人员";
        }
        if (s.contains("车船税")){
            if (s.contains("办理")||s.contains("资讯")){
                return "请去往四号号窗口办理企业所得税的相关内容";
            }
            return "车船税是指对在我国境内应依法到公安、交通、农业、渔业、军事等管理部门办理登记的车辆、船舶，根据其种类，按照规定的计税依据和年税额标准计算征收的一种财产税,请去四号号窗口，或咨询工作人员";
        }
        return "请资讯服务人员";

    }

    public  boolean haveketwords(String s,String target){
        char[] array_s = s.toCharArray();
        char[] array_target = target.toCharArray();
        if (array_s.length<3){
            return false;
        }
        int result = 0;
        for (int i =0;i<array_target.length;i++){
            for (int t = 0;t<array_s.length;t++){
                if (array_target[i] == array_s[t]){
                    result++;
                    break;
                }
            }
        }
        if (result>target.length()/2){
            return true;
        }
        else return false;

    }

    public static GetdishuiAnswer getInstance(){
        if (getdishuiAnswer==null){
            synchronized (GetdishuiAnswer.class){
                if (getdishuiAnswer==null){
                    getdishuiAnswer = new GetdishuiAnswer();
                }
            }
        }
        return getdishuiAnswer;
    }
}
