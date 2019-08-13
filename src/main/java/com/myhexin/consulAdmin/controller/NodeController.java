package com.myhexin.consulAdmin.controller;

import com.ecwid.consul.v1.agent.model.Check;
import com.ecwid.consul.v1.catalog.model.CatalogNode;
import com.ecwid.consul.v1.catalog.model.Node;
import com.myhexin.consulAdmin.common.reslut.ResultDTO;
import com.myhexin.consulAdmin.common.reslut.ResultFactory;
import com.myhexin.consulAdmin.service.IConsulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "/node" )
public class NodeController {
    @Autowired
    private IConsulService consulService;

    //TODO node的注册和注销

    /**
     * 返回consul中的节点：List<Node>      (list node:  /v1/catalog/nodes)
     * */
    @RequestMapping(value = "/nodes",method = RequestMethod.GET)
    public ResultDTO getCatalogNodes(){
        List<Node> list= consulService.getCatalogNodes(null);
        return ResultFactory.success(list);
    }
    /**
     * 返回节点的service信息：CatalogNode   (list service for node: /v1/catalog/node/nodeName)
     * */
    @RequestMapping(value = "/servicesForNode",method = RequestMethod.GET)
    public ResultDTO listServiceForNode(String nodeName){
        CatalogNode catalogNode=consulService.getCatalogNode(nodeName,null);
        return ResultFactory.success(catalogNode);
    }


}
