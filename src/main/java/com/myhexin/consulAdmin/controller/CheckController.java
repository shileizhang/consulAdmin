package com.myhexin.consulAdmin.controller;

import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.agent.model.Check;
import com.ecwid.consul.v1.agent.model.NewCheck;
import com.myhexin.consulAdmin.common.reslut.ResultDTO;
import com.myhexin.consulAdmin.common.reslut.ResultFactory;
import com.myhexin.consulAdmin.service.IConsulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
/**
 * NewCheck,Check
 * */
@RestController("/check")
public class CheckController {
    @Autowired
    private IConsulService consulService;
    /**
     * 注册check到agent: NewCheck  (register chcek: /v1/agent/check/register)
     * */
    @RequestMapping(value = "/checkRegister",method = RequestMethod.PUT)
    public ResultDTO agentCheckRegister(NewCheck newCheck){
        consulService.agentCheckRegister(newCheck);
        return ResultFactory.success();
    }
    /**
     * 注销check :  (/v1/agent/check/deregister/checkId)
     * */
    @RequestMapping(value = "/checkDeregister",method = RequestMethod.DELETE)
    public ResultDTO agentCheckDeregister(String checkId){
        consulService.agentCheckDeregister(checkId);
        return ResultFactory.success();
    }

    /**
     * 返回【本地agent】注册的所有检查
     * 返回节点的check信息：Map<String, Check> (list checks: v1/agent/checks)
     * */
    @RequestMapping(value = "/listChecks",method = RequestMethod.GET)
    public ResultDTO listChecks(){
        Map<String, Check> map=consulService.getAgentChecks();
        return ResultFactory.success(map);
    }
    /**
     * 返回node的检查
     * 返回结果包含node serfHealth    (List checks for Node: /v1/health/node/nodeName)
     * */
    @RequestMapping(value = "/getChecksForNode",method = RequestMethod.GET)
    public ResultDTO getChecksForNode(String nodeName, QueryParams queryParams){
        List<com.ecwid.consul.v1.health.model.Check> list= consulService.getHealthChecksForNode(nodeName,queryParams);
        return ResultFactory.success(list);
    }
    /**
     * 返回service的检查
     *  (List checks for service: /v1/health/check/serviceName)
     * */
    @RequestMapping(value = "/getChecksForService",method = RequestMethod.GET)
    public ResultDTO getChecksForService(String serviceName,QueryParams queryParams){
        List<com.ecwid.consul.v1.health.model.Check> list=consulService.getHealthChecksForService(serviceName,queryParams);
        return ResultFactory.success(list);
    }
    /**
     *  按CheckState查出check
     *  返回某个checkstate的check :(4中checkState)Check.CheckStatus.CRITICAL, Check.CheckStatus.WARNING, Check.CheckStatus.PASSING, Check.CheckStatus.UNKNOWN
     * */
    @RequestMapping(value = "/getChecksForState",method = RequestMethod.GET)
    public ResultDTO getHealthChecksState(com.ecwid.consul.v1.health.model.Check.CheckStatus checkStatus, QueryParams queryParams){
        List<com.ecwid.consul.v1.health.model.Check> list=consulService.getHealthChecksState(checkStatus, queryParams);
        return ResultFactory.success(list);
    }

}
