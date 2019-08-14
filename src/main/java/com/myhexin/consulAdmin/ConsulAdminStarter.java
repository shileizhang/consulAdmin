package com.myhexin.consulAdmin;

import com.myhexin.consulAdmin.service.IConsulService;
import com.myhexin.consulAdmin.service.impl.EcwidConsulServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ConsulAdminStarter {
    public static void main(String[] args) {
        ApplicationContext context=SpringApplication.run(ConsulAdminStarter.class,args);
        IConsulService ecwidConsulService= ((EcwidConsulServiceImpl) context.getBean("ecwidConsulServiceImpl"));
        //测试
        //ecwidConsulService.storeKV("123","123345");
        //ecwidConsulService.removeKV("123");
        //返回某个节点的所有service(一般不用这个)  /v1/agent/services
        System.out.println(ecwidConsulService.getAgentServices());
        //    /v1/catalog/services
        System.out.println("catalogServices");
        System.out.println(ecwidConsulService.getCatalogServices(null));
        //返回所有check都通过的service  /v1/health/service/thrift-calculator-rest-server onlyPassing=true
        System.out.println(ecwidConsulService.findHealthyService("thrift-calculator-rpc-server", true));
        //获取consul所有节点  "/v1/catalog/nodes"
        System.out.println(ecwidConsulService.getCatalogNodes(null));
        //查询某个节点的所有node-service  /v1/catalog/node/nodeName(list service for node)
        System.out.println(ecwidConsulService.getCatalogNode("consul-dev",null));
        System.out.println("about check\n");
        //获取agent的check  /v1/agent/checks
        System.out.println(ecwidConsulService.getAgentChecks());
        //  "/v1/health/node/nodeName"
        System.out.println(ecwidConsulService.getHealthChecksForNode("consul-dev", null));

        // /v1/catalog/service/:service  返回catalogService 包含node和service

        // /v1/health/service/:service   返回healthService  包含node,service,check
        System.out.println(ecwidConsulService.findHealthyService("thrift-calculator-rpc-server", false));
    }
}
