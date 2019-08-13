package com.myhexin.consulAdmin.service.impl;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.Check;
import com.ecwid.consul.v1.agent.model.NewCheck;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.catalog.model.CatalogNode;
import com.ecwid.consul.v1.catalog.model.CatalogRegistration;
import com.ecwid.consul.v1.catalog.model.CatalogService;
import com.ecwid.consul.v1.catalog.model.Node;
import com.ecwid.consul.v1.health.model.HealthService;
import com.ecwid.consul.v1.kv.model.GetValue;
import com.myhexin.consulAdmin.service.IConsulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * catalog,agent(node),service,check
 *
 * NewService ,NewService.Check
 * Service,CatalogService,HealthService
 * Node,CatalogNode
 * com.ecwid.consul.v1.health.model.Check  VS com.ecwid.consul.v1.agent.model.Check
 * */
@Service
public class EcwidConsulServiceImpl implements IConsulService {

    @Autowired
    private ConsulClient client = null;

    //注册服务  注意：可以封装check
    @Override
    public void registerService(NewService newService) {
            // register new service
/*        NewService newService = new NewService();
        newService.setId(serviceId);
        newService.setName(serviceName);
        newService.setTags(Arrays.asList("EU-West", "EU-East"));
        newService.setPort(8080);

        NewService.Check serviceCheck = new NewService.Check();
        serviceCheck.setHttp("http://127.0.0.1:8080/health");
        serviceCheck.setInterval("10s");
        //serviceCheck.setDeregisterCriticalServiceAfter("10s");

        newService.setCheck(serviceCheck);*/

        client.agentServiceRegister(newService);
    }
    //注销服务
    @Override
    public void deRegisterService(String serviceId) {
        client.agentServiceDeregister(serviceId);
    }
    /**
     * /v1/agent/services     VS   /v1/catalog/services(不好)
     * */
    //本节点的service
    @Override
    public Map<String, com.ecwid.consul.v1.agent.model.Service> getAgentServices(){
        Response<Map<String, com.ecwid.consul.v1.agent.model.Service>> mapResponse= client.getAgentServices();
        Map<String,com.ecwid.consul.v1.agent.model.Service> map=mapResponse.getValue();
        return map;
    }
    //按条件查出catalog中的service    "/v1/catalog/services"
    @Override
    public Map<String, List<String>> getCatalogServices(QueryParams queryParams){
        return client.getCatalogServices(queryParams).getValue();
    }

    /**
     * "/v1/health/service/" + serviceName  VS   "/v1/catalog/service/" + serviceName
     * */
    //onlyPassing=true  获取名为serviceName的健康的服务,否则就是查出  "/v1/health/service/" + serviceName
    @Override
    public List<HealthService> findHealthyService(String serviceName, boolean onlyPassing) {
        Response<List<HealthService>> healthyServices = client.getHealthServices(serviceName, onlyPassing, QueryParams.DEFAULT);
        return healthyServices.getValue();
    }
    //根据serviceName从目录catalog中查询出service，包含service的注册节点Node信息  "/v1/catalog/service/" + serviceName
    @Override
    public List<CatalogService> getCatalogService(String serviceName, QueryParams queryParams){
        return client.getCatalogService(serviceName,queryParams).getValue();
    }


   //注册节点，可用于注册service和check
    @Override
    public void catalogRegister(CatalogRegistration catalogRegistration){
        client.catalogRegister(catalogRegistration);
    }
    //条件查询consul的节点Node(不包含节点的service信息)   "/v1/catalog/nodes"
    @Override
    public List<Node> getCatalogNodes(QueryParams queryParams){
        Response<List<Node>> listResponse=client.getCatalogNodes(queryParams);
        return listResponse.getValue();
    }
    //获取catalog中的node(包含service信息)   /v1/catalog/node/nodeName
    @Override
    public CatalogNode getCatalogNode(String nodeName, QueryParams queryParams) {
        return client.getCatalogNode(nodeName,queryParams).getValue();
    }

    // /v1/health/checks/serviceName  list checks for service
    @Override
    public List<com.ecwid.consul.v1.health.model.Check> getHealthChecksForService(String serviceName, QueryParams queryParams){
        return client.getHealthChecksForService(serviceName,queryParams).getValue();
    }
    /**
     * /v1/health/node/nodeName   list checks for node     VS   /v1/agent/checks
     * */
    //  /v1/health/node/nodeName   list checks for node
    @Override
    public List<com.ecwid.consul.v1.health.model.Check> getHealthChecksForNode(String nodeName,QueryParams queryParams){
        //Response<List<com.ecwid.consul.v1.health.model.Check>> getHealthChecksForNode(String nodeName, QueryParams queryParams)
        return client.getHealthChecksForNode(nodeName,queryParams).getValue();
    }
    //返回某个agent的所有check   /v1/agent/checks
    @Override
    public Map<String, Check> getAgentChecks() {
        return client.getAgentChecks().getValue();
    }
    //为agent注册Check
    @Override
    public void agentCheckRegister(NewCheck newCheck) {
        client.agentCheckRegister(newCheck);
    }
    //注销check
    @Override
    public void agentCheckDeregister(String checkId) {
        client.agentCheckDeregister(checkId);
    }

    @Override
    public List<com.ecwid.consul.v1.health.model.Check> getHealthChecksState(com.ecwid.consul.v1.health.model.Check.CheckStatus checkStatus, QueryParams queryParams){
        return client.getHealthChecksState(checkStatus, queryParams).getValue();
    }




    @Override
    public boolean storeKV(String key, String value) {
        Response<Boolean> booleanResponse = client.setKVValue(key, value);
        return  booleanResponse.getValue();
    }
    @Override
    public String getKV(String key) {
        Response<GetValue> getValueResponse = client.getKVValue(key);
        //return getValueResponse.getValue().getValue();
        return getValueResponse.getValue().getDecodedValue();
    }
    @Override
    public void removeKV(String key){
        client.deleteKVValue(key);
    }

    @Override
    public List<String> findRaftPeers() {
        Response<List<String>> listResponse = client.getStatusPeers();
        return listResponse.getValue();
    }

    @Override
    public String findRaftLeader() {
        Response<String> stringResponse = client.getStatusLeader();
        return stringResponse.getValue();
    }
}

