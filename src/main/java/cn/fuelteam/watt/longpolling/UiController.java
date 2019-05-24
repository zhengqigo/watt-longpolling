package cn.fuelteam.watt.longpolling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import cn.fuelteam.watt.longpolling.common.EventSimulator;
import cn.fuelteam.watt.longpolling.common.LongPollingSession;

@Controller
public class UiController {

    private static final Logger logger = LoggerFactory.getLogger(UiController.class);

    @Autowired
    EventSimulator simulator;

    @RequestMapping("/register/{userId}")
    @ResponseBody
    public DeferredResult<String> registerClient(@PathVariable("userId") final long userId) {
        logger.info("Registering for userId: " + userId);
        final DeferredResult<String> deferredResult = new DeferredResult<>();
        // Add paused http requests to event queue
        simulator.getPollingQueue().add(new LongPollingSession(userId, deferredResult));
        return deferredResult;
    }

    @RequestMapping("/simulate/{userId}")
    @ResponseBody
    public String simulateEvent(@PathVariable("userId") final long userId) {
        logger.info("Simulating event for userId: " + userId);
        simulator.simulateIncomingNotification(userId);
        return "Simulating event for userId: " + userId;
    }
}