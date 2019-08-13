package com.myhexin.consulAdmin.service;

import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.Check;
import com.ecwid.consul.v1.agent.model.NewCheck;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.agent.model.Service;
import com.ecwid.consul.v1.catalog.model.CatalogNode;
import com.ecwid.consul.v1.catalog.model.CatalogRegistration;
import com.ecwid.consul.v1.catalog.model.CatalogService;
import com.ecwid.consul.v1.catalog.model.Node;
import com.ecwid.consul.v1.health.model.HealthService;
import java.util.List;
import java.util.Map;

public interface IConsulService {

     void registerService(NewService newService) ;
     void deRegisterService(String serviceId);

     //Agent的Serivce
     Map<String, Service> getAgentServices(); //获取Agent的所有Service
     Map<String, List<String>> getCatalogServices(QueryParams queryParams);//不用

    //Catalog的Service（list node for service）
     List<CatalogService> getCatalogService(String serviceName, QueryParams queryParams);//获取目录中ServiceName对应的Node

     List<HealthService> findHealthyService(String serviceName, boolean onlyPassing);  //"/v1/health/service/" + serviceName

    List<Node> getCatalogNodes(QueryParams queryParams);//条件查询Node
    CatalogNode getCatalogNode(String nodeName, QueryParams queryParams);//根据Node名查询node的service  "/v1/catalog/node/" + nodeName
    /**使用该接口注册Node*/
     void catalogRegister(CatalogRegistration catalogRegistration);//注册[节点]，服务，check等


    // "/v1/health/checks/" + serviceName
     List<com.ecwid.consul.v1.health.model.Check> getHealthChecksForService(String serviceName, QueryParams queryParams);
    //  "/v1/health/node/nodeName"
     List<com.ecwid.consul.v1.health.model.Check> getHealthChecksForNode(String nodeName,QueryParams queryParams);
     //List checks for state
     List<com.ecwid.consul.v1.health.model.Check> getHealthChecksState(com.ecwid.consul.v1.health.model.Check.CheckStatus checkStatus, QueryParams queryParams);

    //Check对象包含很多重要的信息
    Map<String, Check> getAgentChecks();  //查询所有check   /v1/agent/checks
    void agentCheckRegister(NewCheck newCheck);//注册check到agent "/v1/agent/check/register"
    void agentCheckDeregister(String checkId);//注销check   "/v1/agent/check/deregister/"

    //增，删，查 K/V
     boolean storeKV(String key, String value); //存k/v
     String getKV(String key);  //取出k/v
     void removeKV(String key); //删除k/v

     //Find Raft peers.
     List<String> findRaftPeers();
     //Find Raft leader.
     String findRaftLeader();
}