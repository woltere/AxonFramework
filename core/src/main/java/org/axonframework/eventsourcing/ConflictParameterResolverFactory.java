package org.axonframework.eventsourcing;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.common.Priority;
import org.axonframework.common.annotation.ParameterResolver;
import org.axonframework.common.annotation.ParameterResolverFactory;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.unitofwork.CurrentUnitOfWork;

import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;

/**
 * Creates a ConflictParameterResolver instance. ConflictParameterResolver is used to provide values to command
 * handlers. Conflict instances are provided to parameters of type Conflict.
 *
 * @author Joris van der Kallen
 * @since 3.0
 */
@Priority(Priority.HIGH)
public class ConflictParameterResolverFactory implements ParameterResolverFactory {

    private final ConflictParameterResolver instance = new ConflictParameterResolver();

    @Override
    public ParameterResolver createInstance(Executable executable, Parameter[] parameters, int parameterIndex) {
        if (parameters[parameterIndex].getType().isAssignableFrom(Conflict.class)) {
            return instance;
        }
        return null;
    }

    private class ConflictParameterResolver implements ParameterResolver<Conflict> {

        @Override
        public Conflict resolveParameterValue(Message message) {
            return (Conflict) CurrentUnitOfWork.get().resources().get("conflicts");
        }

        @Override
        public boolean matches(Message message) {
            return message.getClass().isAssignableFrom(CommandMessage.class);
        }
    }
}
