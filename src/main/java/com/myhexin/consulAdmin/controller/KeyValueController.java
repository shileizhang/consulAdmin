package com.myhexin.consulAdmin.controller;

import com.myhexin.consulAdmin.common.reslut.ResultDTO;
import com.myhexin.consulAdmin.common.reslut.ResultFactory;
import com.myhexin.consulAdmin.service.IConsulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/kv")
public class KeyValueController {
    @Autowired
    private IConsulService consulService;
    /**
     * 增kv
     * */
    @RequestMapping(value = "/store",method = RequestMethod.PUT)
    public ResultDTO storeKV(String key,String value){
        Boolean flag=consulService.storeKV(key,value);
        if (flag) {
            return ResultFactory.success();
        }else{
            return ResultFactory.fail();
        }
    }
    /**
     * 删kv
     * */
    @RequestMapping(value = "/removekv",method = RequestMethod.DELETE)
    public ResultDTO removeKV(String key){
        consulService.removeKV(key);
        return  ResultFactory.success();
    }
    /**
     * 查kv
     * */
    @RequestMapping(value = "/getkv",method = RequestMethod.GET)
    public ResultDTO getKV(String key){
        String value=consulService.getKV(key);
        return ResultFactory.success(value);
    }


}
