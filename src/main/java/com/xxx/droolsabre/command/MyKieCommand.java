package com.aixuexi.droolsabre.command;

import org.drools.core.command.impl.ExecutableCommand;
import org.drools.core.command.runtime.BatchExecutionCommandImpl;
import org.drools.core.command.runtime.rule.GetObjectsCommand;
import org.drools.core.command.runtime.rule.InsertObjectCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * my kie-command wrap .
 * @author wang'ao
 * @date 2018-04-23
 */
public class MyKieCommand {
    private List<ExecutableCommand> commands = new ArrayList();

    private static final String DAFAULT_KIE_SESSION = "defaultKieSession" ;

    /**
     * new class .
     * @return
     */
    public static MyKieCommand getNewInstance() {
        return new MyKieCommand();
    }

    public MyKieCommand insert(Object o) {
        return insert(o,null);
    }

    public MyKieCommand insert(Object o, String outIdentifier) {
        this.commands.add(new InsertObjectCommand(o, outIdentifier));
        return this;
    }

    public MyKieCommand getObjects(String outIdentifier) {
        this.commands.add(new GetObjectsCommand(null, outIdentifier));
        return this;
    }

    public MyKieCommand getObjects() {
        return getObjects(null);
    }

    /**
     * when use config 'defaultKieSession' or none any config .
     * @return
     */
    public BatchExecutionCommandImpl getBatchCommand() {
        return getBatchCommand(DAFAULT_KIE_SESSION);
    }

    /**
     * for wb-project 'sessionId' ,config in 'project-prop' .
     * @param sessionId
     * @return
     */
    public BatchExecutionCommandImpl getBatchCommand(String sessionId) {
        return new BatchExecutionCommandImpl(commands, sessionId);
    }

}
