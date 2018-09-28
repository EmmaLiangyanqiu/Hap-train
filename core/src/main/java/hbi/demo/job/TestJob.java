package hbi.demo.job;

import com.hand.hap.job.AbstractJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * Created by Emma on 2018/9/1.
 */
@DisallowConcurrentExecution
public class TestJob extends AbstractJob {

    @Override
    public void safeExecute(JobExecutionContext jobExecutionContext) throws Exception {
        String stuName=jobExecutionContext.getMergedJobDataMap().getString("stuName");
        System.out.println(stuName);
    }
}
