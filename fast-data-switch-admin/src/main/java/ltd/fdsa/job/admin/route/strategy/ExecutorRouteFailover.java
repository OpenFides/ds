package ltd.fdsa.job.admin.route.strategy;

import ltd.fdsa.job.admin.route.ExecutorRouter;
import ltd.fdsa.job.admin.scheduler.JobScheduler;
import ltd.fdsa.ds.core.job.executor.Executor;
import ltd.fdsa.ds.core.model.Result;
import ltd.fdsa.ds.core.job.model.TriggerParam;
import ltd.fdsa.ds.core.util.I18nUtil;


import java.util.List;

public class ExecutorRouteFailover extends ExecutorRouter {

    @Override
    public Result<String> route(TriggerParam triggerParam, List<String> addressList) {

        StringBuffer beatResultSB = new StringBuffer();
        for (String address : addressList) {
            // beat
            Result<String> beatResult = null;
            try {
                Executor executorBiz = JobScheduler.getExecutorClient(address);
                beatResult = executorBiz.beat();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                beatResult = Result.fail(500, "" + e);
            }
            beatResultSB
                    .append((beatResultSB.length() > 0) ? "<br><br>" : "")
                    .append(I18nUtil.getInstance("").getString("jobconf_beat") + "：")
                    .append("<br>address：")
                    .append(address)
                    .append("<br>code：")
                    .append(beatResult.getCode())
                    .append("<br>msg：")
                    .append(beatResult.getMessage());

            // beat success
            if (beatResult.getCode() == Result.success().getCode()) {

                beatResult.setMessage(beatResultSB.toString());
                beatResult.setData(address);
                return beatResult;
            }
        }
        return Result.fail(500, beatResultSB.toString());
    }
}
