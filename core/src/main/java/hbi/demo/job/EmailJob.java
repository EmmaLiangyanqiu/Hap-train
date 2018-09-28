package hbi.demo.job;

import com.hand.hap.job.AbstractJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * Created by Emma on 2018/9/1.
 */
@DisallowConcurrentExecution
public class EmailJob extends AbstractJob {
    @Override
    public void safeExecute(JobExecutionContext jobExecutionContext) throws Exception {
        String stuName=jobExecutionContext.getMergedJobDataMap().getString("stuName");
        String stuNo=jobExecutionContext.getMergedJobDataMap().getString("stuNo");
        System.out.println(stuName+":"+stuNo);
    }
}
