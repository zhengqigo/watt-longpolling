package cn.fuelteam.watt.longpolling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import cn.fuelteam.watt.longpolling.longpolling.LongPollingEventSimulator;
import cn.fuelteam.watt.longpolling.longpolling.LongPollingSession;

@Controller
public class UiController {

    private static final Logger logger = LoggerFactory.getLogger(UiController.class);

    @Autowired
    LongPollingEventSimulator simulator;

    @RequestMapping("/register/{dossierId}")
    @ResponseBody
    public DeferredResult<String> registerClient(@PathVariable("dossierId") final long dossierId) {
        logger.info("Registering client for dossier id: " + dossierId);
        final DeferredResult<String> deferredResult = new DeferredResult<>();
        // Add paused http requests to event queue
        simulator.getPollingQueue().add(new LongPollingSession(dossierId, deferredResult));
        return deferredResult;
    }

    @RequestMapping("/simulate/{dossierId}")
    @ResponseBody
    public String simulateEvent(@PathVariable("dossierId") final long dossierId) {
        logger.info("Simulating event for dossier id: " + dossierId);
        simulator.simulateIncomingNotification(dossierId);
        return "Simulating event for dossier Id: " + dossierId;
    }
}