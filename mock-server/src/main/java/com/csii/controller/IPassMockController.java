package com.csii.controller;

import com.csii.common.util.ResponseUtil;
import com.csii.vo.MQSourceNo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 模拟Ipass接口返回
 * @Author: chengyu
 * @Date: 2026/10/28 14:15
 */
@RestController
@RequestMapping("/jac_ipaas")
public class IPassMockController {


    @RequestMapping("/dehoop_out/ts_query_mqs_sourceno_from_dehoop")
    public void queryMQSourceNo(HttpServletRequest request, HttpServletResponse response) {
        String sourceNo = "2503140029";
        List<MQSourceNo> sourceNoList = new ArrayList<>();
        sourceNoList.add(new MQSourceNo(sourceNo));

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code", 200);
        resultMap.put("msg", "查询成功");
        resultMap.put("totalCount", 1);
        resultMap.put("data", sourceNoList);

        ResponseUtil.write(response, resultMap);
    }
}