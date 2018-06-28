package com.aixuexi.droolsabre;

import com.aixuexi.droolsabre.conf.Configuration;
import org.drools.core.command.runtime.BatchExecutionCommandImpl;
import org.kie.api.runtime.ExecutionResults;
import org.kie.internal.runtime.helper.BatchExecutionHelper;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;

import java.util.Set;

public class MyKieServerClient {
    private Configuration serverConfigure;
    private Set<Class<?>> classes ;


    private RuleServicesClient ruleClient ;

    private MyKieServerClient(){}
    public void init() {
        KieServicesConfiguration configuration = KieServicesFactory.newRestConfiguration( serverConfigure.getServerUrl(),serverConfigure.getUserName(), serverConfigure.getPassword());
        configuration.setMarshallingFormat(MarshallingFormat.JAXB);
        configuration.addExtraClasses(classes);

        this.ruleClient = KieServicesFactory.newKieServicesClient(configuration).getServicesClient(RuleServicesClient.class);
    }

    public ExecutionResults exeBatchCommand(String containerId, BatchExecutionCommandImpl batchCommand) {
//      KieServices.Factory.get().getCommands().newBatchExecution();

        ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(containerId, batchCommand);

        System.out.println("---------------------");
        System.out.println("request body -- >>>>>>>>>>>>>>: \n" + BatchExecutionHelper.newJSonMarshaller().toXML(batchCommand));
        System.out.println("---------------------");
        System.out.println("response -- >>>>>>>>>>>>>>: \n" + BatchExecutionHelper.newJSonMarshaller().toXML(response));
        System.out.println("---------------------");

        return response.getResult();
    }

    public Configuration getServerConfigure() {
        return serverConfigure;
    }

    public void setServerConfigure(Configuration serverConfigure) {
        this.serverConfigure = serverConfigure;
    }

    public Set<Class<?>> getClasses() {
        return classes;
    }

    public void setClasses(Set<Class<?>> classes) {
        this.classes = classes;
    }
}
