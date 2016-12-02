package com.pm.SpringConfig;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.SystemCommandTasklet;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Created by izabella on 23.11.16.
 */
public class BatchConfiguration {
    @Bean
    @StepScope
    public Tasklet fileSplittingTasklet(@Value("#{jobParameters['inputFile']}") String inputFile, @Value("#{jobParameters['stagingDirectory']}") String stagingDirectory) throws Exception {
        SystemCommandTasklet tasklet=new SystemCommandTasklet();
        tasklet.setCommand(String.format("split -a 5 -l 10000 %s %s",inputFile,stagingDirectory));
        tasklet.setTimeout(60000l);
        tasklet.setWorkingDirectory("/tmp/logs_temp");
        tasklet.afterPropertiesSet();
        return tasklet;
    }

}
