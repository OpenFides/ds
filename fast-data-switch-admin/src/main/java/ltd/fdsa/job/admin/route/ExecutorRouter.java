package ltd.fdsa.job.admin.route;

import ltd.fdsa.ds.core.model.Result;
import ltd.fdsa.ds.core.job.model.TriggerParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class ExecutorRouter {
    protected static Logger logger = LoggerFactory.getLogger(ExecutorRouter.class);

    /**
     * route address
     *
     * @param addressList
     * @return Result.content=address
     */
    public abstract Result<String> route(TriggerParam triggerParam, List<String> addressList);
}
