package org.apache.nifi.reporting.prometheus.api;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import org.apache.nifi.controller.status.ProcessGroupStatus;

/**
 * Created by mjoerg on 12.09.17.
 */
public class PrometheusMetricsFactory {


    private static final CollectorRegistry REGISTRY = new CollectorRegistry();

    private static final Gauge AMOUNT_FLOWFILES_TOTAL = Gauge.build()
            .name("process_group_amount_flowfiles_total")
            .help("Total number of FlowFiles in ProcessGroup")
            .labelNames("status", "server", "application", "process_group")
            .register(REGISTRY);

    private static final Gauge AMOUNT_BYTES_TOTAL = Gauge.build()
            .name("process_group_amount_bytes_total")
            .help("Total number of Bytes in ProcessGroup")
            .labelNames("status", "server", "application", "process_group")
            .register(REGISTRY);

    private static final Gauge AMOUNT_THREADS_TOTAL = Gauge.build()
            .name("process_group_amount_threads_total")
            .help("Total amount of threads in ProcessGroup")
            .labelNames("status", "server", "application", "process_group")
            .register(REGISTRY);

    private static final Gauge SIZE_CONTENT_TOTAL = Gauge.build()
            .name("process_group_size_content_total")
            .help("Total size of content in ProcessGroup")
            .labelNames("status", "server", "application", "process_group")
            .register(REGISTRY);

    private static final Gauge AMOUNT_ITEMS = Gauge.build()
            .name("process_group_amount_items")
            .help("Total amount of items in ProcessGroup")
            .labelNames("status", "server", "application", "process_group")
            .register(REGISTRY);


    public static CollectorRegistry createNifiMetrics(ProcessGroupStatus status, String hostname, String applicationId) {
        String processGroupID = status.getId();
        AMOUNT_FLOWFILES_TOTAL.labels("sent", hostname, applicationId, processGroupID).set(status.getFlowFilesSent());
        AMOUNT_FLOWFILES_TOTAL.labels("queued", hostname, applicationId, processGroupID).set(status.getFlowFilesSent());
        AMOUNT_FLOWFILES_TOTAL.labels("received", hostname, applicationId, processGroupID).set(status.getFlowFilesReceived());

        AMOUNT_BYTES_TOTAL.labels("sent", hostname, applicationId, processGroupID).set(status.getBytesSent());
        AMOUNT_BYTES_TOTAL.labels("read", hostname, applicationId, processGroupID).set(status.getBytesRead());
        AMOUNT_BYTES_TOTAL.labels("written", hostname, applicationId, processGroupID).set(status.getBytesWritten());
        AMOUNT_BYTES_TOTAL.labels("received", hostname, applicationId, processGroupID).set(status.getBytesReceived());
        AMOUNT_BYTES_TOTAL.labels("transferred", hostname, applicationId, processGroupID).set(status.getBytesTransferred());

        SIZE_CONTENT_TOTAL.labels("output", hostname, applicationId, processGroupID).set(status.getOutputContentSize());
        SIZE_CONTENT_TOTAL.labels("input", hostname, applicationId, processGroupID).set(status.getInputContentSize());
        SIZE_CONTENT_TOTAL.labels("queued", hostname, applicationId, processGroupID).set(status.getQueuedContentSize());

        AMOUNT_ITEMS.labels("output", hostname, applicationId, processGroupID).set(status.getOutputCount());
        AMOUNT_ITEMS.labels("input", hostname, applicationId, processGroupID).set(status.getInputCount());
        AMOUNT_ITEMS.labels("queued", hostname, applicationId, processGroupID).set(status.getQueuedCount());

        AMOUNT_THREADS_TOTAL.labels("nano", hostname, applicationId, processGroupID).set(status.getActiveThreadCount());

        return REGISTRY;
    }
}
