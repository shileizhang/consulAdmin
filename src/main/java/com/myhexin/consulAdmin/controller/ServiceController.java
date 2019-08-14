package com.myhexin.consulAdmin.controller;

import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.agent.model.Service;
import com.ecwid.consul.v1.catalog.model.CatalogService;
import com.ecwid.consul.v1.health.model.HealthService;
import com.myhexin.consulAdmin.common.reslut.ResultDTO;
import com.myhexin.consulAdmin.common.reslut.ResultFactory;
import com.myhexin.consulAdmin.service.IConsulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 增，删，查 service
 * NewService  CatalogService HealthService
 * */
@RestController("/service")
public class ServiceController {
    @Autowired
    private IConsulService consulService;
    /**
     * 注册service,可以附带注册service的check  (/v1/agent/service/register)
     * */
    @RequestMapping(value = "/register",method = RequestMethod.PUT)
    public ResultDTO registerService(NewService newService){
        consulService.registerService(newService);
        return ResultFactory.success();
    }
    /**
     * 注销service  (/v1/agent/service/deregister)
     * */
    @RequestMapping(value = "/deregister",method = RequestMethod.DELETE)
    public ResultDTO deRegisterService(String serviceId){
        consulService.deRegisterService(serviceId);
        return ResultFactory.success();
    }

    /**
     * Service
     *
     * 获取agent所有的service:     (list service: /v1/agent/services)
     * */
    @RequestMapping(value = "/getAllAgentServices",method = RequestMethod.GET)
    public ResultDTO getAllAgentServices(){
        //Map<String, Service> getAgentServices()
        Map<String, Service> map=consulService.getAgentServices();
        return ResultFactory.success(map);
    }

    /**
     * CatalogService
     *
     * 返回根据serviceName找到的service:  CatalogService  ("/v1/catalog/service/" + serviceName)   !!! 包含Service的Node信息
    * */
    @RequestMapping(value = "/getCatalogService",method = RequestMethod.GET)
    public ResultDTO getCatalogService(String serviceName){
        List<CatalogService> list=consulService.getCatalogService(serviceName, null);
        return ResultFactory.success(list);
    }

    /**
     * HealthService
     *
     * 返回某个service的状态
     * @Param onlyPassing :
     *  1.为true表示返回该serviceName监测通过的service
     *  2.为false:查出serviceName的Service相关的check,node等信息
     *  包含node的 serfHealth
     * 返回健康监测通过的名为serviceName的service信息（包含check,node,service）(/v1/health/service/serviceName)
     * */
    @RequestMapping(value = "/findHealthyService",method = RequestMethod.GET)
    public ResultDTO findHealthyService(String serviceName,boolean onlyPassing){
        List<HealthService> list= consulService.findHealthyService(serviceName,onlyPassing);
        return ResultFactory.success(list);
    }
    @RequestMapping(value = "/allServiceInfo",method = RequestMethod.GET)
    public ResultDTO allServiceInfo(){
        Map<String,List<String>> map=consulService.getCatalogServices(null);
        Set<String> serviceNames=map.keySet();
        Iterator<String> iterator=serviceNames.iterator();
        String serviceName;
        Map<String,List<HealthService>> allServiceInfo=new HashMap<>();
        while(iterator.hasNext()){
            serviceName=iterator.next();
            List<HealthService> list=consulService.findHealthyService(serviceName,false);
            allServiceInfo.put(serviceName,list);
        }
        return ResultFactory.success(allServiceInfo);
    }

}
