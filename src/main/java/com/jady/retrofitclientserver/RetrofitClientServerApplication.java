package com.jady.retrofitclientserver;

import com.codahale.metrics.jetty9.InstrumentedQueuedThreadPool;
import org.eclipse.jetty.server.LowResourceMonitor;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class RetrofitClientServerApplication {

    @Autowired
    private JettyThreadPoolSettings jettyThreadPoolSettings;

    @Autowired
    private JettyLowResourceMonitorSettings jettyLowResourceMonitorSettings;

    @Autowired
    private JettyServerPortSettings jettyServerPortSettings;

    private  static int port = 0;

    public static void main(String[] args) {

        try {
            port = Integer.parseInt(args[0])>1000 ? Integer.parseInt(args[0]) : port;
//            System.getProperties().put( "server.port", port );
        } catch (Exception ignored) {

        }
        SpringApplication.run(RetrofitClientServerApplication.class, args);
    }

//    @Bean
//    public JettyEmbeddedServletContainerFactory jettyEmbeddedServletContainerFactory() {
//        final JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory(9090);
//        factory.addServerCustomizers((Server server) -> {
//            final QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
//            threadPool.setMinThreads(8);
//            threadPool.setMaxThreads(200);
//            threadPool.setIdleTimeout(60000);
//            threadPool.setName(Server.class.getName() + ".threadPool");
//        });
//        return factory;
//    }

    @Bean
    public JettyEmbeddedServletContainerFactory jettyEmbeddedServletContainerFactory( ) {

        if(port == 0){
            port = jettyServerPortSettings.getPort();
        }
        final JettyEmbeddedServletContainerFactory factory =  new JettyEmbeddedServletContainerFactory(port);
        factory.addServerCustomizers
                ( customizers ->   {

            final QueuedThreadPool threadPool = new InstrumentedQueuedThreadPool(MetricHelper.getMetric());
            threadPool.setMaxThreads(jettyThreadPoolSettings.getMaxThreads());
            threadPool.setMinThreads(jettyThreadPoolSettings.getMinThreads());
            threadPool.setIdleTimeout(jettyThreadPoolSettings.getIdleTimeout());
            threadPool.setDetailedDump(jettyThreadPoolSettings.isDetailedDump());


            Server server = new Server(threadPool);
            LowResourceMonitor lowResourcesMonitor = new LowResourceMonitor(server);
            lowResourcesMonitor.setPeriod(jettyLowResourceMonitorSettings.getPeriod());
            lowResourcesMonitor.setLowResourcesIdleTimeout(jettyLowResourceMonitorSettings.getIdleTimeout());
            lowResourcesMonitor.setMonitorThreads(jettyLowResourceMonitorSettings.isMonitorThreads());
            lowResourcesMonitor.setMaxConnections(jettyLowResourceMonitorSettings.getMaxConnections());
            lowResourcesMonitor.setMaxMemory(jettyLowResourceMonitorSettings.getMaxMemory());
            lowResourcesMonitor.setMaxLowResourcesTime(jettyLowResourceMonitorSettings.getMaxLowResourcesTime());


            server.addBean(lowResourcesMonitor);

        });
        return factory;
    }



}
