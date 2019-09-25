/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.controlcenter.sba;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import de.codecentric.boot.admin.server.notify.LoggingNotifier;
import kcwiki.management.controlcenter.websocket.handler.SubscriberHandler;
import org.iharu.spring.SpringUtils;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// tag::customization-notifiers[]
public class CustomNotifier  extends AbstractEventNotifier {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingNotifier.class);

    public CustomNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent) {
                LOGGER.info("Instance {} ({}) is {}", instance.getRegistration().getName(), event.getInstance(),
                    ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus());
                SpringUtils.getBean(SubscriberHandler.class).sendNotifierMsg(
                        instance.getRegistration().getName(), 
                        event.getInstance().getValue(), 
                        ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus(),
                        null);
            } else {
                LOGGER.info("Instance {} ({}) {}", instance.getRegistration().getName(), event.getInstance(),
                    event.getType());
            }
        });
    }
}
// end::customization-notifiers[]
